package com.denluoyia.douyue.utils;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;


/**
 * Created by denluoyia
 * Date 2018/06/26
 * DouYue
 */
public class ViewUIAdapter {

    public static void adapterTitleWrap(Activity activity, LinearLayout linearLayout){
        if (linearLayout == null || linearLayout.getOrientation() == LinearLayout.HORIZONTAL) return;
        View view = new View(linearLayout.getContext());
        view.setBackgroundColor(Color.parseColor("#ff313131"));
        linearLayout.addView(view, 0, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, StatusBarUtil.getStatusBarHeight(activity)));
    }
}
