LOCAL_PATH := $(call my-dir)  
include $(CLEAR_VARS)  
LOCAL_MODULE    :=jni  
LOCAL_SRC_FILES :=jchild.c dynamic.c

#LOCAL_SRC_FILES +=bsdiff.c jchild.c bspatch

LOCAL_LDLIBS += -llog  #开启日志
include $(BUILD_SHARED_LIBRARY)  
 
#nativethread线程模块
include $(CLEAR_VARS)  
LOCAL_MODULE    :=nativethread  
LOCAL_SRC_FILES :=nativethread.cpp
LOCAL_LDLIBS += -llog  #开启日志
include $(BUILD_SHARED_LIBRARY)  

# 增量更新，编译时需要分开编译
# bspatch合并包，bsdiff生成差分包
#LOCAL_MODULE    :=bspatch  
#LOCAL_SRC_FILES :=bspatch.c
#LOCAL_MODULE    :=bsdiff  
#LOCAL_SRC_FILES :=bsdiff.c

#LOCAL_SRC_FILES － 编译的源文件
#LOCAL_C_INCLUDES － 需要包含的头文件目录
#LOCAL_SHARED_LIBRARIES － 链接时需要的外部库