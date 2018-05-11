package com.reeching.epub.fagment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.reeching.epub.R;
import com.reeching.epub.activity.BookListsActivity;
import com.reeching.epub.adapter.GridViewAdapter;
import com.reeching.epub.bean.SortBean;

import java.util.ArrayList;

/**
 * 分类——第二级分类
 */
public class SorkBookFragment extends Fragment {

    private View view;
    private GridView gridView;
    private GridViewAdapter adapter;
    private FragmentActivity context;
    ArrayList<SortBean.InfosBean.BigListBean.SmallListBean> dataList;

    public SorkBookFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context=getActivity();
        view=inflater.inflate(R.layout.fragment_sork_book, container, false);
        gridView= (GridView) view.findViewById(R.id.gridView);
        dataList= (ArrayList<SortBean.InfosBean.BigListBean.SmallListBean>) getArguments().getSerializable("data");
        adapter = new GridViewAdapter(context, dataList);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                String id = dataList.get(arg2).getSId();
                String name = dataList.get(arg2).getSname();
                Intent intent = new Intent(getActivity(),BookListsActivity.class);
                intent.putExtra("id",id);
                intent.putExtra("bookName",name);
                startActivity(intent);
            }
        });
        return view;
    }
}
