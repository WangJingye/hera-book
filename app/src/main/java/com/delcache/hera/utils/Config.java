package com.delcache.hera.utils;

import java.util.Arrays;
import java.util.List;

public class Config {

    public static List<String> getLoginAction() {
        String[] ret = {"FragmentUser","FragmentCollect"};
        return Arrays.asList(ret);
    }
}
