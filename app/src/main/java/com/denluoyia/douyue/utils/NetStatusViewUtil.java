package com.denluoyia.douyue.utils;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.denluoyia.douyue.R;
import com.denluoyia.douyue.widget.netstateview.StatusViewCreator;

/**
 * Created by denluoyia
 * Date 2018/07/03
 * DouYue
 */
public class NetStatusViewUtil {

    private final static int STATUS_NORMAL = 0;

    private final static int STATUS_LOADING = 1;
    private final static int STATUS_EMPTY = 2;
    private final static int STATUS_ERROR = 3;
    private final static int STATUS_NET_ERROR = 4;

    private volatile int mCurrentStatus = 0;
    private FrameLayout mRootFrameLayout;
    private Context mContext;
    private FrameLayout.LayoutParams mLayoutParams;
    private View mLoadingView, mEmptyView, mErrorView, mNetErrorView;
    private View.OnClickListener mInnerListener;


    public NetStatusViewUtil(FrameLayout frameLayout){
        mRootFrameLayout = frameLayout;
        mContext = mRootFrameLayout.getContext();
        mLayoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    }


    public void statusNormal(){
        mCurrentStatus = STATUS_NORMAL;
        notifyStatusView();
    }


    public View statusLoading(){
        mCurrentStatus = STATUS_LOADING;
        notifyStatusView();
        return mLoadingView;
    }

    public View statusEmpty(){
        mCurrentStatus = STATUS_EMPTY;
        notifyStatusView();
        return mEmptyView;
    }


    public View statusError(String msg, View.OnClickListener listener){
        statusError(msg);
        mInnerListener = listener;
        mErrorView.setOnClickListener(mOnClickListener);
        return mErrorView;
    }

    public View statusError(String msg){
        statusError();
        TextView tv = mErrorView.findViewById(R.id.status_view_hint);
        tv.setText(msg);
        return mErrorView;
    }

    public View statusError(){
        mCurrentStatus = STATUS_ERROR;
        notifyStatusView();
        return mErrorView;
    }

    public View statusNetError(){
        mCurrentStatus = STATUS_NET_ERROR;
        notifyStatusView();
        return mNetErrorView;
    }


    /** 优先级高于 设置默认的布局*/
    public View setLoadingView(View view){
        mLoadingView = view;
        return mLoadingView;
    }

    public View setEmptyView(View view){
        mEmptyView = view;
        return mEmptyView;
    }

    public View setErrorView(View view){
        mErrorView = view;
        return mErrorView;
    }
    public View setNetErrorView(View view){
        mNetErrorView = view;
        return mNetErrorView;
    }

    public static void setStatusViewCreate(StatusViewCreator creator){
        sStatusViewCreator = creator;
    }


    private static StatusViewCreator sStatusViewCreator = new StatusViewCreator() {

        @Override
        public View createLoadingLayout(Context context) {
            return super.createLoadingLayout(context);
        }

        @Override
        public View createEmptyLayout(Context context) {
            return super.createEmptyLayout(context);
        }

        @Override
        public View createErrorLayout(Context context) {
            return super.createErrorLayout(context);
        }

        @Override
        public View createNetErrorLayout(Context context) {
            return super.createNetErrorLayout(context);
        }
    };

    private void notifyStatusView(){
        if (mLoadingView == null){
            mLoadingView = sStatusViewCreator.createLoadingLayout(mContext);
        }
        if (mEmptyView == null){
            mEmptyView = sStatusViewCreator.createEmptyLayout(mContext);
        }
        if (mErrorView == null){
            mErrorView = sStatusViewCreator.createErrorLayout(mContext);
        }
        if (mNetErrorView == null){
            mNetErrorView = sStatusViewCreator.createNetErrorLayout(mContext);
        }
        mRootFrameLayout.removeView(mLoadingView);
        mRootFrameLayout.removeView(mEmptyView);
        mRootFrameLayout.removeView(mErrorView);
        mRootFrameLayout.removeView(mNetErrorView);
        mLayoutParams.gravity = Gravity.CENTER;
        switch (mCurrentStatus){
            case STATUS_LOADING:
                mRootFrameLayout.addView(mLoadingView);
                break;
            case STATUS_EMPTY:
                mRootFrameLayout.addView(mEmptyView);
                break;
            case STATUS_ERROR:
                mRootFrameLayout.addView(mErrorView);
                break;
            case STATUS_NET_ERROR:
                mRootFrameLayout.addView(mNetErrorView);
                break;
            default:
                break;
        }
    }

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            statusLoading();
            if (mInnerListener != null){
                mInnerListener.onClick(v);
            }
        }
    };

}
