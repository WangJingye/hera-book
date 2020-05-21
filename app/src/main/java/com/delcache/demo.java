package com.delcache;

import com.delcache.hera.bean.HttpResult;
import com.delcache.hera.bean.UserBean;

public class demo {

    public static void main(String args[]) {
        HttpResult<UserBean> h=new HttpResult<>();
       System.out.println( h.getEmpty()==null);
    }
}
