package com.denluoyia.douyue.widget.netstateview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.denluoyia.douyue.R;


public class CreateNetErrorView extends LinearLayout {
    public CreateNetErrorView(Context context) {
        this(context, null);
    }

    public CreateNetErrorView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CreateNetErrorView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.layout_net_status_net_error, this, true);
    }

    public void setTextHint(String msg){
        TextView tv = findViewById(R.id.status_view_hint);
        tv.setText(msg);
    }
}
