package com.delcache.hera.controller.user;

import android.content.Context;
import com.delcache.hera.bean.table.BookBean;
import com.delcache.hera.controller.base.Controller;
import com.delcache.hera.helper.RequestHelper;
import com.delcache.hera.interfaces.RefreshUIInterface;
import com.delcache.hera.widget.ProgressSubscriber;

import java.util.List;

public class UserController extends Controller {

    public UserController(Context mContext, RefreshUIInterface refreshUIInterface) {
        super(mContext, refreshUIInterface);
    }

    public void addToCollection(BookBean bookBean) {
        bookBean.setPageId(1);
        bookBean.setIsAdded(1);
        bookBean.setId(bookBean.getBookId());
        bookBean.save();
        refreshUIInterface.refreshWithResult(null, 1);
    }

    public void getCollection() {
        refreshUIInterface.refreshUI(BookBean.listAll(BookBean.class));
    }
}
