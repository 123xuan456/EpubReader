package com.reeching.epub.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListPopupWindow;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.koolearn.android.util.LogUtils;
import com.koolearn.android.util.SharedPreferencesUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.reeching.epub.R;
import com.reeching.epub.adapter.SearchBookAdapter;
import com.reeching.epub.adapter.SearchHistoryAdapter;
import com.reeching.epub.bean.SearchBookBean;
import com.reeching.epub.constant.Constant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

import static com.reeching.epub.constant.Constant.SEARCHBOOKS;
import static com.reeching.epub.utils.ActivityUtil.openAct;


/**
 * Created by 绍轩 on 2017/11/2.
 * 搜索书籍
 */

public class SearchBookActivity extends Activity {
    @Bind(R.id.back)
    ImageView back;
    @Bind(R.id.search_s)
    SearchView search_s;
    @Bind(R.id.tvClear)
    ImageView tvClear;
    @Bind(R.id.r1)
    RelativeLayout r1;
    @Bind(R.id.search_lin)
    LinearLayout search_lin;
    @Bind(R.id.auto_list)
    ListView auto_list;
    private ListPopupWindow mListPopupWindow;
    private SearchBookAdapter adapter;

    //搜索历史
    private List<String> mHisList = new ArrayList<>();
    private List<SearchBookBean.InfosBean> testArray;

    private SearchHistoryAdapter mHisAdapter;
    private String user_Id;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        user_Id= SharedPreferencesUtil.getInstance().getString(Constant.LOGIN_ID,"");
        setSearchView();
        initView();
        setdata();

    }

    private void setdata() {
        mListPopupWindow.setAdapter(adapter);
        mHisAdapter = new SearchHistoryAdapter(this, mHisList);
        auto_list.setAdapter(mHisAdapter);
        auto_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                search(mHisList.get(position));
            }
        });
        initSearchHistory();
    }

    private void initView() {
        testArray = new ArrayList<SearchBookBean.InfosBean>();// 每次输入的时候，重新初始化数据列表
        mListPopupWindow =new ListPopupWindow(this);
        adapter = new SearchBookAdapter(this, testArray);
        mListPopupWindow = new ListPopupWindow(this);
        mListPopupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        mListPopupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        //设置popupWindow位置
        mListPopupWindow.setAnchorView(search_lin);


        search_s.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            //输入完成后，点击回车或是完成键
            @Override
            public boolean onQueryTextSubmit(String newText) {
                    Log.e("onQueryTextSubmit","我是点击回车按钮");
                if (TextUtils.isEmpty(newText)) {
                    if (mListPopupWindow.isShowing())
                        mListPopupWindow.dismiss();
                } else {
                    if (!mListPopupWindow.isShowing()) {
                        mListPopupWindow.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
                        mListPopupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
                        mListPopupWindow.show();
                    }
                    testArray = new ArrayList<SearchBookBean.InfosBean>();// 每次输入的时候，重新初始化数据列表
                    LogUtils.i(user_Id);
                    OkGo.post(SEARCHBOOKS).tag(this)
                            .params("memberId",user_Id)
                            .params("title",newText)
                            .params("author",newText)
                            .params("bigTypeName",newText)
                            .params("smallTypeName",newText)
                            .params("contentPure",newText)
                            .params("pageSize",10)
                            .params("pageNo",1)
                            .execute(new StringCallback() {
                                @Override
                                public void onSuccess(String s, Call call, Response response) {
                                    Gson g=new Gson();
                                    SearchBookBean search = g.fromJson(s, SearchBookBean.class);
                                    if (search.getResult().equals("1")){
                                        testArray=search.getInfos();
                                        adapter.refreshData(testArray);// Adapter刷新数据
                                    }

                                }
                            });
                }
                Log.e("onQueryTextChange","我是内容改变");
                return false;
            }

            //查询文本框有变化时事件
            @Override
            public boolean onQueryTextChange(String newText) {
//                if (TextUtils.isEmpty(newText)) {
//                    if (mListPopupWindow.isShowing())
//                        mListPopupWindow.dismiss();
//                } else {
//                    if (!mListPopupWindow.isShowing()) {
//                        mListPopupWindow.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
//                        mListPopupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
//                        mListPopupWindow.show();
//                    }
//                    testArray = new ArrayList<SearchBookBean.InfosBean>();// 每次输入的时候，重新初始化数据列表
//                    OkGo.post(SEARCHBOOKS).tag(this)
//                            .params("title",newText)
//                            .params("author",newText)
//                            .params("bigTypeName",newText)
//                            .params("smallTypeName",newText)
//                            .params("contentPure",newText)
//                            .params("pageSize",10)
//                            .params("pageNo",1)
//                            .execute(new StringCallback() {
//                                @Override
//                                public void onSuccess(String s, Call call, Response response) {
//                                    Gson g=new Gson();
//                                    SearchBookBean search = g.fromJson(s, SearchBookBean.class);
//                                   if (search.getResult().equals("1")){
//                                       testArray=search.getInfos();
//                                       LogUtils.i(testArray.size());
//                                       adapter.refreshData(testArray);// Adapter刷新数据
//                                   }
//
//                                }
//                            });
//                }
//                Log.e("onQueryTextChange","我是内容改变");
               return false;
            }
        });
        mListPopupWindow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SearchBookBean.InfosBean data = adapter.getItem(position);
                search(data.getTitle());
                String memberId="";
                memberId=data.getMemberId();
                Map<String,Object> map=new HashMap<String, Object>();
                map.put("bookName",data.getTitle());
                map.put("id",data.getId());
                map.put("author",data.getAuthor());
                map.put("bigTypeName",data.getBigTypeName());
                map.put("bigTypeName",data.getBigTypeName());
                map.put("summaryContent",data.getSummaryContent());
                map.put("memberId",memberId);
                map.put("downloadCount",data.getDownloadCount());
                map.put("ifDownload",data.getIfDownload());
                map.put("rsaKeyUrl",data.getRsaKeyUrl());
                map.put("encryptedUrl",data.getEncryptedUrl());
                openAct(SearchBookActivity.this,BooKDetailsActivity.class,map);
                if (mListPopupWindow.isShowing())
                    mListPopupWindow.dismiss();
            }
        });
    }

    private void search(String key) {
        search_s.setQueryHint(key);
        if (!TextUtils.isEmpty(key)) {
            search_s.setQuery(key, true);
            saveSearchHistory(key);
        }

    }

    private void saveSearchHistory(String query) {
        List<String> list = SharedPreferencesUtil.getInstance().getSearchHistory();
        if (list == null) {
            list = new ArrayList<>();
            list.add(query);
        } else {
            Iterator<String> iterator = list.iterator();
            while (iterator.hasNext()) {
                String item = iterator.next();
                if (TextUtils.equals(query, item)) {
                    iterator.remove();
                }
            }
            list.add(0, query);
        }
        int size = list.size();
        if (size > 20) { // 最多保存20条
            for (int i = size - 1; i >= 20; i--) {
                list.remove(i);
            }
        }
        SharedPreferencesUtil.getInstance().saveSearchHistory(list);
        initSearchHistory();

    }

    private void initSearchHistory() {
        List<String> list = SharedPreferencesUtil.getInstance().getSearchHistory();
        mHisList.clear();
        if (list != null && list.size() > 0) {
            tvClear.setEnabled(true);
            mHisList.addAll(list);
        } else {
            tvClear.setEnabled(false);
        }
        mHisAdapter.notifyDataSetChanged();
    }



    //设置searchView样式
    private void setSearchView() {
        if(search_s==null){
            return;
        }else{
            //获取到TextView的ID
            int id = search_s.getContext().getResources().getIdentifier("android:id/search_src_text",null,null);
            //获取到TextView的控件
            TextView tv_search = (TextView) search_s.findViewById(id);
            //设置字体大小为14sp
            tv_search.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);//14sp
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) tv_search.getLayoutParams();
            layoutParams.bottomMargin = -7;
            tv_search.setLayoutParams(layoutParams);
        }
        int id=getResources().getIdentifier("android:id/search_plate",//查找文件sdk\platforms\android-17\data\res\layout\search_view.xml中的id
                null,//知道资源类型，底层自动实现
                getPackageName());//包名是清单文件里面的此项目的包名
        LinearLayout layout=(LinearLayout) search_s.findViewById(id);
        layout.setBackgroundResource(R.mipmap.seek_bk);
    }


    @OnClick({R.id.back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
        }
    }

}
