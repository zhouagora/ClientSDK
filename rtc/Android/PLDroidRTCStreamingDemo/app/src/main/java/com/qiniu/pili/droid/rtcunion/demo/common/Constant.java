package com.qiniu.pili.droid.rtcunion.demo.common;

import com.qiniu.pili.droid.union.UNRTCEngine;

public class Constant {

    public static final String MEDIA_SDK_VERSION;

    static {
        String sdk = "undefined";
        try {
            sdk = UNRTCEngine.getSdkVersion();
        } catch (Throwable e) {
        }
        MEDIA_SDK_VERSION = sdk;
    }

    public static boolean PRP_ENABLED = true;
    public static float PRP_DEFAULT_LIGHTNESS = .65f;
    public static float PRP_DEFAULT_SMOOTHNESS = 1.0f;
    public static final float PRP_MAX_LIGHTNESS = 1.0f;
    public static final float PRP_MAX_SMOOTHNESS = 1.0f;

}
