package com.reeching.epub.adapter;

import android.content.Context;

import com.reeching.epub.R;
import com.reeching.epub.bean.BookCommentBean;
import com.reeching.epub.recyclerviewadapter.ViewHolder;
import com.reeching.epub.recyclerviewadapter.base.CommonBaseAdapter;

import java.util.List;

import static com.reeching.epub.utils.AppValidationUtil.cutPhoto;

/**
 * Created by 绍轩 on 2017/10/20.
 */

public class BookCommentAdapter extends CommonBaseAdapter<BookCommentBean.InfosBean> {


    public BookCommentAdapter(Context context, List<BookCommentBean.InfosBean> datas, boolean isOpenLoadMore) {
        super(context, datas, isOpenLoadMore);
    }


    @Override
    protected void convert(ViewHolder holder, BookCommentBean.InfosBean data, int position) {
        holder.setText(R.id.tvBookTitle,data.getNickname());
        holder.setText(R.id.tvTitle,data.getContent());
        holder.setText(R.id.tvTime,data.getCreateDate());
        String photo = data.getPhoto();
        holder.setImageUrl(R.id.ivBookCover,cutPhoto(photo),R.mipmap.hand);
    }

    @Override
    protected int getItemLayoutId() {
        return R.layout.item_bookcomment;
    }

}
