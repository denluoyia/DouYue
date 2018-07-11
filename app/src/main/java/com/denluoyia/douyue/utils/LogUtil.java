package com.denluoyia.douyue.utils;

import android.util.Log;

import com.denluoyia.douyue.constant.Configs;

/**
 * Common Log Util
 */
public class LogUtil {

    /** 日志级别 V D I W E 依次增强*/

    private static boolean DEBUG = Configs.DEBUG;

    public static void v(String tag, String content){
        if (!DEBUG) return;
        Log.v(tag, content == null ? "null" : content);
    }

    public static void v(String tag, Object...objects){
        if (!DEBUG) return;
        Log.v(tag, getInfo(objects));
    }

    public static void d(String tag, String content){
        if (!DEBUG) return;
        Log.d(tag, content == null ? "null" : content);
    }

    public static void d(String tag, Object...objects){
        if (!DEBUG) return;
        Log.d(tag, getInfo(objects));
    }


    public static void i(String tag, String content){
        if (!DEBUG) return;
        Log.i(tag, content == null ? "null" : content);
    }

    public static void i(String tag, Object...objects){
        if (!DEBUG) return;
        Log.i(tag, getInfo(objects));
    }

    public static void w(String tag, String content){
        if (!DEBUG) return;
        Log.w(tag, content == null ? "null" : content);
    }

    public static void w(String tag, Object...objects){
        if (!DEBUG) return;
        Log.w(tag, getInfo(objects));
    }


    public static void e(String tag, String content){
        if (!DEBUG) return;
        Log.e(tag, content == null ? "null" : content);
    }

    public static void e(String tag, Object...objects){
        if (!DEBUG) return;
        Log.e(tag, getInfo(objects));
    }

    private static String getInfo(Object...objects){
        StringBuilder sb = new StringBuilder();
        if (objects == null){
            return "no message";
        }else{
            for (Object obj : objects){
                sb.append("--");
                sb.append(obj);
            }
        }

        return sb.toString();
    }
}
