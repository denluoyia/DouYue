package com.denluoyia.douyue.event;

/**
 * Created by denluoyia
 * Date 2018/07/02
 * DouYue
 */
public class SearchAdapterItemClickEvent {
    /**
     * 1 热门点击
     * 2 历史记录点击
     * 3 搜索结果条目点击
     */
    public int clickType;
    public String bookName;
    public String bookId;
    public SearchAdapterItemClickEvent(int clickType, String bookName){
        this.clickType = clickType;
        this.bookName = bookName;
    }

    public SearchAdapterItemClickEvent(int clickType, String bookName, String bookId){
        this.clickType = clickType;
        this.bookName = bookName;
        this.bookId = bookId;
    }
}
