package com.reeching.epub.fagment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.reeching.epub.R;
import com.reeching.epub.adapter.InformAdapter;
import com.reeching.epub.bean.InformBean;
import com.reeching.epub.constant.Constant;
import com.reeching.epub.utils.ToastUtil;

import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

import static com.lzy.okgo.cache.CacheMode.FIRST_CACHE_THEN_REQUEST;


/**
 * 通知列表
 */
public class MessageInformFragment extends Fragment  implements SwipeRefreshLayout.OnRefreshListener{
    private RecyclerView recyclerView;
    private InformAdapter recycleAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ImageView notice;
    private List<InformBean.InfosBean> InformList;

    public MessageInformFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_message_list, container, false);
        // Inflate the layout for this fragment
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView );
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout );
        notice = (ImageView) view.findViewById(R.id.notice );
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        //设置布局管理器
        recyclerView.setLayoutManager(layoutManager);
        //设置为垂直布局，这也是默认的
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        //设置增加或删除条目的动画
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),
                DividerItemDecoration.VERTICAL));
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(R.color.holo_orange_light,R.color.color_drackorange);

        initData();

        return view;
    }


    @Override
    public void onResume() {
        super.onResume();

    }

    private void initData() {
        OkGo.post(Constant.NOTICE).tag(this).
                params("pageNo",1)
                .params("pageSize",20).
                cacheMode(FIRST_CACHE_THEN_REQUEST).
                execute(new StringCallback() {

                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        showView(s);
                    }

                    @Override
                    public void onCacheSuccess(String s, Call call) {
                        showView(s);
                    }

                });


    }

    private void showView(String s) {
        Gson g=new Gson();
        InformBean Inform = g.fromJson(s, InformBean.class);
        if (Inform.getResult().equals("1")){
            notice.setVisibility(View.GONE);
            InformList=Inform.getInfos();
            recycleAdapter= new InformAdapter(getContext(),InformList);
            recyclerView.setAdapter(recycleAdapter);
            recycleAdapter.setOnItemClickListener(new InformAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    ToastUtil.showToast(getContext(),InformList.get(position).getTitle());
                }

                @Override
                public void onItemLongClick(View view, int position) {

                }
            });
        }else {
            notice.setVisibility(View.VISIBLE);
        }


    }


    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onRefresh() {
        initData();
        swipeRefreshLayout.setRefreshing(false);
        recycleAdapter.notifyDataSetChanged();
        Toast.makeText(getContext(), "刷新完成!", Toast.LENGTH_SHORT).show();
    }
}
