package com.delcache.hera.bean;

public class HttpResult<T> {

    private String message;

    private int code;

    private String success;

    private T data;

    public String getMessage() {
        return message;
    }

    public T getEmpty() {
        return (T)new Object();
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
