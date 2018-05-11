package com.reeching.epub.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.widget.GridView;

import com.reeching.epub.R;


public class MyGridView extends GridView {

	private Bitmap background;

	public MyGridView(Context context) {
		super(context);
	}

//	public MyGridView(Context context, AttributeSet attrs) {
//		super(context, attrs);
//	}

	public MyGridView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO 自动生成的构造函数存根
	}

	public MyGridView(Context context, AttributeSet attrs) {
		super(context, attrs);
		background = BitmapFactory.decodeResource(getResources(),
				R.drawable.book_shelf_cenetr);
	}

	@Override
	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int expandSpec = MeasureSpec.makeMeasureSpec(
				Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}
//	@Override
//	protected void dispatchDraw(Canvas canvas) {
//		int count = getChildCount();
//		int top = count > 0 ? getChildAt(0).getTop() : 0;
//		int backgroundWidth = background.getWidth();
//		int backgroundHeight = background.getHeight()+2;
//		int width = getWidth();
//		int height = getHeight();
//
////		for (int y = top; y < height; y += backgroundHeight) {
////			for (int x = 0; x < width; x += backgroundWidth) {
////				canvas.drawBitmap(background, x, y, null);
////			}
////		}
//
//		super.dispatchDraw(canvas);
//	}

}
