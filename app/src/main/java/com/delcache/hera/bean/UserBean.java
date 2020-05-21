package com.delcache.hera.bean;

import com.google.gson.annotations.SerializedName;

public class UserBean extends BaseBean {

    @SerializedName("userid")
    private String userId;

    @SerializedName("username")
    private String userName;

    @SerializedName("realname")
    private String realName;

    @SerializedName("telephone")
    private String telephone;

    @SerializedName("identity")
    private String identity;

    @SerializedName("email")
    private String email;

    @SerializedName("avatar")
    private String avatar;

    @SerializedName("sex")
    private String sex;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String gettelephone() {
        return telephone;
    }

    public void settelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
