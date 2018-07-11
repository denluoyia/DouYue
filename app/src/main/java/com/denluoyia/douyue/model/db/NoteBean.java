package com.denluoyia.douyue.model.db;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by denluoyia
 * Date 2018/07/04
 * DouYue
 */
@Entity
public class NoteBean implements Parcelable{
    public static final int NOTE_STATUS_OK = 1;
    public static final int NOTE_STATUS_IDLE = 0;

    @Id(autoincrement = true)
    private Long id;  //大写的Long,经常踩坑
    private String title;
    @NotNull
    private String content;
    private int status;
    private long createTime;
    private long lastEditTime;

    @Generated(hash = 205613324)
    public NoteBean(Long id, String title, @NotNull String content, int status,
            long createTime, long lastEditTime) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.status = status;
        this.createTime = createTime;
        this.lastEditTime = lastEditTime;
    }
    @Generated(hash = 451626881)
    public NoteBean() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getTitle() {
        return this.title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getContent() {
        return this.content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public int getStatus() {
        return this.status;
    }
    public void setStatus(int status) {
        this.status = status;
    }
    public long getCreateTime() {
        return this.createTime;
    }
    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }
    public long getLastEditTime() {
        return this.lastEditTime;
    }
    public void setLastEditTime(long lastEditTime) {
        this.lastEditTime = lastEditTime;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.title);
        dest.writeString(this.content);
        dest.writeInt(this.status);
        dest.writeLong(this.createTime);
        dest.writeLong(this.lastEditTime);
    }

    private NoteBean(Parcel source) {
        this.id = source.readLong();
        this.title = source.readString();
        this.content = source.readString();
        this.status = source.readInt();
        this.createTime = source.readLong();
        this.lastEditTime = source.readLong();
    }


    public static final Creator<NoteBean> CREATOR = new Creator<NoteBean>() {
        @Override
        public NoteBean createFromParcel(Parcel source) {
            return new NoteBean(source);
        }

        @Override
        public NoteBean[] newArray(int size) {
            return new NoteBean[size];
        }
    };
}
