package com.denluoyia.douyue.utils;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

/**
 * ClipboardManager
 * To copy to clipboard or paste to target
 */
public class ClipboardUtil {

    public static ClipboardManager getClipboardManager(){
        return (ClipboardManager) UIUtil.getContext().getSystemService(Context.CLIPBOARD_SERVICE);
    }

    /**
     * 复制到剪切板
     * @param content  复制的内容
     */
    public static void copyStringToClipboard(String content){
        getClipboardManager().setPrimaryClip(ClipData.newPlainText(content.trim(), content.trim()));
    }

    /**
     *
     * @return
     */
    public static String pasteStringFromClipboard(){
        return getClipboardManager().getPrimaryClip().getItemAt(0).getText().toString();
    }

}
