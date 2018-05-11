package com.reeching.epub.base;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.reeching.epub.R;
import com.reeching.epub.activity.SearchBookActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.reeching.epub.utils.ActivityUtil.openAct;
import static com.reeching.epub.utils.FileUtil.getExternalFilesDirPath;


/**
 * Created by 绍轩 on 2017/10/10.
 *
 */

public abstract class BaseBookActivity extends Activity {
    @Bind(R.id.top_finish)
    ImageView top_finish;
    @Bind(R.id.top_search)
    ImageView topSearch;
    @Bind(R.id.top_books)
    ImageView top_books;
    protected Context mContext;
    //保存加密之后电子书目录
    public String SD_KEYBOOKS;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        SD_KEYBOOKS= getExternalFilesDirPath(BaseBookActivity.this)+"downlond/";
        mContext=this;
        transparent19and20();
        /**
         * 对各种控件进行设置、适配、填充数据
         */
        setview();
        setData();

    }
    protected void transparent19and20() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT
                && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }
    public abstract int getLayoutId();
    public abstract void setview();
    public abstract void setData();

    @OnClick({R.id.top_finish, R.id.top_search, R.id.top_books})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.top_finish:
                finish();
                break;
            case R.id.top_search:
                openAct(this,SearchBookActivity.class);
                break;
            case R.id.top_books:
                break;
        }

    }

}
