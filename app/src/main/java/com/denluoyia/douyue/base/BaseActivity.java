package com.denluoyia.douyue.base;

import android.app.Activity;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.denluoyia.douyue.DouYueApp;
import com.denluoyia.douyue.R;
import com.denluoyia.douyue.manager.EventManager;
import com.denluoyia.douyue.utils.UIUtil;

import java.lang.ref.WeakReference;

import butterknife.ButterKnife;

/**
 * Created by denluoyia
 * Date 2018/06/27
 * DouYue
 */
public abstract class BaseActivity extends AppCompatActivity{

    private final String TAG = getClass().getSimpleName();
    protected abstract int setContentViewId();
    protected abstract void doBusiness();
    private WeakReference<Activity> activityWeakReference = null;
    private DouYueApp mApplication;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int contentViewId = setContentViewId();
        if (contentViewId == 0){
            throw new IllegalArgumentException("must set Activity's contentView!!!");
        }
        setContentView(contentViewId);
        mApplication = (DouYueApp) DouYueApp.getApplication();
        activityWeakReference = new WeakReference<Activity>(this);
        mApplication.pushTask(activityWeakReference);
        ButterKnife.bind(this);
        EventManager.register(this);
        this.doBusiness();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventManager.unregister(this);
        if(mApplication != null && activityWeakReference != null) {
            mApplication.removeTask(activityWeakReference);
        }
    }

    protected void initToolbar(Toolbar toolbar){
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("");
        actionBar.setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    /** 设置应用字体不受系统字体大小调节的影响 */
    @Override
    public Resources getResources() {
        Resources     res           = super.getResources();
        Configuration configuration = new Configuration();
        configuration.setToDefaults();
        res.updateConfiguration(configuration, res.getDisplayMetrics());
        return res;
    }


}
