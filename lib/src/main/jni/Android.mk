LOCAL_PATH := $(call my-dir)
include $(CLEAR_VARS)

LOCAL_MODULE        := JniSandbox
LOCAL_SRC_FILES     := jni-glue.c calculation.c future.cc
LOCAL_LDLIBS        +=  -lm -llog
LOCAL_CPP_EXTENSION += .cpp .cc
LOCAL_CPPFLAGS      += -std=c++11 -pthread
LOCAL_CPP_FEATURES  := rtti exceptions
include $(BUILD_SHARED_LIBRARY)
