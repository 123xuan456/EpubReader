package com.reeching.epub.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.koolearn.android.util.SharedPreferencesUtil;
import com.reeching.epub.R;
import com.reeching.epub.utils.ResourceUtil;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okserver.download.DownloadManager;
import okserver.download.DownloadService;

import static com.reeching.epub.constant.Constant.LOGIN_ID;
import static com.reeching.epub.constant.Constant.LOGIN_NAME;
import static com.reeching.epub.constant.Constant.LOGIN_NICKNAME;
import static com.reeching.epub.constant.Constant.LOGIN_PASSWORD;
import static com.reeching.epub.constant.Constant.LOGIN_PHONENUMBER;
import static com.reeching.epub.constant.Constant.LOGIN_PHOTO;
import static com.reeching.epub.constant.Constant.LOGIN_SEX;
import static com.reeching.epub.utils.ActivityUtil.openAct;
import static com.reeching.epub.utils.FileUtil.delFile;
import static com.reeching.epub.utils.FileUtil.getExternalFilesDirPath;
import static com.reeching.epub.utils.FileUtil.getFolderSize;


public class SettingActivity extends Activity {

    @Bind(R.id.top_finish)
    ImageView topFinish;
    @Bind(R.id.top_bookname)
    TextView topBookname;
    @Bind(R.id.tvCacheSize)
    TextView tvCacheSize;
    @Bind(R.id.top_search)
    ImageView topSearch;
    @Bind(R.id.top_books)
    ImageView topBooks;
    @Bind(R.id.feedBack)
    RelativeLayout feedBack;
    @Bind(R.id.appUpdate)
    RelativeLayout appUpdate;
    @Bind(R.id.quit)
    RelativeLayout quit;
    @Bind(R.id.cleanCache)
    RelativeLayout cleanCache;
    @Bind(R.id.changePasswd)
    RelativeLayout changePasswd;//修改密码
    private DownloadManager downloadManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        topBookname.setText(ResourceUtil.getString(this, R.string.set));
        topSearch.setVisibility(View.GONE);
        topBooks.setVisibility(View.GONE);
        tvCacheSize.setText(getFolderSize(SettingActivity.this));
        downloadManager = DownloadService.getDownloadManager();
    }

    @OnClick({R.id.top_finish, R.id.top_search,R.id.feedBack,R.id.appUpdate,R.id.quit,R.id.cleanCache,R.id.changePasswd})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.top_finish:
                finish();
                break;
            case R.id.top_search:
                openAct(this,SearchBookActivity.class);
                break;
            case R.id.feedBack:
                openAct(this,FeedBackActivity.class);
                break;
            case R.id.changePasswd:
                openAct(this,ChangePasswdActivity.class);
                break;
            case R.id.appUpdate:
                updateApp();
                break;
            case R.id.quit:
                setUser();
                finish();
                break;
            case R.id.cleanCache://清楚缓存
                clearCache();

                break;
        }
    }


    private void updateApp() {
//        OkGo.get(APPUPDATE).tag(this).params()

    }
    private void clearCache() {
        new AlertDialog.Builder(this).setTitle("提示")
                .setMessage("清除缓存会删除已下载书籍")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        delFile(new File(getExternalFilesDirPath(SettingActivity.this)),true);
                        downloadManager.removeAllTask();
                        tvCacheSize.setText(getFolderSize(SettingActivity.this));
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();
    }

    public static void setUser(){
        setUser("","","","","","","");
    }

    public static void setUser(String id,String nickname,String passWord,String phone,String photo,String sex,String name){
        SharedPreferencesUtil.getInstance().putString(LOGIN_ID,id);
        SharedPreferencesUtil.getInstance().putString(LOGIN_NICKNAME,nickname);
        SharedPreferencesUtil.getInstance().putString(LOGIN_PASSWORD,passWord);
        SharedPreferencesUtil.getInstance().putString(LOGIN_PHONENUMBER,phone);
        SharedPreferencesUtil.getInstance().putString(LOGIN_NAME,name);
        SharedPreferencesUtil.getInstance().putString(LOGIN_PHOTO,photo);
        SharedPreferencesUtil.getInstance().putString(LOGIN_SEX,sex);
    }
}
