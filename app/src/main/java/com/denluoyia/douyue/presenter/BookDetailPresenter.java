package com.denluoyia.douyue.presenter;

import com.denluoyia.douyue.constant.URLConstant;
import com.denluoyia.douyue.manager.net.IRequestCallback;
import com.denluoyia.douyue.manager.net.NetManager;
import com.denluoyia.douyue.model.BookItemBean;

import java.io.IOException;

/**
 * Created by denluoyia
 * Date 2018/07/02
 * DouYue
 */
public class BookDetailPresenter implements BookDetailContract.Presenter {

    private BookDetailContract.View mView;

    public BookDetailPresenter(BookDetailContract.View view){
        this.mView = view;
    }

    @Override
    public void requestData(String bookId) {
        String url = URLConstant.URL_BOOK_INFO + bookId;
        NetManager.get(url, null, new IRequestCallback<BookItemBean>() {
            @Override
            public void onFailure(Exception e) {
                mView.loadDataFailed(e == null ? null : e.getMessage());
            }

            @Override
            public void onSuccess(BookItemBean bean) {
                mView.loadDataSuccess(bean);
            }
        });
    }

    @Override
    public void detachView() {
        mView  = null;
    }
}
