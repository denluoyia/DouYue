package com.denluoyia.douyue.presenter;

import android.text.TextUtils;

import com.denluoyia.douyue.constant.URLConstant;
import com.denluoyia.douyue.manager.net.IRequestCallback;
import com.denluoyia.douyue.manager.net.NetManager;
import com.denluoyia.douyue.model.ItemListBean;
import com.denluoyia.douyue.utils.TimeUtil;

import java.io.IOException;
import java.util.LinkedHashMap;

/**
 * Created by denluoyia
 * Date 2018/06/26
 * DouYue
 */
public class ItemListPresenter implements ItemListContract.Presenter{

    private ItemListContract.View mView;
    private int mCurrPage = 1;
    private int mModelType;

    public ItemListPresenter(ItemListContract.View view, int type){
        this.mView = view;
        this.mModelType = type;
    }

    @Override
    public void loadInitData() {
        mCurrPage = 1;
        request();
    }

    @Override
    public void loadMoreData() {
        mCurrPage++;
        request();
    }

    private void request(){
        LinkedHashMap<String, String> params = new LinkedHashMap<>();
        params.put("p", String.valueOf(mCurrPage));
        params.put("model", String.valueOf(mModelType));
        params.put("time", TimeUtil.getTimeStamp());
        params.put("device_id", "866963027059338");
        NetManager.get(URLConstant.URL_CATEGORIES_LIST, params, new IRequestCallback<ItemListBean>() {
            @Override
            public void onFailure(Exception e) {
                if (mView != null){
                    mView.loadDataFailed(e == null ? null : e.getMessage());
                }
            }

            @Override
            public void onSuccess(ItemListBean bean) {
                if (bean == null || bean.getDatas() == null){
                    mView.loadDataFailed((bean != null && !TextUtils.isEmpty(bean.getMsg())) ? bean.getMsg() : null);
                    return;
                }
                if (mCurrPage == 1){
                    mView.loadInitDataSuccess(bean);
                }else{
                    mView.loadMoreDataSuccess(bean);
                }
            }
        });
    }

    public int getCurrPage(){
        return mCurrPage;
    }

    @Override
    public void detachView() {
        mView = null;
    }
}
