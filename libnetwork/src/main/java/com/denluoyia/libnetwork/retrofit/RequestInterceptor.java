package com.denluoyia.libnetwork.retrofit;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by luozhenqin on 2019/10/24
 */
public class RequestInterceptor implements Interceptor {
    private static final String REFERER_FIELD_NAME = "Referer";
    private static final String ACCEPT_FIELD_NAME = "Accept";
    private static final String ACCEPT_FIELD_VALUE = "application/json";
    private static final String REQUEST_BY_APP_FIELD_NAME = "requestByApp";

    private final String baseUrl;

    public RequestInterceptor(String baseUrl){
        this.baseUrl = baseUrl;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Request.Builder builder = request.newBuilder()
                .addHeader(REFERER_FIELD_NAME, baseUrl)
                .addHeader(ACCEPT_FIELD_NAME, ACCEPT_FIELD_VALUE)
                .addHeader(REQUEST_BY_APP_FIELD_NAME, "true");

        Request newRequest = builder.build();
        return chain.proceed(newRequest);
    }


}
