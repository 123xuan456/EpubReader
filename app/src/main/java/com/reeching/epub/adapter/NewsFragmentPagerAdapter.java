package com.reeching.epub.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by 绍轩 on 2017/10/11.
 */

public class NewsFragmentPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragmentList;
    private FragmentManager fm;
    public NewsFragmentPagerAdapter(FragmentManager fm,List<Fragment> fragmentList) {
        super(fm);
        this.fragmentList=fragmentList;
        this.fm=fm;
    }



    @Override
    public Fragment getItem(int idx) {
        return fragmentList.get(idx);
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return fragmentList.size();
    }
    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;  //没有找到child要求重新加载
    }
}
