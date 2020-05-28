package com.delcache.hera.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.delcache.hera.R;
import com.delcache.hera.bean.BookMenuBean;

import java.util.List;

/**
 * 适配器
 */
public class DetailListAdapter extends BaseListAdapter {
    public DetailListAdapter(Context context, List list) {
        super(context, list);
    }

    @Override
    public View getView(int position, View view, ViewGroup arg2) {
        ViewHolder holder;
        if (view == null) {
            view = mInflater.inflate(R.layout.adapter_detail_item, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        BookMenuBean bookMenuBean = (BookMenuBean) getItem(position);
        holder.titleText.setText(bookMenuBean.getTitle());
        holder.titleText.setTag(bookMenuBean.getMenuId());
        return view;
    }

    class ViewHolder {
        @BindView(R.id.book_menu_title)
        TextView titleText;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}