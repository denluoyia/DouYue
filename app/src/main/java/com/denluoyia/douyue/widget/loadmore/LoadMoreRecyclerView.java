package com.denluoyia.douyue.widget.loadmore;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

public class LoadMoreRecyclerView extends RecyclerView{

    private ILoadMoreListener mLoadMoreListener;

    public LoadMoreRecyclerView(Context context) {
        super(context);
    }

    public LoadMoreRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public LoadMoreRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setLoadMoreListener(ILoadMoreListener loadMoreListener){
        this.mLoadMoreListener = loadMoreListener;
    }


    /**
     * 滑动到最后一个item的时候出发加载更多
     * @param dx
     * @param dy
     */
    @Override
    public void onScrolled(int dx, int dy) {
        super.onScrolled(dx, dy);

        int lastVisibleItemPosition;
        LayoutManager layoutManager = getLayoutManager();
        if (layoutManager instanceof GridLayoutManager){
            lastVisibleItemPosition = ((GridLayoutManager) layoutManager).findLastVisibleItemPosition();
        }else if (layoutManager instanceof LinearLayoutManager){
            lastVisibleItemPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
        }else{
            return; //不处理交错布局
        }
        if (lastVisibleItemPosition + 1 == getAdapter().getItemCount()){
            if (mLoadMoreListener != null){
                mLoadMoreListener.loadMore();
            }
        }
    }

}
