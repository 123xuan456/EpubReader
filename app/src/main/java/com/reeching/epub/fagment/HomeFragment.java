package com.reeching.epub.fagment;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.koolearn.android.kooreader.KooReader;
import com.koolearn.android.kooreader.libraryService.BookCollectionShadow;
import com.koolearn.android.util.LogUtils;
import com.koolearn.android.util.SharedPreferencesUtil;
import com.koolearn.kooreader.book.Book;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.request.GetRequest;
import com.reeching.epub.R;
import com.reeching.epub.activity.SearchBookActivity;
import com.reeching.epub.adapter.BookShelfsListAdaper;
import com.reeching.epub.bean.BookShelfBean;
import com.reeching.epub.constant.Constant;
import com.reeching.epub.utils.FileUtil;
import com.reeching.epub.utils.PhoneUtil;
import com.reeching.epub.utils.ToastUtil;
import com.reeching.epub.view.MyGridView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
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
import static com.reeching.epub.R.id.top_all;
import static com.reeching.epub.adapter.BookShelfsListAdaper.returnSuffix;
import static com.reeching.epub.constant.Constant.ADEQUATE;
import static com.reeching.epub.constant.Constant.DEFICIENCY;
import static com.reeching.epub.constant.Constant.DOWNLOADBOOK;
import static com.reeching.epub.constant.Constant.LOGIN_ID;
import static com.reeching.epub.constant.Constant.REMOVEBOOKSHELFS;
import static com.reeching.epub.constant.Constant.SERVIECE_DOWNLOND;
import static com.reeching.epub.utils.ActivityUtil.openAct;
import static com.reeching.epub.utils.AesUtils.decrypt;
import static com.reeching.epub.utils.AppValidationUtil.cutPhoto;
import static com.reeching.epub.utils.AppValidationUtil.isEmpty;
import static com.reeching.epub.utils.FileUtil.gainSDFreeSize;
import static com.reeching.epub.utils.FileUtil.getBytes;
import static com.reeching.epub.utils.FileUtil.getExternalFilesDirPath;
import static com.reeching.epub.utils.FileUtil.getFile;

/**
 * A simple {@link Fragment} subclass.
 * 首页——图书列表
 */
public class HomeFragment extends Fragment {
    @Bind(R.id.tvProgress)
    TextView tvProgress;
    @Bind(R.id.top_edit)
    TextView topEdit;
    @Bind(R.id.top_no)
    TextView top_no;
    @Bind(top_all)
    TextView topAll;
    @Bind(R.id.top_del)
    TextView topDel;
    @Bind(R.id.cancel)
    TextView topCancel;
    @Bind(R.id.top_book)
    TextView topBook;
    @Bind(R.id.top_search)
    ImageView topSearch;
    @Bind(R.id.top_add)
    ImageView topAdd;
    @Bind(R.id.empty_icon)
    ImageView emptyIcon;
    @Bind(R.id.detail_author)
    TextView detailAuthor;
    @Bind(R.id.bookshelf_nothing)
    RelativeLayout bookshelfNothing;
    @Bind(R.id.gdBookshelf)
    MyGridView gdBookshelf;
    @Bind(R.id.bookshelf_something)
    RelativeLayout bookshelfSomething;
    @Bind(R.id.home_lin)
    LinearLayout home_lin;
    @Bind(R.id.scroll_home)
    ScrollView scroll_home;
    private View view = null;
    ArrayList<String> sd_nameList = new ArrayList<>();//sd中的电子书
    private List<BookShelfBean.InfosBean> shelfs = new ArrayList<>();//网络书架

    private RelativeLayout eidtMenulayout;//
    private TextView numOfBookFilesTxt;//选中显示
    private ImageView bookDelete;
    private ImageView bookAll;
    private ImageView bookUnAll;
    private RelativeLayout searchDialoglayout;
    private DownloadManager downloadManager;
    private DownloadInfo downloadInfo;
    private int p;
    private String dowUrl;
    private BookShelfBean.InfosBean b1;
    Context context;
    private String memberId;
    private static BookShelfsListAdaper mABdapter;
    private String deviceId;
    private String device;

    private final BookCollectionShadow myCollection = new BookCollectionShadow();
    //保存加密之后电子书目录
    public static String SD_KEYBOOKS;
    private RelativeLayout book_search;

    public HomeFragment() {
    }

    public static Fragment newInstance() {
        HomeFragment f = new HomeFragment();
        return f;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view == null) {
            SD_KEYBOOKS = getExternalFilesDirPath(getActivity()) + "downlond/";
            deviceId = PhoneUtil.getInstance().getPhoneImei(getContext());
            device = PhoneUtil.getInstance().getPhoneModel() + PhoneUtil.getInstance().getSDKVersionNumber();
            memberId = SharedPreferencesUtil.getInstance().getString(LOGIN_ID);
            view = inflater.inflate(R.layout.fragment_home, null);
            ButterKnife.bind(this, view);
            downloadManager = DownloadService.getDownloadManager();
            downloadManager.setTargetFolder(SD_KEYBOOKS);//下载路径
            downloadManager.getThreadPool().setCorePoolSize(9);//设置最大下载数
            intView();
            //下载记录
            getDownloadHistory();
        }
        return view;
    }

    private void initLister() {
        gdBookshelf.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                p = position;
                final BookShelfBean.InfosBean b = shelfs.get(p);
                String isRead = b.getIsRead();
                final String destFileName1 = SD_KEYBOOKS + b.getBookName() + ".epub";//
                final String filePath = getExternalFilesDirPath(getContext()) + b.getBookName() + ".epub";//解密完成
//                final String secretKey=b.getRsaKeyUrl();
                String bookId = b.getBooksId();
                String memberId = b.getMemberId();
                if (mABdapter.isEditMode()) {
                    if (mABdapter != null) {
                        int value = mABdapter.getItemState()[p] == 1 ? 0 : 1;
                        mABdapter.getItemState()[p] = value;
                        mABdapter.notifyDataSetChanged();
                        updaBookSelectText(Integer.toString(mABdapter
                                .getCheckedItemCount()));
                    }
                } else {
                    if (isRead.equals("1")) {
                        //1.一级判断是否有权限打开
                        LogUtils.i("允许打开" + destFileName1);
                        /**
                         * 1.首先判断是否有权限打开书
                         * 2.判断书籍是否已经下载过
                         * 3.下载完成用户点击的是否是解密完成的书籍
                         */
                        for (int i = 0; i < sd_nameList.size(); i++) {
                            String names = sd_nameList.get(i);
                            //下载过的书名
                            names = returnSuffix(names);
                            LogUtils.i(names);
                            LogUtils.i(b.getBookName());
                            if (names.equals(b.getBookName())) {
                                myCollection.bindToService(getActivity(), new Runnable() {
                                    public void run() {
                                        if (getFile(decrypt(getBytes(destFileName1)), filePath)) {
                                            Book book = myCollection.getBookByFile(filePath);
                                            if (book != null) {
                                                openBook(book,b.getBooksId());
                                            } else {
                                                Toast.makeText(getActivity(), "打开失败,请重试", Toast.LENGTH_SHORT).show();
                                            }
                                        } else {
                                            LogUtils.i("解密失败");
                                        }
                                    }
                                });
                            } else {
                                LogUtils.i("没下载次书");
                            }
                        }
                    } else {
                        ToastUtil.showToast(getContext(), "不允许打开");
                    }

                }

            }
        });

        gdBookshelf.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                b1 = shelfs.get(position);
                new AlertDialog.Builder(getActivity()).setTitle("提示")
                        .setMessage("下载" + b1.getBookName())
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String destFileName1 = SD_KEYBOOKS + b1.getBookName() + ".epub";
                                dowUrl = SERVIECE_DOWNLOND + b1.getEncryptedUrl();
                                String isDown = b1.getIsDownload();
                                LogUtils.i(dowUrl);
                                if (isDown.equals("1")) {
                                    LogUtils.i("允许下载");
                                    if (FileUtil.isExsit(destFileName1)) {
                                        ToastUtil.showToast(getContext(), "已经下载了");
                                    } else {
                                        // downKey(rsa);
                                        downBook(dowUrl, cutPhoto(b1.getBookPhoto()),b1.getBooksId());
                                    }
                                } else {
                                    ToastUtil.showToast(getActivity(), "不被允许");
                                }
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();
                return true;
            }
        });
    }

    private void openBook(Book book,String bookId) {
        KooReader.openBookActivity(getContext(), book, null,bookId);
    }


    private void intView() {
        topSearch.setVisibility(View.GONE);
        topAdd.setVisibility(View.GONE);
        book_search = (RelativeLayout) LayoutInflater.from(
                getActivity()).inflate(R.layout.book_search, null);
        eidtMenulayout = (RelativeLayout) LayoutInflater.from(
                getActivity()).inflate(R.layout.bookshelf_edit, null);
        numOfBookFilesTxt = (TextView) eidtMenulayout.findViewById(R.id.numOfBookFiles);
        searchDialoglayout = (RelativeLayout) LayoutInflater.from(
                getActivity()).inflate(R.layout.search_dialog, null);
    }

    @Override
    public void onStart() {
        super.onStart();
        initLister();

    }

    @Override
    public void onResume() {
        super.onResume();
        if (!isEmpty(memberId)) {
            initData();
        }

        sd_nameList = FileUtil.getFileDirSuffix(SD_KEYBOOKS);
        mABdapter = new BookShelfsListAdaper(getActivity(), shelfs, sd_nameList);
        gdBookshelf.setAdapter(mABdapter);

    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case DEFICIENCY:
                    ToastUtil.showToast(context, "内存不足");
                    break;
                case ADEQUATE:
//                    String newFilePath = (String) (msg.obj);
//                    LogUtils.d(newFilePath);
                    recordDown();//添加下载记录
                    sd_nameList = FileUtil.getFileDirSuffix(SD_KEYBOOKS);
                    mABdapter.setmData(shelfs, sd_nameList);
                    mABdapter.notifyDataSetChanged();
                    break;
            }

        }
    };


    private void downBook(final String dowUrl, String imgUrl,String bookID) {
//        if (downloadManager.getDownloadInfo(dowUrl) != null) {
//            Log.i("url","="+downloadManager.getDownloadInfo(dowUrl).getFileName());
//            Toast.makeText(getContext(), "已经下载不用重复下载"+dowUrl, Toast.LENGTH_SHORT).show();
//        } else {
        GetRequest request = OkGo.get(dowUrl)//
                .headers("headerKey1", "headerValue1")//
                .headers("headerKey2", "headerValue2")//
                .params("paramKey1", "paramValue1")//
                .params("paramKey2", "paramValue2");
        downloadManager.addTask(dowUrl, imgUrl,bookID, request, null);
        downloadInfo = downloadManager.getDownloadInfo(dowUrl);
        if (downloadInfo != null) {
            //如果任务存在，把任务的监听换成当前页面需要的监听
            downloadInfo.setListener(new DownloadListener() {
                @Override
                public void onProgress(DownloadInfo downloadInfo) {

                }

                @Override
                public void onFinish(final DownloadInfo downloadInfo) {
                    LogUtils.i("下载完成,文件大小=" + downloadInfo.getTotalLength());
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Message message = new Message();
                            if (gainSDFreeSize() > downloadInfo.getTotalLength()) {
//                                    getFile(getBytes(downloadInfo.getTargetPath()),Constant.SD_BOOKS,b1.getBookName());
                                message.what = ADEQUATE;
                            } else {
                                message.what = DEFICIENCY;
                            }
                            handler.sendMessage(message);
                        }
                    }).start();
                }

                @Override
                public void onError(DownloadInfo downloadInfo, String errorMsg, Exception e) {
                }
            });
        }
//        }
    }

    //添加下载记录
    private void recordDown() {
        OkGo.post(DOWNLOADBOOK).tag(this)
                .params("memberId", b1.getMemberId())
                .params("bookId", b1.getBooksId())
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


    private void initData() {
        OkGo.post(Constant.BOOKSHELF).tag(this).
                params("memberId", memberId).
                cacheKey(Constant.BOOKSHELF).
                cacheMode(FIRST_CACHE_THEN_REQUEST).
                execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {

                        handleResponse(s);
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);

                    }

                    @Override
                    public void onCacheSuccess(String s, Call call) {
                        super.onCacheSuccess(s, call);
                        handleResponse(s);
                    }

                    @Override
                    public void onAfter(String s, Exception e) {
                        super.onAfter(s, e);
                        updateView();

                    }

                    //获得后台书架数据
                    private void handleResponse(String s) {
                        Gson gson = new Gson();
                        BookShelfBean bookShelf = gson.fromJson(s, BookShelfBean.class);
                        if (bookShelf.getResult().equals("1")) {
                            shelfs = bookShelf.getInfos();
                            sd_nameList = FileUtil.getFileDirSuffix(SD_KEYBOOKS);
//                            mABdapter.setmData(shelfs,sd_nameList);
                        } else {
                            LogUtils.i("空数据");
                            shelfs = new ArrayList<>();
                        }
                        mABdapter.setmData(shelfs, sd_nameList);
                        mABdapter.notifyDataSetChanged();

                    }
                });


    }


    @OnClick({R.id.top_edit, R.id.top_del, R.id.top_search, R.id.top_add, R.id.cancel, top_all, R.id.top_no, R.id.bookshelf_nothing})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            //编辑页面
            case R.id.top_edit:
                if (mABdapter != null && mABdapter.getCount() > 0) {
                    if (!mABdapter.isEditMode()) {
                        mABdapter.setEditMode(true);
                        mABdapter.notifyDataSetChanged();

                        topDel.setVisibility(View.VISIBLE);
                        topEdit.setVisibility(View.INVISIBLE);
                        topCancel.setVisibility(View.VISIBLE);
                        topAll.setVisibility(View.VISIBLE);
                        top_no.setVisibility(View.GONE);
                    } else {
                        mABdapter.setEditMode(false);
                        mABdapter.notifyDataSetChanged();
                    }
                } else {
                    Toast.makeText(getActivity(), "书架中还没有书籍!", Toast.LENGTH_SHORT).show();
                }

                break;
            //删除
            case R.id.top_del:
                delLocalShelf();
                cancel();

                break;
            //取消
            case R.id.cancel:
                cancel();
                break;
            case R.id.top_search:
                openAct(getActivity(), SearchBookActivity.class);
                break;
            //全选
            case top_all:
                mABdapter.checkAll();
                mABdapter.notifyDataSetChanged();
                topAll.setVisibility(View.GONE);
                topCancel.setVisibility(View.GONE);
                top_no.setVisibility(View.VISIBLE);
                break;
            //取消
            case R.id.top_no:
                cancel();
                break;
            case R.id.top_add:


                break;
            //没有书籍时点击跳转到搜索页面
            case R.id.bookshelf_nothing:
                openAct(getActivity(), SearchBookActivity.class);
                break;

        }
    }

    private void cancel() {
        topDel.setVisibility(View.INVISIBLE);
        topEdit.setVisibility(View.VISIBLE);
        topCancel.setVisibility(View.INVISIBLE);
        topAll.setVisibility(View.GONE);
        top_no.setVisibility(View.GONE);
        mABdapter.setEditMode(false);
        mABdapter.uncheckAll();
        mABdapter.notifyDataSetChanged();

    }


    //更新选中显示
    private void updaBookSelectText(String s) {
        numOfBookFilesTxt.setText("选中" + s + "个文件！");
    }

    //删除书籍
    public void delLocalShelf() {
        List<String> a = new ArrayList<>();
        Log.i("MAIN", mABdapter.getItemState().length + "");
        if (mABdapter.getItemState() != null
                && mABdapter.getItemState().length > 0) {
            for (int i = 0; i < mABdapter.getItemState().length; i++) {
                if (mABdapter.getItemState()[i] == 1) {
                    String bookid = shelfs.get(i).getId();
                    String destFileName1 = SD_KEYBOOKS + shelfs.get(i).getBookName() + ".epub";
                    a.add(bookid);
                    LogUtils.i(bookid);
                    LogUtils.i(destFileName1);
                    downloadManager.removeTask(destFileName1, true);
                }
            }
            LogUtils.i(a.toString());
            if (a.size() != 0)
                OkGo.post(REMOVEBOOKSHELFS).tag(this)
                        .addUrlParams("ids", a)
                        .execute(new StringCallback() {
                            @Override
                            public void onSuccess(String s, Call call, Response response) {
                                LogUtils.i(s);
                                try {
                                    JSONObject jsonObject = new JSONObject(s);
                                    String result = jsonObject.getString("result");
                                    if (result.equals("1")) {
                                        if (mABdapter.isEditMode()) {
                                            mABdapter.setEditMode(false);
                                        }
                                        mABdapter.notifyDataSetChanged();
                                    }
                                    Toast.makeText(getActivity(), jsonObject.getString("msg"), Toast.LENGTH_SHORT)
                                            .show();

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onError(Call call, Response response, Exception e) {
                                super.onError(call, response, e);
                                ToastUtil.showToast(getContext(), "删除失败");
                            }

                            @Override
                            public void onAfter(String s, Exception e) {
                                super.onAfter(s, e);
                                initData();
                            }
                        });


        }
    }

    private void updateView() {

        LogUtils.i(mABdapter.getCount());
        if (mABdapter != null && mABdapter.getCount() > 0) {
            bookshelfSomething.setVisibility(View.VISIBLE);
            bookshelfNothing.setVisibility(View.GONE);
        } else {
            //未添加任何书籍时
            bookshelfSomething.setVisibility(View.GONE);
            bookshelfNothing.setVisibility(View.VISIBLE);
        }

    }

    //点击返回按键
    public static boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == event.KEYCODE_BACK) {
            Log.d("HomeFrgment", "OK");
            mABdapter.setEditMode(false);
            mABdapter.notifyDataSetChanged();
//            if ()
            return true;
        }
        return false;
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        myCollection.unbind();
        OkGo.getInstance().cancelTag(this);
    }


    public void getDownloadHistory() {
//        OkGo.post(GETDOWNLOADLIST).
//                params("memberId",memberId)
//                .execute(new StringCallback() {
//                    @Override
//                    public void onSuccess(String s, Call call, Response response) {
//
//                    }
//                });
    }


}

