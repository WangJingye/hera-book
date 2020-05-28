package com.delcache.hera.fragment.book;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.bottomnavigation.LabelVisibilityMode;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import butterknife.BindView;
import com.delcache.hera.R;
import com.delcache.hera.activity.MainActivity;
import com.delcache.hera.adapter.DetailListAdapter;
import com.delcache.hera.bean.BookBean;
import com.delcache.hera.bean.BookMenuBean;
import com.delcache.hera.controller.user.BookController;
import com.delcache.hera.controller.user.UserController;
import com.delcache.hera.fragment.base.BaseFragment;
import com.delcache.hera.helper.FragmentHelper;
import com.delcache.hera.helper.ImageHelper;
import com.delcache.hera.utils.Utils;
import com.delcache.hera.widget.CustomToolbar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FragmentBookInfo extends BaseFragment {

    @BindView(R.id.detail_tab)
    TabLayout tabLayout;
    @BindView(R.id.vp_content)
    ViewPager vpContent;
    @BindView(R.id.book_author)
    TextView bookAuthorText;
    @BindView(R.id.book_title)
    TextView bookTitleText;
    @BindView(R.id.book_category)
    TextView bookCategoryText;
    @BindView(R.id.book_pic)
    ImageView bookPicView;
    @BindView(R.id.action_toolbar)
    CustomToolbar toolbar;

    @BindView(R.id.add_to_collect)
    TextView addToCollectView;
    @BindView(R.id.read_book)
    TextView readBookView;
    @BindView(R.id.download_book)
    TextView downloadBookView;

    private BookController bookController;
    private UserController userController;
    private TextView bookSortView;
    private TextView BookDescriptionView;

    private List<View> viewList = new ArrayList<>(2);
    private String[] viewPageStrList = {"详情", "目录"};
    private DetailListAdapter detailListAdapter;
    private int bookId;
    private List<BookMenuBean> menuList;
    private BookBean bookBean;
    private ListView menuListView;

    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return init(R.layout.fragment_book_info, container);
    }

    @Override
    protected void setupData() {
        bookController = new BookController(mContext, this);
        userController = new UserController(mContext, this);
        if (getArguments() != null) {
            this.bookId = Integer.parseInt(getArguments().getString("bookId"));
        }
    }

    @Override
    protected void setupView(View view) {
        toolbar.setLeftTitleText("");
        toolbar.setLeftTitleDrawable(R.mipmap.icon_back, Utils.getColor(mContext, R.color.colorDefault));
        toolbar.setRightTitleDrawable(R.mipmap.icon_more, Utils.getColor(mContext, R.color.colorDefault));
        toolbar.setBackgroundColor(Utils.getColor(mContext, R.color.colorGray));
        toolbar.setLeftTitleClickListener(this);
    }

    @Override
    protected void sendRequest() {
        bookController.bookInfoRequest(this.bookId);
    }

    @Override
    public void refreshUI(Object data) {
        bookBean = (BookBean) data;
        menuList = bookBean.getMenuList();
        bookAuthorText.setText(bookBean.getAuthor());
        bookTitleText.setText(bookBean.getTitle());
        bookCategoryText.setText(bookBean.getCategoryName());
        ImageHelper.getInstance().showImage(bookBean.getPic(), bookPicView);
        initTabView();
        initBottomBar();
    }

    @Override
    public void refreshWithResult(Object data, int type) {
        if (type == 1) {
            setBottomBarAddToCollectBtn(1);
        }
    }

    @Override
    protected int isDisplayBottomMenu() {
        return View.GONE;
    }


    protected void initTabView() {
        View view = null;
        view = getLayoutInflater().inflate(R.layout.fragment_book_info_description, null);
        BookDescriptionView = (TextView) view.findViewById(R.id.book_info);
        BookDescriptionView.setText(bookBean.getDesc());
        viewList.add(view);
        view = getLayoutInflater().inflate(R.layout.fragment_book_menu, null);
        TextView bookStatusView = (TextView) view.findViewById(R.id.book_status);
        bookStatusView.setText(bookBean.getBookStatusWithMenuCount());
        bookSortView = (TextView) view.findViewById(R.id.book_menu_sort);
        bookSortView.setOnClickListener(this);
        menuListView = (ListView) view.findViewById(R.id.book_menu_list);
        detailListAdapter = new DetailListAdapter(mContext, menuList);
        menuListView.setAdapter(detailListAdapter);
        menuListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                BookMenuBean item = (BookMenuBean) adapterView.getItemAtPosition(position);
                FragmentBookDetail detail = new FragmentBookDetail();
                Bundle args = new Bundle();
                args.putInt("menuId", item.getMenuId());
                args.putInt("bookId", bookId);
                detail.setArguments(args);
                FragmentHelper.getInstance().addFragment(detail);
            }
        });
        viewList.add(view);
        tabLayout.setupWithViewPager(vpContent);
        vpContent.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return viewList.size();
            }

            @Override
            public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
                return view == o;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                View view = viewList.get(position);
                container.addView(view);
                return view;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View) object);
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return viewPageStrList[position];
            }
        });
    }

    protected void initBottomBar() {
        addToCollectView.setOnClickListener(this);
        readBookView.setOnClickListener(this);
        setBottomBarAddToCollectBtn(bookBean.getIsAdded());
    }

    protected void setBottomBarAddToCollectBtn(int isAdded) {
        if (isAdded == 1) {
            addToCollectView.setText(getString(R.string.text_collecting));
            addToCollectView.setTextColor(Utils.getColor(mContext, R.color.colorCCC));
            Drawable dw = ContextCompat.getDrawable(mContext, R.mipmap.icon_collecting);
            dw.setBounds(0, 0, dw.getMinimumWidth(), dw.getMinimumHeight());
            dw.setTint(Utils.getColor(mContext, R.color.colorCCC));
            addToCollectView.setCompoundDrawables(dw, null, null, null);
            addToCollectView.setOnClickListener(null);
        } else {
            addToCollectView.setText(getString(R.string.text_add_to_collect));
            addToCollectView.setTextColor(Utils.getColor(mContext, R.color.colorBlack));
            Drawable dw = ContextCompat.getDrawable(mContext, R.mipmap.icon_add_to_collect);
            dw.setBounds(0, 0, dw.getMinimumWidth(), dw.getMinimumHeight());
            dw.setTint(Utils.getColor(mContext, R.color.colorBlack));
            addToCollectView.setCompoundDrawables(dw, null, null, null);
            addToCollectView.setOnClickListener(this);
        }
    }


    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.book_menu_sort:
                showMenuList();
                break;
            case R.id.add_to_collect:
                userController.addToCollectionRequest(bookId);
                break;
            case R.id.read_book:
                FragmentBookDetail detail = new FragmentBookDetail();
                Bundle args = new Bundle();
                args.putInt("menuId", bookBean.getPageId());
                args.putInt("bookId", bookId);
                detail.setArguments(args);
                FragmentHelper.getInstance().addFragment(detail);
                break;
        }
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
        detailListAdapter.notifyDataSetChanged();
        menuListView.scrollTo(0, 0);
    }

}
