package com.denluoyia.douyue.base;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by denluoyia
 * Date 2018/07/04
 * DouYue
 */
public abstract class BaseRecyclerViewAdapter<T, VHH extends BaseItemViewHolder> extends RecyclerView.Adapter<VHH> {

    protected List<T> mDataList = new ArrayList<>();
    protected BaseActivity mActivity;

    public BaseRecyclerViewAdapter(BaseActivity mActivity) {
        this.mActivity = mActivity;
        mDataList.clear();
    }

    public void refreshDataList(List<T> dataList){
        mDataList.clear();
        if (dataList != null && dataList.size() > 0) {
            mDataList.addAll(dataList);
        }
        notifyDataSetChanged();
    }


    public void loadMoreDataList(List<T> dataList){
        if (dataList == null || dataList.size() == 0) return;
        int beforeSize = mDataList.size();
        mDataList.addAll(dataList);
        notifyItemRangeChanged(beforeSize, dataList.size());
    }

    protected abstract VHH iCreateViewHolder(@NonNull ViewGroup parent, int viewType);
    protected abstract void iBindViewHolder(@NonNull VHH holder, int position);
    protected abstract void onItemClickListener(View view, int position);
    protected abstract boolean onItemLongClickListener(View view, int position);

    @NonNull
    @Override
    public VHH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return iCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull VHH holder, int position) {
        holder.itemView.setTag(holder.itemView.getId(), position);
        holder.itemView.setOnClickListener(mInnerClickListener);
        holder.itemView.setOnLongClickListener(mInnerLongClickListener);
        iBindViewHolder(holder, position);
    }

    public T getItem(int position){
        return position < mDataList.size() ? mDataList.get(position) : null;
    }

    public List<T> getList(){
        return mDataList;
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    private View.OnClickListener mInnerClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int position = (int) v.getTag(v.getId());
            if (getItem(position) == null) return;
            onItemClickListener(v, position);
        }
    };

    private View.OnLongClickListener mInnerLongClickListener = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            int position = (int) v.getTag();
            return getItem(position) == null || onItemLongClickListener(v, position);
        }
    };
}
