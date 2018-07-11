package com.denluoyia.douyue.view.activity;


import com.denluoyia.douyue.R;
import com.denluoyia.douyue.base.BaseActivity;
import com.jia.jsplayer.listener.OnJsVideoControlListener;
import com.jia.jsplayer.utils.DisplayUtils;
import com.jia.jsplayer.video.JsPlayer;

import butterknife.BindView;

public class VideoTestActivity extends BaseActivity {

    @BindView(R.id.js_player)
    JsPlayer jsPlayer;

    @Override
    protected int setContentViewId() {
        return R.layout.activity_video_test;
    }

    @Override
    protected void doBusiness() {

        jsPlayer.setPath(new com.jia.jsplayer.bean.IVideoInfo() {
            @Override
            public String getVideoTitle() {
                return "视频标题";
            }

            @Override
            public String getVideoPath() {
                return "http://img.owspace.com/V_lvd742315_1530261148.5771452.mp4";
            }
        });
        jsPlayer.setOnVideoControlListener(new OnJsVideoControlListener(){
            @Override
            public void onStartPlay() {
                jsPlayer.startPlay();
            }

            @Override
            public void onBack() {

            }

            @Override
            public void onFullScreen() {
                DisplayUtils.toggleScreenOrientation(VideoTestActivity.this);
            }

            @Override
            public void onRetry(int errorStatus) {

            }
        });

    }

    @Override
    protected void onStop() {
        jsPlayer.onStop();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        jsPlayer.onDestroy();
        super.onDestroy();
    }
}
