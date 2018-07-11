package com.denluoyia.douyue.manager.net;

import com.denluoyia.douyue.utils.LogUtil;
import com.denluoyia.douyue.utils.StringUtil;

import java.io.File;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Function: OKHttpClient的配置封装
 * Created by denluoyia
 * Date 2018/06/22
 * DouYue
 */
@SuppressWarnings("all")
public class OkHttpClientWrap {

    private OkHttpClient mOkHttpClient;
    private OkHttpClient.Builder  mBuilder;
    private static final AtomicReference<OkHttpClientWrap> INSTANCE = new AtomicReference<>();

    private static final int DEFAULT_CONNECT_TIMEOUT = 10;
    private static final int DEFAULT_READ_TIMEOUT = 15;
    private static final int DEFAULT_WRITE_TIMEOUT = 30;


    public static OkHttpClientWrap getInstance(){
        for (; ;){
            OkHttpClientWrap okHttpClientWrap = INSTANCE.get();
            if (okHttpClientWrap != null) return okHttpClientWrap;
            okHttpClientWrap = new OkHttpClientWrap();
            if (INSTANCE.compareAndSet(null, okHttpClientWrap)) return okHttpClientWrap;
        }
    }

    private OkHttpClientWrap(){
        mBuilder = new OkHttpClient.Builder();
        mBuilder.connectTimeout(DEFAULT_CONNECT_TIMEOUT, TimeUnit.SECONDS);
        mBuilder.readTimeout(DEFAULT_READ_TIMEOUT, TimeUnit.SECONDS);
        mBuilder.writeTimeout(DEFAULT_WRITE_TIMEOUT, TimeUnit.SECONDS);
        mBuilder.hostnameVerifier(new MyHostnameVerifier());
        mBuilder.sslSocketFactory(createSSLSocketFactory());
        mOkHttpClient = mBuilder.build();
    }


    /**====================================================================*/
        // 异步get请求
        public void get(String url, Map<String, String> paramMap, IRequestCallback requestCallback) {
            if (paramMap != null && paramMap.size() > 0) {
                StringBuilder urlBuilder = new StringBuilder(url);
                for(Map.Entry<String, String> entry : paramMap.entrySet()) {
                    urlBuilder.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
                }
                url = urlBuilder.toString();
                url = url.substring(0, url.length()-1);
            }

            Request.Builder builder = new Request.Builder();
            Request request = builder.get().url(url).build();
            requestCallback.onStart();
            LogUtil.e("NetManager-get", url);
            mOkHttpClient.newCall(request).enqueue(requestCallback);
        }


        //异步post请求
        public void post(String url, Map<String, String> paramMap, IRequestCallback requestCallback){
            RequestBody requestBody = setRequestBody(paramMap);
            Request.Builder builder = new Request.Builder();
            Request request = builder.post(requestBody).url(url).build();
            requestCallback.onStart();
            mOkHttpClient.newCall(request).enqueue(requestCallback);
        }


    /**====================================================================*/

    /**=================通用处理get post的参数规范==============================*/
    private void detailGetUrl(){

    }

    private void detailPostUrl(){

    }
    /**====================================================================*/

    /**
     * 处理请求参数
     * @param paramMap 请求参数
     * @return RequestBody
     */
    private static RequestBody setRequestBody(Map<String, String> paramMap){
        FormBody.Builder builder = new FormBody.Builder();
        if (paramMap == null) paramMap = new LinkedHashMap<>();
        for(Map.Entry<String, String> entry : paramMap.entrySet()) {
            builder.add(entry.getKey(), entry.getValue());
        }
        return builder.build();
    }


    private class MyHostnameVerifier implements HostnameVerifier {
        @Override
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    }

    /**
     * 生成安全套接字工厂，用于https请求的证书跳过
     * @return SSLSocketFactory
     */
    private SSLSocketFactory createSSLSocketFactory() {
        SSLSocketFactory ssfFactory = null;
        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, new TrustManager[]{new TrustAllCerts()}, new SecureRandom());
            ssfFactory = sc.getSocketFactory();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ssfFactory;
    }

    /**
     * 用于信任所有证书
     */
   class TrustAllCerts implements X509TrustManager {
        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException { }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException { }

        @Override
        public X509Certificate[] getAcceptedIssuers() { return new X509Certificate[0]; }
    }

}
