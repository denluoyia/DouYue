package com.denluoyia.douyue.manager.audioplayer;

/**
 * Created by denluoyia
 * Date 2018/06/28
 * DouYue
 */
public interface IAudioPlayer {

    boolean isPlaying();

    void initStart(String url);

    void playOrPause();

    int getDuration();

    int getCurrentProgress();

    void seekTo(int progress);

    void releasePlayer();
}
