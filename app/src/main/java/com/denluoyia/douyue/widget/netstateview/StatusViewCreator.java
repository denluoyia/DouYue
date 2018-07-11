package com.denluoyia.douyue.widget.netstateview;

import android.content.Context;
import android.view.View;

/**
 * Created by denluoyia
 * Date 2018/07/03
 * DouYue
 */
public abstract class StatusViewCreator {

    /** loading layout */
    public View createLoadingLayout(Context context){
        return new CreateLoadingView(context);
    }

    /** empty layout */
    public View createEmptyLayout(Context context){
        return new CreateEmptyView(context);
    }

    /** error layout */
    public View createErrorLayout(Context context){
        return new CreateErrorView(context);
    }

    /** net error layout */
    public View createNetErrorLayout(Context context){
        return new CreateNetErrorView(context);
    }
}
