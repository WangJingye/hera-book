package com.delcache.hera.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CarouselBean extends BaseBean {

    @SerializedName("pic")
    private String pic;

    @SerializedName("type")
    private int type;

    @SerializedName("link")
    private String link;

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}