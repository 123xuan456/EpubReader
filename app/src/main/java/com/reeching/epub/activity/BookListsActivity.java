package com.reeching.epub.activity;

import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.koolearn.android.util.LogUtils;
import com.koolearn.android.util.SharedPreferencesUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.reeching.epub.R;
import com.reeching.epub.adapter.BookListsAdapter;
import com.reeching.epub.base.BaseBookActivity;
import com.reeching.epub.bean.SorkBookBean;
import com.reeching.epub.constant.Constant;
import com.reeching.epub.recyclerviewadapter.ViewHolder;
import com.reeching.epub.recyclerviewadapter.interfaces.OnItemClickListener;
import com.reeching.epub.recyclerviewadapter.interfaces.OnLoadMoreListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;

import static com.lzy.okgo.cache.CacheMode.FIRST_CACHE_THEN_REQUEST;
import static com.reeching.epub.utils.ActivityUtil.openAct;

/**
 * 分类——书籍列表
 */
public class BookListsActivity extends BaseBookActivity implements SwipeRefreshLayout.OnRefreshListener {
    private RecyclerView recyclerView;
    private BookListsAdapter recycleAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Handler handler = new Handler();
    private String id;
    private List<SorkBookBean.InfosBean> bookList=new ArrayList<>();

    private int pageNo=1;
    private String user_Id;

    @Override
    public int getLayoutId() {
        return R.layout.activity_book_lists;
    }

    @Override
    public void setview() {
        user_Id= SharedPreferencesUtil.getInstance().getString(Constant.LOGIN_ID,"");
        String bookName = getIntent().getStringExtra("bookName");
        id = getIntent().getStringExtra("id");
        TextView top_bookname = (TextView) findViewById(R.id.top_bookname);
        ImageView top_search = (ImageView) findViewById(R.id.top_search);
        top_search.setVisibility(View.GONE);
        top_bookname.setText(bookName);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView );
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout );
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        //设置布局管理器
        recyclerView.setLayoutManager(layoutManager);
        //设置为垂直布局，这也是默认的
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        //设置增加或删除条目的动画
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(R.color.holo_orange_light,R.color.color_drackorange);
        recycleAdapter= new BookListsAdapter(BookListsActivity.this,null,true);
        //初始化 开始加载更多的loading View
        recycleAdapter.setLoadingView(R.layout.load_loading_layout);
        //加载失败，更新footer view提示
        recycleAdapter.setLoadFailedView(R.layout.load_failed_layout);
        //加载完成，更新footer view提示
        recycleAdapter.setLoadEndView(R.layout.load_end_layout);
        recycleAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(boolean isReload) {
                loadMore();
            }
        });
        recycleAdapter.setOnItemClickListener(new OnItemClickListener<SorkBookBean.InfosBean>() {
            @Override
            public void onItemClick(ViewHolder viewHolder, SorkBookBean.InfosBean data, int position) {
                String memberId = "";
                memberId = data.getMemberId();
                Map<String,Object> map=new HashMap<String, Object>();
                map.put("bookName",data.getTitle());
                map.put("id",data.getId());
                map.put("photo",data.getPhoto());

                map.put("bigTypeName",data.getBigTypeName());
                map.put("summaryContent",data.getSummaryContent());
                map.put("author",data.getAuthor());

                map.put("memberId",memberId);
                map.put("downloadCount",data.getDownloadCount());
                map.put("ifDownload",data.getIfDownload());
                map.put("rsaKeyUrl",data.getRsaKeyUrl());
                map.put("encryptedUrl",data.getEncryptedUrl());
                openAct(BookListsActivity.this,BooKDetailsActivity.class,map);
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
        setData(pageNo);
        recyclerView.setAdapter(recycleAdapter);
    }

    private void loadMore() {
        //加载更多
        LogUtils.i("加载更多");
        pageNo++;
        setData(pageNo);
    }


    @Override
    public void setData() {
    }
    //上拉加载获取数据
    public void setData(final int pageNo) {
        LogUtils.i(pageNo);
        LogUtils.i(user_Id);
        OkGo.post(Constant.SECONDARY_BOOKS).tag(this).
                params("smallType",id).
                params("pageNo",pageNo).
                params("pageSize","6").
                params("memberId",user_Id).
                cacheMode(FIRST_CACHE_THEN_REQUEST).
                execute(new StringCallback() {
                    Gson g=new Gson();
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        SorkBookBean sorkbook = g.fromJson(s, SorkBookBean.class);
                        if (sorkbook.getResult().equals("1")){
                            bookList=sorkbook.getInfos();
                            if (pageNo==1){
                                LogUtils.i("下拉刷新pageNo"+pageNo);
                                recycleAdapter.setNewData(bookList);
                            }else {
                                LogUtils.i("上啦加载pageNo"+pageNo);
                                recycleAdapter.setLoadMoreData(bookList);
                            }
                        }else {
                            recycleAdapter.loadEnd();//加载完成
                        }
                    }
                    @Override
                    public void onCacheSuccess(String s, Call call) {
                        super.onCacheSuccess(s, call);
                        SorkBookBean sorkbook = g.fromJson(s, SorkBookBean.class);
                        if (sorkbook.getResult().equals("1")){
                            bookList=sorkbook.getInfos();
                            if (pageNo==1){
                                LogUtils.i("下拉刷新pageNo"+pageNo);
                                recycleAdapter.setNewData(bookList);
                            }
                        }else {
                            recycleAdapter.loadEnd();//加载完成
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                    }
                });

    }

    //下拉刷新
    @Override
    public void onRefresh() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //重置数据
                recycleAdapter.reset();
                if (pageNo>1){
                    pageNo=1;
                }
                setData(pageNo);
                swipeRefreshLayout.setRefreshing(false);
            }
        }, 1000);
    }



}
