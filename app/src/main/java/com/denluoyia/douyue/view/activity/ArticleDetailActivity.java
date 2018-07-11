package com.denluoyia.douyue.view.activity;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.denluoyia.douyue.R;
import com.denluoyia.douyue.base.BaseActivity;
import com.denluoyia.douyue.constant.Constant;
import com.denluoyia.douyue.manager.db.greendao.MyCollectionDaoManager;
import com.denluoyia.douyue.model.DetailBean;
import com.denluoyia.douyue.model.ItemListBean;
import com.denluoyia.douyue.model.db.MyCollectionBean;
import com.denluoyia.douyue.presenter.DetailContract;
import com.denluoyia.douyue.utils.LogUtil;
import com.denluoyia.douyue.utils.UIUtil;
import com.denluoyia.douyue.utils.WebViewSetting;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import butterknife.BindView;
import butterknife.OnClick;


public class ArticleDetailActivity extends BaseActivity implements DetailContract.View{

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.iv_top)
    ImageView imageViewTop;
    @BindView(R.id.update_time)
    TextView updateTime;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.author)
    TextView author;
    @BindView(R.id.web_view)
    WebView webView;
    @BindView(R.id.iv_collect)
    ImageView ivCollect;

    private ItemListBean.ListBean item;
    private String postId;
    private String collectionUrl;

    @Override
    protected int setContentViewId() {
        this.overridePendingTransition(R.anim.translate_bottom_in, R.anim.translate_bottom_out);
        return R.layout.activity_article_detail;
    }

    @Override
    protected void doBusiness() {
        item = getIntent().getParcelableExtra(Constant.INTENT_ACTION_DATA_KEY);
        if (item == null || TextUtils.isEmpty(item.getId())) return;
        postId = item.getId();
        initToolbar(mToolbar);
        Glide.with(this).load(item.getThumbnail()).into(imageViewTop);
        updateTime.setText(item.getUpdate_time());
        title.setText(item.getTitle());
        author.setText(item.getAuthor());
        ivCollect.setBackgroundResource(MyCollectionDaoManager.isCollectionExists(postId) ? R.mipmap.ic_collection : R.mipmap.ic_un_collection);
        WebViewSetting.initWebSetting(webView);
        collectionUrl = WebViewSetting.addParams2DetailUrl(this, item.getHtml5(), false);
        webView.setVisibility(View.GONE);
        initWebSetting();
    }

    @SuppressLint("JavascriptInterface")
    private void initWebSetting(){
        webView.addJavascriptInterface(new InJavaScriptLocalObj(), "localObj");
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onLoadResource(WebView view, String url) {
                super.onLoadResource(view, url);

            }

            @Override
            public void onPageFinished(WebView view, String url) {
                //super.onPageFinished(view, url);
                if (webView.getVisibility() != View.VISIBLE){
                    view.loadUrl("javascript:window.localObj.hideArticleCover("
                            + "document.getElementsByTagName('html')[0].innerHTML)");
                }
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
            }
        });
        webView.loadUrl(collectionUrl);
    }


    final class InJavaScriptLocalObj {
        @JavascriptInterface
        public void hideArticleCover(String html) {
            refreshHtmlContent(html);
        }
    }

    private void refreshHtmlContent(final String html){
        webView.postDelayed(new Runnable() {
            @Override
            public void run() {
                //解析html字符串为对象
                Document document = Jsoup.parse(html);
                Elements element = document.getElementsByClass("imgTop");
                element.attr("style", "display:none");

                String body = document.toString();
                LogUtil.e("htmlReGo", body);
                webView.loadDataWithBaseURL(collectionUrl, body, "text/html", "utf-8", null);
                webView.setVisibility(View.VISIBLE);
                //netViewUtil.statusNormal();
            }
        },0);
    }

    @Override
    public void loadDataSuccess(DetailBean bean) {

    }

    @Override
    public void loadDataFailed(String msg) {
        if (!TextUtils.isEmpty(msg)){
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        }
    }


    @OnClick({R.id.iv_collect})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.iv_collect:
                if (MyCollectionDaoManager.isCollectionExists(postId)){
                    ivCollect.setBackgroundResource(R.mipmap.ic_un_collection);
                    MyCollectionDaoManager.deleteById(postId);
                    Toast.makeText(this, "取消收藏成功", Toast.LENGTH_SHORT).show();
                    return;
                }

                MyCollectionBean bean = new MyCollectionBean();
                bean.setCollectionId(postId);
                bean.setCollectionType(Constant.MY_COLLECTION_TYPE_TEXT);
                bean.setTitle(item.getTitle());
                bean.setUrl(collectionUrl);

                MyCollectionDaoManager.insert(bean);
                ivCollect.setBackgroundResource(R.mipmap.ic_collection);
                Toast.makeText(this, "收藏成功", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    /**
     * 请求WebView数据
     * http://static.owspace.com/wap/295138.html?client=android&device_id=866963027059338&version=1.3.0&show_video=1
     */

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, R.anim.translate_bottom_out);
    }
}
