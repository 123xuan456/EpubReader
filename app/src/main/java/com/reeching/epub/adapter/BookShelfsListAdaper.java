package com.reeching.epub.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.koolearn.android.util.LogUtils;
import com.reeching.epub.R;
import com.reeching.epub.bean.BookShelfBean;
import com.reeching.epub.utils.GlideUtils;

import java.util.ArrayList;
import java.util.List;

import static com.reeching.epub.utils.AppValidationUtil.cutPhoto;


/**
 * 首页书籍列表
 */
public class BookShelfsListAdaper extends BaseAdapter {
	private LayoutInflater mInflater;
	private List<BookShelfBean.InfosBean> mData;
	private int[] itemState;
	private boolean isEditMode = false;
	ArrayList<String> sd_nameList;
	private Context context;
	public BookShelfsListAdaper(Context context, List<BookShelfBean.InfosBean> mData,ArrayList<String> sd_nameList) {
		mInflater = LayoutInflater.from(context);
		this.mData = mData;
		itemState = new int[mData.size()];
		this.sd_nameList=sd_nameList;
		this.context=context;
		LogUtils.i(sd_nameList.size());
		init();
	}

	public List<BookShelfBean.InfosBean> getmData() {
		return mData;
	}

	public void setmData(List<BookShelfBean.InfosBean> smData,ArrayList<String> sd_nameList) {
		this.mData = smData;
		itemState = new int[smData.size()];
		this.sd_nameList=sd_nameList;
		init();
	}

	private void init() {
		for (int i = 0; i < mData.size(); i++) {
			itemState[i] = 0;
		}
	}

	public void uncheckAll() {
		for (int i = 0; i < mData.size(); i++) {
			itemState[i] = 0;
		}
	}

	public boolean isAllChecked() {
		for (int i : itemState) {
			if (i == 0)
				return false;
		}
		return true;
	}

	public void checkAll() {
		for (int i = 0; i < itemState.length; i++) {
			itemState[i] = 1;
		}
	}

	public int getCheckedItemCount() {
		int count = 0;
		for (int i : itemState) {
			if (i == 1)
				count++;
		}
		return count;
	}

	@Override
	public int getCount() {
		return mData.size();
	}
	public void setCount(int Data) {
	}
	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	public int[] getItemState() {
		return itemState;
	}

	public void setItemState(int[] itemState) {
		this.itemState = itemState;
	}

	public boolean isEditMode() {
		return isEditMode;
	}

	public void setEditMode(boolean isEditMode) {
		this.isEditMode = isEditMode;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		// convertView为null的时候初始化convertView。
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = mInflater.inflate(
					R.layout.item_bookshelf_gridview, null);
			holder.img = (ImageView) convertView.findViewById(R.id.cover);
			holder.title = (TextView) convertView.findViewById(R.id.tvBookName);
			holder.tvDown = (TextView) convertView.findViewById(R.id.tvDown);
			holder.tvRead = (TextView) convertView.findViewById(R.id.tvRead);
			holder.item_rl = (RelativeLayout) convertView.findViewById(R.id.item_rl);
			holder.checked = (ImageView) convertView.findViewById(R.id.bookshelfFileSelectIcon);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		BookShelfBean.InfosBean b = mData.get(position);
		holder.title.setText(b.getBookName());
		GlideUtils.loadImageViewError(context,cutPhoto(b.getBookPhoto()), holder.img,R.drawable.ic_shelf_epub);
		if (isEditMode) {
			holder.checked.setVisibility(View.VISIBLE);
			if (itemState[position] == 0) {
				holder.checked.setBackgroundResource(R.drawable.checkbox_unselect);
			} else {
				holder.checked.setBackgroundResource(R.drawable.checkbox_selected);
			}
		} else {
			holder.checked.setVisibility(View.GONE);
		}
		if (b.getIsRead().equals("1")){
			holder.tvRead.setText("允许阅读");
		}else if (b.getIsRead().equals("0")){
			holder.tvRead.setText("可以阅读");
		}else {
			holder.tvRead.setText("不可阅读");
		}
		if (b.getIsDownload().equals("1")){
			for (int i=0;i<sd_nameList.size();i++){
				String names = sd_nameList.get(i);
				names=returnSuffix(names);
				if (names.equals(b.getBookName())){
					holder.tvDown.setText("已经下载");
				}
			}
		}else {
			holder.tvDown.setText("不可下载");
			holder.tvDown.setBackgroundColor(R.color.darkgray);
		}
		convertView.setTag(holder);
		return convertView;
	}

	public final class ViewHolder {
		public ImageView img;
		public TextView title;
		public TextView tvDown;
		public TextView tvRead;
		public RelativeLayout item_rl;
		public ImageView checked;
	}

	public static String returnSuffix(String fileName){

		if (fileName.lastIndexOf(".") > 0){
			String fileSuffix = fileName.substring(0,fileName.lastIndexOf("."));
			return fileSuffix;
		}
		return null;
	}
	public String returnName(String fileName){

		if (fileName.indexOf(".") > 0){
			String name = fileName.substring(fileName.indexOf("."));
			return name;
		}
		return null;
	}

}