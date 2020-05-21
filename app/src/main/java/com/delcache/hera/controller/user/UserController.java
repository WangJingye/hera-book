package com.delcache.hera.controller.user;

import android.content.Context;
import com.delcache.hera.controller.base.Controller;
import com.delcache.hera.interfaces.RefreshUIInterface;

public class UserController extends Controller {

    public UserController(Context mContext, RefreshUIInterface refreshUIInterface) {
        super(mContext, refreshUIInterface);
    }

    @Override
    protected boolean onBackPressed() {
        return false;
    }

}
