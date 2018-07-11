package com.denluoyia.douyue.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import butterknife.ButterKnife;

/**
 * Created by denluoyia
 * Date 2018/06/27
 * DouYue
 */
public class BaseItemViewHolder extends RecyclerView.ViewHolder{

    public BaseItemViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
