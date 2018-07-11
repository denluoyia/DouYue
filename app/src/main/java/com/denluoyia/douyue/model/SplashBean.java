package com.denluoyia.douyue.model;

import java.util.List;

/**
 * Created by denluoyia
 * Date 2018/06/25
 * DouYue
 */
public class SplashBean {
    /**
     * count: 10,
     * status: "ok"
     * time: 1526893955
     * images: ["https://img.owspace.com/Public/uploads/Picture/2018-05-21/5b028d82b6b12.jpg","https://img.owspace.com/Public/uploads/Picture/2018-05-21/5b026d96acaf9.jpg"]
     */

    private int count;
    private String status;
    private int time;
    private List<String> images;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }
}
