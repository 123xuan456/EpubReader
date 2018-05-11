package com.reeching.epub.adapter;

import android.content.Context;

import com.reeching.epub.R;
import com.reeching.epub.bean.SorkBookBean;
import com.reeching.epub.recyclerviewadapter.ViewHolder;
import com.reeching.epub.recyclerviewadapter.base.CommonBaseAdapter;

import java.util.List;

import static com.reeching.epub.utils.AppValidationUtil.cutPhoto;



/**
 * Created by 绍轩 on 2017/10/20.
 */

public class BookListsAdapter extends CommonBaseAdapter<SorkBookBean.InfosBean> {


    public BookListsAdapter(Context context, List<SorkBookBean.InfosBean> datas, boolean isOpenLoadMore) {
        super(context, datas, isOpenLoadMore);
    }


    @Override
    protected void convert(ViewHolder holder, SorkBookBean.InfosBean data, int position) {
        holder.setText(R.id.tvBookListTitle,data.getTitle());
        holder.setText(R.id.tvBookAuthor,data.getAuthor());
        holder.setText(R.id.tvBookListDesc,data.getSummaryContent());
        String photo = data.getPhoto();
        holder.setImageUrl(R.id.ivBookListCover,cutPhoto(photo),R.drawable.cover_default);
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_bookslist;
    }

}
