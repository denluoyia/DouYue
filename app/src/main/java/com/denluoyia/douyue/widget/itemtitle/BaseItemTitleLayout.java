package com.denluoyia.douyue.widget.itemtitle;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;

import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.denluoyia.douyue.R;

/**
 * Created by denluoyia
 * Date 2018/06/28
 * DouYue
 */
public class BaseItemTitleLayout extends RelativeLayout{

    protected ImageView leftImageIcon, rightImageIcon;
    protected TextView  leftTextView, rightTextView;
    protected View dividerLine;

    //默认属性参数
    protected boolean leftIconVisible = false;
    protected boolean rightIconVisible = false;
    protected boolean dividerLineVisible = true;
    protected Drawable leftIconRes;
    protected int leftTextSize = 14;
    protected int leftTextColor = 0xff4f4f4f;
    protected int rightTextSize = 12;
    protected int rightTextColor = 0xff929292;
    protected int rightTextLocation = 1;


    public BaseItemTitleLayout(Context context) {
        this(context, null);
    }

    public BaseItemTitleLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BaseItemTitleLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.view_item_title_layout, this, true);
        leftImageIcon = findViewById(R.id.iv_left);
        leftTextView = findViewById(R.id.tv_left);
        rightTextView = findViewById(R.id.tv_right);
        rightImageIcon = findViewById(R.id.iv_right_arrow);
        dividerLine = findViewById(R.id.divider_line);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.BaseItemTitleLayout, defStyleAttr, 0);
        if (ta != null){
            preSetParams(); //子类可以重新设置
            leftIconVisible = ta.getBoolean(R.styleable.BaseItemTitleLayout_bit_left_icon_visible, leftIconVisible);
            rightIconVisible = ta.getBoolean(R.styleable.BaseItemTitleLayout_bit_right_icon_visible, rightIconVisible);
            leftIconRes = ta.getDrawable(R.styleable.BaseItemTitleLayout_bit_left_icon);
            leftTextSize = ta.getInteger(R.styleable.BaseItemTitleLayout_bit_left_text_size, leftTextSize);
            leftTextColor = ta.getColor(R.styleable.BaseItemTitleLayout_bit_left_text_color, leftTextColor);
            rightTextSize = ta.getInteger(R.styleable.BaseItemTitleLayout_bit_right_text_size, rightTextSize);
            rightTextColor = ta.getColor(R.styleable.BaseItemTitleLayout_bit_right_text_color, rightTextColor);
            rightTextLocation = ta.getInteger(R.styleable.BaseItemTitleLayout_bit_right_text_location, 1);
            dividerLineVisible = ta.getBoolean(R.styleable.BaseItemTitleLayout_bit_bottom_divider_visible, dividerLineVisible);
            String leftText = ta.getString(R.styleable.BaseItemTitleLayout_bit_left_text);
            String rightText = ta.getString(R.styleable.BaseItemTitleLayout_bit_right_text);

            leftImageIcon.setVisibility(leftIconVisible ? VISIBLE : GONE);
            rightImageIcon.setVisibility(rightIconVisible ? VISIBLE : GONE);
            dividerLine.setVisibility(dividerLineVisible ? VISIBLE : GONE);
            if (leftIconRes != null){
                leftImageIcon.setImageDrawable(leftIconRes);
            }
            leftTextView.setTextColor(leftTextColor);
            leftTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, leftTextSize);
            rightTextView.setTextColor(rightTextColor);
            rightTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, rightTextSize);

            if (!TextUtils.isEmpty(leftText)) leftTextView.setText(leftText);
            if (!TextUtils.isEmpty(rightText)) rightTextView.setText(rightText);
            rightTextView.setGravity(rightTextLocation == 1 ? Gravity.RIGHT : rightTextLocation == 0 ? Gravity.CENTER : Gravity.LEFT);
            postFixParams();
            ta.recycle();
        }
    }

    protected void preSetParams(){ //默认属性参数配置

    }

    protected void postFixParams(){ //修复特殊性参数设置

    }

    public void setRightText(String text){
        rightTextView.setText(text);
    }

    public void setLeftText(String text){
        leftTextView.setText(text);
    }

    public int dip2px(float dip){
        float scale = getContext().getResources().getDisplayMetrics().density;
        return (int)(scale * dip + 0.5f);
    }
}
