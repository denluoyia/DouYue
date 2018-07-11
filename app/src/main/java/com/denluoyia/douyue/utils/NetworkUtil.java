package com.denluoyia.douyue.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

public class NetworkUtil {

    private static final String TAG = NetworkUtil.class.getSimpleName();

    /** judge the status of network */
    public static boolean isNetworkConnected(Context context){
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = null;
            if (mConnectivityManager != null) {
                mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            }
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    /** get the type of network connection */
    public static int getNetworkType(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = null;
            if (mConnectivityManager != null) {
                mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            }
            if (mNetworkInfo != null) {
                return mNetworkInfo.getType();
            }
        }
        return -1;
    }

    /** judge current network is wifi */
    public static boolean isWifi(Context context){
        return getNetworkType(context) == ConnectivityManager.TYPE_WIFI;
    }

    /** 获取内网IP */
    public static String getLocalIPAddress(Context context) {
        WifiManager mWifiManager = (WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo    mWifiInfo    = null;
        if (mWifiManager != null){
            mWifiInfo = mWifiManager.getConnectionInfo();
        }
        if (mWifiInfo != null) {
            int ipAddress = mWifiInfo.getIpAddress();
            return String.valueOf(ipAddress & 0xFF) + "." +
                    ((ipAddress >> 8)&0XFF) + "." +
                    ((ipAddress >> 16)&0xFF) + "." +
                    ((ipAddress >> 24) & 0xFF);

        }
        return null;
    }


}
