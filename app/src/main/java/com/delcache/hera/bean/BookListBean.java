package com.delcache.hera.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BookListBean extends BaseBean {


    @SerializedName("category")
    private String category;
    @SerializedName("list")
    private List<BookBean> list;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<BookBean> getList() {
        return list;
    }

    public void setList(List<BookBean> list) {
        this.list = list;
    }
}