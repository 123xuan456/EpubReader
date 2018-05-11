package com.reeching.epub.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.koolearn.android.kooreader.libraryService.BookCollectionShadow;
import com.reeching.epub.R;
import com.reeching.epub.adapter.DownloadAdapter;
import com.reeching.epub.utils.ResourceUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okserver.download.DownloadInfo;
import okserver.download.DownloadManager;
import okserver.download.DownloadService;
import okserver.task.ExecutorWithListener;

import static com.reeching.epub.utils.FileUtil.gainSDAllSize;
import static com.reeching.epub.utils.FileUtil.gainSDFreeSize;

//下载管理
public class DownloadActivity extends Activity implements View.OnClickListener, ExecutorWithListener.OnAllTaskEndListener {
    @Bind(R.id.top_finish)
    ImageView topFinish;
    @Bind(R.id.top_bookname)
    TextView topBookname;
    @Bind(R.id.top_search)
    ImageView topSearch;
    @Bind(R.id.top_books)
    ImageView topBooks;
    private List<DownloadInfo> allTask;
    private DownloadAdapter adapter;
    private DownloadManager downloadManager;
    private ListView listView;
    //    private ListView listView1;
    private TextView checkTv;
    //    private LinearLayout line_downloading;
//    private LinearLayout line_finished;
    private ScrollView scrollView;
    ArrayList<String> sd_nameList = new ArrayList<>();//sd中的电子书
    private ArrayAdapter<String> adapter1;
    private final BookCollectionShadow myCollection = new BookCollectionShadow();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_downlond);
        ButterKnife.bind(this);
        setview();
        setData();
    }


    public void setview() {
        listView = (ListView) findViewById(R.id.listView);
//        listView1= (ListView) findViewById(R.id.listView1);
        checkTv = (TextView) findViewById(R.id.checkTv);
//        line_downloading= (LinearLayout) findViewById(R.id.line_downloading);
//        line_finished= (LinearLayout) findViewById(R.id.line_finished);
//        scrollView= (ScrollView) findViewById(R.id.scrollView);
        topBookname.setText(ResourceUtil.getString(this, R.string.mydownlond));
        topSearch.setVisibility(View.GONE);
        topBooks.setVisibility(View.GONE);
        topFinish.setOnClickListener(this);
    }


    @Override
    protected void onResume() {
        super.onResume();
        checkTv.setText("手机总内存: " + gainSDAllSize(getApplicationContext()) + ", " + "可用内存: "
                + gainSDFreeSize(getApplicationContext()));
    }

    public void setData() {
        downloadManager = DownloadService.getDownloadManager();
        concealView();
        adapter = new DownloadAdapter(this, allTask, downloadManager, myCollection);
        listView.setAdapter(adapter);

        downloadManager.getThreadPool().getExecutor().addOnAllTaskEndListener(this);
    }

    //
    @Override
    public void onAllTaskEnd() {
        for (DownloadInfo downloadInfo : allTask) {
            if (downloadInfo.getState() != DownloadManager.FINISH) {
                return;
            }
        }
        checkTv.setText("手机总内存: " + gainSDAllSize(getApplicationContext()) + ", " + "可用内存: "
                + gainSDFreeSize(getApplicationContext()));
        concealView();
    }

    public void concealView() {
        allTask = downloadManager.getAllTask();
        if (allTask.size() == 0) listView.setVisibility(View.GONE);//没有下载任务隐藏

    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.removeAll:
                downloadManager.removeAllTask();
                adapter.notifyDataSetChanged();  //移除的时候需要调用
                break;
            case R.id.pauseAll:
                downloadManager.pauseAllTask();
                break;
            case R.id.stopAll:
                downloadManager.stopAllTask();
                break;
            case R.id.startAll:
                downloadManager.startAllTask();
                break;
            case R.id.top_finish:
                finish();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //记得移除，否者会回调多次
        downloadManager.getThreadPool().getExecutor().removeOnAllTaskEndListener(this);
        myCollection.unbind();
    }


}
