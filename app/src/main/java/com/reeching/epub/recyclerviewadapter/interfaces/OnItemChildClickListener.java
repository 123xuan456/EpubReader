package com.reeching.epub.recyclerviewadapter.interfaces;


import com.reeching.epub.recyclerviewadapter.ViewHolder;

/**
 */
public interface OnItemChildClickListener<T> {
    void onItemChildClick(ViewHolder viewHolder, T data, int position);
}
