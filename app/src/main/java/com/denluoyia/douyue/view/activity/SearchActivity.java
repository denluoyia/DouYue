package com.denluoyia.douyue.view.activity;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.denluoyia.douyue.R;
import com.denluoyia.douyue.base.BaseActivity;
import com.denluoyia.douyue.constant.Constant;
import com.denluoyia.douyue.event.SearchAdapterItemClickEvent;
import com.denluoyia.douyue.manager.db.origin.BookSearchHistoryDbOpenHelper;
import com.denluoyia.douyue.model.BookSearchResultListBean;
import com.denluoyia.douyue.presenter.SearchContract;
import com.denluoyia.douyue.presenter.SearchPresenter;
import com.denluoyia.douyue.utils.UIUtil;
import com.denluoyia.douyue.view.adapter.SearchHistoryAdapter;
import com.denluoyia.douyue.view.adapter.SearchHotAdapter;
import com.denluoyia.douyue.view.adapter.SearchResultAdapter;
import com.denluoyia.douyue.widget.ClearEditText;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class SearchActivity extends BaseActivity implements SearchContract.View{

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.recycler_view_hots)
    RecyclerView hotRecyclerView;
    @BindView(R.id.recycler_view_history)
    RecyclerView historyRecyclerView;
    @BindView(R.id.cet_search)
    ClearEditText searchEdt;
    @BindView(R.id.recycler_view_search_result)
    RecyclerView resultRecyclerView;
    @BindView(R.id.layout_search_result)
    FrameLayout layoutSearchResult;
    @BindView(R.id.tv_search_result_tip)
    TextView tvSearchResultTip;

    private SearchHistoryAdapter searchHistoryAdapter;
    private SearchResultAdapter searchResultAdapter;
    private List<String> hotList = Arrays.asList(UIUtil.getStringArray(R.array.search_hots_array));
    private SearchPresenter mPresenter;

    @Override
    protected int setContentViewId() {
        this.overridePendingTransition(R.anim.translate_bottom_in, R.anim.translate_bottom_out);
        return R.layout.activity_search;
    }

    @Override
    protected void doBusiness() {
        initToolbar(mToolbar);
        searchEdt.setOnEditorActionListener(mOnEditorActionListener);
        searchEdt.addTextChangedListener(mTextWatcher);
        hotRecyclerView.setLayoutManager(new GridLayoutManager(this, 2)); //两列
        SearchHotAdapter searchHotAdapter = new SearchHotAdapter(hotList);
        hotRecyclerView.setAdapter(searchHotAdapter);

        historyRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        searchHistoryAdapter = new SearchHistoryAdapter();
        searchHistoryAdapter.refreshData();
        historyRecyclerView.setAdapter(searchHistoryAdapter);

        resultRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        searchResultAdapter = new SearchResultAdapter();
        resultRecyclerView.setAdapter(searchResultAdapter);

        mPresenter = new SearchPresenter(this);
    }

    @OnClick({R.id.item_search_history})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.item_search_history:
                break;
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, R.anim.translate_bottom_out);
    }

    private TextView.OnEditorActionListener mOnEditorActionListener = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId == EditorInfo.IME_ACTION_DONE
                    || actionId == EditorInfo.IME_ACTION_GO
                    || actionId == EditorInfo.IME_ACTION_SEARCH
                    || actionId == EditorInfo.IME_ACTION_SEND
                    || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                requestSearch();
                return true;
            }
            return false;
        }
    };

    private TextWatcher mTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            requestSearch();
        }
    };

    private void requestSearch() {
        String input = searchEdt.getText().toString().trim();
        if (TextUtils.isEmpty(input)){
            layoutSearchResult.setVisibility(View.GONE);
            return;
        }
        mPresenter.requestSearch(input);
        layoutSearchResult.setVisibility(View.VISIBLE);
    }

    @Override
    public void searchDataFailure(String msg) {
        tvSearchResultTip.setVisibility(View.VISIBLE);
        tvSearchResultTip.setText(!TextUtils.isEmpty(msg) ? msg : "无相关搜索结果，可以换个关键词试试~");
    }

    List<String> dataList = new ArrayList<>();
    @Override
    public void searchDataSuccess(BookSearchResultListBean bean) {
        tvSearchResultTip.setVisibility(View.GONE);
        dataList.clear();
        searchResultAdapter.refreshDataList(bean.getBooks());
    }

    @Override
    protected void onDestroy() {
        mPresenter.detachView();
        super.onDestroy();
    }

    @SuppressWarnings("unused")
    @Subscribe
    public void onEventSearchAdapterItemClick(SearchAdapterItemClickEvent event){
        switch (event.clickType){
            case 1:
            case 2:
                searchEdt.setText(event.bookName);
                if (!TextUtils.isEmpty(event.bookName)){
                    searchEdt.setSelection(event.bookName.length());
                }
                break;
            case 3:
                BookSearchHistoryDbOpenHelper.BookSearchHistoryDbHelper.getsInstance().insert(event.bookName);
                searchHistoryAdapter.refreshData();
                Intent intent = new Intent(this, BookDetailActivity.class);
                searchEdt.setText(""); //清空再跳转
                intent.putExtra(Constant.INTENT_ACTION_DATA_KEY, event.bookId);
                startActivity(intent);
                break;
        }
    }
}
