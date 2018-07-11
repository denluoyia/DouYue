package com.denluoyia.douyue.view.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.denluoyia.douyue.DouYueApp;
import com.denluoyia.douyue.R;
import com.denluoyia.douyue.base.BaseActivity;
import com.denluoyia.douyue.presenter.MainContract;
import com.denluoyia.douyue.presenter.MainPresenter;
import com.denluoyia.douyue.utils.StatusBarUtil;
import com.denluoyia.douyue.utils.ViewUIAdapter;
import com.denluoyia.douyue.view.adapter.FragmentListAdapter;
import com.denluoyia.douyue.view.fragment.ItemListFragment;
import com.denluoyia.douyue.widget.MainTitleTabView;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements MainContract.View{

    @BindView(R.id.titlebar)
    LinearLayout titleBar;
    @BindView(R.id.main_tab_title)
    MainTitleTabView mainTitleTabView;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;

    MainPresenter mPresenter;

    @Override
    protected int setContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected void doBusiness() {
        StatusBarUtil.setTransparentStatusBar(this);
        ViewUIAdapter.adapterTitleWrap(this, titleBar);
        mainTitleTabView.bindViewPager(viewPager);
        mPresenter = new MainPresenter(this);
        final List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(ItemListFragment.newInstance(1)); //1 文字
        fragmentList.add(ItemListFragment.newInstance(3)); //3 声音
        fragmentList.add(ItemListFragment.newInstance(2)); //2 影像
        viewPager.setAdapter(new FragmentListAdapter(getSupportFragmentManager(), fragmentList));
        viewPager.setOffscreenPageLimit(fragmentList.size());
        viewPager.setCurrentItem(0);
    }

    @OnClick({R.id.iv_toggle, R.id.ic_search,R.id.item_my_collection, R.id.item_my_notes})
    public void onClick(View view){
        Intent intent = null;
        switch (view.getId()){
            case R.id.iv_toggle:
                mDrawerLayout.openDrawer(Gravity.START);
                break;
            case R.id.ic_search:
                startActivity(new Intent(this, SearchActivity.class));
                break;

            case R.id.item_my_collection:
                mDrawerLayout.closeDrawer(Gravity.START);
                intent = new Intent(this, MyCollectionActivity.class);
                break;
            case R.id.item_my_notes:
                mDrawerLayout.closeDrawer(Gravity.START);
                intent = new Intent(this, NoteActivity.class);
                break;
        }
        if (intent != null){
            startActivity(intent);
            overridePendingTransition(R.anim.translate_bottom_in, R.anim.translate_bottom_out);
        }
    }

    private long touchTime = 0;
    @Override
    public void onBackPressed() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - touchTime < 2000){
            super.onBackPressed();
            DouYueApp.getApplication().removeAll();
        }
        touchTime = currentTime;
        Toast.makeText(this, "再按一次退出应用", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        if (mPresenter != null) mPresenter.detachView();
        super.onDestroy();
    }

}
