package com.denluoyia.douyue.event;

import com.denluoyia.douyue.model.db.NoteBean;

/**
 * Created by denluoyia
 * Date 2018/07/04
 * DouYue
 */
public class NoteListChangeEvent {

    public static final int TYPE_UPDATE = 1;
    public static final int TYPE_DELETE = 2;
    public static final int TYPE_ADD = 3;

    public int nodeOp;
    public NoteBean item;

    public NoteListChangeEvent(int type, NoteBean item){
        this.nodeOp = type;
        this.item = item;
    }
}
