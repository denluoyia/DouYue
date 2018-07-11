package com.denluoyia.douyue.utils;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;

import com.denluoyia.douyue.base.BaseApplication;

/**
 * function : UI工具类：包括常用功能：获取全局的上下文、主线程相关、主线程任务执行、资源id获取、资源获取、像数大小转换、自定义Toast.
 *
 * <p>Created by lzj on 2016/1/28.<p/>
 */
@SuppressWarnings({"unused", "UnusedReturnValue", "WeakerAccess"})
public class UIUtil {

    private static final String TAG = UIUtil.class.getSimpleName();

    /** 获取全局上下文 */
    public static Context getContext() {
        return BaseApplication.getApplication();
    }

    /** 获取主线程 */
    public static Thread getMainThread() {
        return BaseApplication.getMainThread();
    }

    /** 获取主线程ID */
    public static long getMainThreadId() {
        return BaseApplication.getMainThreadId();
    }

    /** 获取主线程消息轮询器 */
    public static android.os.Looper getMainLooper() {
        return BaseApplication.getMainThreadLooper();
    }

    /** 获取主线程的handler */
    public static Handler getHandler() {
        return BaseApplication.getMainThreadHandler();
    }

    /** 在主线程中延时一定时间执行runnable */
    public static boolean postDelayed(Runnable runnable, long delayMillis) {
        return getHandler().postDelayed(runnable, delayMillis);
    }

    /** 在主线程执行runnable */
    public static boolean post(Runnable runnable) {
        return getHandler().post(runnable);
    }

    /** 从主线程looper里面移除runnable */
    public static void removeCallbacksFromMainLooper(Runnable runnable) {
        getHandler().removeCallbacks(runnable);
    }

    /** 判断当前的线程是否为主线程 */
    public static boolean isRunInMainThread() {
        return android.os.Process.myTid() == getMainThreadId();
    }

    /** 在主线程中运行任务 */
    public static void runInMainThread(Runnable runnable) {
        if (isRunInMainThread()) {
            runnable.run();
        } else {
            UIUtil.post(runnable);
        }
    }

    /* ----------------------根据资源id获取资源------start------------------------- */

    /**
     * 填充layout布局文件
     */
    public static View inflate(int resId) {
        return LayoutInflater.from(getContext()).inflate(resId, null);
    }

    /**
     * 获取资源
     */
    public static Resources getResources() {
        return getContext().getResources();
    }

    /**
     * 获取文字
     */
    public static String getString(int resId) {
        return getResources().getString(resId);
    }

    /**
     * 获取文字数组
     */
    public static String[] getStringArray(int resId) {
        return getResources().getStringArray(resId);
    }

    /**
     * 获取dimen
     */
    public static int getDimens(int resId) {
        return getResources().getDimensionPixelSize(resId);
    }

    /**
     * 获取drawable
     */
    public static Drawable getDrawable(int resId) {
        return ContextCompat.getDrawable(getContext(), resId);
    }

    /**
     * 获取Bitmap
     */
    public static Bitmap getBitmap(int resId) {
        return BitmapFactory.decodeResource(getResources(), resId);
    }

    /**
     * 获取颜色
     */
    public static int getColor(int resId) {
        return ContextCompat.getColor(getContext(), resId);
    }

    /**
     * 获取颜色选择器
     */
    public static ColorStateList getColorStateList(int resId) {
        return ContextCompat.getColorStateList(getContext(), resId);
    }

    /* ----------------------根据资源id获取资源------end------------------------- */

    /* ----------------------px与dip相互转换------start------------------------- */

    /**
     * dip转换为px
     */
    public static int dip2px(float dip) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dip * scale + 0.5f);
    }

    /**
     * px转换为dip
     */
    public static int px2dip(int px) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }

    /* ----------------------px与dip相互转换------end------------------------- */

}