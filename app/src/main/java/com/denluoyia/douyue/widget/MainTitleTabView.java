package com.denluoyia.douyue.widget;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.denluoyia.douyue.R;
import com.denluoyia.douyue.utils.UIUtil;

/**
 * Created by denluoyia
 * Date 2018/06/26
 * DouYue
 */
public class MainTitleTabView extends LinearLayout implements RadioGroup.OnCheckedChangeListener, ViewPager.OnPageChangeListener{

    private ViewPager mViewPager;
    private RadioGroup mRadioGroup;
    private RadioButton mRbText, mRbVoice, mRbMovie;

    public MainTitleTabView(Context context) {
        this(context, null);
    }

    public MainTitleTabView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MainTitleTabView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.view_main_title_tab, this, true);
        init();
    }

    public void bindViewPager(@NonNull ViewPager viewPager){
        mViewPager = viewPager;
        mViewPager.addOnPageChangeListener(this);
        mViewPager.setCurrentItem(0);
    }

    private void init(){
        mRadioGroup = findViewById(R.id.tabTitle);
        mRbText = findViewById(R.id.rb_text);
        mRbVoice = findViewById(R.id.rb_voice);
        mRbMovie = findViewById(R.id.rb_movie);
        mRadioGroup.setOnCheckedChangeListener(this);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        mRadioGroup.check(position == 0 ? R.id.rb_text : position == 1 ? R.id.rb_voice : R.id.rb_movie);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId){
            case R.id.rb_text:
                selectItem(0);
                mViewPager.setCurrentItem(0);
                break;
            case R.id.rb_voice:
                selectItem(1);
                mViewPager.setCurrentItem(1);
                break;
            case R.id.rb_movie:
                selectItem(2);
                mViewPager.setCurrentItem(2);
                break;
        }
    }

    private void selectItem(int position){
        switch (position){
            case 0:
                mRbText.setTextColor(Color.WHITE);
                mRbVoice.setTextColor(UIUtil.getColor(R.color.text_second_color_primary));
                mRbMovie.setTextColor(UIUtil.getColor(R.color.text_second_color_primary));
                break;
            case 1:
                mRbText.setTextColor(UIUtil.getColor(R.color.text_second_color_primary));
                mRbVoice.setTextColor(Color.WHITE);
                mRbMovie.setTextColor(UIUtil.getColor(R.color.text_second_color_primary));
                break;
            case 2:
                mRbText.setTextColor(UIUtil.getColor(R.color.text_second_color_primary));
                mRbVoice.setTextColor(UIUtil.getColor(R.color.text_second_color_primary));
                mRbMovie.setTextColor(Color.WHITE);
                break;
        }
    }
}
