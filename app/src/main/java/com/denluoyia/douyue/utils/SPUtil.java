package com.denluoyia.douyue.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * SharedPreferences Util
 */
public class SPUtil {

    private static SharedPreferences SP;

    private static SharedPreferences getSP(Context context){
        if (SP == null){
            SP = context.getSharedPreferences("app_sp", Context.MODE_PRIVATE);
        }
        return SP;
    }


    public static void putString(String key, String value){
        getSP(UIUtil.getContext()).edit().putString(key, value).apply();
    }

    public static String getString(String key, String defVal){
        return getSP(UIUtil.getContext()).getString(key, defVal);
    }

    public static void putInt(Context context, String key, int value){
        getSP(context).edit().putInt(key, value).apply();
    }

    public static int getInt(String key, int defVal){
        return getSP(UIUtil.getContext()).getInt(key, defVal);
    }

    public static void putFloat(Context context, String key, int value){
        getSP(context).edit().putInt(key, value).apply();
    }

    public static Float getFloat(String key, float defVal){
        return getSP(UIUtil.getContext()).getFloat(key, defVal);
    }

    public static void putLong(String key, Long value){
        getSP(UIUtil.getContext()).edit().putLong(key, value).apply();
    }

    public static Long getFloat(String key, long defVal){
        return getSP(UIUtil.getContext()).getLong(key, defVal);
    }

    public static void putBoolean(String key, Boolean value){
        getSP(UIUtil.getContext()).edit().putBoolean(key, value).apply();
    }

    public static Boolean getBoolean(String key, Boolean defVal){
        return getSP(UIUtil.getContext()).getBoolean(key, defVal);
    }

}
