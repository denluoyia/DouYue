package com.denluoyia.douyue.presenter;

import com.denluoyia.douyue.base.BasePresenter;
import com.denluoyia.douyue.base.BaseView;
import com.denluoyia.douyue.model.DetailBean;

/**
 * Created by denluoyia
 * Date 2018/06/27
 * DouYue
 */
public interface DetailContract {
    interface Presenter extends BasePresenter {
        void loadData(String postId);
    }

    interface View extends BaseView {
        void loadDataSuccess(DetailBean bean);
        void loadDataFailed(String msg);
    }
}
