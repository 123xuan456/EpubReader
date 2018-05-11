package com.reeching.epub.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.koolearn.android.util.LogUtils;
import com.koolearn.android.util.SharedPreferencesUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.reeching.epub.R;
import com.reeching.epub.bean.LoginBean;
import com.reeching.epub.utils.ToastUtil;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

import static com.reeching.epub.R.id.et_mobile;
import static com.reeching.epub.R.id.et_password;
import static com.reeching.epub.constant.Constant.LOGIN;
import static com.reeching.epub.constant.Constant.LOGIN_ID;
import static com.reeching.epub.constant.Constant.LOGIN_LOGINNAME;
import static com.reeching.epub.constant.Constant.LOGIN_NAME;
import static com.reeching.epub.constant.Constant.LOGIN_NICKNAME;
import static com.reeching.epub.constant.Constant.LOGIN_OFFICERPHOTO;
import static com.reeching.epub.constant.Constant.LOGIN_PASSWORD;
import static com.reeching.epub.constant.Constant.LOGIN_PHONENUMBER;
import static com.reeching.epub.constant.Constant.LOGIN_PHOTO;
import static com.reeching.epub.constant.Constant.LOGIN_SEX;
import static com.reeching.epub.utils.ActivityUtil.openAct;
import static com.reeching.epub.utils.AppValidationUtil.isEmpty;


/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends Activity {

    @Bind(et_mobile)
    EditText etMobile;
    @Bind(R.id.iv_clean_phone)
    ImageView ivCleanPhone;
    @Bind(et_password)
    EditText etPassword;
    @Bind(R.id.clean_password)
    ImageView cleanPassword;
    @Bind(R.id.iv_show_pwd)
    ImageView ivShowPwd;
    @Bind(R.id.btn_login)
    Button btnLogin;
    @Bind(R.id.regist)
    TextView regist;
    @Bind(R.id.forget_password)
    TextView forgetPassword;
    @Bind(R.id.content)
    LinearLayout content;
    @Bind(R.id.container)
    RelativeLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        initListener();
        etMobile.setText(SharedPreferencesUtil.getInstance().getString(LOGIN_LOGINNAME));
        etPassword.setText(SharedPreferencesUtil.getInstance().getString(LOGIN_PASSWORD));
    }

    private void initListener() {
        etMobile.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s) && ivCleanPhone.getVisibility() == View.GONE) {
                    ivCleanPhone.setVisibility(View.VISIBLE);
                } else if (TextUtils.isEmpty(s)) {
                    ivCleanPhone.setVisibility(View.GONE);
                }
            }
        });
        etPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s) && cleanPassword.getVisibility() == View.GONE) {
                    cleanPassword.setVisibility(View.VISIBLE);
                } else if (TextUtils.isEmpty(s)) {
                    cleanPassword.setVisibility(View.GONE);
                }
                if (s.toString().isEmpty())
                    return;
                if (!s.toString().matches("[A-Za-z0-9]+")) {
                    String temp = s.toString();
                    Toast.makeText(LoginActivity.this, R.string.please_input_limit_pwd, Toast.LENGTH_SHORT).show();
                    s.delete(temp.length() - 1, temp.length());
                    etPassword.setSelection(s.length());
                }
            }
        });

    }

    private void login() {
        final String phone = etMobile.getText().toString();
        String password = etPassword.getText().toString();
        if (isEmpty(phone)) {
            Toast.makeText(this, "手机号不能为空", Toast.LENGTH_SHORT).show();
            return;
        } else if (isEmpty(password)) {
            Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        } else {
            OkGo.post(LOGIN).tag(this)
                    .params("loginName", phone)
                    .params("password", password)
                    .execute(new StringCallback() {
                        @Override
                        public void onSuccess(String s, Call call, Response response) {
                            Gson gson = new Gson();
                            LogUtils.i(s);
                            LoginBean l = gson.fromJson(s, LoginBean.class);
                            if (l.getResult().equals("1")) {

                                LoginBean.InfosBean info = l.getInfos();
                                //登录成功获取的手机号
                                String info_phone = info.getPhone();
                                String photo = info.getPhoto();
                                String officerphoto = info.getOfficerPhoto();
                                if (!TextUtils.isEmpty(photo)){
                                    photo = photo.substring(1, photo.length());
                                }
                                if (!TextUtils.isEmpty(officerphoto)){
                                    officerphoto = officerphoto.substring(1, officerphoto.length());
                                }
                                SharedPreferencesUtil.getInstance().putString(LOGIN_ID, info.getId());
                                SharedPreferencesUtil.getInstance().putString(LOGIN_NICKNAME, info.getNickname());
                                SharedPreferencesUtil.getInstance().putString(LOGIN_LOGINNAME, info.getLoginName());
                                SharedPreferencesUtil.getInstance().putString(LOGIN_NAME, info.getName());
                                SharedPreferencesUtil.getInstance().putString(LOGIN_PASSWORD, info.getPassword());
                                SharedPreferencesUtil.getInstance().putString(LOGIN_PHONENUMBER, info_phone);
                                SharedPreferencesUtil.getInstance().putString(LOGIN_PHOTO, photo);
                                SharedPreferencesUtil.getInstance().putString(LOGIN_OFFICERPHOTO, officerphoto);
                                SharedPreferencesUtil.getInstance().putString(LOGIN_SEX, info.getSex());
                                finish();
                            }
                            ToastUtil.showToast(getApplicationContext(), l.getMsg());
                        }

                        @Override
                        public void onError(Call call, Response response, Exception e) {
                            super.onError(call, response, e);
                            ToastUtil.showToast(LoginActivity.this,"网络错误");
                        }
                    });
        }
    }

    @OnClick({ R.id.btn_login,R.id.regist,R.id.iv_show_pwd,R.id.iv_clean_phone,R.id.clean_password})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                login();
                break;
            case R.id.regist:
                openAct(LoginActivity.this, RegisterActivity.class);
                break;
            case R.id.iv_show_pwd:
                if (etPassword.getInputType() != InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
                    etPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    ivShowPwd.setImageResource(R.drawable.pass_visuable);
                } else {
                    etPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    ivShowPwd.setImageResource(R.drawable.pass_gone);
                }
                String pwd = etPassword.getText().toString();
                if (!TextUtils.isEmpty(pwd))
                    etPassword.setSelection(pwd.length());
                break;
            case R.id.iv_clean_phone:
                etMobile.setText("");
                break;
            case R.id.clean_password:
                etPassword.setText("");
                break;
        }
    }
}

