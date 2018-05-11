package com.reeching.epub.recyclerviewadapter.interfaces;


import com.reeching.epub.recyclerviewadapter.ViewHolder;

/**
 * Time: 2016/8/29 10:48
 */
public interface OnMultiItemClickListeners<T> {
    void onItemClick(ViewHolder viewHolder, T data, int position, int viewType);
}
