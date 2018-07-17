package com.denluoyia.douyue.view.activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.Toolbar;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.denluoyia.douyue.R;
import com.denluoyia.douyue.base.BaseActivity;
import com.denluoyia.douyue.constant.Constant;
import com.denluoyia.douyue.utils.StringUtil;
import com.denluoyia.douyue.utils.WebViewSetting;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;

/**
 * 加载Web通用页
 * 主要：收藏的文字 声音 影像
 * 亦或其他纯Web页
 */
public class CommonWebActivity extends BaseActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.web_view)
    WebView webView;

    @Override
    protected int setContentViewId() {
        return R.layout.activity_common_web;
    }

    @Override
    protected void doBusiness() {
        String url = getIntent().getStringExtra(Constant.INTENT_ACTION_DATA_KEY);
        if (StringUtil.isEmpty(url)){
            finish();
            return;
        }
        initToolbar(toolbar);
        WebViewSetting.initWebSetting(webView);
        webView.addJavascriptInterface(new MJavascriptInterface(this), "imageListener");
        webView.setWebViewClient(new MyWebViewClient());
        webView.loadUrl(url);
    }


    private class MyWebViewClient extends WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            addImageClickListener(view);
        }


        private void addImageClickListener(WebView webView){
            webView.loadUrl("javascript:(function(){ " +
                            "    var objs = document.getElementsByTagName(\"img\"); " +
                            "    var imageUrls = new Array(objs.length); " +
                            "    for(var i = 0; i < objs.length; i++) " +
                            "    { " +
                            "        imageUrls[i] = objs[i].src; " +
                            "        objs[i].onclick = function() { " +
                            "            window.imageListener.openImage(this.src); " +
                            "        } " +
                            "    } " +
                            "    window.imageListener.getImageUrls(imageUrls.toString()); " +
                            "})()");
        }
    }

    //js对应的本地方法
    public class MJavascriptInterface extends Object{
        private Context context;
        private ArrayList<String> imageUrls = new ArrayList<>();
        public MJavascriptInterface(Context context){
            this.context = context;
        }

        @android.webkit.JavascriptInterface
        public void openImage(String img){
            Intent intent = new Intent();
            intent.putStringArrayListExtra("imageUrls", this.imageUrls);
            intent.putExtra("currImage", img); //当前的图片
            intent.setClass(context, ImageBrowseActivity.class);
            context.startActivity(intent);
        }

        @android.webkit.JavascriptInterface
        public void getImageUrls(String urls){
            String[] tempArr = urls.split(",");
            this.imageUrls.clear();
            if (tempArr != null && tempArr.length > 0){
                this.imageUrls.addAll(Arrays.asList(tempArr));
            }
        }
    }
}
