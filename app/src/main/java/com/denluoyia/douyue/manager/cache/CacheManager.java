package com.denluoyia.douyue.manager.cache;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import com.denluoyia.douyue.utils.FileUtil;
import com.denluoyia.douyue.utils.LogUtil;
import com.denluoyia.douyue.utils.UIUtil;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.util.concurrent.atomic.AtomicReference;

/**
 * function:缓存管理器
 */
@SuppressWarnings("unused")
public class CacheManager {
    private static final String TAG = "CacheManager";
    private static final int MAX_COUNT = 10 * 1024 * 1024;
    private static final int DEFAULT_VALUE_COUNT = 1;
    private static File dirExternal;
    private static File dirData;

    private static final AtomicReference<CacheManager> INSTANCE = new AtomicReference<>();

    public static CacheManager getInstance() {
        for (; ; ) {
            CacheManager cacheManager = INSTANCE.get();
            if (cacheManager != null) return cacheManager;
            cacheManager = new CacheManager();
            if (INSTANCE.compareAndSet(null, cacheManager)) return cacheManager;
        }
    }

    private DiskLruCache mDiskLruCache;

    private CacheManager() {
        dirExternal = new File(FileUtil.getAppCacheDirPath());
        dirData = new File(UIUtil.getContext().getCacheDir().getAbsolutePath());
        isCacheOpened();
    }

    private boolean isCacheOpened() {
        if (!isClosed()) return true;
        try {
            mDiskLruCache = DiskLruCache.open(
                    FileUtil.getAppCacheDir(),
                    CacheUtil.getAppVersion(UIUtil.getContext()),
                    DEFAULT_VALUE_COUNT, MAX_COUNT);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    //--------------------static method-----start

    /** String  写 */
    public static void putString(String key, String value) {
        getInstance().putAsString(key, value);
    }

    /** String  读 */
    public static String getString(String key) {
        return getInstance().getAsString(key);
    }

    /** byte  写 */
    public static void putByte(String key, byte[] value) {
        getInstance().putAsByte(key, value);
    }

    /** byte  读 */
    public static byte[] getByte(String key) {
        return getInstance().getAsBytes(key);
    }

    /** Serializable  写 */
    public static void putSerializable(String key, Serializable value) {
        getInstance().putAsSerializable(key, value);
    }

    /** Serializable  读 */
    public static <T> T getSerializable(String key) {
        return getInstance().getAsSerializable(key);
    }

    /** Bitmap  写 */
    public static void putBitmap(String key, Bitmap value) {
        getInstance().putAsBitmap(key, value);
    }

    /** Bitmap  读 */
    public static Bitmap getBitmap(String key) {
        return getInstance().getAsBitmap(key);
    }

    /** Drawable  写 */
    public static void putDrawable(String key, Drawable value) {
        getInstance().putAsDrawable(key, value);
    }

    /** Drawable  读 */
    public static Drawable getDrawable(String key) {
        return getInstance().getAsDrawable(key);
    }

    /** 根据key移除该缓存 */
    public static boolean remove(String key) {
        return getInstance().removeAsKey(key);
    }

    /** 删除所有缓存 */
    public static void clear() {
        getInstance().delete();
    }

    /** 刷新缓存 */
    public static void flushCache() {
        getInstance().flush();
    }

    /** 获取当前缓存大小 */
    public static long size() {
        return getInstance().sizeOf();
    }

    //--------------------static method-----end

    private void putAsString(String key, String value) {
        if (!getInstance().isCacheOpened()) return;
        DiskLruCache.Editor edit = null;
        BufferedWriter bw = null;
        try {
            edit = editor(key);
            if (edit == null) return;
            OutputStream os = edit.newOutputStream(0);
            bw = new BufferedWriter(new OutputStreamWriter(os));
            bw.write(value);
            edit.commit();//write CLEAN
        } catch (IOException e) {
            e.printStackTrace();
            try {
                edit.abort();//write REMOVE
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } finally {
            try {
                if (bw != null)
                    bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String getAsString(String key) {
        if (!getInstance().isCacheOpened()) return null;
        InputStream inputStream;
        inputStream = get(key);
        if (inputStream == null) return null;
        String str = null;
        try {
            str = CacheUtil.readFully(new InputStreamReader(inputStream, CacheUtil.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
            try {
                inputStream.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        return str;
    }


    private void putAsByte(String key, byte[] value) {
        if (!getInstance().isCacheOpened()) return;
        OutputStream out = null;
        DiskLruCache.Editor editor = null;
        try {
            editor = editor(key);
            if (editor == null) {
                return;
            }
            out = editor.newOutputStream(0);
            out.write(value);
            out.flush();
            editor.commit();//write CLEAN
        } catch (Exception e) {
            e.printStackTrace();
            try {
                if (editor != null) {
                    editor.abort();//write REMOVE
                }
            } catch (IOException e1) {
                e1.printStackTrace();
            }

        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private byte[] getAsBytes(String key) {
        if (!getInstance().isCacheOpened()) return null;
        byte[] res = null;
        InputStream is = get(key);
        if (is == null) return null;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            byte[] buf = new byte[256];
            int len;
            while ((len = is.read(buf)) != -1) {
                baos.write(buf, 0, len);
            }
            res = baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }


    private void putAsSerializable(String key, Serializable value) {
        if (!getInstance().isCacheOpened()) return;
        DiskLruCache.Editor editor = editor(key);
        ObjectOutputStream oos = null;
        if (editor == null) return;
        try {
            OutputStream os = editor.newOutputStream(0);
            oos = new ObjectOutputStream(os);
            oos.writeObject(value);
            oos.flush();
            editor.commit();
        } catch (IOException e) {
            e.printStackTrace();
            try {
                editor.abort();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } finally {
            try {
                if (oos != null)
                    oos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @SuppressWarnings("unchecked")
    private <T> T getAsSerializable(String key) {
        if (!getInstance().isCacheOpened()) return null;
        T t = null;
        InputStream is = get(key);
        ObjectInputStream ois = null;
        if (is == null) return null;
        try {
            ois = new ObjectInputStream(is);
            t = (T) ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (ois != null)
                    ois.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return t;
    }

    private void putAsBitmap(String key, Bitmap bitmap) {
        if (!getInstance().isCacheOpened()) return;
        putAsByte(key, CacheUtil.bitmap2Bytes(bitmap));
    }

    private Bitmap getAsBitmap(String key) {
        if (!getInstance().isCacheOpened()) return null;
        byte[] bytes = getAsBytes(key);
        if (bytes == null) return null;
        return CacheUtil.bytes2Bitmap(bytes);
    }

    private void putAsDrawable(String key, Drawable value) {
        if (!getInstance().isCacheOpened()) return;
        putAsBitmap(key, CacheUtil.drawable2Bitmap(value));
    }

    private Drawable getAsDrawable(String key) {
        if (!getInstance().isCacheOpened()) return null;
        byte[] bytes = getAsBytes(key);
        if (bytes == null) {
            return null;
        }
        return CacheUtil.bitmap2Drawable(CacheUtil.bytes2Bitmap(bytes));
    }

    private boolean removeAsKey(String key) {
        if (!getInstance().isCacheOpened()) return false;
        try {
            key = CacheUtil.hashKeyForDisk(key);
            return mDiskLruCache.remove(key);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private void close() {
        if (mDiskLruCache == null) return;
        try {
            mDiskLruCache.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void delete() {
        if (mDiskLruCache == null) return;
        try {
            mDiskLruCache.delete();
            FileUtil.deleteDirsAndFile(dirExternal);
            FileUtil.deleteDirsAndFile(dirData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void flush() {
        if (!getInstance().isCacheOpened()) return;
        try {
            mDiskLruCache.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean isClosed() {
        return mDiskLruCache == null || mDiskLruCache.isClosed();
    }

    private long sizeOf() {
        if (!getInstance().isCacheOpened()) return 0;
        flush();
        return FileUtil.sizeFiles(dirExternal) + FileUtil.sizeFiles(dirData);
    }

    public void setMaxSize(long maxSize) {
        if (!getInstance().isCacheOpened()) return;
        mDiskLruCache.setMaxSize(maxSize);
    }

    private File getDirectory() {
        if (!getInstance().isCacheOpened()) return null;
        return mDiskLruCache.getDirectory();
    }

    private long getMaxSize() {
        if (!getInstance().isCacheOpened()) return 0;
        return mDiskLruCache.getMaxSize();
    }

    public DiskLruCache.Editor editor(String key) {
        if (!getInstance().isCacheOpened()) return null;
        try {
            key = CacheUtil.hashKeyForDisk(key);
            //wirte DIRTY
            DiskLruCache.Editor edit = mDiskLruCache.edit(key);
            //edit maybe null :the entry is editing
            if (edit == null) {
                LogUtil.w(TAG, "the entry spcified key:" + key + " is editing by other . ");
            }
            return edit;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public InputStream get(String key) {
        if (!getInstance().isCacheOpened()) return null;
        try {
            DiskLruCache.Snapshot snapshot = mDiskLruCache.get(CacheUtil.hashKeyForDisk(key));
            if (snapshot == null) {//not find entry , or entry.readable = false
                LogUtil.e(TAG, "not find entry , or entry.readable = false");
                return null;
            }
            //write READ
            return snapshot.getInputStream(0);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
