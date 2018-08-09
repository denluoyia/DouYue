package com.denluoyia.douyue.manager.audioplayer;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.denluoyia.douyue.R;
import com.denluoyia.douyue.utils.TimeUtil;
import com.denluoyia.douyue.utils.UIUtil;

/**
 * Created by denluoyia on Date 2018/07/27
 */
public class MyAudioController extends LinearLayout{

    private TextView timeCurr, timeTotal;
    private SeekBar seekBar;
    private ImageView btnPrev, btnNext, btnPlay;
    private String audioUrl;

    private boolean firstInit = true;


    private Activity mActivity;
    private AudioPlayService mAudioPlayService;

    /**===============外部调用设置=================== */
        public MyAudioController setActivity(Activity activity){
            this.mActivity = activity;
            bindAudioPlayService();
            return this;
        }

        public MyAudioController setAudioUrl(String audioUrl){
            this.audioUrl = audioUrl;
            return this;
        }
        //提供外部进行控制开始播放
        public void startPlay(){
            this.firstInit = false;
            mAudioPlayService.initStart(audioUrl);
            UIUtil.post(r);
            seekBar.setMax(mAudioPlayService.getDuration());
            mAudioPlayService.playOrPause();
            btnPlay.setImageResource(R.mipmap.play_btn_pause);
        }

    /**=================================== */

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mAudioPlayService = ((AudioPlayService.LocalBinder) service).getAudioPlayService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mAudioPlayService = null;
        }
    };

    private void bindAudioPlayService(){
        Intent intent = new Intent(mActivity, AudioPlayService.class);
        intent.setAction("com.denluoyia.douyue.Audio");
        mActivity.bindService(intent, connection, Context.BIND_AUTO_CREATE);
    }


    public MyAudioController(Context context) {
        this(context, null);
    }

    public MyAudioController(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.layout_audio_controller, this, true);
        initViews();
    }

    private void initViews(){
        timeCurr = findViewById(R.id.time_play_curr);
        timeTotal = findViewById(R.id.time_play_total);
        btnPrev = findViewById(R.id.btn_play_prev);
        btnNext = findViewById(R.id.btn_play_next);
        btnPlay = findViewById(R.id.btn_play_or_pause);
        seekBar = findViewById(R.id.seek_bar);

        btnPlay.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (firstInit){
                    mAudioPlayService.initStart(audioUrl);
                    UIUtil.post(r);
                    seekBar.setMax(mAudioPlayService.getDuration());
                    firstInit = false;
                }
                mAudioPlayService.playOrPause();
                btnPlay.setImageResource(mAudioPlayService.isPlaying() ? R.mipmap.play_btn_pause : R.mipmap.play_btn_play);
            }
        });

        btnPrev.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                int newCurrTime = mAudioPlayService.getCurrentProgress() - (int)(0.05 * mAudioPlayService.getDuration());
                mAudioPlayService.seekTo(newCurrTime < 0 ? 0 : newCurrTime);
                btnPlay.setImageResource(R.mipmap.play_btn_pause);
            }
        });

        btnNext.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                int newCurrTime2 = mAudioPlayService.getCurrentProgress() + (int)(0.05 * mAudioPlayService.getDuration());
                mAudioPlayService.seekTo(newCurrTime2 > mAudioPlayService.getDuration() ? mAudioPlayService.getDuration() : newCurrTime2);
                btnPlay.setImageResource(R.mipmap.play_btn_pause);
            }
        });
    }



    Runnable r = new Runnable() {
        @Override
        public void run() {
            timeCurr.setText(TimeUtil.formatPlayTime(mAudioPlayService.getCurrentProgress()));
            timeTotal.setText(TimeUtil.formatPlayTime(mAudioPlayService.getDuration()));
            seekBar.setProgress(mAudioPlayService.getCurrentProgress());
            seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    if (fromUser) {
                        mAudioPlayService.seekTo(seekBar.getProgress());
                    }
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                    UIUtil.removeCallbacksFromMainLooper(r);
                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    UIUtil.post(r);
                    btnPlay.setImageResource(R.mipmap.play_btn_pause);
                }
            });
            UIUtil.postDelayed(r, 1000);
        }
    };
}
