package com.denluoyia.douyue.presenter;

import com.denluoyia.douyue.constant.URLConstant;
import com.denluoyia.douyue.manager.net.IRequestCallback;
import com.denluoyia.douyue.manager.net.NetManager;
import com.denluoyia.douyue.model.BookSearchResultListBean;
import com.denluoyia.douyue.utils.LogUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * Created by denluoyia
 * Date 2018/07/02
 * DouYue
 */
public class SearchPresenter implements SearchContract.Presenter {

    private SearchContract.View mView;

    public SearchPresenter(SearchContract.View view){
        this.mView = view;
    }

    @Override
    public void requestSearch(String text) {
        HashMap<String, String> params = new LinkedHashMap<>();
        params.put("q", text);
        params.put("count", "10"); //默认搜索最多前10个
        NetManager.get(URLConstant.URL_BOOK_SEARCH, params, new IRequestCallback<BookSearchResultListBean>() {
            @Override
            public void onFailure(Exception e) {
                LogUtil.e("requestSearch", "failed");
                mView.searchDataFailure(e == null ? null : e.getMessage());
            }

            @Override
            public void onSuccess(BookSearchResultListBean bean) {
                LogUtil.e("requestSearch", "success");
                mView.searchDataSuccess(bean);
            }
        });
    }

    @Override
    public void detachView() {
        mView = null;
    }
}
