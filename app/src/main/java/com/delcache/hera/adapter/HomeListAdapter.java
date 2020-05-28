package com.delcache.hera.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.delcache.hera.R;
import com.delcache.hera.bean.BookBean;
import com.delcache.hera.bean.BookListBean;
import com.delcache.hera.fragment.book.FragmentBookInfo;
import com.delcache.hera.helper.FragmentHelper;
import com.delcache.hera.utils.Constants;
import com.delcache.hera.utils.MyLogger;
import com.delcache.hera.utils.Utils;
import com.delcache.hera.view.ExpandListView;

import java.util.ArrayList;
import java.util.List;

/**
 * 适配器
 */
public class HomeListAdapter extends BaseListAdapter {
    public HomeListAdapter(Context context, List list) {
        super(context, list);
    }

    @Override
    public View getView(int position, View view, ViewGroup arg2) {
        ViewHolder holder;
        if (view == null) {
            MyLogger.getLogger().e(position);
            view = mInflater.inflate(R.layout.adapter_home_item_list, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        BookListBean bookListBean = (BookListBean) getItem(position);
        holder.categoryText.setText(bookListBean.getCategory());
        holder.bookList.clear();
        holder.bookList.addAll(bookListBean.getList());
        if (holder.homeItemListAdapter == null) {
            holder.homeItemListAdapter = new HomeListItemAdapter(mContext, holder.bookList);
            holder.homeItemListView.setAdapter(holder.homeItemListAdapter);
            holder.homeItemListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    BookBean bookBean = (BookBean) adapterView.getItemAtPosition(i);
                    Bundle bundle = new Bundle();
                    bundle.putString("bookId", String.valueOf(bookBean.getBookId()));
                    Fragment fragment = new FragmentBookInfo();
                    fragment.setArguments(bundle);
                    FragmentHelper.getInstance().addFragment(fragment);
                }
            });
        } else {
            holder.homeItemListAdapter.notifyDataSetChanged();
        }
        return view;
    }

    class ViewHolder {
        @BindView(R.id.home_item_category)
        TextView categoryText;
        @BindView(R.id.home_item_list)
        ExpandListView homeItemListView;
        HomeListItemAdapter homeItemListAdapter;
        List<BookBean> bookList = new ArrayList<>();

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}