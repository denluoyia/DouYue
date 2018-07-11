package com.denluoyia.douyue.manager.net;
import java.io.File;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Created by denluoyia
 * Date 2018/06/22
 * DouYue
 */
public class NetManager {
    private static final String TAG = "NetManager";
    private static final AtomicReference<NetManager> INSTANCE = new AtomicReference<>();
    private Map<String, IRequestCallback> mCallbackMap;

    public static NetManager getInstance(){
        for (; ;){
            NetManager netManager = INSTANCE.get();
            if (netManager != null) return netManager;
            netManager = new NetManager();
            if (INSTANCE.compareAndSet(null, netManager)) return netManager;
        }
    }

    private NetManager(){
        mCallbackMap = new ConcurrentHashMap<>();
    }


    /** GET 异步数据请求 */
    public static void get(String url, Map<String, String> paramMap, IRequestCallback requestCallback){
        OkHttpClientWrap.getInstance().get(url, paramMap, requestCallback);
    }

    /** POST 异步数据请求 */
    public static void post(String url, Map<String, String> paramMap, IRequestCallback requestCallback){
        OkHttpClientWrap.getInstance().post(url, paramMap, requestCallback);
    }



    public class FileDownloadCallback extends IRequestCallback<File>{

        private final String tag;

        public FileDownloadCallback(String tag){
            this.tag = tag;
        }

        @Override
        public void onStart() {
            if (mCallbackMap.get(tag) != null){
                mCallbackMap.get(tag).onStart();
            }
        }

        @Override
        public void onFailure(Exception e) {

        }

        @Override
        public void onSuccess(File bean) {

        }

        @Override
        public void onUpdateProgress(int writeSize, int totalSize, boolean isCompleted) {
            super.onUpdateProgress(writeSize, totalSize, isCompleted);
        }
    }

}
