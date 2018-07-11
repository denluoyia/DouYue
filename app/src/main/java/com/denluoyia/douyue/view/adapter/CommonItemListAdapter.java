package com.denluoyia.douyue.view.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.denluoyia.douyue.R;
import com.denluoyia.douyue.base.BaseActivity;
import com.denluoyia.douyue.base.BaseFooterLoadRecyclerViewAdapter;
import com.denluoyia.douyue.base.BaseItemViewHolder;
import com.denluoyia.douyue.constant.Constant;
import com.denluoyia.douyue.model.ItemListBean;
import com.denluoyia.douyue.utils.ItemAnimUtil;
import com.denluoyia.douyue.view.activity.ArticleDetailActivity;
import com.denluoyia.douyue.view.activity.AudioDetailActivity;
import com.denluoyia.douyue.view.activity.VideoDetailActivity;

import butterknife.BindView;

/**
 * Created by denluoyia
 * Date 2018/06/26
 * DouYue
 */
public class CommonItemListAdapter extends BaseFooterLoadRecyclerViewAdapter<ItemListBean.ListBean, CommonItemListAdapter.ItemViewHolder> {

    private ItemAnimUtil itemAnimUtil = new ItemAnimUtil();

    public CommonItemListAdapter(BaseActivity activity) {
        super(activity);
    }

    @Override
    protected ItemViewHolder iCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_layout, parent, false));
    }

    @Override
    protected void iBindViewHolder(final ItemViewHolder viewHolder, int position) {
        final ItemListBean.ListBean item = mDataList.get(position);
        if (item == null) return;
        itemAnimUtil.addAnimation(viewHolder);
        viewHolder.tvTitle.setText(item.getTitle());
        viewHolder.tvAuthor.setText(item.getAuthor());
        Glide.with(mActivity).load(item.getThumbnail()).into(viewHolder.iv);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAction(mActivity, viewHolder.iv, item);
            }
        });
    }

    public static class ItemViewHolder extends BaseItemViewHolder {
        @BindView(R.id.iv)
        ImageView iv;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_author)
        TextView tvAuthor;
        public ItemViewHolder(View itemView) {
            super(itemView);
        }
    }

    public void startAction(Context mContext, View view, ItemListBean.ListBean item) {
        Intent intent;
        if (!TextUtils.isEmpty(item.getFm())){
            intent = new Intent(mContext, AudioDetailActivity.class);
        }else if (!TextUtils.isEmpty(item.getVideo())){
            intent = new Intent(mContext, VideoDetailActivity.class);
        }else{
            intent = new Intent(mContext, ArticleDetailActivity.class);
        }
        intent.putExtra(Constant.INTENT_ACTION_DATA_KEY, item);
        mContext.startActivity(intent);

    }
}

