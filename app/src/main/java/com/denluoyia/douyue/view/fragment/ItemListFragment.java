package com.denluoyia.douyue.view.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.denluoyia.douyue.R;
import com.denluoyia.douyue.base.BaseActivity;
import com.denluoyia.douyue.base.BaseFragment;
import com.denluoyia.douyue.model.ItemListBean;
import com.denluoyia.douyue.presenter.ItemListContract;
import com.denluoyia.douyue.presenter.ItemListPresenter;
import com.denluoyia.douyue.utils.NetStatusViewUtil;
import com.denluoyia.douyue.utils.UIUtil;
import com.denluoyia.douyue.view.adapter.CommonItemListAdapter;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class ItemListFragment extends BaseFragment implements ItemListContract.View, SwipeRefreshLayout.OnRefreshListener{

    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.recycler_view)
    RecyclerView mRecyclerView;

    private int itemListType;
    private ItemListPresenter mPresenter;
    private CommonItemListAdapter mAdapter;
    private int mLastVisibleItem = -1;
    private boolean hasMore = true;

    private NetStatusViewUtil netViewUtil;

    public static ItemListFragment newInstance(int itemListType){
        ItemListFragment fragment = new ItemListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("item_list_type", itemListType);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int setContentViewId() {
        return R.layout.fragment_item_list;
    }

    @Override
    protected void doBusiness() {
        itemListType = getArguments().getInt("item_list_type");
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeColors(UIUtil.getColor(R.color.colorAccent));
        mPresenter = new ItemListPresenter(this, itemListType);
        mAdapter = new CommonItemListAdapter((BaseActivity) getActivity());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (newState == RecyclerView.SCROLL_STATE_IDLE && !mSwipeRefreshLayout.isRefreshing() && hasMore && (mLastVisibleItem + 1  == mAdapter.getItemCount())){
                   mPresenter.loadMoreData();
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                mLastVisibleItem = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastVisibleItemPosition();
            }
        });
        netViewUtil = new NetStatusViewUtil((FrameLayout) findViewById(R.id.frame_layout));
        netViewUtil.statusLoading();
        mPresenter.loadInitData();
    }


    @Override
    public void loadDataFailed(String msg) {
        mSwipeRefreshLayout.setRefreshing(false);
        if (mPresenter.getCurrPage() == 1){
            netViewUtil.statusError("出错啦，点击重新尝试加载", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPresenter.loadInitData();
                    netViewUtil.statusLoading();
                }
            });
        }
        if (!TextUtils.isEmpty(msg)){
            Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void loadInitDataSuccess(ItemListBean bean) {
        netViewUtil.statusNormal();
        mSwipeRefreshLayout.setRefreshing(false);
        if (bean != null && bean.getDatas().size() > 0){
            mAdapter.refreshDataList(bean.getDatas());
        }
    }

    @Override
    public void loadMoreDataSuccess(ItemListBean bean) {
        if (bean != null && bean.getDatas().size() > 0){
            mAdapter.loadMoreDataList(bean.getDatas());
        }else{
            hasMore = false;
            mAdapter.setHasMore(hasMore);
            mAdapter.notifyItemChanged(mAdapter.getItemCount() - 1);
        }
    }

    @Override
    public void onRefresh() {
        mSwipeRefreshLayout.setRefreshing(true);
        mPresenter.loadInitData();
    }
}
