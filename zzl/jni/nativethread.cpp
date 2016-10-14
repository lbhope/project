#include "com_example_zzl_jni_NativeThread.h"
#include <pthread.h>

#include <stdio.h>
#include <unistd.h>

//原生worker线程参数
struct NativeWorkerArgs {
	jint id;
	jint iterations;
};
//java虚拟机接口指针
static JavaVM* gVm = NULL;
static jobject gObj = NULL;

//线程同步 互斥锁
static pthread_mutex_t mutex;

//onCallbackMsg方法id被缓存
static jmethodID callbackMsgId = NULL;

void throwException(JNIEnv* env, char* msg);
/*
 * Class:     com_example_zzl_jni_NativeThread
 * Method:    nativeInit
 * Signature: ()V
 */JNIEXPORT void JNICALL Java_com_example_zzl_jni_NativeThread_nativeInit(
		JNIEnv * env, jobject obj) {
	if (NULL == gObj) {
		//为对象创建一个新的全局引用
		gObj = env->NewGlobalRef(obj);
		if (NULL == gObj) {
			goto exit;
		}
	}

	//初始化互斥锁
	if (0 != pthread_mutex_init(&mutex, NULL)) {
		throwException(env, "unable to init pthread_mutex_init");
		goto exit;
	}

	//没有被缓存
	if (NULL == callbackMsgId) {
		//从对象中获取类
		jclass clazz = env->GetObjectClass(obj);
		callbackMsgId = env->GetMethodID(clazz, "onCallbackMsg",
				"(Ljava/lang/String;)V");
		//如果没有找到
		if (NULL == callbackMsgId) {
			throwException(env, "unable to find method onCallbackMsg");
		}
	}

	exit: return;
}

JNIEXPORT void JNICALL Java_com_example_zzl_jni_NativeThread_nativeFree(
		JNIEnv * env, jobject obj) {
	if (NULL != gObj) {
		//为对象创建一个新的全局引用
		env->DeleteGlobalRef(gObj);
		gObj = NULL;
	}

	//销毁互斥锁
	if (0 != pthread_mutex_destroy(&mutex)) {
		throwException(env, "unable to destroy pthread_mutex_destroy");
	}
}

/*
 * Signature: (II)V
 */JNIEXPORT void JNICALL Java_com_example_zzl_jni_NativeThread_nativeWorker(
		JNIEnv * env, jobject obj, jint id, jint iterations) {
	jint i = 0;

	for (; i < iterations; i++) {
		char msg[50];
		sprintf(msg, "工作线程id:%d,执行第 %d 次", id, i);
		jstring msgStr = env->NewStringUTF(msg);
		env->CallVoidMethod(obj, callbackMsgId, msgStr);
		if (NULL != env->ExceptionOccurred()) {
			break;
		}
		sleep(1);
	}
}

/*
 * POSIX线程
 * 它不是java平台一部分，虚拟机不能识别POSIX，因此要先将它附加到虚拟机上。
 */

jint JNI_OnLoad(JavaVM* vm, void* reserved) {
	//缓存java虚拟机接口指针
	gVm = vm;
	return JNI_VERSION_1_4;
}

/**
 * native附加线程
 */
static void* nativeWorkerThread(void* args) {
	JNIEnv* env = NULL;
	//将当前线程附加到java虚拟机上，并且获得JNIEnv接口指针
	if (0 == gVm->AttachCurrentThread(&env, NULL)) {

		NativeWorkerArgs* nativeWorkerArgs = (NativeWorkerArgs*) args;

		if (0 != pthread_mutex_lock(&mutex)) {
			throwException(env, "unable to lock pthread_mutex_lock");
			goto exit;
		}

		//在线程上下文中运行原生worker
		jint i = 0;
		jint iterations = nativeWorkerArgs->iterations;
		jint id = nativeWorkerArgs->id;

		for (; i < iterations; i++) {
			char msg[50];
			sprintf(msg, "ndk工作线程id:%d,执行第 %d 次", id, i);
			jstring msgStr = env->NewStringUTF(msg);
			env->CallVoidMethod(gObj, callbackMsgId, msgStr);
			if (NULL != env->ExceptionOccurred()) {
				break;
			}
			sleep(1);
		}

		if (0 != pthread_mutex_unlock(&mutex)) {
			throwException(env, "unable to unlock pthread_mutex_unlock");
			goto exit;
		}

		//释放元素的worker线程参数
		delete nativeWorkerArgs;

		//从java虚拟机中分离出当前线程
		gVm->DetachCurrentThread();
	}
	exit: return (void*) 1;
}

/*
 * Class:     com_example_zzl_jni_NativeThread
 * Method:    posixThreads
 * Signature: (II)V
 */JNIEXPORT void JNICALL Java_com_example_zzl_jni_NativeThread_posixThreads(
		JNIEnv * env, jobject obj, jint threads, jint iterations) {
	jint i = 0;

	for (; i < threads; i++) {
		//封装参数
		NativeWorkerArgs* workerArgs = new NativeWorkerArgs();
		workerArgs->id = i;
		workerArgs->iterations = iterations;

		//线程句柄
		pthread_t thread;
		int result = -1;
		//创建新的线程
		result = pthread_create(&thread, NULL, nativeWorkerThread,
				(void*) workerArgs);
//		pthread_join() 可以使一个函数等待线程的终止，可挂起UI线程
		//线程创建失败
		if (result != 0) {
			jclass exception = env->FindClass("java/lang/RuntimeException");
			env->ThrowNew(exception, "unable to create thread");
		}
	}

}
void throwException(JNIEnv* env, char* msg) {
	jclass exception = env->FindClass("java/lang/RuntimeException");
	env->ThrowNew(exception, msg);
}
