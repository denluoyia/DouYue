package com.denluoyia.douyue.view.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.denluoyia.douyue.R;
import com.denluoyia.douyue.base.BaseItemViewHolder;
import com.denluoyia.douyue.event.SearchAdapterItemClickEvent;
import com.denluoyia.douyue.manager.EventManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by denluoyia
 * Date 2018/07/02
 * DouYue
 */
public class SearchHotAdapter extends RecyclerView.Adapter<SearchHotAdapter.ItemViewHolder> {

    private List<String> mDataList = new ArrayList<>();

    public SearchHotAdapter(List<String> dataList){
        mDataList.clear();
        if (dataList == null || dataList.size() == 0) return;
        mDataList.addAll(dataList);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_search_hot, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        final String item = mDataList.get(position);
        if (item == null) return;
        holder.tvNum.setText(String.valueOf(holder.getLayoutPosition() + 1));
        holder.tvBookTitle.setText(item);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventManager.post(new SearchAdapterItemClickEvent(1, item));
            }
        });
    }

    public static class ItemViewHolder extends BaseItemViewHolder {
        @BindView(R.id.text_num)
        TextView tvNum;
        @BindView(R.id.text_book_title)
        TextView tvBookTitle;
        public ItemViewHolder(View itemView) {
            super(itemView);
        }
    }
}
