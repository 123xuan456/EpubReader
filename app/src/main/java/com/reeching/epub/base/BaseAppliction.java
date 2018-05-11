package com.reeching.epub.base;

import android.content.Context;

import com.koolearn.android.util.SharedPreferencesUtil;
import com.koolearn.klibrary.ui.android.library.ZLAndroidApplication;
import com.lzy.okgo.OkGo;

import org.litepal.LitePal;

/**
 * Created by 绍轩 on 2017/10/14.
 */

public class BaseAppliction extends ZLAndroidApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        SharedPreferencesUtil.init(getApplicationContext(), getPackageName() + "_preference", Context.MODE_MULTI_PROCESS);
        OkGo.init(this);
        LitePal.initialize(this);
    }
}
