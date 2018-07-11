package com.denluoyia.douyue.widget.netstateview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.denluoyia.douyue.R;


public class CreateEmptyView extends LinearLayout{


    public CreateEmptyView(@NonNull Context context) {
        this(context, null);
    }

    public CreateEmptyView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CreateEmptyView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.layout_net_status_empty, this, true);
    }

    public void setTextHint(String textHint){
        TextView tv = findViewById(R.id.status_view_hint);
        tv.setText(textHint);
    }

}
