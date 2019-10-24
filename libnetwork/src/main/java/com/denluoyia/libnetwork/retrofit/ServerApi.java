package com.denluoyia.libnetwork.retrofit;

import com.denluoyia.libnetwork.model.BookItemBean;
import com.denluoyia.libnetwork.model.ItemListBean;
import com.google.gson.annotations.SerializedName;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by denluoyia on 2019/3/18
 * Description: Retrofit网络数据请求接口
 */
public interface ServerApi {

    /**
     * https://api.douban.com/v2/book/24934182
     */
    public static final String URL_BOOK_INFO = "https://api.douban.com/v2/book/";

    @GET("/{id}")
    Observable<BookItemBean> getBookDetail(@Path(value = "id") String id);

}
