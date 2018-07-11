package com.denluoyia.douyue.view.activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.denluoyia.douyue.R;
import com.denluoyia.douyue.base.BaseActivity;
import com.denluoyia.douyue.constant.Constant;
import com.denluoyia.douyue.manager.db.greendao.MyCollectionDaoManager;
import com.denluoyia.douyue.model.ItemListBean;
import com.denluoyia.douyue.model.db.MyCollectionBean;
import com.denluoyia.douyue.utils.NetworkUtil;
import com.denluoyia.douyue.utils.WebViewSetting;

import butterknife.BindView;
import butterknife.OnClick;

public class VideoDetailActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.iv_top)
    ImageView imageViewTop;
    @BindView(R.id.video_view)
    VideoView videoView;
    @BindView(R.id.btn_init_play)
    ImageView btnInitPlay;
    @BindView(R.id.fl_layout)
    FrameLayout fl_layout;
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

    private String postId;
    private ItemListBean.ListBean item;

    @Override
    protected int setContentViewId() {
        this.overridePendingTransition(R.anim.translate_bottom_in, R.anim.translate_bottom_out);
        return R.layout.activity_video_detail;
    }

    String videoUrl;
    private String collectionUrl;

    @Override
    protected void doBusiness() {
        item = getIntent().getParcelableExtra(Constant.INTENT_ACTION_DATA_KEY);
        if (item == null || TextUtils.isEmpty(item.getId())) return;
        postId = item.getId();
        videoUrl = item.getVideo();
        initToolbar(mToolbar);
        Glide.with(this).load(item.getThumbnail()).into(imageViewTop);
        ivCollect.setBackgroundResource(MyCollectionDaoManager.isCollectionExists(postId) ? R.mipmap.ic_collection : R.mipmap.ic_un_collection);
        WebViewSetting.initWebSetting(webView);
        collectionUrl = WebViewSetting.addParams2DetailUrl(this, item.getHtml5(), true);
        webView.loadUrl(collectionUrl);
    }

    @OnClick({R.id.btn_init_play, R.id.iv_collect})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.btn_init_play:
                if (!NetworkUtil.isWifi(this) && NetworkUtil.isNetworkConnected(this)){
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
                if (MyCollectionDaoManager.isCollectionExists(postId)){
                    ivCollect.setBackgroundResource(R.mipmap.ic_un_collection);
                    MyCollectionDaoManager.deleteById(postId);
                    Toast.makeText(this, "取消收藏成功", Toast.LENGTH_SHORT).show();
                    return;
                }

                MyCollectionBean bean = new MyCollectionBean();
                bean.setCollectionId(postId);
                bean.setCollectionType(Constant.MY_COLLECTION_TYPE_VIDEO);
                bean.setTitle(item.getTitle());
                bean.setUrl(collectionUrl);
                MyCollectionDaoManager.insert(bean);
                ivCollect.setBackgroundResource(R.mipmap.ic_collection);
                Toast.makeText(this, "收藏成功", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void startPlay(){
        Uri uri = Uri.parse(videoUrl);//将路径转换成uri
        videoView.setVideoURI(uri);//为视频播放器设置视频路径
        videoView.setMediaController(new MediaController(this));//显示控制栏
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                videoView.start();//开始播放视频
            }
        });
        fl_layout.setVisibility(View.GONE);
    }


    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, R.anim.translate_bottom_out);
    }
}
