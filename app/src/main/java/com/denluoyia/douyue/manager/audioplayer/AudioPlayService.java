package com.denluoyia.douyue.manager.audioplayer;

import android.app.Service;
import android.content.Intent;

import android.content.ServiceConnection;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by denluoyia
 * Date 2018/06/28
 * DouYue
 */
public class AudioPlayService extends Service implements IAudioPlayer{

    private final String TAG = getClass().getSimpleName();

    private AudioPlayer mPlayer;
    private LocalBinder mBinder = new LocalBinder();

    @Override
    public void onCreate() {
        Log.d(TAG,"--onCreate");
        super.onCreate();
        mPlayer = AudioPlayer.getInstance();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG,"--onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG,"--onBind");
        return mBinder;
    }

    @Override
    public void unbindService(ServiceConnection conn) {
        Log.d(TAG,"--unbindService");
        super.unbindService(conn);
    }

    @Override
    public boolean stopService(Intent name) {
        Log.d(TAG,"--stopService");
        return super.stopService(name);
    }

    @Override
    public boolean isPlaying() {
        return mPlayer.isPlaying();
    }

    @Override
    public void initStart(String url) {
        mPlayer.initStart(url);
    }

    @Override
    public void playOrPause() {
        mPlayer.playOrPause();
    }

    @Override
    public int getDuration() {
        return mPlayer.getDuration();
    }

    @Override
    public int getCurrentProgress() {
        return mPlayer.getCurrentProgress();
    }

    @Override
    public void seekTo(int progress) {
        mPlayer.seekTo(progress);
    }

    @Override
    public void releasePlayer() {
        mPlayer.releasePlayer();
    }

    public class LocalBinder extends Binder {
        public AudioPlayService getAudioPlayService(){
            return AudioPlayService.this;
        }
    }

}
