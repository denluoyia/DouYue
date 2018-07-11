package com.denluoyia.douyue.presenter;

import com.denluoyia.douyue.base.BasePresenter;
import com.denluoyia.douyue.base.BaseView;
import com.denluoyia.douyue.model.BookItemBean;

/**
 * Created by denluoyia
 * Date 2018/07/02
 * DouYue
 */
public interface BookDetailContract {

    interface Presenter extends BasePresenter{
        void requestData(String bookId);
    }

    interface View extends BaseView {
        void loadDataFailed(String msg);
        void loadDataSuccess(BookItemBean bean);
    }
}
