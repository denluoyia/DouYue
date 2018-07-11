package com.denluoyia.douyue.manager.db.greendao;

import com.denluoyia.douyue.DouYueApp;
import com.denluoyia.douyue.model.db.MyCollectionBean;

import java.util.List;

/**
 * Created by denluoyia
 * Date 2018/07/04
 * DouYue
 */
public class MyCollectionDaoManager {

    /**
     *插入一条笔记
     */
    public static void insert(MyCollectionBean bean){
        DouYueApp.getDaoSession().getMyCollectionBeanDao().insert(bean);
    }

    /**
     *删除笔记
     */
    public static void delete(MyCollectionBean bean){
        DouYueApp.getDaoSession().getMyCollectionBeanDao().delete(bean);
    }

    /**
     *更新笔记
     */
    public static void update(MyCollectionBean bean){
        DouYueApp.getDaoSession().getMyCollectionBeanDao().update(bean);
    }

    /**
     *查询所有可编辑的笔记
     */
    public static List<MyCollectionBean> queryAll(){
        return DouYueApp.getDaoSession().getMyCollectionBeanDao().queryBuilder().orderDesc(MyCollectionBeanDao.Properties.Id).list();
    }

    /**分页进行加载 降序查询 每页10条数据*/
    public List<MyCollectionBean> getDataByPageOffset(int page){
        return DouYueApp.getDaoSession().getMyCollectionBeanDao().queryBuilder().orderDesc(MyCollectionBeanDao.Properties.Id).offset((page-1) * 10).limit(10).list();
    }

}
