package com.reeching.epub.recyclerviewadapter.interfaces;


import com.reeching.epub.recyclerviewadapter.ViewHolder;

/**
 */
public interface OnItemClickListener<T> {
    void onItemClick(ViewHolder viewHolder, T data, int position);
}
