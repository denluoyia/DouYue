package com.denluoyia.douyue.view.activity;


import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.denluoyia.douyue.R;
import com.denluoyia.douyue.base.BaseActivity;
import com.denluoyia.douyue.constant.Constant;
import com.denluoyia.douyue.model.BookItemBean;
import com.denluoyia.douyue.presenter.BookDetailContract;
import com.denluoyia.douyue.presenter.BookDetailPresenter;
import com.denluoyia.douyue.utils.StringUtil;

import butterknife.BindView;
import butterknife.OnClick;

public class BookDetailActivity extends BaseActivity implements BookDetailContract.View {

    @BindView(R.id.book_cover)
    ImageView ivBookCover;
    @BindView(R.id.tv_content_intro)
    TextView tvContentIntro;
    @BindView(R.id.tv_author_intro)
    TextView tvAuthorIntro;
    @BindView(R.id.tv_catalog_list)
    TextView tvCatalogList;
    @BindView(R.id.ll_content)
    LinearLayout llContent;
    @BindView(R.id.ll_author)
    LinearLayout llAuthor;
    @BindView(R.id.ll_catalog)
    LinearLayout llCatalog;

    private BookDetailPresenter mPresenter;


    @Override
    protected int setContentViewId() {
        return R.layout.activity_book_detail;
    }

    @Override
    protected void doBusiness() {
        String bookId = getIntent().getStringExtra(Constant.INTENT_ACTION_DATA_KEY);
        if (TextUtils.isEmpty(bookId)) finish();
        mPresenter = new BookDetailPresenter(this);
        mPresenter.requestData(bookId);
    }

    @Override
    public void loadDataFailed(String msg) {
        if (!TextUtils.isEmpty(msg)){
            Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void loadDataSuccess(BookItemBean bean) {
        if(bean != null){
            Glide.with(this).load(bean.getImage()).into(ivBookCover);
            llContent.setVisibility(StringUtil.isEmpty(bean.getSummary()) ? View.GONE : View.VISIBLE);
            llAuthor.setVisibility(StringUtil.isEmpty(bean.getSummary()) ? View.GONE : View.VISIBLE);
            llCatalog.setVisibility(StringUtil.isEmpty(bean.getCatalog()) ? View.GONE : View.VISIBLE);
            tvContentIntro.setText(bean.getSummary());
            tvAuthorIntro.setText(bean.getAuthor_intro());
            tvCatalogList.setText(bean.getCatalog());
        }
    }

    @OnClick({R.id.iv_back})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.iv_back:
                onBackPressed();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        mPresenter.detachView();
        super.onDestroy();
    }
}
