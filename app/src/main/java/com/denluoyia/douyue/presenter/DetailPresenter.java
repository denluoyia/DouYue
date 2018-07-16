package com.denluoyia.douyue.presenter;

import com.denluoyia.douyue.manager.net.IRequestCallback;
import com.denluoyia.douyue.manager.net.NetManager;
import com.denluoyia.douyue.model.DetailBean;

import java.util.LinkedHashMap;

/**
 * Created by denluoyia
 * Date 2018/06/27
 * DouYue
 */
public class DetailPresenter implements DetailContract.Presenter {

    private DetailContract.View mView;
    public DetailPresenter(DetailContract.View view){
        this.mView = view;
    }

    @Override
    public void loadData(String postId) {
        LinkedHashMap<String, String> params = new LinkedHashMap<>();
        params.put("post_id", postId);
        NetManager.get("http://static.owspace.com/?c=api&a=getPost&show_sdv=1&", params, new IRequestCallback<DetailBean>() {
            @Override
            public void onFailure(Exception e) {
                mView.loadDataFailed(e == null ? null : e.getMessage());
            }

            @Override
            public void onSuccess(DetailBean bean) {
                if (bean == null || bean.getDatas() == null){
                    mView.loadDataFailed(bean != null ? bean.getMsg() : null);
                    return;
                }
                mView.loadDataSuccess(bean);
            }

        });
    }

    @Override
    public void detachView() {
        mView = null;
    }
}
