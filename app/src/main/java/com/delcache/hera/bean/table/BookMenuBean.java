package com.delcache.hera.bean.table;

import com.delcache.hera.bean.BaseBean;
import com.google.gson.annotations.SerializedName;
import com.orm.dsl.Column;

public class BookMenuBean extends BaseBean {

    @SerializedName("book_id")
    @Column(name = "book_id")
    private long bookId;

    @SerializedName("menu_id")
    @Column(name = "menu_id")
    private long menuId;

    @SerializedName("title")
    @Column(name = "title")
    private String title;

    @SerializedName("content")
    @Column(name = "detail")
    private String detail;

    @SerializedName("prev")
    @Column(name = "prev_id")
    private long prevId;

    @SerializedName("next")
    @Column(name = "next_id")
    private long nextId;

    public long getBookId() {
        return bookId;
    }

    public void setBookId(long bookId) {
        this.bookId = bookId;
    }

    public long getMenuId() {
        return menuId;
    }

    public void setMenuId(long menuId) {
        this.menuId = menuId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public long getPrevId() {
        return prevId;
    }

    public void setPrevId(long prevId) {
        this.prevId = prevId;
    }

    public long getNextId() {
        return nextId;
    }

    public void setNextId(long nextId) {
        this.nextId = nextId;
    }
}