package com.delcache.hera.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseListAdapter extends BaseAdapter {

    protected Context mContext;

    protected LayoutInflater mInflater;

    protected List mContentList;

    public BaseListAdapter() {
        super();
        // TODO Auto-generated constructor stub
    }

    public BaseListAdapter(Context context, List list) {
        this.mContext = context;
        if (list == null || list.isEmpty()) {
            this.mContentList = new ArrayList();
        } else {
            this.mContentList = list;
        }
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        if (this.mContentList != null) {
            return this.mContentList.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        if (this.mContentList != null) {
            return this.mContentList.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        // TODO Auto-generated method stub
        return preInflateItemView(position, view, mContentList);
    }

    /**
     * Item 渲染预处理方法
     *
     * @param position
     * @param view
     * @return
     */
    protected View preInflateItemView(int position, View view, List contentList) {
        Object content = contentList.get(position);
        view = inflateItemView(position, view, content);
        return view;
    }

    /**
     * Item 渲染方法
     *
     * @param position
     * @param view
     * @return
     */
    protected View inflateItemView(int position, View view, Object content) {
        return setupView(position, view, content);
    }

    protected View setupView(int position, View view, Object content) {
        return null;
    }


    public static Bitmap getHttpBitmap(String url) {
        Bitmap bmp = null;
        try {
            URL myurl = new URL(url);
            // 获得连接
            HttpURLConnection conn = (HttpURLConnection) myurl.openConnection();
            conn.setConnectTimeout(6000);//设置超时
            conn.setDoInput(true);
            conn.setUseCaches(false);//不缓存
            conn.connect();
            InputStream is = conn.getInputStream();//获得图片的数据流
            bmp = BitmapFactory.decodeStream(is);
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bmp;
    }


    public void setPicture(final String url, final ImageView imageView) {
        new Thread(new Runnable() {

            @Override
            public void run() {
                //获取图片
                final Bitmap bitmap = getHttpBitmap(url);
                //发送一个Runnable对象
                imageView.post(new Runnable() {


                    @Override
                    public void run() {
                        imageView.setImageBitmap(bitmap);//在ImageView中显示从网络上获取到的图片
                    }

                });

            }
        }).start();//开启线程
    }

}
