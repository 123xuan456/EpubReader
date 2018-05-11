package com.reeching.epub.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.koolearn.android.util.LogUtils;
import com.reeching.epub.R;
import com.reeching.epub.bean.InformBean;

import java.util.List;

/**
 * Created by 绍轩 on 2017/10/20.
 */

public class InformAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private Context context;
    private List<InformBean.InfosBean> data;
    public InformAdapter(Context context, List<InformBean.InfosBean> data) {
        this.context = context;
        this.data = data;
        LogUtils.i(data.size());
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_informlist, parent,
                    false);
            return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            InformBean.InfosBean infosBean = data.get(position);

          ((ItemViewHolder) holder).tv.setText(infosBean.getTitle());
          ((ItemViewHolder) holder).tvBookListDesc.setText(infosBean.getNoticeContent());

            if (onItemClickListener != null) {
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = holder.getLayoutPosition();
                        onItemClickListener.onItemClick(holder.itemView, position);
                    }
                });

                holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        int position = holder.getLayoutPosition();
                        onItemClickListener.onItemLongClick(holder.itemView, position);
                        return false;
                    }
                });
            }
        }
    }


    static class ItemViewHolder extends RecyclerView.ViewHolder {

        private final TextView tvBookListDesc;//简介
        TextView tv;//书名

        public ItemViewHolder(View view) {
            super(view);
            tv = (TextView) view.findViewById(R.id.tvBookListTitle);
            tvBookListDesc = (TextView) view.findViewById(R.id.tvBookListDesc);
        }
    }
}
