package com.denluoyia.douyue.view.activity;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bm.library.PhotoView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.FutureTarget;
import com.bumptech.glide.request.target.Target;
import com.denluoyia.douyue.R;
import com.denluoyia.douyue.base.BaseActivity;
import com.denluoyia.douyue.utils.FileUtil;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 大图切换查看浏览页
 * ViewPager + PhotoView
 */

public class ImageBrowseActivity extends BaseActivity {

    @BindView(R.id.view_pager)
    ViewPager mViewPager;
    @BindView(R.id.tv_page_num_switch)
    TextView pageNumSwitcher;

    private ArrayList<String> imageUrls;
    private String currImage;
    private int currPosition;


    @Override
    protected int setContentViewId() {
        overridePendingTransition(R.anim.anim_scale_enter, R.anim.anim_scale_exit);
        return R.layout.activity_image_browser;
    }

    @Override
    protected void doBusiness() {
        imageUrls = getIntent().getStringArrayListExtra("imageUrls");
        currImage = getIntent().getStringExtra("currImage");
        if (imageUrls == null || imageUrls.size() == 0){
            finish();
        }
        currPosition = getClickEnterPositionAndPreDeal(currImage);
        pageNumSwitcher.setText((currPosition + 1) + "/" + imageUrls.size());
        mViewPager.setPageMargin((int) (getResources().getDisplayMetrics().density * 16));
        mViewPager.setAdapter(new MyPagerAdapter());
        mViewPager.addOnPageChangeListener(mOnPageChangeListener);
        mViewPager.setCurrentItem(currPosition);
    }

    @OnClick({R.id.iv_download_img})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.iv_download_img:
                saveImage();
                break;
        }
    }

    private void saveImage(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                File dir = FileUtil.getDownloadImgPath();
                if (!dir.exists()){
                    dir.mkdirs();
                }
                SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss"); //简单设置当前时间为图片名称形式
                String fileName = df.format(new Date()) + ".png";
                File file = new File(dir, fileName);
                try {
                    FileUtil.copyFile(getCacheFileByImageUrl(imageUrls.get(currPosition)), file);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(ImageBrowseActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (IOException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(ImageBrowseActivity.this, "保存失败", Toast.LENGTH_SHORT).show();
                        }
                    });
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private File getCacheFileByImageUrl(String imgUrl) {
        File cacheFile = null;
        FutureTarget<File> future = Glide.with(this)
                .load(imgUrl)
                .downloadOnly(Target.SIZE_ORIGINAL,Target.SIZE_ORIGINAL);
        try {
            cacheFile = future.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return cacheFile;
    }


    private class MyPagerAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return imageUrls.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            if (!TextUtils.isEmpty(imageUrls.get(position))){
                PhotoView photoView = new PhotoView(ImageBrowseActivity.this);
                photoView.enable();
                photoView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                Glide.with(ImageBrowseActivity.this).load(imageUrls.get(position)).into(photoView);
                container.addView(photoView);
                return photoView;
            }
            return null;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }
    }

    private ViewPager.OnPageChangeListener mOnPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {
            currPosition = position;
            pageNumSwitcher.setText((currPosition + 1) + "/" + imageUrls.size());
            mViewPager.setTag(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    private int getClickEnterPositionAndPreDeal(String url){
        //特殊处理去重 存在html中有两个前两张的图片一样 隐藏一个
        if (imageUrls.size() > 2 && imageUrls.get(0).equals(imageUrls.get(1))){
            imageUrls.remove(1);
        }
        for (int i = 0; i < imageUrls.size(); i++){
            if (imageUrls.get(i).equals(url)) return i;
        }
        return 0;
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, R.anim.anim_scale_exit);
    }

    @Override
    protected void onDestroy() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Glide.get(ImageBrowseActivity.this).clearDiskCache();//清理磁盘缓存需要在子线程中执行
            }
        }).start();
        Glide.get(this).clearMemory();//清理内存缓存可以在UI主线程中进行
        super.onDestroy();
    }

}
