package com.delcache.hera.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.delcache.hera.R;
import com.delcache.hera.bean.table.BookBean;
import com.delcache.hera.helper.ImageHelper;

import java.util.List;

/**
 * 适配器
 */
public class CollectionListAdapter extends BaseListAdapter {
    public CollectionListAdapter(Context context, List list) {
        super(context, list);
    }

    @Override
    public View getView(int position, View view, ViewGroup arg2) {
        ViewHolder holder;
        if (view == null) {
            view = mInflater.inflate(R.layout.adapter_collect_item, null);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        BookBean bookBean = (BookBean) getItem(position);
        holder.bookTitleText.setText(bookBean.getTitle());
        ImageHelper.getInstance().showImage(bookBean.getPic(), holder.bookPicView);
        return view;
    }

    class ViewHolder {
        @BindView(R.id.book_title)
        TextView bookTitleText;
        @BindView(R.id.book_pic)
        ImageView bookPicView;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}