package com.denluoyia.douyue.presenter;

import com.denluoyia.douyue.base.BasePresenter;
import com.denluoyia.douyue.base.BaseView;

/**
 * Created by denluoyia
 * Date 2018/06/25
 * DouYue
 */
public interface SplashContract {

    interface Presenter extends BasePresenter{
        void getSplashImages();
    }

    interface View extends BaseView {

    }
}
