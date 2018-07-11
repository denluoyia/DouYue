package com.denluoyia.douyue.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

import com.denluoyia.douyue.base.BaseApplication;

import java.lang.reflect.Method;

public class ScreenUtil {

    /**
     * 获取屏幕的宽度
     * @param context 上下文
     * @return 屏幕的宽度
     */
    public static int getScreenWidth(Context context){
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }

    /**
     * 获取屏幕的高度
     * @param context 上下文
     * @return 屏幕的高度
     */
    public static int getScreenHeight(Context context){
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.heightPixels;
    }


    /**
     * 获取状态栏高度
     * @param context 在Activity类中使用;
     * @return 状态栏高度
     */
    public static int getStatusBarHeight(Context context){
        int statusBarHeight = -1;
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height").get(object).toString());
            statusBarHeight = context.getResources().getDimensionPixelSize(height);
        }catch (Exception e){
            e.printStackTrace();
        }
        return statusBarHeight;
    }


    /**
     * 获取手机显示App区域的大小（头部导航栏+ActionBar+根布局），不包括虚拟按钮
     *
     * @return
     */
    public static int[] getAppSize() {
        int[] size = new int[2];
        DisplayMetrics metrics = getDisplayMetrics();
        size[0] = metrics.widthPixels;
        size[1] = metrics.heightPixels;
        return size;
    }

    /**
     * 获取整个手机屏幕的大小(包括虚拟按钮)
     * 必须在onWindowFocus方法之后使用
     *
     * @param activity
     * @return
     */
    public static int[] getScreenSize(AppCompatActivity activity) {
        int[] size = new int[2];
        View decorView = activity.getWindow().getDecorView();
        size[0] = decorView.getWidth();
        size[1] = decorView.getHeight();
        return size;
    }

    /**
     * 获取导航栏的高度
     *
     * @return
     */
    public static int getStatusBarHeight() {
        Resources resources = BaseApplication.getApplication().getResources();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        return resources.getDimensionPixelSize(resourceId);
    }

    /**
     * 获取虚拟按键的高度
     *
     * @return
     */
    public static int getNavigationBarHeight() {
        int navigationBarHeight = 0;
        Resources rs = BaseApplication.getApplication().getResources();
        int id = rs.getIdentifier("navigation_bar_height", "dimen", "android");
        if (id > 0 && hasNavigationBar()) {
            navigationBarHeight = rs.getDimensionPixelSize(id);
        }
        return navigationBarHeight;
    }

    /**
     * 是否存在虚拟按键
     *
     * @return
     */
    private static boolean hasNavigationBar() {
        boolean hasNavigationBar = false;
        Resources rs = BaseApplication.getApplication().getResources();
        int id = rs.getIdentifier("config_showNavigationBar", "bool", "android");
        if (id > 0) {
            hasNavigationBar = rs.getBoolean(id);
        }
        try {
            Class systemPropertiesClass = Class.forName("android.os.SystemProperties");
            Method m = systemPropertiesClass.getMethod("get", String.class);
            String navBarOverride = (String) m.invoke(systemPropertiesClass, "qemu.hw.mainkeys");
            if ("1".equals(navBarOverride)) {
                hasNavigationBar = false;
            } else if ("0".equals(navBarOverride)) {
                hasNavigationBar = true;
            }
        } catch (Exception e) {
        }
        return hasNavigationBar;
    }

    public static DisplayMetrics getDisplayMetrics() {
        DisplayMetrics metrics = BaseApplication.getApplication().getResources().getDisplayMetrics();
        return metrics;
    }


    /**
     * 状态栏变透明 布局置顶上去 适合大图片背景
     * @param activity Activity
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static void setTransparentStatusBar(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
        } else {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }
}
