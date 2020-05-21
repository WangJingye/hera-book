package com.delcache.hera.bean;

import com.google.gson.annotations.SerializedName;

public class ResetTokenBean extends BaseBean {

    @SerializedName("reset_token")
    private String resetToken;
    public String getResetToken() {
        return resetToken;
    }

    public void setResetToken(String resetToken) {
        this.resetToken = resetToken;
    }



}
