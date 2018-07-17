package com.denluoyia.douyue.utils;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

/**
 * Created by denluoyia
 * Date 2018/07/17
 * DouYue
 */
public class ToastUtil {

    private static Toast mToast = null;

    public static void showShort(Context context, CharSequence message){
        if (mToast == null){
            mToast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        }else{
            mToast.setText(message);
        }
        mToast.show();
    }


    public static void showCustomToast(Context context, CharSequence message, int duration, View view, boolean isGravity, int gravity, int xOffset, int yOffset, boolean isMargin, float horizontalMargin, float verticalMargin){
        if (mToast == null){
            mToast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        }else{
            mToast.setText(message);
        }

        if (view != null){
            mToast.setView(view);
        }
        if (isMargin){
            mToast.setMargin(horizontalMargin, verticalMargin);
        }
        if (isGravity){
            mToast.setGravity(gravity, xOffset, yOffset);
        }
        mToast.show();
    }
}
