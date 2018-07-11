package com.denluoyia.douyue.model.db;

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
public class MyCollectionBean {
    @Id(autoincrement = true)
    private Long id;
    @NotNull
    private String title;
    @NotNull
    private String collectionId;
    @NotNull
    private int collectionType; // 1 文字 3 声音 2 影像 4 图书

    private String url;

    @Generated(hash = 778021249)
    public MyCollectionBean(Long id, @NotNull String title,
            @NotNull String collectionId, int collectionType, String url) {
        this.id = id;
        this.title = title;
        this.collectionId = collectionId;
        this.collectionType = collectionType;
        this.url = url;
    }
    @Generated(hash = 1429354802)
    public MyCollectionBean() {
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
    public String getCollectionId() {
        return this.collectionId;
    }
    public void setCollectionId(String collectionId) {
        this.collectionId = collectionId;
    }
    public int getCollectionType() {
        return this.collectionType;
    }
    public void setCollectionType(int collectionType) {
        this.collectionType = collectionType;
    }
    public String getUrl() {
        return this.url;
    }
    public void setUrl(String url) {
        this.url = url;
    }


}
