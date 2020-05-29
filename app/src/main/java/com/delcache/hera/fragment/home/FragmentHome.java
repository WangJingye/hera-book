package com.delcache.hera.fragment.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import butterknife.BindView;
import com.delcache.hera.R;
import com.delcache.hera.activity.MainActivity;
import com.delcache.hera.adapter.HomeListAdapter;
import com.delcache.hera.bean.HomeBean;
import com.delcache.hera.controller.book.BookController;
import com.delcache.hera.fragment.base.BaseFragment;
import com.delcache.hera.utils.Utils;

public class FragmentHome extends BaseFragment {

    @BindView(R.id.home_swipe)
    SwipeRefreshLayout homeSwipeLayout;
    @BindView(R.id.home_list)
    ListView homeListView;
    private HomeListAdapter listAdapter;
    private BookController bookController;

    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return init(R.layout.fragment_home, container);
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) mContext).setSelectedTab(0);
    }

    @Override
    protected void setupData() {
        bookController = new BookController(mContext, this);
    }

    @Override
    protected void sendRequest() {
        super.sendRequest();
        bookController.homeRequest();
    }

    @Override
    public void refreshUI(Object data) {
        HomeBean homeBean = (HomeBean) data;
        if (listAdapter == null) {
            listAdapter = new HomeListAdapter(mContext, homeBean.getBookListBeanList());
            homeListView.setAdapter(listAdapter);
        } else {
            homeListView.requestLayout();
            listAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected int isDisplayBottomMenu() {
        return View.VISIBLE;
    }

    @Override
    public boolean onBackPressed() {
        Utils.exit(mContext);
        return true;
    }
}
