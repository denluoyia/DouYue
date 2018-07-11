package com.denluoyia.douyue.view.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.denluoyia.douyue.R;
import com.denluoyia.douyue.base.BaseItemViewHolder;
import com.denluoyia.douyue.event.SearchAdapterItemClickEvent;
import com.denluoyia.douyue.manager.EventManager;
import com.denluoyia.douyue.manager.db.origin.BookSearchHistoryDbOpenHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by denluoyia
 * Date 2018/07/02
 * DouYue
 */
public class SearchHistoryAdapter extends RecyclerView.Adapter<SearchHistoryAdapter.ItemViewHolder>{

    private List<String> mDataList = new ArrayList<>();

    public void refreshData(){
        mDataList.clear();
        mDataList.addAll(BookSearchHistoryDbOpenHelper.BookSearchHistoryDbHelper.getsInstance().queryAll());
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_search_history, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        final String item = mDataList.get(position);
        holder.tvSearchHistory.setText(item);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventManager.post(new SearchAdapterItemClickEvent(2, item));
            }
        });
        holder.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BookSearchHistoryDbOpenHelper.BookSearchHistoryDbHelper.getsInstance().delete(item);
                mDataList.remove(item);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    public static class ItemViewHolder extends BaseItemViewHolder {
        @BindView(R.id.tv_search_history)
        TextView tvSearchHistory;
        @BindView(R.id.iv_delete)
        ImageView ivDelete;
        public ItemViewHolder(View itemView) {
            super(itemView);
        }
    }
}
