LOCAL_PATH := $(call my-dir)  
include $(CLEAR_VARS)  
LOCAL_MODULE    :=bspatch  
LOCAL_SRC_FILES :=bspatch.c

#LOCAL_SRC_FILES +=bsdiff.c jchild.c bspatch

LOCAL_LDLIBS += -llog  #开启日志
include $(BUILD_SHARED_LIBRARY)  

 

# 增量更新，编译时需要分开编译
# bspatch合并包，bsdiff生成差分包
#LOCAL_MODULE    :=bspatch  
#LOCAL_SRC_FILES :=bspatch.c
#LOCAL_MODULE    :=bsdiff  
#LOCAL_SRC_FILES :=bsdiff.c