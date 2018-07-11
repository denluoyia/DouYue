package com.denluoyia.douyue.presenter;

import com.denluoyia.douyue.base.BasePresenter;
import com.denluoyia.douyue.base.BaseView;
import com.denluoyia.douyue.model.ItemListBean;

/**
 * Created by denluoyia
 * Date 2018/06/26
 * DouYue
 */
public interface ItemListContract {

    interface Presenter extends BasePresenter{
        void loadInitData();
        void loadMoreData();
    }

    interface View extends BaseView {

        void loadDataFailed(String msg);

        void loadInitDataSuccess(ItemListBean bean);

        void loadMoreDataSuccess(ItemListBean bean);

    }
}
