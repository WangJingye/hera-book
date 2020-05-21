package com.delcache.hera.bean;

import com.orm.SugarRecord;

import java.io.Serializable;

public class BaseBean extends SugarRecord implements Serializable {

    private int requestId;

    private boolean judgeFlag;


    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    public boolean isJudgeFlag() {
        return judgeFlag;
    }

    public void setJudgeFlag(boolean judgeFlag) {
        this.judgeFlag = judgeFlag;
    }
}
