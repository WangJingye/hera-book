package com.delcache.hera.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BookBean extends BaseBean {

    @SerializedName("book_id")
    private int bookId;
    @SerializedName("title")
    private String title;
    @SerializedName("pic")
    private String pic;
    @SerializedName("desc")
    private String desc;
    @SerializedName("author")
    private String author;
    @SerializedName("category_name")
    private String categoryName;
    @SerializedName("is_end")
    private int isEnd;
    @SerializedName("is_added")
    private int isAdded;
    @SerializedName("page_id")
    private int pageId;
    @SerializedName("list")
    private List<BookMenuBean> menuList;

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getIsEnd() {
        return isEnd;
    }

    public void setIsEnd(int isEnd) {
        this.isEnd = isEnd;
    }

    public int getIsAdded() {
        return isAdded;
    }

    public void setIsAdded(int isAdded) {
        this.isAdded = isAdded;
    }

    public int getPageId() {
        return pageId;
    }

    public void setPageId(int pageId) {
        this.pageId = pageId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public List<BookMenuBean> getMenuList() {
        return menuList;
    }

    public void setMenuList(List<BookMenuBean> menuList) {
        this.menuList = menuList;
    }

    public String getBookStatusWithMenuCount() {
        StringBuilder str = new StringBuilder();
        if (this.isEnd == 1) {
            str.append("完结，共");
        } else {
            str.append("连载中，共");
        }
        str.append(menuList.size());
        str.append("章");
        return str.toString();
    }
}