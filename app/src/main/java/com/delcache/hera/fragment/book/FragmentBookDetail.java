package com.delcache.hera.fragment.book;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.*;
import android.widget.*;
import butterknife.BindView;
import com.delcache.hera.R;
import com.delcache.hera.adapter.DetailListAdapter;
import com.delcache.hera.bean.table.BookBean;
import com.delcache.hera.bean.table.BookMenuBean;
import com.delcache.hera.controller.book.BookController;
import com.delcache.hera.fragment.base.BaseFragment;
import com.delcache.hera.utils.Constants;
import com.delcache.hera.utils.Utils;
import com.delcache.hera.widget.CustomToolbar;

import java.util.Collections;
import java.util.List;

public class FragmentBookDetail extends BaseFragment {
    @BindView(R.id.book_detail)
    TextView bookDetailView;
    private BookController bookController;

    @BindView(R.id.detail_scroll_view)
    ScrollView detailScrollView;
    @BindView(R.id.detail_actionbar)
    CustomToolbar toolbar;

    private GestureDetector mGestureDetector;
    private long menuId;
    private long bookId;
    private BookMenuBean bookMenuBean;
    private BookBean bookBean;
    private PopupWindow popupWindow;
    private DetailListAdapter menuAdapter;
    private ListView menuListView;
    private TextView bookSortView;
    private List<BookMenuBean> menuList;

    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = init(R.layout.fragment_book_detail, container);
        if (Constants.readMode) {
            view.setBackgroundColor(Utils.getColor(mContext, R.color.colorGray));
            detailScrollView.setBackgroundColor(Utils.getColor(mContext, R.color.colorGray));
            toolbar.setBackgroundColor(Utils.getColor(mContext, R.color.colorGray));
            toolbar.setLeftTitleColor(Utils.getColor(mContext, R.color.colorNight));
            toolbar.setMainTitleColor(Utils.getColor(mContext, R.color.colorNight));
            toolbar.setRightTitleColor(Utils.getColor(mContext, R.color.colorNight));
            bookDetailView.setTextColor(Utils.getColor(mContext, R.color.colorNight));
        }
        return view;
    }

    @Override
    protected void setupView(View view) {
        initToolBar();
        mGestureDetector = new GestureDetector(mContext, new LearnGestureListener());
        //为fragment添加OnTouchListener监听器
        detailScrollView.setOnTouchListener((v, event) -> mGestureDetector.onTouchEvent(event));
    }

    @Override
    protected void setupData() {
        bookController = new BookController(mContext, this);
        if (getArguments() != null) {
            this.menuId = getArguments().getLong("menuId");
            this.bookId = getArguments().getLong("bookId");
        }
    }

    @Override
    protected void sendRequest() {
        bookController.bookDetailRequest(this.bookId, this.menuId);
    }

    @Override
    public void refreshUI(Object data) {
        bookMenuBean = (BookMenuBean) data;
        bookDetailView.setText(bookMenuBean.getDetail());
        toolbar.setMainTitle(bookMenuBean.getTitle());
        detailScrollView.scrollTo(0, 0);
    }

    @Override
    public void refreshWithResult(Object data, int type) {
        if (type == 1) {
            bookBean = (BookBean) data;
            menuList = bookBean.getMenuList();
            showLeftMenu();
        }
    }

    protected void initToolBar() {
        toolbar.setRightTitleText(getString(R.string.text_menu));
        toolbar.setRightTitleDrawable(R.mipmap.icon_book);
        toolbar.setLeftTitleClickListener(this);
        toolbar.setRightTitleClickListener(this);
    }


    @Override
    protected int isDisplayBottomMenu() {
        return View.GONE;
    }

    //设置手势识别监听器
    public class LearnGestureListener extends GestureDetector.SimpleOnGestureListener {
        private int verticalMinistance = 300;            //水平最小识别距离
        private int minVelocity = 10;            //最小识别速度

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if (e1.getX() - e2.getX() > verticalMinistance && Math.abs(velocityX) > minVelocity) {
                if (bookMenuBean.getNextId() == 0) {
                    showMsg("已经是最后一章了");
                    return false;
                }
                bookController.bookDetailRequest(bookId, bookMenuBean.getNextId());
            } else if (e2.getX() - e1.getX() > verticalMinistance && Math.abs(velocityX) > minVelocity) {
                if (bookMenuBean.getPrevId() == 0) {
                    showMsg("已经是第一章了");
                    return false;
                }
                bookController.bookDetailRequest(bookId, bookMenuBean.getPrevId());

            }
            return false;
        }

        //此方法必须重写且返回真，否则onFling不起效
        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }
    }


    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.txt_right_title:
                if (popupWindow == null) {
                    bookController.getMenuListRequest(bookId);
                } else {
                    popupWindow.showAtLocation(toolbar, Gravity.START, 0, 0);
                }
                break;
            case R.id.book_menu_sort:
                showMenuList();
        }
    }

    protected void showLeftMenu() {
        View popupView = LayoutInflater.from(mContext).inflate(R.layout.fragment_book_menu, null);
        menuListView = popupView.findViewById(R.id.book_menu_list);
        bookSortView = popupView.findViewById(R.id.book_menu_sort);
        TextView bookStatusView = popupView.findViewById(R.id.book_status);
        bookStatusView.setText(bookBean.getBookStatusWithMenuCount());
        bookSortView.setOnClickListener(this);
        menuAdapter = new DetailListAdapter(mContext, menuList,1);
        if (Constants.readMode) {
            popupView.setBackgroundColor(Utils.getColor(mContext, R.color.colorGray));
            menuListView.setBackgroundColor(Utils.getColor(mContext, R.color.colorGray));
            bookStatusView.setTextColor(Utils.getColor(mContext, R.color.colorNight));
            bookSortView.setTextColor(Utils.getColor(mContext, R.color.colorNight));
        }
        menuListView.setAdapter(menuAdapter);
        menuListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                BookMenuBean item = (BookMenuBean) adapterView.getItemAtPosition(position);
                menuId = item.getMenuId();
                popupWindow.dismiss();
                sendRequest();
            }
        });
        //创建Popupwindow 实例，200，LayoutParams.MATCH_PARENT 分别是宽高
        popupWindow = new PopupWindow(popupView, 600, ViewGroup.LayoutParams.MATCH_PARENT, true);
        //设置动画效果
        popupWindow.setAnimationStyle(R.anim.in_from_left);
        //点击其他地方消失
        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (popupView != null && popupView.isShown()) {
                    popupWindow.dismiss();
                }
                return false;
            }
        });
        popupWindow.showAtLocation(toolbar, Gravity.START, 0, 0);
    }

    protected void showMenuList() {
        int res;
        if ("desc".equals(bookSortView.getTag())) {
            bookSortView.setTag("asc");
            res = R.mipmap.icon_sort_desc;
            bookSortView.setText(getString(R.string.text_sort_desc));
        } else {
            bookSortView.setTag("desc");
            res = R.mipmap.icon_sort_asc;
            bookSortView.setText(getString(R.string.text_sort_asc));
        }
        Drawable dwLeft = ContextCompat.getDrawable(mContext, res);
        dwLeft.setBounds(0, 0, dwLeft.getMinimumWidth(), dwLeft.getMinimumHeight());
        bookSortView.setCompoundDrawables(dwLeft, null, null, null);
        Collections.reverse(menuList);
        menuAdapter.notifyDataSetChanged();
        menuListView.scrollTo(0, 0);
    }
}
