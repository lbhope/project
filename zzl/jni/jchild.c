#include "com_example_zzl_jni_JChild.h"
#include "android/log.h"
#include <string.h>
#include <stdlib.h>
#include <stdio.h>
#define LOG_TAG "System.out.c"
#define LOGD(...) __android_log_print(ANDROID_LOG_DEBUG, LOG_TAG, __VA_ARGS__)
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO, LOG_TAG, __VA_ARGS__)


//JNIEnv 结构体指针,env二级指针
JNIEXPORT jstring JNICALL Java_com_example_zzl_jni_JChild_getNativeString
(JNIEnv * env, jobject jobj){
	return (*env)->NewStringUTF(env, "C String");
}



/*
* Method:    getStringFromC
* Signature: ()Ljava/lang/String;
*/
JNIEXPORT jstring JNICALL Java_com_example_zzl_jni_JChild_getStringFromC
(JNIEnv * env, jobject jobj){
	return (*env)->NewStringUTF(env, "C String2");
}

/*
* Method:    getString2FromC
* Signature: (I)Ljava/lang/String;
*/
JNIEXPORT jstring JNICALL Java_com_example_zzl_jni_JChild_getString2FromC
(JNIEnv * env, jobject jobj, jint num){
	return (*env)->NewStringUTF(env, "C String2");
}

/*
	1、通过GetObjectClass获取jclass，或者FindClass
	2、获取字段、方法的id（GetXXXID）,签名可以通过javap。
	3、根据id获取字段的值，方法 （GetObjectXXX）
*/
/*
* Method:    accessField
* Signature: ()Ljava/lang/String;
*/
JNIEXPORT jstring JNICALL Java_com_example_zzl_jni_JChild_accessField
(JNIEnv *env, jobject jobj){
	//jobj是child对象，JChild.class
	jclass cls = (*env)->GetObjectClass(env, jobj);
	jfieldID fieldid = (*env)->GetFieldID(env, cls, "key", "Ljava/lang/String;");
	jstring fieldval = (*env)->GetObjectField(env, jobj, fieldid);
	printf("jstr:%#x\n", &fieldval);

	//jstring -> c字符串
	const char* cstr = (*env)->GetStringUTFChars(env, fieldval, NULL);
	if (cstr == NULL){ //可能因为内存太少而失败
		return NULL;
	}
	char  str[] = "hello ";
	strcat(str, cstr);
	(*env)->ReleaseStringUTFChars(env, fieldval, cstr);
	//c字符串 ->jstring
	jstring new_jstr = (*env)->NewStringUTF(env, str);

	//修改key
	//Set<Type>Field
	(*env)->SetObjectField(env, jobj, fieldid, new_jstr);
	printf("new_jstr:%#x\n", &new_jstr);
	return new_jstr;
}

/*
* Method:    accessStaticField
* Signature: ()V
*/
JNIEXPORT void JNICALL Java_com_example_zzl_jni_JChild_accessStaticField
(JNIEnv *env, jobject jobj){
	LOGI("accessStaticField .... ");
	jclass cls = (*env)->GetObjectClass(env, jobj);
	//获取静态字段的值
	jfieldID fieldid = (*env)->GetStaticFieldID(env, cls, "count", "I");
	jint fieldval = (*env)->GetStaticIntField(env, cls, fieldid);
	fieldval = fieldval + 2;
	//设置静态字段的值
	(*env)->SetStaticIntField(env,cls,fieldid,fieldval);

}

/*
* Signature: ()V
*/
JNIEXPORT void JNICALL Java_com_example_zzl_jni_JChild_accessMethod
(JNIEnv *env, jobject jobj){
	LOGI("accessMethod .... ");
	//访问java方法
	jclass cls = (*env)->GetObjectClass(env, jobj);
	jmethodID methodid = (*env)->GetMethodID(env,cls,"genRandomInt","(I)I");
	//调用Call<Tyoe>Method函数访问java方法
	jint random = (*env)->CallIntMethod(env, jobj, methodid, 100);
	printf("random num:%ld", random);
}

/*
* 调用java方法获取uuid，根据uuid生成txt文件保存在E盘
* Signature: ()V
*/
JNIEXPORT void JNICALL Java_com_example_zzl_jni_JChild_accessStaticMethod
(JNIEnv *env, jobject jobj){
	LOGI("accessStaticMethod .... ");
	//访问java方法
	jclass cls = (*env)->GetObjectClass(env, jobj);
	jmethodID methodid = (*env)->GetStaticMethodID(env, cls, "getUUID", "()Ljava/lang/String;");
	//调用CallStatic<Tyoe>Method函数访问java方法
	jstring uuid = (*env)->CallStaticObjectMethod(env, cls, methodid);
	const char* cfilename = (*env)->GetStringUTFChars(env, uuid, NULL);
	char filename[100];
	//sprintf 进行占位符拼接，将最终的字符串放到filename中
	sprintf(filename, "E://%s.txt", cfilename);
	//打印char字符串
	LOGI("accessStaticMethod ....  %s",cfilename);
	//释放资源
//	(*env)->ReleaseStringUTFChars(env, uuid, cfilename);

//	FILE *file = fopen(filename, "w");
//	LOGI("accessStaticMethod .... 214");
//	char a[27]= "abcdefghijklmn";
//	fputs(a,file);
//	LOGI("accessStaticMethod .... 215");
//	fclose(file);
//	LOGI("accessStaticMethod .... 216");


}

/*
* 访问构造函数
* Signature: ()Ljava/util/Date;
*/
JNIEXPORT jobject JNICALL Java_com_example_zzl_jni_JChild_accessConstructor
(JNIEnv *env, jobject jobj){
	LOGI("accessConstructor .... ");
	jclass cls = (*env)->FindClass(env, "java/util/Date");
	//jmethodID
	jmethodID constructor_mid = (*env)->GetMethodID(env, cls, "<init>", "()V");
	//实例化一个Date对象
	jobject date_obj = (*env)->NewObject(env, cls, constructor_mid);
	//调用getTime方法
	jmethodID mid = (*env)->GetMethodID(env, cls, "getTime", "()J");
	jlong time = (*env)->CallLongMethod(env, date_obj, mid);

	printf("\ntime:%lld\n", time);

	return date_obj;
}

/*
* 调用父类方法
* Signature: ()V
*/
JNIEXPORT void JNICALL Java_com_example_zzl_jni_JChild_accessNonvirtualMethod
(JNIEnv *env, jobject jobj){
//	jclass cls = (*env)->GetObjectClass(env, jobj);
//	jfieldID childid = (*env)->GetFieldID(env,cls,"child","Lcom/example/zzl/jni/Animal");
//	jobject childobj = (*env)->GetObjectField(env, cls, childid);
//	//获取child字段的对象，需要调用其方法
//	//执行sayHi方法
//	jclass human_cls = (*env)->FindClass(env, "com/example/zzl/jni/Animal"); //注意：传父类的名称
//	jmethodID mid = (*env)->GetMethodID(env, human_cls, "sayHi", "()V");
//
//	(*env)->CallNonvirtualObjectMethod(env, childobj, human_cls, mid);
}

/*
* Method:    chineseChars
* Signature: (Ljava/lang/String;)Ljava/lang/String;
*/
JNIEXPORT jstring JNICALL Java_com_example_zzl_jni_JChild_chineseChars
(JNIEnv * env, jobject jobj, jstring in){
	LOGI("chineseChars .... ");
	//输出
	char *javastr = (*env)->GetStringUTFChars(env, in, NULL);
	printf("%s\n", javastr);

	//c -> jstring
	char *c_str = "NDK编程";
	//char c_str[] = "NDK编程";
	//jstring jstr = (*env)->NewStringUTF(env, c_str);
	//执行String(byte bytes[], String charsetName)构造方法需要的条件
	//1.jmethodID
	//2.byte数组
	//3.字符编码jstring

	jclass str_cls = (*env)->FindClass(env, "java/lang/String");
	jmethodID constructor_mid = (*env)->GetMethodID(env, str_cls, "<init>", "([BLjava/lang/String;)V");

	//jbyte -> char
	//jbyteArray -> char[]
	jbyteArray bytes = (*env)->NewByteArray(env, strlen(c_str));
	//byte数组赋值
	//0->strlen(c_str)，从头到尾
	//对等于，从c_str这个字符数组，复制到bytes这个字符数组
	(*env)->SetByteArrayRegion(env, bytes, 0, strlen(c_str), c_str);

	//字符编码jstring
	jstring charsetName = (*env)->NewStringUTF(env, "UTF-8");

	//调用构造函数，返回编码之后的jstring
	return (*env)->NewObject(env, str_cls, constructor_mid, bytes, charsetName);
}

int compare(const int *a, const int *b) {
	return (*a) - (*b);
}

/*
* 对数组进行排序
* Signature: ([I)V
*/
JNIEXPORT void JNICALL Java_com_example_zzl_jni_JChild_giveArray
(JNIEnv * env, jobject jobj, jintArray array){
	LOGI("giveArray .... ");
	//jintArray -> jint指针 -> c int 数组
	jint* arr = (*env)->GetIntArrayElements(env, array, NULL);
	//获取数组长度
	int size = (*env)->GetArrayLength(env, array);

	//排序,最后一个参数是函数指针
	qsort(arr, size, sizeof(jint),compare);

	//同步
	//mode
	//0, Java数组进行更新，并且释放C/C++数组
	//JNI_ABORT, Java数组不进行更新，但是释放C/C++数组
	//JNI_COMMIT，Java数组进行更新，不释放C/C++数组（函数执行完，数组还是会释放）
	(*env)->ReleaseIntArrayElements(env, array, arr,JNI_COMMIT);

}

/*
* 返回数组
* Signature: (I)[I
*/
JNIEXPORT jintArray JNICALL Java_com_example_zzl_jni_JChild_getArray
(JNIEnv * env, jobject jobj, jint len){
	LOGI("getArray .... ");
	//创建一个数组
	jintArray arr = (*env)->NewIntArray(env, len);

	//1、通过数组赋值
	int i = 0;
	//int intarr[] = {0};
	//for (; i < len; i++){
		//intarr[i] = 5 + i;
	//}
	//对数组赋值
	//(*env)->SetIntArrayRegion(env, arr, 0, len, intarr);

	//2、通过指针

	jint* pArray = (*env)->GetIntArrayElements(env, arr, NULL);
	for (; i < len; i++){
		pArray[i] = i;
	}

	//同步
	(*env)->ReleaseIntArrayElements(env, arr, pArray, 0);
	return arr;
}

/*
*   //JNI 引用变量
	//引用类型：局部引用和全局引用
	//作用：在JNI中告知虚拟机何时回收一个JNI变量

	//局部引用，通过DeleteLocalRef手动释放对象
	//1.访问一个很大的java对象，使用完之后，还要进行复杂的耗时操作
	//2.创建了大量的局部引用，占用了太多的内存，而且这些局部引用跟后面的操作没有关联性

	//模拟：循环创建数组
* Signature: ()V
*/
JNIEXPORT void JNICALL Java_com_example_zzl_jni_JChild_localRef
(JNIEnv *env, jobject jobj){
	LOGI("localRef .... ");
	int i = 0;
	for (; i < 5; i++){
		//创建Date对象
		jclass cls = (*env)->FindClass(env, "java/util/Date");
		jmethodID constructor_mid = (*env)->GetMethodID(env, cls, "<init>", "()V");
		jobject obj = (*env)->NewObject(env, cls, constructor_mid);
		//此处省略一百行代码...

		//不在使用jobject对象了
		//通知垃圾回收器回收这些对象
		(*env)->DeleteLocalRef(env, obj);
		//此处省略一百行代码...
	}
}


//全局引用
//弱全局引用
//节省内存，在内存不足时可以是释放所引用的对象
//可以引用一个不常用的对象，如果为NULL，临时创建
//创建：NewWeakGlobalRef,销毁：DeleteGlobalWeakRef
//共享(可以跨多个线程)，手动控制内存使用
jstring global_str;
/*
* 创建全局资源
* Signature: ()V
*/
JNIEXPORT void JNICALL Java_com_example_zzl_jni_JChild_createGlobalRef
(JNIEnv *env, jobject jobj){
	jstring obj = (*env)->NewStringUTF(env, "jni development is powerful!");
	global_str = (*env)->NewGlobalRef(env, obj);
}

/*
* 获取全局资源
* Signature: ()Ljava/lang/String;
*/
JNIEXPORT jstring JNICALL Java_com_example_zzl_jni_JChild_getGlobalRef
(JNIEnv *env, jobject jobj){
	return global_str;
}

/*
* 释放全局资源
* Signature: ()V
*/
JNIEXPORT void JNICALL Java_com_example_zzl_jni_JChild_deleteGlobalRef
(JNIEnv *env, jobject jobj){

}




//异常处理
//1.保证Java代码可以运行
//2.补救措施保证C代码继续运行

//JNI自己抛出的异常，在Java层无法被捕捉，只能在C层清空
//用户通过ThrowNew抛出的异常，可以在Java层捕捉
/*
* 异常处理
* Signature: ()V
*/
JNIEXPORT void JNICALL Java_com_example_zzl_jni_JChild_exeception
(JNIEnv *env, jobject jobj){
	LOGI("exeception .... ");
	jclass cls = (*env)->GetObjectClass(env, jobj);
	jfieldID fid = (*env)->GetFieldID(env, cls, "key2", "Ljava/lang/String;");
	//检测是否发生Java异常
	jthrowable exception = (*env)->ExceptionOccurred(env);
	if (exception != NULL){
		//让Java代码可以继续运行
		//清空异常信息
		(*env)->ExceptionClear(env);

		//补救措施
		fid = (*env)->GetFieldID(env, cls, "key", "Ljava/lang/String;");
	}

	//获取属性的值
	jstring jstr = (*env)->GetObjectField(env, jobj, fid);
	char *str = (*env)->GetStringUTFChars(env, jstr, NULL);

	//对比属性值是否合法
	if (strlen(str)!= 0){
		//认为抛出异常，给Java层处理
		jclass newExcCls = (*env)->FindClass(env, "java/lang/IllegalArgumentException");
		(*env)->ThrowNew(env, newExcCls, "key's value is invalid!");
	}
}


/*
* 缓存
* Signature: ()V
*/
//static jfieldID key_id
JNIEXPORT void JNICALL Java_com_example_zzl_jni_JChild_cached
(JNIEnv *env, jobject jobj){
	jclass cls = (*env)->GetObjectClass(env, jobj);
	//获取jfieldID只获取一次
	//局部静态变量
	static jfieldID key_id = NULL;
	if (key_id == NULL){
		key_id = (*env)->GetFieldID(env, cls, "key", "Ljava/lang/String;");
		printf("--------GetFieldID-------\n");
	}
}



//初始化全局变量，动态库加载完成之后，立刻缓存起来
/*
* Method:    initIds
* Signature: ()V
*/

jfieldID key_fid;
jmethodID random_mid;
JNIEXPORT void JNICALL Java_com_example_zzl_jni_JChild_initIds
(JNIEnv *env, jobject jobj){
	jclass cls = (*env)->GetObjectClass(env, jobj);
	key_fid = (*env)->GetFieldID(env, cls, "key", "Ljava/lang/String;");
	random_mid = (*env)->GetMethodID(env, cls, "genRandomInt", "(I)I");
}


