package com.reeching.epub;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.widget.RadioGroup;

import com.koolearn.android.kooreader.libraryService.BookCollectionShadow;
import com.koolearn.android.util.LogUtils;
import com.koolearn.android.util.SharedPreferencesUtil;
import com.reeching.epub.base.BaseActivity;
import com.reeching.epub.constant.Constant;
import com.reeching.epub.fagment.HomeFragment;
import com.reeching.epub.fagment.MessageFragment;
import com.reeching.epub.fagment.MineFragment;
import com.reeching.epub.fagment.SortFragment;
import com.reeching.epub.utils.FileUtil;
import com.reeching.epub.utils.PhoneUtil;


public class MainActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener{
    private RadioGroup radioGroup;
    private Fragment fragment;
    private final BookCollectionShadow myCollection = new BookCollectionShadow();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(this);
        fragment = getSupportFragmentManager().findFragmentById(R.id.fragment);

        if (fragment == null) {
            fragment = HomeFragment.newInstance();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment, HomeFragment.newInstance()).commit();
        }
        //创建文件夹
        FileUtil.createFileDir(this,"");
        FileUtil.createFileDir(this,"downlond");
        FileUtil.createFileDir(this,"books");
        String deviceId = PhoneUtil.getInstance().getPhoneImei(this);
        String device = PhoneUtil.getInstance().getPhoneModel() + PhoneUtil.getInstance().getSDKVersionNumber();
        LogUtils.i(device+"+"+deviceId);
        SharedPreferencesUtil.getInstance().putString(Constant.DEVICEID,deviceId);
        SharedPreferencesUtil.getInstance().putString(Constant.DEVICE,device);
    }
    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        switch (checkedId) {
            case R.id.radio_home: {
                fragment = HomeFragment.newInstance();//首页
            }
            break;
            case R.id.radio_sort: {
                fragment = SortFragment.newInstance();//分类
            }
            break;
            case R.id.radio_information: {
                fragment = MessageFragment.newInstance();//消息
            }
            break;
            case R.id.radio_mine: {
                fragment = MineFragment.newInstance();//我的
            }
            break;
        }
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment, fragment).commit();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN)
            if (fragment instanceof HomeFragment) {
                if (((HomeFragment) fragment).onKeyDown(keyCode, event)) {
                    finish();
                }
            }else {
                //其他的fragment
                finish();
            }

        return true;
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        myCollection.unbind();
    }
}
