package com.denluoyia.douyue.presenter;

import com.denluoyia.douyue.base.BasePresenter;
import com.denluoyia.douyue.base.BaseView;
import com.denluoyia.douyue.model.BookSearchResultListBean;

/**
 * Created by denluoyia
 * Date 2018/07/02
 * DouYue
 */
public interface SearchContract {
    interface Presenter extends BasePresenter {
        void requestSearch(String text);
    }

    interface View extends BaseView {
        void searchDataFailure(String msg);
        void searchDataSuccess(BookSearchResultListBean bean);
    }
}
