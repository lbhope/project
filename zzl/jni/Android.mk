LOCAL_PATH := $(call my-dir)  
include $(CLEAR_VARS)  
LOCAL_MODULE    :=jni  
LOCAL_SRC_FILES :=jchild.c  
LOCAL_LDLIBS += -llog  #开启日志
include $(BUILD_SHARED_LIBRARY)  