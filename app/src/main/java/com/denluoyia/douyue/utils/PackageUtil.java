package com.denluoyia.douyue.utils;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;

/**
 * 包管理工具 应用相关的配置信息 设备信息
 */
public class PackageUtil {

    private static final String TAG = PackageUtil.class.getSimpleName();

    /**
     * 获取指定版本号
     * @param context 上下文
     * @return 没有或异常返回-1
     */
    public static int getPackageVersionCode(Context context) {
        int versionCode = -1;
        PackageManager pm = context.getPackageManager();
        PackageInfo pi = null;
        try {
            pi = pm.getPackageInfo(context.getPackageName(), 0);
            if (null != pi) {
                versionCode = pi.versionCode;
            }
        } catch (PackageManager.NameNotFoundException e) {
            LogUtil.e(TAG, "", e);
        }
        return versionCode;
    }

    /**
     * 获取版本名称
     * @param context 上下文
     * @return 获取不到或异常返回null
     */
    public static String getPackageVersionName(Context context) {
        String versionCode = null;
        PackageManager pm = context.getPackageManager();
        PackageInfo pi = null;
        try {
            pi = pm.getPackageInfo(context.getPackageName(), 0);
            if (null != pi) {
                versionCode = pi.versionName;
            }
        } catch (PackageManager.NameNotFoundException e) {
            LogUtil.e(TAG, "", e);
        }
        return versionCode;
    }

    /**
     * 获取应用名称
     * @param context 应用的上下文
     */
    public static String getAppName(Context context) {
        PackageManager pm = context.getPackageManager();
        String appName = "";
        try {
            appName = pm.getApplicationLabel(pm.getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA)).toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return appName;
    }


    public static String getDeviceId(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return tm.getDeviceId();
    }

}
