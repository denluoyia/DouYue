package com.denluoyia.douyue.view.adapter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.denluoyia.douyue.R;
import com.denluoyia.douyue.base.BaseActivity;
import com.denluoyia.douyue.base.BaseItemViewHolder;
import com.denluoyia.douyue.base.BaseRecyclerViewAdapter;
import com.denluoyia.douyue.constant.Constant;
import com.denluoyia.douyue.manager.net.NetManager;
import com.denluoyia.douyue.model.db.NoteBean;
import com.denluoyia.douyue.utils.TimeUtil;
import com.denluoyia.douyue.view.activity.NoteEditActivity;

import butterknife.BindView;

/**
 * Created by denluoyia
 * Date 2018/07/04
 * DouYue
 */
public class NoteListAdapter extends BaseRecyclerViewAdapter<NoteBean, NoteListAdapter.ItemViewHolder> {

    public NoteListAdapter(BaseActivity mActivity) {
        super(mActivity);
    }

    @Override
    protected ItemViewHolder iCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note_list, parent, false));
    }

    @Override
    protected void iBindViewHolder(@NonNull final ItemViewHolder holder, final int position) {
        NoteBean item = getItem(position);
        if (item == null) return;
        holder.noteTitle.setText(item.getTitle());
        holder.noteContent.setText(item.getContent());
        holder.noteUpdateTime.setText(TimeUtil.intervalTime(item.getLastEditTime(), TimeUtil.getCurrentTime(), mActivity));
        holder.noteOp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnNoteOperateListener != null){
                    mOnNoteOperateListener.onOperateItem(holder.noteOp, position);
                }
            }
        });
    }

    @Override
    protected void onItemClickListener(View view, int position) {
        NoteBean item = getItem(position);
        if (item.getStatus() == NoteBean.NOTE_STATUS_IDLE) return;
        Intent intent = new Intent(mActivity, NoteEditActivity.class);
        intent.putExtra(Constant.INTENT_ACTION_DATA_KEY, Constant.OP_NOTE_READ);
        intent.putExtra("noteItem", getItem(position));
        mActivity.startActivity(intent);
    }

    @Override
    protected boolean onItemLongClickListener(View view, int position) {
        return false;
    }

    public static class ItemViewHolder extends BaseItemViewHolder {
        @BindView(R.id.note_title)
        TextView noteTitle;
        @BindView(R.id.note_content)
        TextView noteContent;
        @BindView(R.id.note_last_edit_time)
        TextView noteUpdateTime;
        @BindView(R.id.note_op)
        ImageView noteOp;
        public ItemViewHolder(View itemView) {
            super(itemView);
        }
    }


    private OnNoteOperateListener mOnNoteOperateListener;
    public void setOnNoteOperateListener(OnNoteOperateListener listener){
        this.mOnNoteOperateListener = listener;
    }

    public interface OnNoteOperateListener{
        void onOperateItem(View view, int position);
    }

}
