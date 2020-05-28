package com.delcache.hera.controller.user;

import android.content.Context;
import com.delcache.hera.bean.BookBean;
import com.delcache.hera.controller.base.Controller;
import com.delcache.hera.helper.RequestHelper;
import com.delcache.hera.interfaces.RefreshUIInterface;
import com.delcache.hera.widget.ProgressSubscriber;

import java.util.List;

public class UserController extends Controller {

    public UserController(Context mContext, RefreshUIInterface refreshUIInterface) {
        super(mContext, refreshUIInterface);
    }

    public void getCollectionRequest() {
        RequestHelper.getInstance().getCollectionRequest()
                .subscribe(new ProgressSubscriber<List<BookBean>>(mContext, false) {
                    @Override
                    public void onNext(List<BookBean> list) {
                        refreshUIInterface.refreshUI(list);
                    }
                });
    }

    public void addToCollectionRequest(int bookId) {
        RequestHelper.getInstance().addToCollectionRequest(bookId)
                .subscribe(new ProgressSubscriber(mContext) {
                    @Override
                    public void onNext(Object o) {
                        refreshUIInterface.refreshWithResult(null, 1);
                    }

                    @Override
                    public void error(String error) {
                        super.error(error);
                    }
                });
    }

}
