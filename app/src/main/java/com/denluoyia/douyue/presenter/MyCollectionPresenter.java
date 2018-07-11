package com.denluoyia.douyue.presenter;

import com.denluoyia.douyue.manager.db.greendao.MyCollectionDaoManager;

/**
 * Created by denluoyia
 * Date 2018/07/05
 * DouYue
 */
public class MyCollectionPresenter implements MyCollectionContract.Presenter {

    private MyCollectionContract.View mView;

    public MyCollectionPresenter(MyCollectionContract.View view){
        this.mView = view;
    }

    @Override
    public void getMyCollectionList() {
       mView.loadDataSuccess(MyCollectionDaoManager.queryAll());
    }

    @Override
    public void detachView() {
        mView = null;
    }
}
