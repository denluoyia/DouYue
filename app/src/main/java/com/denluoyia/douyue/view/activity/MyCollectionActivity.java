package com.denluoyia.douyue.view.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.widget.FrameLayout;

import com.denluoyia.douyue.R;
import com.denluoyia.douyue.base.BaseActivity;
import com.denluoyia.douyue.interf.ItemTouchHelperCallback;
import com.denluoyia.douyue.model.db.MyCollectionBean;
import com.denluoyia.douyue.presenter.MyCollectionContract;
import com.denluoyia.douyue.presenter.MyCollectionPresenter;
import com.denluoyia.douyue.utils.NetStatusViewUtil;
import com.denluoyia.douyue.utils.UIUtil;
import com.denluoyia.douyue.view.adapter.MyCollectionAdapter;

import java.util.List;

import butterknife.BindView;

public class MyCollectionActivity extends BaseActivity implements MyCollectionContract.View{

    @BindView(R.id.frame_layout)
    FrameLayout frameLayout;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private MyCollectionPresenter mPresenter;
    private MyCollectionAdapter mAdapter;
    private ItemTouchHelper mItemTouchHelper;
    NetStatusViewUtil netViewUtil;

    @Override
    protected int setContentViewId() {
        return R.layout.activity_my_collection;
    }

    @Override
    protected void doBusiness() {
        initToolbar(toolbar);
        toolbar.setTitle("我的收藏");
        toolbar.setTitleTextColor(UIUtil.getColor(R.color.white));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new MyCollectionAdapter(this);
        recyclerView.setAdapter(mAdapter);
        mAdapter.setOnStartDragListener(mOnStartDragListener);
        mItemTouchHelper = new ItemTouchHelper(new ItemTouchHelperCallback(mAdapter));
        mItemTouchHelper.attachToRecyclerView(recyclerView);
        netViewUtil = new NetStatusViewUtil(frameLayout);
        netViewUtil.statusLoading();
        mPresenter = new MyCollectionPresenter(this);
        mPresenter.getMyCollectionList();
    }

    @Override
    public void loadDataSuccess(List<MyCollectionBean> list) {
        if (list == null || list.size() == 0){
            netViewUtil.statusEmpty();
            return;
        }
        netViewUtil.statusNormal();
        mAdapter.refreshDataList(list);
    }

    private MyCollectionAdapter.OnStartDragListener mOnStartDragListener = new MyCollectionAdapter.OnStartDragListener() {
        @Override
        public void onStartDrag(MyCollectionAdapter.ItemViewHolder viewHolder) {
            mItemTouchHelper.startDrag(viewHolder);
        }
    };

    @Override
    protected void onDestroy() {
        if (mPresenter != null) mPresenter.detachView();
        super.onDestroy();
    }
}
