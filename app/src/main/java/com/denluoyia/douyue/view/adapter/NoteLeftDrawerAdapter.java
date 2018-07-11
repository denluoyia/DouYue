package com.denluoyia.douyue.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.denluoyia.douyue.R;

import java.util.ArrayList;
import java.util.List;

/**
 * function: 左抽屉数据适配器
 * Created by lzq on 2017/5/22.
 */

public class NoteLeftDrawerAdapter extends BaseAdapter {

    private Context mContext;
    private List<String> mDataList = new ArrayList<>();

    public NoteLeftDrawerAdapter(Context context, List<String> dataList){
        this.mContext = context;
        if (dataList == null || dataList.size() == 0) return;
        mDataList.clear();
        mDataList.addAll(dataList);
    }

    @Override
    public int getCount() {
        return mDataList.size();
    }

    @Override
    public Object getItem(int i) {
        if (i < 0 || i >= mDataList.size()) return null;
        return mDataList.get(i);
    }


    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        String item = (String) getItem(i);
        if (item == null) return null;

        if (view == null){
            view = LayoutInflater.from(mContext).inflate(R.layout.item_left_drawer, viewGroup, false);
        }
        TextView tv = (TextView) view.findViewById(R.id.textView);
        tv.setText(item);
        return view;
    }
}
