package com.denluoyia.douyue.manager.net;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.badoo.mobile.util.WeakHandler;
import com.denluoyia.douyue.manager.JsonManager;
import com.denluoyia.douyue.utils.LogUtil;
import com.denluoyia.douyue.utils.NetworkUtil;
import com.denluoyia.douyue.utils.StringUtil;
import com.denluoyia.douyue.utils.UIUtil;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Function: 网络请求回调接口 支持Json数据对象解析
 * Created by denluoyia
 * Date 2018/06/22
 * DouYue
 */
public abstract class IRequestCallback<T> implements Callback{

    private static final int SUCCESS_CODE = 0X01;
    private static final int FAILURE_CODE = 0x02;
    private WeakHandler mHandler = new WeakHandler(Looper.getMainLooper(), new UICallback<T>(this));

    public void onStart() { }

    public abstract void onFailure(Exception e);

    public abstract void onSuccess(T bean);

    public void onUpdateProgress(int writeSize, int totalSize, boolean isCompleted){

    }


    public final Type mClassType;

    public IRequestCallback() {
        mClassType = getSuperclassTypeParameter(getClass()); //获取泛型参数类型 T
    }

    static Type getSuperclassTypeParameter(Class<?> subclass) {
        Type superclass = subclass.getGenericSuperclass();
        if (superclass instanceof Class) {
            throw new RuntimeException("Missing type parameter.");
        }
        return ((ParameterizedType) superclass).getActualTypeArguments()[0];
    }

    @Override
    public void onFailure(Call call, IOException e) {
        Message msg = Message.obtain();
        msg.what = FAILURE_CODE;
        msg.obj = e;
        mHandler.sendMessage(msg);
    }

    @Override
    public void onResponse(Call call, Response response) {
        if (response.isSuccessful()){
            String responseResult = null;
            try {
                responseResult  = response.body().string();
                LogUtil.e("requestData", StringUtil.unicodeToUTF_8(responseResult));
            } catch (IOException e) {
                e.printStackTrace();
            }
            T result = JsonManager.parseType(responseResult, mClassType);
            Message msg = Message.obtain();
            msg.what = SUCCESS_CODE;
            msg.obj = result;
            mHandler.sendMessage(msg);
        }else{
            Message msg = Message.obtain();
            msg.what = FAILURE_CODE;
            mHandler.sendMessage(msg);
        }
    }

    class UICallback<T> implements Handler.Callback {

        private IRequestCallback<T> mCallback;

        public UICallback(IRequestCallback<T> callback){
            this.mCallback = callback;
        }

        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what){
                case SUCCESS_CODE:
                    if (msg.obj != null){
                        if (mCallback != null){
                            mCallback.onSuccess((T) msg.obj);
                        }
                    }
                    break;
                case FAILURE_CODE:
                    Exception e = null;
                    if (!NetworkUtil.isNetworkConnected(UIUtil.getContext())){
                        e = new IOException("网络似乎有问题"); //先判断网络
                    } else if(msg.obj != null && msg.obj instanceof IOException){
                        e = (Exception) msg.obj;
                    }else{
                        new Exception("response is wrong");
                    }

                    if (mCallback != null){
                        mCallback.onFailure(e);
                    }
                    break;
            }
            return true;
        }
    }

}
