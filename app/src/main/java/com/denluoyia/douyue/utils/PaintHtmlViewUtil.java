package com.denluoyia.douyue.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.denluoyia.douyue.R;
import com.denluoyia.douyue.view.activity.ImageBrowseActivity;

import java.util.ArrayList;

/**
 * Created by denluoyia
 * Date 2018/07/16
 * DouYue
 */
public class PaintHtmlViewUtil {

    private TextView pTv;
    private int imgHeight;
    private int imgWidth;
    private LinearLayout.LayoutParams llParam;
    private View lineView;
    private Typeface typeface;
    private ArrayList<String> imageUrls = new ArrayList<>();
    private Context mContext;

    public PaintHtmlViewUtil(Context context){
        this.mContext = context;
    }

    private LinearLayout.LayoutParams getLinearParams(){
        this.llParam = new LinearLayout.LayoutParams(-1, -6);
        return this.llParam;
    }

    public void addHeadTitle(Context paramContext, ViewGroup paramViewGroup, SpannableStringBuilder paramSpannableStringBuilder){
        addLineView(paramContext, paramViewGroup, null);
        this.pTv = new TextView(paramContext);
        this.pTv.setSingleLine(false);
        this.pTv.setTextColor(UIUtil.getColor(R.color.text_color_primary));
        this.pTv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        this.pTv.setText(paramSpannableStringBuilder, TextView.BufferType.SPANNABLE);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        this.pTv.setLayoutParams(lp);
        paramViewGroup.addView(this.pTv);
    }

    public void addH5TextView(Context paramContext, ViewGroup paramViewGroup, SpannableStringBuilder paramSpannableStringBuilder){
        addLineView(paramContext, paramViewGroup, null);
        this.pTv = new TextView(paramContext);
        this.pTv.setSingleLine(false);
        this.pTv.setTextColor(UIUtil.getColor(R.color.colorAccent));
        this.pTv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
        this.pTv.setText(paramSpannableStringBuilder, TextView.BufferType.SPANNABLE);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        this.pTv.setLayoutParams(lp);
        paramViewGroup.addView(this.pTv);
    }

    public void addPTextView(Context paramContext, ViewGroup paramViewGroup, SpannableStringBuilder paramSpannableStringBuilder){
        this.pTv = new TextView(paramContext);
        this.pTv.setSingleLine(false);
        pTv.setLineSpacing(1.2f, 1.5f);
        pTv.setTextIsSelectable(true);
        pTv.setTextColor(UIUtil.getColor(R.color.text_detail));
        this.pTv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        pTv.setText(paramSpannableStringBuilder, TextView.BufferType.SPANNABLE);
        pTv.setGravity(Gravity.LEFT);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        //lp.setMargins(0, 14, 0, 0); //设置段前距离
        pTv.setLayoutParams(lp);
        paramViewGroup.addView(pTv);
    }

    public void addImageView(final Activity paramActivity, SpannableStringBuilder paramSpannableStringBuilder, ViewGroup paramViewGroup, String imgWidth, String imgHeight, String imgUrl){
        ImageView localImageView = (ImageView) LayoutInflater.from(paramActivity).inflate(R.layout.view_image_view, null);
        this.llParam = getLinearParams();
        if (!TextUtils.isEmpty(imgWidth) && !TextUtils.isEmpty(imgHeight)){
            String wStr = imgWidth.replace("px", "");
            String hStr = imgHeight.replace("px", "");
            this.imgWidth = UIUtil.dip2px((float)Double.parseDouble(wStr));
            this.imgHeight = UIUtil.dip2px((float)Double.parseDouble(hStr));
            this.llParam.height = (this.imgHeight * ScreenUtil.getScreenWidth(paramActivity) / this.imgWidth);
        }
        localImageView.setLayoutParams(this.llParam);
        localImageView.setTag(localImageView.getId(), imgUrl);
        imageUrls.add(imgUrl);
        localImageView.setOnClickListener(mOnImageClickListener);
        paramViewGroup.addView(localImageView);
        Glide.with(paramActivity).load(imgUrl).into(localImageView);
    }

    public void addLineView(Context paramContext, ViewGroup paramViewGroup, String paramString){
        this.lineView = new View(paramContext);
        this.llParam = getLinearParams();
        paramViewGroup.addView(this.lineView);
    }

    private View.OnClickListener mOnImageClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            intent.putStringArrayListExtra("imageUrls", imageUrls);
            intent.putExtra("currImage", (String)v.getTag(v.getId()));
            intent.setClass(mContext, ImageBrowseActivity.class);
            mContext.startActivity(intent);
        }
    };
}
