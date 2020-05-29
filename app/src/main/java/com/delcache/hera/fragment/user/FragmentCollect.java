package com.delcache.hera.fragment.user;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import butterknife.BindView;
import com.delcache.hera.R;
import com.delcache.hera.activity.MainActivity;
import com.delcache.hera.adapter.CollectionListAdapter;
import com.delcache.hera.bean.table.BookBean;
import com.delcache.hera.controller.user.UserController;
import com.delcache.hera.fragment.base.BaseFragment;
import com.delcache.hera.fragment.book.FragmentBookDetail;
import com.delcache.hera.helper.FragmentHelper;
import com.delcache.hera.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class FragmentCollect extends BaseFragment {

    private UserController userController;

    private CollectionListAdapter listAdapter;

    @BindView(R.id.book_collect_list)
    GridView listView;
    private List<BookBean> list = new ArrayList<>();

    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return init(R.layout.fragment_collect, container);
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity) mContext).setSelectedTab(1);
        userController.getCollection();
    }

    @Override
    protected void setupData() {
        userController = new UserController(mContext, this);
    }

    @Override
    protected void setupView(View view) {
        super.setupView(view);
        View emptyView = LayoutInflater.from(mContext).inflate(R.layout.collect_empty, null);
        Utils.setAdapterEmptyView(emptyView, listView);
        emptyView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentHelper.getInstance().redirectHome();
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                BookBean bookBean = (BookBean) adapterView.getItemAtPosition(position);
                FragmentBookDetail detail = new FragmentBookDetail();
                Bundle args = new Bundle();
                args.putLong("menuId", bookBean.getPageId());
                args.putLong("bookId", bookBean.getBookId());
                detail.setArguments(args);
                FragmentHelper.getInstance().addFragment(detail);
            }
        });
    }

    @Override
    public void refreshUI(Object data) {
        list.clear();
        list.addAll((List<BookBean>) data);
        if (listAdapter == null) {
            listAdapter = new CollectionListAdapter(mContext, list);
            listView.setAdapter(listAdapter);
        } else {
            listView.requestLayout();
            listAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected int isDisplayBottomMenu() {
        return View.VISIBLE;
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public boolean onBackPressed() {
        Utils.exit(mContext);
        return true;
    }
}
