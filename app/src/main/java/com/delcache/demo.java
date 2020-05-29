package com.delcache;

import com.delcache.hera.helper.RequestHelper;

public class demo {

    public static void main(String args[]) {
        RequestHelper.getInstance().homeRequest().doOnNext(homeBean -> {
        });
    }
}
