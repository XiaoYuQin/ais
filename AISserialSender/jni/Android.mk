LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE    := uart
LOCAL_SRC_FILES := uart.c 485.c
LOCAL_LDLIBS+= -L$(SYSROOT)/usr/lib -llog
LOCAL_SHARED_LIBRARIES := libuart
include $(BUILD_SHARED_LIBRARY)
