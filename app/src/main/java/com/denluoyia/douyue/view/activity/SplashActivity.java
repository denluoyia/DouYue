package com.denluoyia.douyue.view.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.widget.ImageView;

import com.denluoyia.douyue.R;
import com.denluoyia.douyue.base.BaseActivity;
import com.denluoyia.douyue.utils.FileUtil;
import com.denluoyia.douyue.utils.PermissionUtil;
import com.denluoyia.douyue.utils.UIUtil;

import butterknife.BindView;

public class SplashActivity extends BaseActivity {

    @BindView(R.id.default_img)
    ImageView defaultImg;

    @Override
    protected int setContentViewId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void doBusiness() {
    }

    @Override
    protected void onStart() {
        super.onStart();
        checkInitPermissions();
    }

    public static final int REQUEST_PERMISSION_CODE = 100;
    private void checkInitPermissions(){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            String[] permissions = new String[]{
                    //放入需要授予的权限，例如需要写入的权限
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_PHONE_STATE
            };
            if (PermissionUtil.needCheckPermission(permissions).length > 0){

                ActivityCompat.requestPermissions(this, permissions, REQUEST_PERMISSION_CODE);
            }else{
                handleAfterPermissions();
            }
        } else {
            handleAfterPermissions();
        }
    }

    private void  handleAfterPermissions(){
        FileUtil.getAppCacheDir();
        startWelcomeAnim();
    }


    private void startWelcomeAnim(){
        UIUtil.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, 200);
    }

    public void showPermissionDialog() {
        new AlertDialog.Builder(this).setTitle("提示")
                .setMessage("当前应用缺少必要权限。请点击\"设置\"-\"权限\"-打开所需权限。")
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setPositiveButton("设置", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startAppSettings();
                    }
                })
                .setCancelable(false)
                .show();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_PERMISSION_CODE && PermissionUtil.passPermissions(grantResults)){
            handleAfterPermissions();
        }else{
            showPermissionDialog();
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    /**
     * 启动应用权限设置
     */
    private void startAppSettings() {
        Intent intent = new Intent(
                Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + getPackageName()));
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}

