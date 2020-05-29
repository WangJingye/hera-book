package com.delcache.hera.controller.book;

import android.content.Context;
import com.delcache.hera.bean.table.BookBean;
import com.delcache.hera.bean.table.BookMenuBean;
import com.delcache.hera.bean.HomeBean;
import com.delcache.hera.controller.base.Controller;
import com.delcache.hera.helper.RequestHelper;
import com.delcache.hera.interfaces.RefreshUIInterface;
import com.delcache.hera.widget.ProgressSubscriber;

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

    public void bookInfoRequest(long bookId) {
        RequestHelper.getInstance().bookInfoRequest(bookId)
                .subscribe(new ProgressSubscriber<BookBean>(mContext) {
                    @Override
                    public void onNext(BookBean bookBean) {
                        BookBean bookBean1 = BookBean.findById(BookBean.class, bookBean.getBookId());
                        bookBean.setIsAdded(0);
                        if (bookBean1 != null) {
                            bookBean.setId(bookBean1.getBookId());
                            bookBean.setIsAdded(1);
                            bookBean.setPageId(bookBean1.getPageId());
                        }
                        refreshUIInterface.refreshUI(bookBean);
                    }

                    @Override
                    public void error(String error) {
                        super.error(error);
                    }
                });
    }

    public void bookDetailRequest(long bookId, long menuId) {
        RequestHelper.getInstance().bookDetailRequest(bookId, menuId)
                .subscribe(new ProgressSubscriber<BookMenuBean>(mContext) {
                    @Override
                    public void onNext(BookMenuBean bookMenuBean) {
                        BookBean bookBean = BookBean.findById(BookBean.class, bookMenuBean.getBookId());
                        if (bookBean != null) {
                            bookBean.setPageId(bookMenuBean.getMenuId());
                            bookBean.save();
                        }
                        refreshUIInterface.refreshUI(bookMenuBean);
                    }

                    @Override
                    public void error(String error) {
                        super.error(error);
                    }
                });
    }

    public void getMenuListRequest(long bookId) {
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

    public long getBookLastReadMenuId(long bookId) {
        BookBean bookBean = BookBean.findById(BookBean.class, bookId);
        if (bookBean != null) {
            return bookBean.getPageId();
        }
        return 1;
    }
}
