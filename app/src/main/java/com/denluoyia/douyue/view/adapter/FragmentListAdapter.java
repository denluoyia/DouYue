package com.denluoyia.douyue.view.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by denluoyia
 * Date 2018/07/03
 * DouYue
 */
public class FragmentListAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> mFragmentList = new ArrayList<>();

    public FragmentListAdapter(FragmentManager fm, List<Fragment> fragmentList) {
        super(fm);
        this.mFragmentList.clear();
        if (fragmentList != null && fragmentList.size() > 0){
            this.mFragmentList.addAll(fragmentList);
        }
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }
}
