package com.delcache.hera.controller.user;

import android.content.Context;
import com.delcache.hera.bean.BookBean;
import com.delcache.hera.bean.BookMenuBean;
import com.delcache.hera.bean.HomeBean;
import com.delcache.hera.controller.base.Controller;
import com.delcache.hera.helper.RequestHelper;
import com.delcache.hera.interfaces.RefreshUIInterface;
import com.delcache.hera.widget.ProgressSubscriber;

import java.util.List;

public class BookController extends Controller {

    public BookController(Context mContext, RefreshUIInterface refreshUIInterface) {
        super(mContext, refreshUIInterface);
    }

    public void homeRequest() {
        RequestHelper.getInstance().homeRequest()
                .subscribe(new ProgressSubscriber<HomeBean>(mContext) {
                    @Override
                    public void onNext(HomeBean homeBean) {
                        refreshUIInterface.refreshUI(homeBean);
                    }

                    @Override
                    public void error(String error) {
                        super.error(error);
                    }
                });
    }

    public void bookInfoRequest(int bookId) {
        RequestHelper.getInstance().bookInfoRequest(bookId)
                .subscribe(new ProgressSubscriber<BookBean>(mContext) {
                    @Override
                    public void onNext(BookBean bookBean) {
                        refreshUIInterface.refreshUI(bookBean);
                    }

                    @Override
                    public void error(String error) {
                        super.error(error);
                    }
                });
    }

    public void bookDetailRequest(int bookId, int menuId) {
        RequestHelper.getInstance().bookDetailRequest(bookId, menuId)
                .subscribe(new ProgressSubscriber<BookMenuBean>(mContext) {
                    @Override
                    public void onNext(BookMenuBean bookMenuBean) {
                        refreshUIInterface.refreshUI(bookMenuBean);
                    }

                    @Override
                    public void error(String error) {
                        super.error(error);
                    }
                });
    }

    public void getMenuListRequest(int bookId) {
        RequestHelper.getInstance().bookInfoRequest(bookId)
                .subscribe(new ProgressSubscriber<BookBean>(mContext, false) {
                    @Override
                    public void onNext(BookBean bookBean) {
                        refreshUIInterface.refreshWithResult(bookBean, 1);
                    }

                    @Override
                    public void error(String error) {
                        super.error(error);
                    }
                });
    }

}
