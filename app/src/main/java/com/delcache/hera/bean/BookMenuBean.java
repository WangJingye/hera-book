package com.delcache.hera.bean;

import com.google.gson.annotations.SerializedName;

public class BookMenuBean extends BaseBean {

    @SerializedName("menu_id")
    private int menuId;

    @SerializedName("title")
    private String title;

    @SerializedName("content")
    private String detail;

    @SerializedName("prev")
    private int prevId;

    @SerializedName("next")
    private int nextId;

    public int getMenuId() {
        return menuId;
    }

    public void setMenuId(int menuId) {
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

    public int getPrevId() {
        return prevId;
    }

    public void setPrevId(int prevId) {
        this.prevId = prevId;
    }

    public int getNextId() {
        return nextId;
    }

    public void setNextId(int nextId) {
        this.nextId = nextId;
    }
}