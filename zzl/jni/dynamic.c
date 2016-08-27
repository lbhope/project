#include "jni.h"
#include "android/log.h"
#include <string.h>
#include <stdlib.h>
#include <stdio.h>
#define LOG_TAG "System.out.c"
#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG, LOG_TAG, __VA_ARGS__)
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO, LOG_TAG, __VA_ARGS__)

void register_method_dynamic(JNIEnv * env, jobject jobj){
	LOGI("dynamic 方法被调用了");
}

void register_method_init(JNIEnv * env, jobject jobj){
	LOGI("init 方法被调用了");
}


jint JNI_OnLoad(JavaVM* vm, void* reserved) {
	LOGI("JNI_OnLoad method start .... ");
	JNIEnv* env = NULL;
	jint result = -1;
	if ((*vm)->GetEnv(vm, (void**) &env, JNI_VERSION_1_4) != JNI_OK) {
		LOGI("ERROR: GetEnv failed\n");
		goto bail;
	}

	LOGI("JNI_OnLoad 开始动态注册 .... ");
	//小于0注册失败
	if (register_android_dynamic(env) < 0) {
		LOGI("ERROR: Boa Server native registration failed");
		goto bail;
	}
	LOGI("JNI_OnLoad 动态注册成功 .... ");

	result = JNI_VERSION_1_4;
	bail:
	return result;
}

//typedef struct {
//		char *name; //函数名称
//		char *signature; //函数签名
//		void *fnPtr; 函数指针
//	} JNINativeMethod;


static JNINativeMethod gMethods[] = {
		{
				"dynamic",
				"()V",
				(void *) register_method_dynamic
		},
		{
				"init",
				"()V",
				(void *) register_method_init
		},

};

// This function only registers the native methods, and is called from
// JNI_OnLoad in android_media_MediaPlayer.cpp
int register_android_dynamic(JNIEnv *env) {
	jclass clazz;
	static const char* const className = "com/example/zzl/jni/DynamicLoad";
	clazz = (*env)->FindClass(env, className);
	if (clazz == NULL) {
		LOGI("Can't find class %s\n", className);
		return -1;
	}

	//注册所有方法
	if ((*env)->RegisterNatives(env, clazz, gMethods,sizeof(gMethods) / sizeof(gMethods[0])) != JNI_OK){
		LOGI("Failed registering methods for %s\n", className);
		return -1;
	}
	return 0;
}




