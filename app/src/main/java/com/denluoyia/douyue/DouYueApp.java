package com.denluoyia.douyue;


import com.denluoyia.douyue.base.BaseApplication;
import com.denluoyia.douyue.manager.db.greendao.DaoMaster;
import com.denluoyia.douyue.manager.db.greendao.DaoSession;

/**
 * Created by denluoyia
 * Date 2018/06/26
 * DouYue
 */
public class DouYueApp extends BaseApplication{

    private static DaoSession sDaoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        CrashHandler.getInstance().init(this);
        sDaoSession = DaoMaster.newDevSession(this, "douyue.db");
    }

    public static DaoSession getDaoSession(){
        return sDaoSession;
    }


}
