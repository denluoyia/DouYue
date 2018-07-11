package com.denluoyia.douyue.widget.itemtitle;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.denluoyia.douyue.R;
import com.denluoyia.douyue.utils.UIUtil;

/**
 * Created by denluoyia
 * Date 2018/07/02
 * DouYue
 */
public class CommonItemTitle extends BaseItemTitleLayout{

    public CommonItemTitle(Context context) {
        super(context);
    }

    public CommonItemTitle(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CommonItemTitle(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void postFixParams() {
        super.postFixParams();
        leftTextView.getPaint().setFakeBoldText(true); //加粗
        leftImageIcon.setVisibility(VISIBLE);
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) leftImageIcon.getLayoutParams();
        lp.width = dip2px(2);
        lp.height = dip2px(14);
        leftImageIcon.setLayoutParams(lp);
        leftImageIcon.setBackgroundColor(UIUtil.getColor(R.color.colorAccent));
    }
}
