package com.reeching.epub.fagment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.koolearn.android.util.LogUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.reeching.epub.R;
import com.reeching.epub.activity.SearchBookActivity;
import com.reeching.epub.adapter.ShopAdapter;
import com.reeching.epub.bean.SortBean;
import com.reeching.epub.constant.Constant;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

import static com.lzy.okgo.cache.CacheMode.FIRST_CACHE_THEN_REQUEST;
import static com.reeching.epub.utils.ActivityUtil.openAct;

/**
 * 分类页面
 */
public class SortFragment extends Fragment {
    @Bind(R.id.top_edit)
    TextView topEdit;
    @Bind(R.id.top_book)
    TextView topBook;
    @Bind(R.id.top_search)
    ImageView topSearch;
    @Bind(R.id.top_add)
    ImageView topAdd;
    @Bind(R.id.tools)
    LinearLayout tools;
    @Bind(R.id.tools_scrlllview)
    ScrollView toolsScrlllview;
    @Bind(R.id.goods_pager)
    ViewPager mPager;
    private View view = null;
    private LayoutInflater inflater;
    private ArrayList<String> list =new ArrayList<>();
    private View[] views;
    private TextView[] tvList;
    private View[] imglist;
    private List<SortBean.InfosBean> sortList;
    private ShopAdapter shopAdapter;

    public static Fragment newInstance() {
        SortFragment f = new SortFragment();
        return f;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inflater = LayoutInflater.from(getActivity());
        shopAdapter=new ShopAdapter(getChildFragmentManager(),null,null);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_sort, null);
            ButterKnife.bind(this, view);
            initView();
            getData();
        }
        return view;
    }
    private void initView() {
        topEdit.setVisibility(View.GONE);
        topBook.setText(R.string.sort_title);
        topAdd.setVisibility(View.GONE);
        shopAdapter=new ShopAdapter(getChildFragmentManager(),list,sortList);
        mPager.setAdapter(shopAdapter);
        mPager.addOnPageChangeListener(onPageChangeListener);
        mPager.setCurrentItem(0);
        showToolsView();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public void getData() {
        OkGo.get(Constant.FIRST_CLASSIFYING).tag(this).
                cacheKey(Constant.FIRST_CLASSIFYING).
                cacheMode(FIRST_CACHE_THEN_REQUEST).
                execute(new StringCallback() {
                    Gson g=new Gson();
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        SortBean sortFirst=g.fromJson(s, SortBean.class);
                        sortList=sortFirst.getInfos();
                        //判断是否有缓存数据
                        if (list.size()==0){
                            for (int i=0;i<sortList.size();i++){
                                SortBean.InfosBean sort = sortList.get(i);
                                SortBean.InfosBean.BigListBean bigList = sort.getBigList();
                                String name = bigList.getBigName();
                                list.add(name);
                                LogUtils.i("name="+name);
                            }
                            list=new ArrayList(new HashSet(list));
                            shopAdapter.refreshData(list,sortList);
                            showToolsView();
                        }
                    }

                    @Override
                    public void onCacheSuccess(String s, Call call) {
                        SortBean sortFirst=g.fromJson(s, SortBean.class);
                        sortList=sortFirst.getInfos();
                        for (int i=0;i<sortList.size();i++){
                            SortBean.InfosBean sort = sortList.get(i);
                            SortBean.InfosBean.BigListBean bigList = sort.getBigList();
                            String  name=bigList.getBigName();
                            list.add(name);
                        }
                        shopAdapter.refreshData(list,sortList);
                        showToolsView();
                    }
                    @Override
                    public void onAfter(String s, Exception e) {
                        super.onAfter(s, e);

                    }
                });
    }
    private void showToolsView() {
        LinearLayout toolsLayout = (LinearLayout) view.findViewById(R.id.tools);
        int s = list.size();
        LogUtils.i(list.size());
        tvList = new TextView[s];
        views = new View[s];
        imglist = new View[s];
        for (int i = 0; i < s; i++) {
            View view = inflater.inflate(R.layout.item_list_layout, null);
            view.setId(i);
            view.setOnClickListener(toolsItemListener);
            TextView textView = (TextView) view.findViewById(R.id.list_text);
            ImageView list_img = (ImageView) view.findViewById(R.id.list_img);
            textView.setText(list.get(i));
            toolsLayout.addView(view);
            tvList[i] = textView;
            views[i] = view;
            imglist[i] = list_img;
        }
        changeTextColor(0);
    }
    private View.OnClickListener toolsItemListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mPager.setCurrentItem(v.getId());
        }
    };

    private int currentItem=0;
    /**
     * OnPageChangeListener<br/>
     * 监听ViewPager选项卡变化事的事件
     */
    private ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageSelected(int arg0) {
            if (mPager.getCurrentItem() != arg0)
                mPager.setCurrentItem(arg0);
            if (currentItem != arg0) {
                changeTextColor(arg0);
                changeTextLocation(arg0);
            }

            currentItem = arg0;
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }
    };
    //跟换背景
    private void changeTextColor(int id) {
        if (isAdded()) {//isAdded()方法可以判断当前的Fragment是否已经添加到Activity中，只有当Fragment已经添加到Activity中时才执行getResources()等方法
            if (tvList.length!=0){
                for (int i = 0; i < tvList.length; i++) {
                    if (i != id) {
                        tvList[i].setBackgroundColor(getResources().getColor(R.color.gray_bg));
                        tvList[i].setTextColor(getResources().getColor(R.color.colorBlack));
                        imglist[i].setBackgroundColor(getResources().getColor(R.color.gray_bg));
                    }
                }
                tvList[id].setBackgroundColor(getResources().getColor(R.color.home_bg));
                tvList[id].setTextColor(getResources().getColor(R.color.color_drackorange));
                imglist[id].setBackgroundColor(getResources().getColor(R.color.color_drackorange));
            }
        }
    }

    private void changeTextLocation(int clickPosition) {
        int x = (views[clickPosition].getTop());
        toolsScrlllview.smoothScrollTo(0, x);

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @OnClick(R.id.top_search)
    public void onViewClicked() {
        openAct(getActivity(),SearchBookActivity.class);
    }


}
