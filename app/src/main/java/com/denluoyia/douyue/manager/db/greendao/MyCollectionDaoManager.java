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
     *删除笔记
     */
    public static void deleteById(String id){
        List<MyCollectionBean> list = DouYueApp.getDaoSession().getMyCollectionBeanDao().queryBuilder().where(MyCollectionBeanDao.Properties.CollectionId.eq(id)).list();
        if (list != null && list.size() > 0){
            DouYueApp.getDaoSession().getMyCollectionBeanDao().deleteByKey(list.get(0).getId());
        }
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
    public static List<MyCollectionBean> getDataByPageOffset(int page){
        return DouYueApp.getDaoSession().getMyCollectionBeanDao().queryBuilder().orderDesc(MyCollectionBeanDao.Properties.Id).offset((page-1) * 10).limit(10).list();
    }

    public static boolean isCollectionExists(String id){
        return DouYueApp.getDaoSession().getMyCollectionBeanDao().queryBuilder().where(MyCollectionBeanDao.Properties.CollectionId.eq(id)).list().size() > 0;
    }

}
