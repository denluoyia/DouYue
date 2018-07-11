package com.denluoyia.douyue.utils;

import android.app.Activity;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

/**
 * Created by denluoyia
 * Date 2018/07/09
 * DouYue
 */
public class WebViewSetting {

    public static void initWebSetting(WebView webView){
        WebSettings webSettings = webView.getSettings();
        webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        webSettings.setJavaScriptEnabled(true); //可以在onStop记得把其改为false 在onResume改为true 支持js交互
        webSettings.setUseWideViewPort(true); //将图片调整到适合webView的大小
        webSettings.setLoadWithOverviewMode(true); //缩放至屏幕大小
        webView.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
        webSettings.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
        webSettings.setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
        webSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK); //不使用缓存
    }

    public static String addParams2DetailUrl(Activity activity, String url, boolean isVideo){
        StringBuffer sb = new StringBuffer(url);
        sb.append("?client=android");
        sb.append("&device_id=" + PackageUtil.getDeviceId(activity));
        sb.append("&version=" + "1.3.0");
        sb.append("&show_video=" + (isVideo ? "1" : "0"));
        return sb.toString();
    }
}
