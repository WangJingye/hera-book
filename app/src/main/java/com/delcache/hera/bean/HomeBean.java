package com.delcache.hera.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HomeBean extends BaseBean {
    @SerializedName("carousel")
    private List<CarouselBean> carouselBeanList;

    @SerializedName("book")
    private List<BookListBean> bookListBeanList;

    public List<CarouselBean> getCarouselBeanList() {
        return carouselBeanList;
    }

    public void setCarouselBeanList(List<CarouselBean> carouselBeanList) {
        this.carouselBeanList = carouselBeanList;
    }

    public List<BookListBean> getBookListBeanList() {
        return bookListBeanList;
    }

    public void setBookListBeanList(List<BookListBean> bookListBeanList) {
        this.bookListBeanList = bookListBeanList;
    }
}