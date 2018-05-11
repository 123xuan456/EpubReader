package com.reeching.epub.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.koolearn.android.util.LogUtils;
import com.koolearn.android.util.SharedPreferencesUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.request.GetRequest;
import com.reeching.epub.R;
import com.reeching.epub.adapter.BookCommentAdapter;
import com.reeching.epub.base.BaseBookActivity;
import com.reeching.epub.bean.BookCommentBean;
import com.reeching.epub.constant.Constant;
import com.reeching.epub.recyclerviewadapter.interfaces.OnLoadMoreListener;
import com.reeching.epub.utils.FileUtil;
import com.reeching.epub.utils.GlideUtils;
import com.reeching.epub.utils.PhoneUtil;
import com.reeching.epub.utils.ToastUtil;
import com.reeching.epub.view.DrawableCenterButton;
import com.reeching.epub.view.FullyLinearLayoutManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;
import okserver.download.DownloadInfo;
import okserver.download.DownloadManager;
import okserver.download.DownloadService;
import okserver.listener.DownloadListener;

import static com.lzy.okgo.cache.CacheMode.FIRST_CACHE_THEN_REQUEST;
import static com.reeching.epub.constant.Constant.ADDBOOKSHELF;
import static com.reeching.epub.constant.Constant.ADDGRADE;
import static com.reeching.epub.constant.Constant.ADEQUATE;
import static com.reeching.epub.constant.Constant.DEFICIENCY;
import static com.reeching.epub.constant.Constant.DEVICEID;
import static com.reeching.epub.constant.Constant.DOWNLOADBOOK;
import static com.reeching.epub.constant.Constant.GETGRADELIST;
import static com.reeching.epub.constant.Constant.LOGIN_ID;
import static com.reeching.epub.constant.Constant.SERVIECE_DOWNLOND;
import static com.reeching.epub.utils.AppValidationUtil.cutPhoto;
import static com.reeching.epub.utils.FileUtil.gainSDFreeSize;
import static org.apache.http.util.TextUtils.isEmpty;


/**
 * 图书详情页
 */
public class BooKDetailsActivity extends BaseBookActivity {


    @Bind(R.id.top_bookname)
    TextView topBookname;
    @Bind(R.id.top_search)
    ImageView topsearch;
    @Bind(R.id.ivBookCover)
    ImageView ivBookCover;
    @Bind(R.id.tvBookListTitle)
    TextView tvBookListTitle;
    @Bind(R.id.tvBookListAuthor)
    TextView tvBookListAuthor;
    @Bind(R.id.tvCatgory)
    TextView tvCatgory;
    @Bind(R.id.tvLatelyUpdate)
    TextView tvLatelyUpdate;
    @Bind(R.id.btnJoinCollection)
    DrawableCenterButton btnJoinCollection;
    @Bind(R.id.btnDown)
    DrawableCenterButton btnDown;
    @Bind(R.id.tvLatelyFollower)
    TextView tvLatelyFollower;
    @Bind(R.id.tvRetentionRatio)
    TextView tvRetentionRatio;
    @Bind(R.id.btnpinglun)
    TextView btnpinglun;
    @Bind(R.id.txtedit)
    EditText txtedit;
    @Bind(R.id.tvSerializeWordCount)
    TextView tvSerializeWordCount;
    @Bind(R.id.tvlongIntro)
    TextView tvlongIntro;
    @Bind(R.id.tvMoreReview)
    TextView tvMoreReview;
    @Bind(R.id.rvHotReview)
    RecyclerView rvHotReview;
    private String booksId;
    private String author;
    private String bigTypeName;
    private boolean isJoinedCollections=false;
    private boolean isJoinedbtnDown=false;
    private String login_id;
    private int pageNo=1;
    private BookCommentAdapter recycleAdapter;
    private String encryptedUrl;
    private String deviceId;
    private String device;
    private String bookName;
    private String destFileName1;
    private DownloadManager downloadManager;
    private DownloadInfo downloadInfo;
    private String photo;

    @Override
    public int getLayoutId() {
        return R.layout.activity_boo_kdetails;
    }

    @Override
    public void setview() {
        deviceId = PhoneUtil.getInstance().getPhoneImei(getApplicationContext());
        device = PhoneUtil.getInstance().getPhoneModel() + PhoneUtil.getInstance().getSDKVersionNumber();
        login_id= SharedPreferencesUtil.getInstance().getString(LOGIN_ID);
        bookName = getIntent().getStringExtra("bookName");
        booksId = getIntent().getStringExtra("id");
        author = getIntent().getStringExtra("author");
        bigTypeName = getIntent().getStringExtra("bigTypeName");
        String memberId = getIntent().getStringExtra("memberId");
        String summaryContent = getIntent().getStringExtra("summaryContent");
        String downloadCount = getIntent().getStringExtra("downloadCount");
        String ifDownload = getIntent().getStringExtra("ifDownload");
        photo = getIntent().getStringExtra("photo");
        encryptedUrl = getIntent().getStringExtra("encryptedUrl");

        topBookname.setText(bookName);
        topsearch.setVisibility(View.GONE);
        tvBookListTitle.setText(bookName);
        tvBookListAuthor.setText(author + "  |   ");
        tvCatgory.setText(bigTypeName);
        tvlongIntro.setText(summaryContent);
        tvLatelyFollower.setText(downloadCount);
        GlideUtils.loadImageViewError(this,cutPhoto(photo),ivBookCover,R.drawable.cover_default);
        LogUtils.i(memberId);
        encryptedUrl=SERVIECE_DOWNLOND+encryptedUrl;
        destFileName1 = SD_KEYBOOKS + bookName + ".epub";
        if (!isEmpty(login_id)){
            btnJoinCollection.setEnabled(true);
            btnDown.setEnabled(true);
            //加入书架
            if (memberId.equals("0")){
                initCollection(true);
            }else {
                initCollection(false);
            }
            //下载
            if (ifDownload.equals("1")) {
                LogUtils.i("允许下载");
                initbtnDown(true);
            }else {
                initbtnDown(false);
            }
        } else {
            btnJoinCollection.setEnabled(false);
            btnDown.setEnabled(false);
            ToastUtil.showToast(getApplicationContext(),"没有登录");
        }

        downloadManager = DownloadService.getDownloadManager();


    }

    private void  downBook(final String dowUrl,final String img) {
            GetRequest request = OkGo.get(dowUrl)//
                    .headers("headerKey1", "headerValue1")//
                    .headers("headerKey2", "headerValue2")//
                    .params("paramKey1", "paramValue1")//
                    .params("paramKey2", "paramValue2");
            downloadManager.addTask(dowUrl,img,booksId,request, null);
            downloadInfo = downloadManager.getDownloadInfo(dowUrl);
            if (downloadInfo != null) {
                //如果任务存在，把任务的监听换成当前页面需要的监听
                downloadInfo.setListener(new DownloadListener() {
                    @Override
                    public void onProgress(DownloadInfo downloadInfo) {

                    }

                    @Override
                    public void onFinish(final DownloadInfo downloadInfo) {
                        LogUtils.i("下载完成,文件大小="+downloadInfo.getTotalLength());
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                Message message = new Message();
                                if (gainSDFreeSize()>downloadInfo.getTotalLength()){
                                    message.what = ADEQUATE;
                                }else {
                                    message.what = DEFICIENCY;
                                }
                                handler.sendMessage(message);
                            }
                        }).start();
                    }

                    @Override
                    public void onError(DownloadInfo downloadInfo, String errorMsg, Exception e) {
                        ToastUtil.showToast(BooKDetailsActivity.this,"网络错误");
                    }
                });
            }
    }
    //添加下载记录
    private void recordDown() {
        OkGo.post(DOWNLOADBOOK).tag(this)
                .params("memberId",login_id)
                .params("bookId", booksId)
                .params("device", device)
                .params("deviceId", deviceId)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        LogUtils.i(s);
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                    }
                });


    }
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case DEFICIENCY:
                    ToastUtil.showToast(getApplication(),"内存不足");
                    break;
                case ADEQUATE:
                    btnDown.setText("下载完成");
                    Drawable drawable = ContextCompat.getDrawable(getApplicationContext(), R.mipmap.dowl);
                    drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                    btnDown.setBackgroundDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.btn_join_collection_pressed));
                    btnDown.setCompoundDrawables(drawable, null, null, null);
                    btnDown.postInvalidate();
                    btnDown.setEnabled(false);
                    isJoinedbtnDown = false;
                    recordDown();//添加下载记录
                    break;
            }

        }
    };

    @Override
    public void setData() {
        final FullyLinearLayoutManager layoutManager = new FullyLinearLayoutManager(this);
        //设置布局管理器
        rvHotReview.setLayoutManager(layoutManager);
        //设置为垂直布局，这也是默认的
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        //设置增加或删除条目的动画
        rvHotReview.setItemAnimator(new DefaultItemAnimator());
        rvHotReview.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL));
        recycleAdapter= new BookCommentAdapter(BooKDetailsActivity.this,null,true);
        //初始化 开始加载更多的loading View
        recycleAdapter.setLoadingView(R.layout.load_loading_layout);
        //加载失败，更新footer view提示
//        recycleAdapter.setLoadFailedView(R.layout.load_failed_layout);
        //加载完成，更新footer view提示
        recycleAdapter.setLoadEndView(R.layout.load_end_layout);
        recycleAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(boolean isReload) {
                pageNo++;
                LogUtils.i(isReload+"");
                comment(pageNo);
            }
        });
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        comment(pageNo);
        rvHotReview.setAdapter(recycleAdapter);
    }

    private void comment(final int i) {
        LogUtils.i(i);
        OkGo.post(GETGRADELIST).tag(this)
                .params("bookId",booksId)
                .params("pageNo",i)
                .params("pageSize",6)
                . cacheMode(FIRST_CACHE_THEN_REQUEST)
                .execute(new StringCallback() {
                    public List<BookCommentBean.InfosBean> bookList;
                    Gson g=new Gson();
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        BookCommentBean bookComment = g.fromJson(s, BookCommentBean.class);
                        if (bookComment.getResult().equals("1")){
                            bookList=bookComment.getInfos();
                            if (i==1){
                                recycleAdapter.setNewData(bookList);
                            }else {
                                recycleAdapter.setLoadMoreData(bookList);
                            }
                        }else {
                            recycleAdapter.loadEnd();
                        }
                    }

                    @Override
                    public void onCacheSuccess(String s, Call call) {
                        super.onCacheSuccess(s, call);
                        BookCommentBean bookComment = g.fromJson(s, BookCommentBean.class);
                        if (bookComment.getResult().equals("1")){
                            bookList=bookComment.getInfos();
                            if (i==1){
                                recycleAdapter.setNewData(bookList);
                            }
                        }else {
                            recycleAdapter.loadEnd();
                        }

                    }

                });
    }

    @OnClick({R.id.btnJoinCollection,R.id.btnDown,R.id.btnpinglun,R.id.top_finish})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnJoinCollection:
                if (!isJoinedCollections) {
                    OkGo.post(ADDBOOKSHELF).tag(this)
                            .params("booksId", booksId)
                            .params("memberId",login_id)
                            .params("device", SharedPreferencesUtil.getInstance().getString(DEVICEID))
                            .params("deviceId", SharedPreferencesUtil.getInstance().getString(Constant.DEVICE))
                            .execute(new StringCallback() {
                                @Override
                                public void onSuccess(String s, Call call, Response response) {
                                    JSONObject jsonObject = null;
                                    try {
                                        jsonObject = new JSONObject(s);
                                        String msg = jsonObject.getString("msg");
                                        String result = jsonObject.getString("result");
                                        if (result.equals("1")){
                                            initCollection(false);
                                        }
                                        ToastUtil.showToast(getApplicationContext(), msg);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }

                                @Override
                                public void onError(Call call, Response response, Exception e) {
                                    super.onError(call, response, e);
                                    ToastUtil.showToast(BooKDetailsActivity.this,"网络错误");
                                }
                            });
                }else {
                    initCollection(true);
                }
                break;
            case R.id.btnDown:
                if (isJoinedbtnDown){
                    downBook(encryptedUrl,cutPhoto(photo));
                }else {
                    ToastUtil.showToast(getApplicationContext(),"已经下载/不允许下载");
                }
                break;
            case R.id.btnpinglun://发送评论
                String content = txtedit.getText().toString();
                if (isEmpty(content)) {
                    Toast.makeText(this, "不能空评论", Toast.LENGTH_SHORT).show();
                    return;
                }
                OkGo.post(ADDGRADE).tag(this)
                        .params("memberId",login_id)
                        .params("content",content)
                        .params("bookId",booksId)
                        .execute(new StringCallback() {
                            @Override
                            public void onSuccess(String s, Call call, Response response) {
                                try {
                                    JSONObject obj=new JSONObject(s);
                                    String result=obj.getString("result");
                                    if (result.equals("1")){
                                        onRefresh();
                                        txtedit.setText("");//添加成功后，将内容清空
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                LogUtils.i(s);
                            }
                        });


                break;

            case R.id.top_finish:
                finish();
                break;

        }

    }
    private void initCollection(boolean coll) {
        if (coll) {
            btnJoinCollection.setText(R.string.book_detail_join_collection);
            Drawable drawable = ContextCompat.getDrawable(this, R.drawable.book_detail_info_add_img);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            btnJoinCollection.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.shape_common_btn_solid_normal));
            btnJoinCollection.setCompoundDrawables(drawable, null, null, null);
            btnJoinCollection.postInvalidate();
            isJoinedCollections = false;
        } else {
            btnJoinCollection.setText(R.string.book_detail_remove_collection);
            Drawable drawable = ContextCompat.getDrawable(this, R.drawable.book_detail_info_del_img);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            btnJoinCollection.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.btn_join_collection_pressed));
            btnJoinCollection.setCompoundDrawables(drawable, null, null, null);
            btnJoinCollection.postInvalidate();
            btnJoinCollection.setEnabled(false);
            isJoinedCollections = true;
        }
    }
    private void initbtnDown(boolean coll) {
        if (coll) {
            if (FileUtil.isExsit(destFileName1)){
                btnDown.setText("已经下载了");
                Drawable drawable = ContextCompat.getDrawable(this, R.mipmap.dowl);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                btnDown.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.btn_join_collection_pressed));
                btnDown.setCompoundDrawables(drawable, null, null, null);
                btnDown.postInvalidate();
                btnDown.setEnabled(false);
                isJoinedbtnDown = false;
            }else {
                btnDown.setText("下载");
                Drawable drawable = ContextCompat.getDrawable(this, R.mipmap.dow);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                btnDown.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.shape_common_btn_solid_normal));
                btnDown.setCompoundDrawables(drawable, null, null, null);
                btnDown.postInvalidate();
                btnDown.setEnabled(true);
                isJoinedbtnDown = true;
            }
        } else {
            btnDown.setText("不允许下载");
            Drawable drawable = ContextCompat.getDrawable(this, R.mipmap.dow);
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            btnDown.setBackgroundDrawable(ContextCompat.getDrawable(this, R.drawable.btn_join_collection_pressed));
            btnDown.setCompoundDrawables(drawable, null, null, null);
            btnDown.postInvalidate();
            btnDown.setEnabled(false);
            isJoinedbtnDown = false;
        }
    }


    public void onRefresh() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                //重置数据
                recycleAdapter.reset();
                if (pageNo>1){
                    pageNo=1;
                }
                comment(pageNo);
            }
        });
    }
}
