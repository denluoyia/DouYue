package com.denluoyia.douyue.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

import com.denluoyia.douyue.R;

/**
 * Created by denluoyia
 * Date 2018/06/30
 * DouYue
 */
public class ClearEditText extends android.support.v7.widget.AppCompatEditText{


    private Drawable mClearDrawable; //删除按钮的应用
    private boolean hasFocus; //控件是否有焦点


    public ClearEditText(Context context) {
        this(context, null);
    }

    public ClearEditText(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.editTextStyle);
    }

    public ClearEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        //获取DrawableRight，假设没有就是用默认的删除图片
        mClearDrawable = getCompoundDrawables()[2];
        if (mClearDrawable == null){
            mClearDrawable = ContextCompat.getDrawable(context, R.mipmap.ic_delete_white);
        }
        mClearDrawable.setBounds(0, 0, mClearDrawable.getIntrinsicWidth(), mClearDrawable.getIntrinsicHeight());
        //默认设置隐藏图标
        setClearVisibility(false);
        //设置焦点改变监听
        setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                hasFocus = b;
                if (hasFocus){
                    //是否有输入，长度大于0则删除按钮显示
                    setClearVisibility(getText().toString().length() > 0);
                }else{
                    setClearVisibility(false);
                }
            }
        });

        //设置文本变化监听
        addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                setClearVisibility(charSequence.length() > 0);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    /**
     * 设置清除图标是否显示，如果有输入，则显示，否则不显示
     * getCompoundDrawable() 获得左上右下的一个drawable数组
     * @param visible
     */
    protected void setClearVisibility(boolean visible){
        Drawable right = visible ? mClearDrawable : null;
        setCompoundDrawables(getCompoundDrawables()[0], getCompoundDrawables()[1], right, getCompoundDrawables()[3]);
    }

    /** 好好的理解这里
     *
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == KeyEvent.ACTION_UP){
            if (mClearDrawable != null){
                if (event.getX() > getWidth() - getTotalPaddingRight() && event.getX() < getWidth() - getPaddingRight()){
                    setText(""); //相当于清空输入框的内容
                }
            }
        }
        return super.onTouchEvent(event);
    }
}
