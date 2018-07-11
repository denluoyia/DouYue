package com.denluoyia.douyue.presenter;

import com.denluoyia.douyue.constant.URLConstant;
import com.denluoyia.douyue.manager.net.IRequestCallback;
import com.denluoyia.douyue.manager.net.NetManager;
import com.denluoyia.douyue.model.SplashBean;
import com.denluoyia.douyue.utils.FileUtil;
import com.denluoyia.douyue.utils.LogUtil;
import com.denluoyia.douyue.utils.NetworkUtil;
import com.denluoyia.douyue.utils.StringUtil;
import com.denluoyia.douyue.utils.TimeUtil;
import com.denluoyia.douyue.utils.UIUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by denluoyia
 * Date 2018/06/25
 * DouYue
 */
public class SplashPresenter implements SplashContract.Presenter {

    @Override
    public void getSplashImages() {
//        HashMap<String, String> params = new LinkedHashMap<>();
//        params.put("time", TimeUtil.getTimeStamp());
//        params.put("device_id", StringUtil.randomUUID());
//        NetManager.get(URLConstant.URL_SPLASH_IMAGE, params, new IRequestCallback<SplashBean>() {
//            @Override
//            public void onFailure(IOException e) {
//                LogUtil.e("loadSplashImg", "failed");
//            }
//
//            @Override
//            public void onSuccess(SplashBean bean) {
//                if (NetworkUtil.isWifi(UIUtil.getContext()) && NetworkUtil.isNetworkConnected(UIUtil.getContext())){
//                    List<String> images = bean.getImages();
//                    if (images != null && images.size() > 0){
//                        for (int i = 0; i < images.size(); i++){
//                            final File file = FileUtil.getSplashImgDir();
//                            NetManager.downloadFile(images.get(i), file, new Callback() {
//                                @Override
//                                public void onFailure(Call call, IOException e) {
//
//                                }
//
//                                @Override
//                                public void onResponse(Call call, Response response) throws IOException {
//                                    String url = response.request().url().toString();
//                                    int index = url.lastIndexOf("/");
//                                    String picName = url.substring(index+1);
//                                    File picFile = new File(file, picName);
//                                    if (picFile.exists()){
//                                        return;
//                                    }
//
//                                    FileOutputStream fos = new FileOutputStream(picFile);
//                                    InputStream in = response.body().byteStream();
//                                    byte[] buf = new byte[1024];
//                                    int len;
//                                    while ((len = in.read(buf)) != -1){
//                                        fos.write(buf,0,len);
//                                    }
//                                    fos.flush();
//                                    in.close();
//                                    fos.close();
//                                }
//                            });
//                        }
//                    }
//
//                }else{
//                    LogUtil.e("not downlaod", "当前不是WIFI环境，先不进行下载~");
//                }
//            }
//        });
    }

    @Override
    public void detachView() {

    }
}
