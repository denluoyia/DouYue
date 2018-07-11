package com.denluoyia.douyue.presenter;

import com.denluoyia.douyue.base.BasePresenter;
import com.denluoyia.douyue.base.BaseView;
import com.denluoyia.douyue.model.db.MyCollectionBean;

import java.util.List;

/**
 * Created by denluoyia
 * Date 2018/07/05
 * DouYue
 */
public interface MyCollectionContract {

    interface Presenter extends BasePresenter {
        void getMyCollectionList();
    }

    interface View extends BaseView {
        void loadDataSuccess(List<MyCollectionBean> list);
    }
}
