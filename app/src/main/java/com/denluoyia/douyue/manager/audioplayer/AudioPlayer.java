package com.denluoyia.douyue.manager.audioplayer;

import android.media.MediaPlayer;
import android.text.TextUtils;

import java.io.IOException;

/**
 * Created by denluoyia
 * Date 2018/06/29
 * DouYue
 */
public class AudioPlayer implements IAudioPlayer{

    private static volatile AudioPlayer sInstance;
    private MediaPlayer mPlayer;

    private AudioPlayer(){
        mPlayer = new MediaPlayer();
    }

    public static AudioPlayer getInstance(){
        if (null == sInstance){
            synchronized (AudioPlayer.class){
                if (null == sInstance){
                    sInstance = new AudioPlayer();
                }
            }
        }
        return sInstance;
    }

    @Override
    public boolean isPlaying() {
        return mPlayer.isPlaying();
    }

    @Override
    public void initStart(String url) {
        if (TextUtils.isEmpty(url)) return;
        mPlayer.reset();
        try {
            mPlayer.setDataSource(url);
            mPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void playOrPause() {
        if (mPlayer.isPlaying()){
            mPlayer.pause();
        }else {
            mPlayer.start();
        }
    }

    @Override
    public int getDuration() {
        return mPlayer.getDuration();
    }

    @Override
    public int getCurrentProgress() {
        return mPlayer.getCurrentPosition();
    }

    @Override
    public void seekTo(int progress) { //拖动之后立即播放
        mPlayer.seekTo(progress);
        if (!mPlayer.isPlaying()){
            mPlayer.start();
        }
    }

    @Override
    public void releasePlayer() {
        mPlayer.reset();
        mPlayer.release();
        sInstance = null;
    }


}
