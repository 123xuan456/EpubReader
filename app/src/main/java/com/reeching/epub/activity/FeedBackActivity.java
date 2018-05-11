package com.reeching.epub.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.koolearn.android.util.LogUtils;
import com.koolearn.android.util.SharedPreferencesUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.reeching.epub.R;
import com.reeching.epub.utils.ToastUtil;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

import static com.reeching.epub.constant.Constant.ADDSUGGEST;
import static com.reeching.epub.constant.Constant.LOGIN_ID;
import static com.reeching.epub.utils.AppValidationUtil.isEmpty;

/**
 * 意见反馈
 */
public class FeedBackActivity extends Activity {

    @Bind(R.id.top_finish)
    ImageView topFinish;
    @Bind(R.id.top_bookname)
    TextView topBookname;
    @Bind(R.id.top_search)
    ImageView topSearch;
    @Bind(R.id.top_books)
    ImageView topBooks;
    @Bind(R.id.feedBack)
    EditText feedBack;
    @Bind(R.id.btn_suggest)
    Button btn_suggest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_back);
        ButterKnife.bind(this);
        topSearch.setVisibility(View.GONE);
        topBookname.setText(R.string.feedback);
    }

    @OnClick({R.id.top_finish, R.id.btn_suggest})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.top_finish:
                finish();
                break;
            case R.id.btn_suggest:
                String content = feedBack.getText().toString();
                if (!isEmpty(content)){
                    OkGo.post(ADDSUGGEST).tag(this)
                            .params("memberId", SharedPreferencesUtil.getInstance().getString(LOGIN_ID))
                            .params("content",content)
                            .execute(new StringCallback() {
                                @Override
                                public void onSuccess(String s, Call call, Response response) {
                                    try {
                                        JSONObject jsonObject=new JSONObject(s);
                                        String msg = jsonObject.getString("msg");
                                        if (msg.equals("保存成功")){
                                            ToastUtil.showToast(FeedBackActivity.this,"提交成功");
                                            finish();
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    LogUtils.i(s);
                                }
                            });
                }else {
                    ToastUtil.showToast(FeedBackActivity.this,"请输入内容");
                }
                break;
        }
    }
}
