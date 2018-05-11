package com.reeching.epub.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.reeching.epub.R;
import com.reeching.epub.utils.ResourceUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 我的——我的图书
 */
public class MyBooksActivity extends Activity {

    @Bind(R.id.top_finish)
    ImageView topFinish;
    @Bind(R.id.top_bookname)
    TextView topBookname;
    @Bind(R.id.top_search)
    ImageView topSearch;
    @Bind(R.id.top_books)
    ImageView topBooks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_books);
        ButterKnife.bind(this);
        topBookname.setText(ResourceUtil.getString(this, R.string.mybook));
        topSearch.setVisibility(View.GONE);
        topBooks.setVisibility(View.GONE);

    }

    @OnClick(R.id.top_finish)
    public void onViewClicked() {
        finish();
    }
}
