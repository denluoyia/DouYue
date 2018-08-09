package com.denluoyia.douyue.view.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.denluoyia.douyue.R;
import com.denluoyia.douyue.base.BaseActivity;
import com.denluoyia.douyue.constant.Constant;
import com.denluoyia.douyue.manager.audioplayer.MyAudioController;
import com.denluoyia.douyue.manager.db.greendao.MyCollectionDaoManager;
import com.denluoyia.douyue.model.DetailBean;
import com.denluoyia.douyue.model.ItemListBean;
import com.denluoyia.douyue.model.db.MyCollectionBean;
import com.denluoyia.douyue.presenter.DetailContract;
import com.denluoyia.douyue.presenter.DetailPresenter;
import com.denluoyia.douyue.utils.HtmlParseUtil;
import com.denluoyia.douyue.utils.NetworkUtil;
import com.denluoyia.douyue.utils.UIUtil;
import com.denluoyia.douyue.utils.WebViewSetting;

import butterknife.BindView;
import butterknife.OnClick;


public class AudioDetailActivity extends BaseActivity implements DetailContract.View{

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.iv_collect)
    ImageView ivCollect;
    @BindView(R.id.iv_top)
    ImageView imageViewTop;
    @BindView(R.id.btn_init_play)
    ImageView btnInitPlay;
    @BindView(R.id.hint_progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.myAudioController)
    MyAudioController audioController;

    @BindView(R.id.update_time)
    TextView updateTime;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.author)
    TextView author;
    @BindView(R.id.detail_lead_text)
    TextView leadText;
    @BindView(R.id.web_view)
    WebView webView;
    @BindView(R.id.detail_content)
    LinearLayout llDetailContent;

    private ItemListBean.ListBean item;
    private String collectionUrl;
    private DetailPresenter mPresenter;
    private HtmlParseUtil mHtmlParseUtil;

    private String fmUrl;
    @Override
    protected int setContentViewId() {
        this.overridePendingTransition(R.anim.translate_bottom_in, R.anim.translate_bottom_out);
        return R.layout.activity_audio_detail;
    }

    @Override
    protected void doBusiness() {
        item = getIntent().getParcelableExtra(Constant.INTENT_ACTION_DATA_KEY);
        if (item == null || TextUtils.isEmpty(item.getId())) return;
        fmUrl = item.getFm();
        initToolbar(mToolbar);
        Glide.with(this).load(item.getThumbnail()).into(imageViewTop);
        audioController.setActivity(this).setAudioUrl(fmUrl);
        collectionUrl = WebViewSetting.addParams2DetailUrl(this, item.getHtml5(), false);
        ivCollect.setBackgroundResource(MyCollectionDaoManager.isCollectionExists(item.getId()) ? R.mipmap.ic_collection_selected : R.mipmap.ic_collection_un_selected);
        mHtmlParseUtil = new HtmlParseUtil(this);
        mPresenter = new DetailPresenter(this);
        mPresenter.loadData(item.getId());
    }


    @OnClick({R.id.btn_init_play, R.id.iv_collect})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.btn_init_play:
                if (!NetworkUtil.isWifi(this) && NetworkUtil.isNetworkConnected(this)) {
                    new AlertDialog.Builder(this).setMessage("当前网络为非WIFI环境，您确定要继续播放吗")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    startPlay();
                                    dialog.dismiss();
                                }
                            }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).create().show();
                    return;
                }
                startPlay();
                break;

            case R.id.iv_collect:
                if (MyCollectionDaoManager.isCollectionExists(item.getId())){
                    ivCollect.setBackgroundResource(R.mipmap.ic_collection_un_selected);
                    MyCollectionDaoManager.deleteById(item.getId());
                    Toast.makeText(this, "取消收藏成功", Toast.LENGTH_SHORT).show();
                    return;
                }

                MyCollectionBean bean = new MyCollectionBean();
                bean.setCollectionId(item.getId());
                bean.setCollectionType(Constant.MY_COLLECTION_TYPE_AUDIO);
                bean.setTitle(item.getTitle());
                bean.setUrl(collectionUrl);
                MyCollectionDaoManager.insert(bean);
                ivCollect.setBackgroundResource(R.mipmap.ic_collection_selected);
                Toast.makeText(this, "收藏成功", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void startPlay(){
        btnInitPlay.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        audioController.startPlay();
        UIUtil.postDelayed(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.GONE);
                audioController.setVisibility(View.VISIBLE);
            }
        }, 500);
    }

    @Override
    public void loadDataSuccess(DetailBean bean) {
        if (bean.getDatas().getParseXML() == 1){
            updateTime.setText(bean.getDatas().getUpdate_time());
            title.setText(bean.getDatas().getTitle());
            author.setText(bean.getDatas().getAuthor());
            leadText.setText(bean.getDatas().getLead());
            mHtmlParseUtil.loadHtml(bean.getDatas().getContent(), HtmlParseUtil.HtmlType.STRING, llDetailContent);
        }else{
            webView.setVisibility(View.VISIBLE);
            WebViewSetting.initWebSetting(webView);
            webView.loadUrl(collectionUrl);
        }
    }

    @Override
    public void loadDataFailed(String msg) {
        if (!TextUtils.isEmpty(msg)){
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
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
