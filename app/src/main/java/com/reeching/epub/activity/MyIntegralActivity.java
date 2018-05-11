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
 * 我的——积分页面
 */
public class MyIntegralActivity extends Activity {

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
        setContentView(R.layout.activity_my_integral);
        ButterKnife.bind(this);
        topBookname.setText(ResourceUtil.getString(this, R.string.myintegral));
        topSearch.setVisibility(View.GONE);
        topBooks.setVisibility(View.GONE);
    }

    @OnClick({R.id.top_finish, R.id.top_bookname})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.top_finish:
                finish();
                break;
            case R.id.top_bookname:
                break;
        }
    }
}
