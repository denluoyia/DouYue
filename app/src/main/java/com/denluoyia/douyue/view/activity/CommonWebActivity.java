package com.denluoyia.douyue.view.activity;
import android.support.v7.widget.Toolbar;
import android.webkit.WebView;

import com.denluoyia.douyue.R;
import com.denluoyia.douyue.base.BaseActivity;
import com.denluoyia.douyue.constant.Constant;
import com.denluoyia.douyue.utils.StringUtil;
import com.denluoyia.douyue.utils.WebViewSetting;

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
            return;
        }
        initToolbar(toolbar);
        WebViewSetting.initWebSetting(webView);
        webView.loadUrl(url);
    }

}
