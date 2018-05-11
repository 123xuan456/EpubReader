package com.reeching.epub.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

import static com.reeching.epub.constant.Constant.LOGIN_ID;
import static com.reeching.epub.constant.Constant.LOGIN_PASSWORD;
import static com.reeching.epub.constant.Constant.UPDATEMEMBERPASSWORD;

/**
 * 修改密码
 */
public class ChangePasswdActivity extends AppCompatActivity {

    @Bind(R.id.top_finish)
    ImageView topFinish;
    @Bind(R.id.top_bookname)
    TextView topBookname;
    @Bind(R.id.top_search)
    ImageView topSearch;
    @Bind(R.id.top_books)
    ImageView topBooks;
    @Bind(R.id.textView58)
    TextView textView58;
    @Bind(R.id.old_pas)
    EditText oldPas;
    @Bind(R.id.textView)
    TextView textView;
    @Bind(R.id.new_pas)
    EditText newPas;
    @Bind(R.id.change)
    Button change;
    private String passWord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_passwd);
        ButterKnife.bind(this);
        topBookname.setText(R.string.change_passwd);
        topSearch.setVisibility(View.GONE);
    }

    @OnClick({R.id.top_finish, R.id.change})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.top_finish:
                finish();
                break;
            case R.id.change:
                change();

                break;
        }
    }
    private boolean isPasswordValid(String password) {
        return password.length() >= 6;
    }
    private void change() {
        passWord= SharedPreferencesUtil.getInstance().getString(LOGIN_PASSWORD,"");
        String id = SharedPreferencesUtil.getInstance().getString(LOGIN_ID, "");
        final String news = newPas.getText().toString();
        String oldp = oldPas.getText().toString();
        if (TextUtils.isEmpty(oldp)) {
            Toast.makeText(this, "请输入原密码", Toast.LENGTH_SHORT).show();
            return;
        }else
        if (!passWord.equals(oldp)) {
            Toast.makeText(this, "原密码不正确", Toast.LENGTH_SHORT).show();
            return;
        }else
        if (TextUtils.isEmpty(news)) {
            Toast.makeText(this, "请输入新密码", Toast.LENGTH_SHORT).show();
            return;
        }else
        if (!isPasswordValid(news)) {
            Toast.makeText(this, "密码不能低于6位", Toast.LENGTH_SHORT).show();
            return;
        }else{
            OkGo.post(UPDATEMEMBERPASSWORD).tag(this).
                    params("id",id).params("password",news).
                    execute(new StringCallback() {
                @Override
                public void onSuccess(String s, Call call, Response response) {
                    LogUtils.i(s);
                    try {
                        JSONObject jsonObject=new JSONObject(s);
                        String msg = jsonObject.getString("msg");
                        String result = jsonObject.getString("result");
                        if (result.equals("1")){
                            SharedPreferencesUtil.getInstance().putString(LOGIN_PASSWORD, news);
                            finish();
                        }
                        ToastUtil.showToast(ChangePasswdActivity.this,msg);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                        @Override
                        public void onError(Call call, Response response, Exception e) {
                            super.onError(call, response, e);
                        }
                    });
        }


    }
}
