package com.denluoyia.douyue.base;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.denluoyia.douyue.R;
import com.denluoyia.douyue.manager.EventManager;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by denluoyia
 * Date 2018/06/26
 * DouYue
 */
public abstract class BaseFragment extends Fragment{

    private final String TAG = getClass().getSimpleName();

    protected abstract void doBusiness();
    protected abstract int setContentViewId();
    private View mRootView;
    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        int contentViewId = setContentViewId();
        if (contentViewId == 0){
            throw new IllegalArgumentException("must set fragment's contentView!!!");
        }
        if (null != mRootView){
            ViewGroup parent = (ViewGroup) mRootView.getParent();
            if (null != parent){
                parent.removeView(mRootView);
            }
        }
        mRootView = inflater.inflate(contentViewId, container, false);
        unbinder = ButterKnife.bind(this, mRootView);
        EventManager.register(this);
        return mRootView;
    }

    protected View findViewById(@IdRes int id){
        return mRootView.findViewById(id);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.doBusiness();
    }

    @Override
    public void onDestroyView() {
        unbinder.unbind();
        EventManager.unregister(this);
        super.onDestroyView();
    }
}
