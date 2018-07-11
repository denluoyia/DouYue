package com.denluoyia.douyue.view.adapter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.denluoyia.douyue.R;
import com.denluoyia.douyue.base.BaseActivity;
import com.denluoyia.douyue.base.BaseItemViewHolder;
import com.denluoyia.douyue.base.BaseRecyclerViewAdapter;
import com.denluoyia.douyue.constant.Constant;
import com.denluoyia.douyue.model.db.MyCollectionBean;
import com.denluoyia.douyue.view.activity.BookDetailActivity;
import com.denluoyia.douyue.view.activity.CommonWebActivity;

import javax.sql.CommonDataSource;

import butterknife.BindView;

/**
 * Created by denluoyia
 * Date 2018/07/05
 * DouYue
 */
public class MyCollectionAdapter extends BaseRecyclerViewAdapter<MyCollectionBean, MyCollectionAdapter.ItemViewHolder> {


    public MyCollectionAdapter(BaseActivity mActivity) {
        super(mActivity);
    }

    @Override
    protected ItemViewHolder iCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_my_collection, parent, false));
    }

    @Override
    protected void iBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        MyCollectionBean item = getItem(position);
        holder.tvType.setText(Constant.MyCollectionTypeArray.get(item.getCollectionType()));
        holder.tvTitle.setText(item.getTitle().replace("\n", ""));
    }

    @Override
    protected void onItemClickListener(View view, int position) {
        MyCollectionBean item = getItem(position);
        Intent intent;
        if (item.getCollectionType() == 3){
            intent = new Intent(mActivity, BookDetailActivity.class);
            intent.putExtra(Constant.INTENT_ACTION_DATA_KEY, item.getCollectionId());
        }else{
            intent = new Intent(mActivity, CommonWebActivity.class);
            intent.putExtra(Constant.INTENT_ACTION_DATA_KEY, item.getUrl());
        }

        mActivity.startActivity(intent);
    }

    @Override
    protected boolean onItemLongClickListener(View view, int position) {
        return true;
    }

    public static class ItemViewHolder extends BaseItemViewHolder{
        @BindView(R.id.tv_type)
        TextView tvType;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        public ItemViewHolder(View itemView) {
            super(itemView);
        }
    }
}
