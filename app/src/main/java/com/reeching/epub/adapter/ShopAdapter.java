package com.reeching.epub.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.koolearn.android.util.LogUtils;
import com.reeching.epub.bean.SortBean;
import com.reeching.epub.fagment.SorkBookFragment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 绍轩 on 2017/11/9.
 */

public class ShopAdapter extends FragmentPagerAdapter {
    private List<SortBean.InfosBean.BigListBean.SmallListBean> dataList;
    private ArrayList<String> list;
    private List<SortBean.InfosBean> sortList;
    public ShopAdapter(FragmentManager fm, ArrayList<String> list, List<SortBean.InfosBean> sortList) {
        super(fm);
       this.list=list;
       this.sortList=sortList;
    }
    @Override
    public Fragment getItem(int index) {
        LogUtils.i(list.toString()+","+sortList.size());
        Fragment fragment = new SorkBookFragment();
        Bundle bundle = new Bundle();
        if (list!=null){
            String id = sortList.get(index).getBigList().getBigId();
            dataList=sortList.get(index).getBigList().getSmallList();
            bundle.putString("id", id);
            bundle.putString("sortName", sortList.get(index).getBigList().getBigName());
            bundle.putSerializable("data", (Serializable) dataList);
            fragment.setArguments(bundle);
        }
        return fragment;
    }
    @Override
    public int getCount() {
        return list.size();
    }
    /**
     * 刷新数据
     */
    public void refreshData(ArrayList<String> list, List<SortBean.InfosBean> sortList) {
        this.list=list;
        this.sortList=sortList;
        notifyDataSetChanged();
    }


}