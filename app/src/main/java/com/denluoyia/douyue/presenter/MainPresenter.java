package com.denluoyia.douyue.presenter;

public class MainPresenter implements MainContract.Presenter{

    private MainContract.View mView;

    public MainPresenter(MainContract.View view){
        this.mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }
}
