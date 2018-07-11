package com.denluoyia.douyue.utils;

import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import static android.content.ContentValues.TAG;
import static android.os.Environment.getExternalStorageDirectory;

public class FileUtil {

    /** 创建文件，父目录不存在则创建父目录 */
    public static void createFile(String filePath) {
        try {
            File file = new File(filePath);
            if(file.exists()) file.delete();
            if(!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            file.createNewFile();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 移动文件
     * @param fromFile 源文件
     * @param toFile   目标文件
     */
    public static void moveFileTo(File fromFile, File toFile) throws IOException {
        FileInputStream ins   = new FileInputStream(fromFile);
        FileOutputStream out   = new FileOutputStream(toFile);
        byte[]           bytes = new byte[1024*4];
        int              len;
        while((len = ins.read(bytes)) != -1) {
            out.write(bytes, 0, len);
        }
        ins.close();
        out.close();
        fromFile.delete();
    }


    /**
     * 规范file地址为Uri格式(追加file://)
     * @param path 文件路径 eg: /sdcard/0/aa.jpg;
     * @return uri文件路径 eg；file:///sdcard/0/aa.jpg.
     */
    public static String formatFileUri(String path) {
        if(!TextUtils.isEmpty(path) && !path.startsWith("file://")) {
            path = "file://"+path;
        }
        return path;
    }

    /**
     * 规范file地址为Uri格式(去掉file://)
     * @return uri文件路径 eg；file:///sdcard/0/aa.jpg.
     */
    public static String formatFileUri2Normal(String uri) {
        if(!TextUtils.isEmpty(uri) && uri.startsWith("file://")) {
            uri.replace("file://", "");
        }
        return uri;
    }


    /**
     * 获取APP根目录
     *
     * @return File(" / mnt / storage0 / Android / data / packagename ") if the phone has SD card,else return File("data/data/packagename/cache")
     */
    public static File getAppRootDir() {
        File externalCacheDir = UIUtil.getContext().getExternalCacheDir();
        if(null == getExternalStorageDirectory() || externalCacheDir == null) {
            return UIUtil.getContext().getCacheDir();
        } else {
            File externalAppRootDir = externalCacheDir.getParentFile();
            if(!externalAppRootDir.exists()) {
                externalAppRootDir.mkdirs();
            }
            return externalAppRootDir;
        }
    }


    public static File getAppCacheDir(){
        return new File(getAppRootDir(), "/cache/");
    }

    public static File getSplashImgDir(){
        return new File(getAppRootDir(), "/splashImg/");
    }


    /**
     * 获取APP缓存目录路径
     *
     * @return path(" / mnt / storage0 / Android / data / packagename / cache / ") if the phone has SD card,else return path("data/data/packagename/cache/")
     */
    public static String getAppCacheDirPath() {
        String path = getAppCacheDir().getAbsolutePath();
        if(!TextUtils.isEmpty(path) && !path.endsWith(File.separator)) {
            path = path+File.separator;
        }
        LogUtil.v(TAG, "getAppCacheDirPath = "+path);
        return path;
    }

    /**
     * 获取日志记录目录文件
     *
     * @return File(" / mnt / storage0 / packagename / .crashLog ") if the phone has SD card,else return File("data/data/packagename/cache/.crashLog")
     */
    public static File getLogDir() {
        File dir = new File(getAppRootDir(), "/.crashLog/");
        if(!dir.exists()) {
            dir.mkdirs();
        }
        Log.v(TAG, "getLogDir = "+dir.getAbsolutePath());
        return dir;
    }


    /**
     * 删除指定路径的文件
     *
     * @param filePath 文件路径
     */
    public static boolean deleteFile(String filePath) {
        boolean result = false;
        File    file   = new File(filePath);
        if(file.exists()) {
            result = file.delete();
        }
        return result;
    }

    /**
     * 删除目录下所有文件包括子目录(不删除根目录)
     *
     * @param dir 当前需要删除的目录文件
     */
    public static boolean deleteDirsAndFile(File dir) {
        return deleteDirsAndFile(dir, false);
    }

    /**
     * 删除目录下所有文件包括子目录
     *
     * @param dir        当前需要删除的目录文件
     * @param selfDelete 是否删除根目录
     */
    public static boolean deleteDirsAndFile(File dir, boolean selfDelete) {
        if(dir == null) return true;
        File[] files = dir.listFiles();
        if(files != null && files.length > 0) {
            for(File file : files) {
                if(file.isDirectory()) {
                    deleteDirsAndFile(file, true);
                }
                file.delete();
            }
        }
        if(selfDelete) dir.delete();
        return true;
    }


    /**
     * 计算文件夹目录下所有文件的大小
     */
    public static long sizeFiles(File dir) {
        long result = 0;
        if(dir == null) return result;
        File[] files = dir.listFiles();
        if(files == null || files.length == 0) {
            return result;
        }
        for(File file : files) {
            if(file.isDirectory()) {
                result += sizeFiles(file);
            }
            result += file.length();
        }
        return result;
    }

    /**
     * 获取指定文件大小
     */
    public static long getFileSize(File file) {
        long size = 0;
        try {
            if(file.exists()) {
                FileInputStream fis = new FileInputStream(file);
                size = fis.available();
            } else {
                file.createNewFile();
                Log.e("获取文件大小", "文件不存在!");
            }
            return size;
        } catch(Exception e) {
            return 0;
        }
    }


    public static boolean isExists(File file){
        return file.exists();
    }

}
