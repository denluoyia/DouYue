package com.denluoyia.douyue.base;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.denluoyia.douyue.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by denluoyia
 * Date 2018/07/03
 * DouYue
 */
public abstract class BaseFooterLoadRecyclerViewAdapter<T, VHH extends BaseItemViewHolder> extends RecyclerView.Adapter<BaseItemViewHolder> {

    protected final String TAG = this.getClass().getSimpleName();
    protected static final int FOOTER_TYPE = 0x01;
    protected static final int CONTENT_TYPE = 0x02;

    protected BaseActivity mActivity;
    protected List<T> mDataList = new ArrayList<>();

    protected boolean hasMore = true;
    protected boolean isError = false;

    public BaseFooterLoadRecyclerViewAdapter(BaseActivity activity){
        this.mActivity = activity;
        mDataList.clear();
    }

    public void setHasMore(boolean hasMore){
        this.hasMore = hasMore;
    }

    public void setIsError(boolean isError){
        this.isError = isError;
    }

    protected abstract VHH iCreateViewHolder(ViewGroup parent, int viewType);

    protected abstract void iBindViewHolder(VHH viewHolder, int position);

    @NonNull
    @Override
    public BaseItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        BaseItemViewHolder holder;
        if (viewType == FOOTER_TYPE){
            holder = new FooterViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_footer_item_layout, parent, false));
        }else{
            holder = this.iCreateViewHolder(parent, viewType);
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseItemViewHolder holder, int position) {
        if (holder instanceof FooterViewHolder){
            if (mDataList.size() == 0) return;
            FooterViewHolder footerHolder = (FooterViewHolder) holder;
            if (isError){
                isError = false;
                footerHolder.pbLoading.setVisibility(View.GONE);
                footerHolder.tvNoMoreTip.setVisibility(View.GONE);
                footerHolder.tvErrorTip.setVisibility(View.VISIBLE);
                footerHolder.tvErrorTip.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // 重新加载 ~ 回调接口
                    }
                });
            }

            if (hasMore){
                footerHolder.pbLoading.setVisibility(View.VISIBLE);
                footerHolder.tvNoMoreTip.setVisibility(View.GONE);
                footerHolder.tvErrorTip.setVisibility(View.GONE);
            }else{
                footerHolder.pbLoading.setVisibility(View.GONE);
                footerHolder.tvNoMoreTip.setVisibility(View.VISIBLE);
                footerHolder.tvErrorTip.setVisibility(View.GONE);
            }
        }else{
            VHH vhh = (VHH) holder;
            iBindViewHolder(vhh, position);
        }
    }


    /**
     * 加载初始
     */
    public void refreshDataList(List<T> dataList) {
        if(dataList == null) return;
        mDataList.clear();
        mDataList.addAll(dataList);
        notifyDataSetChanged();
    }


    /**
     * 加载更多
     */
    public void loadMoreDataList(List<T> dataList) {
        if(dataList == null || dataList.size() == 0) return;
        hasMore = true;
        int sizeBefore = mDataList.size();
        mDataList.addAll(dataList);
        notifyItemRangeInserted(sizeBefore, dataList.size());
    }

    /**
     * 移除指定位置的条目对象
     */
    public T removeItem(int position) {
        T t = null;
        if(position < mDataList.size() && position >= 0) {
            t = mDataList.remove(position);
            notifyDataSetChanged();
        }
        return t;
    }


    @Override
    public int getItemViewType(int position) {
        if (position + 1 == getItemCount()) return FOOTER_TYPE;
        return CONTENT_TYPE;
    }

    public T getItem(int position){
        return position < mDataList.size() ? mDataList.get(position) : null;
    }

    @Override
    public int getItemCount() {
        return mDataList.size() + 1;
    }

    public static class FooterViewHolder extends BaseItemViewHolder {
        @BindView(R.id.pb_loading)
        ProgressBar pbLoading;
        @BindView(R.id.tv_no_more_tip)
        TextView tvNoMoreTip;
        @BindView(R.id.tv_error_tip)
        TextView tvErrorTip;
        public FooterViewHolder(View itemView) {
            super(itemView);
        }
    }
}
