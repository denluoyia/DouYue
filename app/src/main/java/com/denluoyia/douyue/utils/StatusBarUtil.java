package com.denluoyia.douyue.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;


/**
 * A Simple StatusBar Util
 * @author Denluoyia
 * Created on 2018/04/28
 */
public class StatusBarUtil {

    /** 设置状态栏颜色(纯色) 就不用在activity的布局文件中设置fitsSystemWindows=true属性了
     *
     * @param activity Activity
     * @param color 颜色
     */
    public static void setStatusBarColor(Activity activity, int color){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){ //>=21 5.0系统
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            activity.getWindow().setStatusBarColor(color);  //直接设置statusBar的颜色
        }else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){ //>=19 4.4系统
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
            View statusBarView = new View(activity);
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    getStatusBarHeight(activity));

            statusBarView.setBackgroundColor(color);
            decorView.addView(statusBarView, lp);
            ViewGroup rootView = activity.getWindow().getDecorView().findViewById(android.R.id.content);
            rootView.setPadding(0, getStatusBarHeight(activity), 0, 0);
        }
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
}
