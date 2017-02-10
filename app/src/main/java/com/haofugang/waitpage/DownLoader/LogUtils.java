package com.haofugang.waitpage.DownLoader;

import android.util.Log;

/**
 * Desc:
 * POST HFG
 * 2017年1月3日16:19:28
 */

public class LogUtils {
    private static final boolean LOG_SWITCH = true;

    public static void d(String tag, String msg) {
        if (LOG_SWITCH) {
            Log.d(tag, msg);
        }
    }

    public static void e(String tag, String msg) {
        if (LOG_SWITCH) {
            Log.e(tag, msg);
        }
    }

}
