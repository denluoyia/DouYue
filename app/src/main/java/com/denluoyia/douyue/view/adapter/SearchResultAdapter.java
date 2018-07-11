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
import com.denluoyia.douyue.model.BookItemBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by denluoyia
 * Date 2018/07/02
 * DouYue
 */
public class SearchResultAdapter extends RecyclerView.Adapter<SearchResultAdapter.ItemViewHolder> {

    private List<BookItemBean> mDataList = new ArrayList<>();

    public void refreshDataList(List<BookItemBean> dataList){
        mDataList.clear();
        if (dataList != null && dataList.size() > 0){
            mDataList.addAll(dataList);
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_search_result, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        final BookItemBean item = mDataList.get(position);
        if (item == null) return;
        holder.tvSearchResult.setText(item.getTitle());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventManager.post(new SearchAdapterItemClickEvent(3, item.getTitle(), item.getId()));
            }
        });
    }

    public static class ItemViewHolder extends BaseItemViewHolder {
        @BindView(R.id.tv_search_result)
        TextView tvSearchResult;
        public ItemViewHolder(View itemView) {
            super(itemView);
        }
    }

}
