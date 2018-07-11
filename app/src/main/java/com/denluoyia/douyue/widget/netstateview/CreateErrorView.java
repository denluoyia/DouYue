package com.denluoyia.douyue.widget.netstateview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.denluoyia.douyue.R;


public class CreateErrorView extends LinearLayout{
    public CreateErrorView(Context context) {
        this(context, null);
    }

    public CreateErrorView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CreateErrorView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.layout_net_status_error, this, true);
    }
}
