package com.denluoyia.douyue.model;

import java.util.List;

/**
 * Created by denluoyia
 * Date 2018/06/30
 * DouYue
 */
public class BookSearchResultListBean {

    /**
     * count: 20,
     * start: 0,
     * total: 1214
     */

    private int count;
    private int start;
    private int total;
    private List<BookItemBean> books;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<BookItemBean> getBooks() {
        return books;
    }

    public void setBooks(List<BookItemBean> books) {
        this.books = books;
    }

}
