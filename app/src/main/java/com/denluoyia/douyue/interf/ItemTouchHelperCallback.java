package com.denluoyia.douyue.interf;

import android.graphics.Canvas;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import static android.support.v7.widget.helper.ItemTouchHelper.ACTION_STATE_DRAG;

/**
 * RecyclerView的拖拽和侧滑删除帮助类
 */

public class ItemTouchHelperCallback extends ItemTouchHelper.Callback{

    private OnItemCallbackListener mOnItemCallbackListener;

    public ItemTouchHelperCallback(OnItemCallbackListener mItemCallbackListener){
        mOnItemCallbackListener = mItemCallbackListener;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int dragFlag = ItemTouchHelper.DOWN | ItemTouchHelper.UP;
        int swipedFlag = 0;
        return makeMovementFlags(dragFlag, swipedFlag);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        mOnItemCallbackListener.onItemMoved(viewHolder.getAdapterPosition(), target.getAdapterPosition());
        return true;
    }


    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

    }

    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        super.onSelectedChanged(viewHolder, actionState);
        if(actionState == ACTION_STATE_DRAG){
            //长按时调用
           viewHolder.itemView.setBackgroundColor(Color.parseColor("#EFEFEF"));
        }
    }

    @Override
    public void onChildDrawOver(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        super.onChildDrawOver(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
    }

    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);
        viewHolder.itemView.setBackgroundColor(Color.parseColor("#00000000"));
    }

    /** 禁用侧滑删除功能 */
    @Override
    public boolean isItemViewSwipeEnabled() {
        return false;
    }

    /** 禁用长按拖拽功能 */
    @Override
    public boolean isLongPressDragEnabled() {
        return false;
    }
}
