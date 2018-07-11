package com.denluoyia.douyue.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.denluoyia.douyue.R;
import com.denluoyia.douyue.utils.UIUtil;

/**
 * Created by denluoyia
 * Date 2018/07/06
 * DouYue
 */
public class CustomDialog extends Dialog{


    public CustomDialog(@NonNull Context context) {
        super(context);
    }

    public CustomDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected CustomDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public static class AlterBuilder {

        private Context context;
        private CharSequence title;
        private CharSequence content;
        private View contentView;
        private DialogInterface.OnClickListener btnNegativeClickListener;
        private DialogInterface.OnClickListener btnPositiveClickListener;
        private CharSequence btnOkText;
        private CharSequence btnCancelText;
        private boolean canceledOnTouchOutside = false;

        public AlterBuilder(Context context){
            this.context = context;
        }

        public AlterBuilder setTitle(CharSequence title){
            this.title = title;
            return this;
        }

        public AlterBuilder setTitle(int titleId){
            this.title = context.getString(titleId);
            return this;
        }

        public AlterBuilder setContent(CharSequence content){
            this.content = content;
            return this;
        }

        public AlterBuilder setContent(int contentId){
            this.content = context.getString(contentId);
            return this;
        }

        public AlterBuilder setContentView(View contentView){
            this.contentView = contentView;
            return this;
        }

        public AlterBuilder setBtnPositiveClick(String btnOkText, DialogInterface.OnClickListener btnClickListener){
            this.btnOkText = btnOkText;
            this.btnPositiveClickListener = btnClickListener;
            return this;
        }

       public AlterBuilder setBtnNegativeClick(String btnCancelText, DialogInterface.OnClickListener btnClickListener){
            this.btnCancelText = btnCancelText;
            this.btnNegativeClickListener = btnClickListener;
            return this;
       }

        public AlterBuilder setCanceledOnTouchOutside(boolean canceledOnTouchOutside){
            this.canceledOnTouchOutside = canceledOnTouchOutside;
            return this;
        }

        public Dialog create(){ //
            final CustomDialog dialog = new CustomDialog(context, R.style.BaseDialogTheme);
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = inflater.inflate(R.layout.cd_custom_layout, null);

            TextView tvTitle = view.findViewById(R.id.cd_title);
            TextView tvContent = view.findViewById(R.id.cd_content);
            TextView btnOk = view.findViewById(R.id.cd_btn_ok);
            TextView btnCancel = view.findViewById(R.id.cd_btn_cancel);
            if (TextUtils.isEmpty(title)){
                tvTitle.setVisibility(View.GONE);
            }else{
                tvTitle.setText(title);
            }

            if (TextUtils.isEmpty(content)){
                tvContent.setVisibility(View.GONE);
            }else{
                tvContent.setText(content);
            }
            btnOk.setText(btnOkText);
            btnCancel.setText(btnCancelText);
            if (btnPositiveClickListener != null){
                btnOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        btnPositiveClickListener.onClick(dialog, DialogInterface.BUTTON_POSITIVE);
                        dialog.dismiss();
                    }
                });
            }

            if (btnNegativeClickListener != null){
                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        btnNegativeClickListener.onClick(dialog, DialogInterface.BUTTON_NEGATIVE);
                        dialog.dismiss();
                    }
                });
            }

            int widthPixels = context.getResources().getDisplayMetrics().widthPixels - UIUtil.dip2px(80);
            view.setLayoutParams(new LinearLayout.LayoutParams(widthPixels, ViewGroup.LayoutParams.WRAP_CONTENT));

            dialog.setContentView(view);
            dialog.setCanceledOnTouchOutside(canceledOnTouchOutside);
            dialog.setCancelable(false);
            return dialog;
        }

    }
}
