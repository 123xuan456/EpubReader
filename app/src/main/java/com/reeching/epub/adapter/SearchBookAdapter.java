package com.reeching.epub.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.koolearn.android.util.LogUtils;
import com.reeching.epub.R;
import com.reeching.epub.bean.SearchBookBean;
import com.reeching.epub.utils.GlideUtils;

import java.util.List;

import static com.reeching.epub.utils.AppValidationUtil.cutPhoto;


/**
 * Created by 绍轩 on 2017/10/16.
 */

public class SearchBookAdapter extends BaseAdapter {

    private List<SearchBookBean.InfosBean> mTitleArray;// 标题列表
    private LayoutInflater inflater = null;
    private Context mContext;

    /**
     * Adapter构造方法
     *
     * @param titleArray
     */
    public SearchBookAdapter(Context context, List<SearchBookBean.InfosBean> titleArray) {
        // TODO Auto-generated constructor stub
        this.mTitleArray = titleArray;
        this.mContext = context;
        inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    /**
     * 获取总数
     */
    @Override
    public int getCount() {
        LogUtils.i(mTitleArray.size());
        // TODO Auto-generated method stub
        if (mTitleArray.size()==0){
            return 0;
        }
        return mTitleArray.size();
    }

    /**
     * 获取Item内容
     */
    @Override
    public SearchBookBean.InfosBean getItem(int position) {
        // TODO Auto-generated method stub
        return mTitleArray.get(position);
    }


    /**
     * 获取Item的ID
     */
    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub

        ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_search_layout, null);
            holder.titleTv = (TextView) convertView.findViewById(R.id.title_tv);
            holder.ivIconSeach = (ImageView) convertView.findViewById(R.id.ivIconSeach);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.titleTv.setText(mTitleArray.get(position).getTitle());
        String photo = mTitleArray.get(position).getPhoto();
        GlideUtils.loadImageViewSize(mContext,cutPhoto(photo),50,50, holder.ivIconSeach);
        return convertView;
    }

    class ViewHolder {
        TextView titleTv;
        ImageView ivIconSeach;
    }

    /**
     * 刷新数据
     *
     * @param array
     */
    public void refreshData(List<SearchBookBean.InfosBean> array) {
        this.mTitleArray = array;
        notifyDataSetChanged();
    }

}
