package com.denluoyia.douyue.utils;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

/**
 * 实现RecyclerView 条目的动画加载显示
 * Created by denluoyia
 */

public class ItemAnimUtil {

    private int mLastPosition = -1;

    public void addAnimation(RecyclerView.ViewHolder holder) {
        if (holder.getLayoutPosition() > mLastPosition) {
            for (Animator animator : getAnimators(holder.itemView)){
                startAnim(animator, holder.getLayoutPosition());
            }
            mLastPosition = holder.getLayoutPosition();
        }
    }

    private Interpolator mInterpolator = new LinearInterpolator();
    protected void startAnim(Animator anim, int index) {
        anim.setDuration(300).start();
        anim.setInterpolator(mInterpolator);
    }

    public Animator[] getAnimators(View view) {
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, "scaleX", 0.5f, 1f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, "scaleY", 0.5f, 1f);
        return new ObjectAnimator[] { scaleX, scaleY };
    }
}
