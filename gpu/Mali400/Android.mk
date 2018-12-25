ifeq ($(strip $(TARGET_BOARD_PLATFORM_GPU)), mali400)
LOCAL_PATH := $(call my-dir)
ifneq ($(strip $(GRAPHIC_MEMORY_PROVIDER)), dma_buf)
# Use BUILD_PREBUILT instead of PRODUCT_COPY_FILES to bring in the NOTICE file.
include $(CLEAR_VARS)
LOCAL_PREBUILT_LIBS := lib/$(TARGET_ARCH)/libEGL_mali.so
LOCAL_MODULE_TAGS := optional
LOCAL_MODULE_PATH := $(TARGET_OUT_SHARED_LIBRARIES)/egl
include $(BUILD_MULTI_PREBUILT)

include $(CLEAR_VARS)
LOCAL_PREBUILT_LIBS := lib/$(TARGET_ARCH)/libGLESv1_CM_mali.so
LOCAL_MODULE_TAGS := optional
LOCAL_MODULE_PATH := $(TARGET_OUT_SHARED_LIBRARIES)/egl
include $(BUILD_MULTI_PREBUILT)

include $(CLEAR_VARS)
LOCAL_PREBUILT_LIBS := lib/$(TARGET_ARCH)/libGLESv2_mali.so
LOCAL_MODULE_TAGS := optional
LOCAL_MODULE_PATH := $(TARGET_OUT_SHARED_LIBRARIES)/egl
include $(BUILD_MULTI_PREBUILT)

include $(CLEAR_VARS)
LOCAL_PREBUILT_LIBS := lib/$(TARGET_ARCH)/libMali.so
LOCAL_MODULE_TAGS := optional
include $(BUILD_MULTI_PREBUILT)

include $(CLEAR_VARS)
LOCAL_PREBUILT_LIBS := lib/$(TARGET_ARCH)/libUMP.so
LOCAL_MODULE_TAGS := optional
include $(BUILD_MULTI_PREBUILT)

else


include $(CLEAR_VARS)
ifeq ($(strip $(TARGET_BOARD_PLATFORM)), rk322x)
  ifeq ($(strip $(TARGET_BOARD_PLATFORM_PRODUCT)), box)
  LOCAL_PREBUILT_LIBS := lib/$(TARGET_ARCH)/rk322x_box/libGLES_mali.so
  else
  LOCAL_PREBUILT_LIBS := lib/$(TARGET_ARCH)/rk322x/libGLES_mali.so
  endif
else
LOCAL_PREBUILT_LIBS := lib/$(TARGET_ARCH)/libGLES_mali.so
endif
LOCAL_MODULE_TAGS := optional
include $(BUILD_MULTI_PREBUILT)

endif
endif
