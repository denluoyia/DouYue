package com.denluoyia.douyue.manager.db.greendao;

import com.denluoyia.douyue.DouYueApp;
import com.denluoyia.douyue.model.db.NoteBean;

import java.util.List;

/**
 * Created by denluoyia
 * Date 2018/07/04
 * DouYue
 */
public class NoteDaoManager {

    /**
     *插入一条笔记
     */
    public static void insert(NoteBean note){
        DouYueApp.getDaoSession().getNoteBeanDao().insert(note);
    }

    /**
     *删除笔记
     */
    public static void delete(NoteBean note){
        DouYueApp.getDaoSession().getNoteBeanDao().delete(note);
    }

    /**
     *更新笔记
     */
    public static void update(NoteBean note){
        DouYueApp.getDaoSession().getNoteBeanDao().update(note);
    }

    /**
     *查询所有可编辑的笔记
     */
    public static List<NoteBean> queryAllByStatusOK(){
        return DouYueApp.getDaoSession().getNoteBeanDao().queryBuilder().where(NoteBeanDao.Properties.Status.eq(NoteBean.NOTE_STATUS_OK)).list();
    }

    /**
     * 查询回收站里面的笔记
     */
    public static List<NoteBean> queryAllByStatusIDLE(){
        return DouYueApp.getDaoSession().getNoteBeanDao().queryBuilder().where(NoteBeanDao.Properties.Status.eq(NoteBean.NOTE_STATUS_IDLE)).list();
    }
}
