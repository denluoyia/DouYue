package com.denluoyia.douyue.widget.netstateview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import com.denluoyia.douyue.R;


public class CreateLoadingView extends FrameLayout {

    public CreateLoadingView(@NonNull Context context) {
        this(context, null);
    }

    public CreateLoadingView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CreateLoadingView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.layout_net_status_loading, this, true);
    }
}
