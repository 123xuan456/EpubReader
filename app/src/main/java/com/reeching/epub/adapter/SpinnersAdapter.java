package com.reeching.epub.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.reeching.epub.R;
import com.reeching.epub.bean.DescriptionBean;

import java.util.List;

/**
 * Created by 绍轩 on 2018/3/8.
 */

public class SpinnersAdapter extends BaseAdapter {
    private List<DescriptionBean.InfosBean> mList;
    private Context mContext;

    public SpinnersAdapter(Context mContext, List<DescriptionBean.InfosBean> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater _LayoutInflater= LayoutInflater.from(mContext);
        convertView=_LayoutInflater.inflate(R.layout.item_spinnerlist, null);
        if(convertView!=null)
        {
            TextView _TextView1=(TextView)convertView.findViewById(R.id.tv);
            _TextView1.setText(mList.get(position).getLabel());
        }
        return convertView;
    }


}
