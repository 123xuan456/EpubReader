package com.reeching.epub.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.koolearn.android.util.LogUtils;
import com.koolearn.android.util.SharedPreferencesUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.reeching.epub.R;
import com.reeching.epub.adapter.SpinnersAdapter;
import com.reeching.epub.base.BaseBookActivity;
import com.reeching.epub.bean.DescriptionBean;
import com.reeching.epub.bean.LoginBean;
import com.reeching.epub.constant.Constant;
import com.reeching.epub.utils.FileUtil;
import com.reeching.epub.utils.GlideUtils;
import com.reeching.epub.utils.ToastUtil;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import okhttp3.Response;

import static com.reeching.epub.R.id.radio_man;
import static com.reeching.epub.R.id.radio_woman;
import static com.reeching.epub.constant.Constant.DEVICEID;
import static com.reeching.epub.constant.Constant.GETSELECTLIST;
import static com.reeching.epub.constant.Constant.LOGIN_ID;
import static com.reeching.epub.constant.Constant.LOGIN_LOGINNAME;
import static com.reeching.epub.constant.Constant.LOGIN_NAME;
import static com.reeching.epub.constant.Constant.LOGIN_NICKNAME;
import static com.reeching.epub.constant.Constant.LOGIN_OFFICERPHOTO;
import static com.reeching.epub.constant.Constant.LOGIN_PASSWORD;
import static com.reeching.epub.constant.Constant.LOGIN_PHONENUMBER;
import static com.reeching.epub.constant.Constant.LOGIN_PHOTO;
import static com.reeching.epub.constant.Constant.LOGIN_SEX;
import static com.reeching.epub.constant.Constant.REGISTERMEMBER;
import static com.reeching.epub.constant.Constant.SERVIECE_DOWNLOND;
import static com.reeching.epub.utils.AppValidationUtil.isEmpty;
import static com.reeching.epub.utils.AppValidationUtil.isPhone;
import static com.reeching.epub.utils.FileUtil.createFile;
import static com.reeching.epub.utils.FileUtil.getExternalFilesDirPath;


/**
 * 注册/修改
 */
public class RegisterActivity extends BaseBookActivity {

    private final static int ALBUM = 1;
    private final static int PHOTO = 2;
    private final static int OVER = 3;
    @Bind(R.id.top_search)
    ImageView top_search;
    @Bind(R.id.top_bookname)
    TextView topBookname;
    @Bind(R.id.view)
    CircleImageView view;
    @Bind(R.id.register_head)
    RelativeLayout registerHead;
    @Bind(R.id.textView5)
    TextView textView5;
    @Bind(R.id.radioGroup2)
    RadioGroup radioGroup2;
    @Bind(R.id.register_sex)
    RelativeLayout registerSex;
    @Bind(R.id.textView)
    TextView textView;
    @Bind(R.id.name)
    EditText name1;
    @Bind(R.id.textView39)
    TextView textView39;
    @Bind(R.id.editText6)
    EditText editText6;
    @Bind(R.id.textView40)
    TextView textView40;
    @Bind(R.id.editText7)
    EditText editText7;
    @Bind(R.id.textView42)
    TextView textView42;
    @Bind(R.id.relativeLayout7)
    RelativeLayout relativeLayout7;
    @Bind(R.id.textView43)
    TextView textView43;
    @Bind(R.id.relativeLayout8)
    RelativeLayout relativeLayout8;
    @Bind(R.id.editText9)
    EditText editText9;
    @Bind(R.id.editText10)
    EditText editText10;
    @Bind(R.id.checkBox)
    CheckBox checkBox;
    @Bind(R.id.relativeLayout81)
    RelativeLayout relativeLayout81;
    @Bind(R.id.button3)
    Button button3;
    @Bind(R.id.radio_man)
    RadioButton radioMan;
    @Bind(R.id.radio_woman)
    RadioButton radioWoman;
    @Bind(R.id.company)
    TextView company;
    @Bind(R.id.companyName)
    EditText companyName;
    @Bind(R.id.company1)
    TextView company1;
    @Bind(R.id.companySite)
    EditText companySite;
    @Bind(R.id.n)
    TextView n;
    @Bind(R.id.number)
    EditText number;
    @Bind(R.id.LoginName)
    EditText LoginName;
    @Bind(R.id.t)
    TextView t;
    @Bind(R.id.grade)
    Spinner grade;
    @Bind(R.id.t1)
    TextView t1;
    @Bind(R.id.card)
    ImageView card;
    @Bind(R.id.rl_card)
    RelativeLayout rlCard;

    private LinearLayout ll_popup;
    private PopupWindow pop;
    private String gender;
    private boolean isHidden = true;
    private File file;
    private String path1;//头像路径
    private String path;//证件图片路径
    private String name;//
    private String device;
    private String deviceId;
    private String nickName;
    private String passWord;
    private String phone;
    private String photo;
    private String sex;
    private String user_Id;
    private String password;
    private String passwor;
    //头像路径
    public static String SD_HEAD;
    //证件照
    public static String SD_CARD;
    private String gradeValue;
    private File cardFile;
    private boolean isCard;

    @Override
    public int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    public void setview() {
        nickName = SharedPreferencesUtil.getInstance().getString(LOGIN_NICKNAME, "");
        user_Id = SharedPreferencesUtil.getInstance().getString(LOGIN_ID, "");
        passWord = SharedPreferencesUtil.getInstance().getString(LOGIN_PASSWORD, "");
        phone = SharedPreferencesUtil.getInstance().getString(LOGIN_PHONENUMBER, "");
        photo = SharedPreferencesUtil.getInstance().getString(LOGIN_PHOTO, "");
        sex = SharedPreferencesUtil.getInstance().getString(LOGIN_SEX, "");
        name = SharedPreferencesUtil.getInstance().getString(LOGIN_NAME, "");
        if (user_Id.equals("")) {
            topBookname.setText(R.string.register_top);
            button3.setText(R.string.register_top);
            editText7.setEnabled(true);
        } else {
            //修改页面
            topBookname.setText(R.string.revise_top);
            button3.setText(R.string.revise_btn);
            name1.setText(name);
            editText6.setText(nickName);
            editText7.setText(phone);
            editText7.setEnabled(false);
            String photoUrl = SERVIECE_DOWNLOND + photo;
            LogUtils.i(sex);
            GlideUtils.loadImageViewError(this, photoUrl, view, R.mipmap.a);
            if (sex.equals("1")) {
                radioMan.setChecked(true);
            } else {
                radioWoman.setChecked(true);
            }
            //在修改页面时，将修改密码隐藏
            relativeLayout81.setVisibility(View.GONE);
            password = passWord;
            passwor = passWord;
        }

        top_search.setVisibility(View.GONE);
        deviceId = SharedPreferencesUtil.getInstance().getString(DEVICEID);
        device = SharedPreferencesUtil.getInstance().getString(Constant.DEVICE);
        gender = "1";
        SD_HEAD = getExternalFilesDirPath(this) + "Head/h148944erw48r9wp.png";
        SD_CARD = getExternalFilesDirPath(this) + "card.png";
        file = createFile(SD_HEAD);
        cardFile = createFile(SD_CARD);


    }

    @Override
    public void setData() {
        OkGo.get(GETSELECTLIST).tag(this)
                .params("type", "titleName")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(String s, Call call, Response response) {
                        Gson gson = new Gson();
                        DescriptionBean descriptionBean = gson.fromJson(s, DescriptionBean.class);
                        if (descriptionBean.getResult().equals("1")) {
                            SpinnersAdapter spAdapter = new SpinnersAdapter(RegisterActivity.this, descriptionBean.getInfos());
                            grade.setAdapter(spAdapter);
                            grade.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    DescriptionBean.InfosBean info = (DescriptionBean.InfosBean) grade.getSelectedItem();
                                    gradeValue = info.getValue();
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });

                        }
                    }
                });


    }


    @OnClick({R.id.register_head, R.id.button3, R.id.checkBox, radio_man, radio_woman, R.id.top_finish, R.id.rl_card})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.register_head:
                backgroundAlpha(0.5f);
                isCard = false;
                initPopu();
                break;
            case R.id.rl_card:
                backgroundAlpha(0.5f);
                isCard = true;
                initPopu();
                break;
            case radio_woman:
                gender = "2";
                break;
            case radio_man:
                gender = "1";
                break;
            case R.id.top_finish:
                finish();
                break;
            case R.id.checkBox:
                if (isHidden) {
                    //设置EditText文本为可见的
                    editText9.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    editText10.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    //设置EditText文本为隐藏的
                    editText9.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    editText10.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                isHidden = !isHidden;
                editText9.postInvalidate();
                editText10.postInvalidate();
                //切换后将EditText光标置于末尾
                CharSequence charSequence = editText10.getText();
                if (charSequence instanceof Spannable) {
                    Spannable spanText = (Spannable) charSequence;
                    Selection.setSelection(spanText, charSequence.length());
                }
                CharSequence charSequence1 = editText9.getText();
                if (charSequence1 instanceof Spannable) {
                    Spannable spanText = (Spannable) charSequence1;
                    Selection.setSelection(spanText, charSequence1.length());
                }
                break;
            case R.id.button3:
                register();
                break;
        }
    }

    //屏幕颜色变暗
    private void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getWindow().setAttributes(lp);
    }


    private void register() {
        final String nickname1 = editText6.getText().toString();
        final String phone1 = editText7.getText().toString();
        final String name = name1.getText().toString();
        final String companyName1 = companyName.getText().toString();
        final String companySite1 = companySite.getText().toString();
        final String number1 = number.getText().toString();
        final String LoginName1 = LoginName.getText().toString();
        //注册页面
        if (user_Id.equals("")) {
            password = editText9.getText().toString();
            passwor = editText10.getText().toString();
        }
        if (isEmpty(nickname1)) {
            Toast.makeText(this, "昵称不能为空", Toast.LENGTH_SHORT).show();
            return;
        } else if (isEmpty(name)) {
            Toast.makeText(this, "姓名不能为空", Toast.LENGTH_SHORT).show();
            return;
        } else if (isEmpty(LoginName1)) {
            Toast.makeText(this, "登录名不能为空", Toast.LENGTH_SHORT).show();
            return;
        } else if (isEmpty(path1)) {
            Toast.makeText(this, "选择头像", Toast.LENGTH_SHORT).show();
            return;
        } else if (isEmpty(path)) {
            Toast.makeText(this, "选择照片", Toast.LENGTH_SHORT).show();
            return;
        } else if (isEmpty(gender)) {//
            Toast.makeText(this, "性别不能为空", Toast.LENGTH_SHORT).show();
            return;
        } else if (isEmpty(phone1)) {
            Toast.makeText(this, "手机号不能为空", Toast.LENGTH_SHORT).show();
            return;
        } else if (!isPhone(phone1)) {//设置手机格式
            Toast.makeText(this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(companyName1)) {
            Toast.makeText(this, "单位名称不能为空", Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(companySite1)) {
            Toast.makeText(this, "单位地址不能为空", Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(number1)) {
            Toast.makeText(this, "军官号码不能为空", Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(gradeValue)) {
            Toast.makeText(this, "头衔不能为空", Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        } else if (!password.equals(passwor)) {
            Toast.makeText(this, "两次密码不相同", Toast.LENGTH_SHORT).show();
            return;
        } else if (!isPasswordValid(password)) {//设置密码必须不少于6个
            Toast.makeText(this, "密码不能低于六位", Toast.LENGTH_SHORT).show();
            return;
        } else {
            LogUtils.i(phone1 + "/" + password + "/" + nickname1 + "/" + gender + "/" + path1 + "/" + device + "/" + deviceId + "/" + file.getPath());

            OkGo.post(REGISTERMEMBER).tag(this)
                    .params("id", user_Id)//有数据为修改，没数据注册
                    .params("phone", phone1)
                    .params("password", password)
                    .params("nickname", nickname1)
                    .params("loginName", LoginName1)
                    .params("sex", gender)
                    .params("unitName", companyName1)
                    .params("unitAdddress", companySite1)
                    .params("officerNumber", number1)
                    .params("officerTitle", gradeValue)
                    .params("device", device)
                    .params("deviceId", deviceId)
                    .params("postsPic", file)
                    .params("cardFile", cardFile)
                    .params("photo", path)
                    .params("photo1", path1)
                    .params("name", name)
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
                        }
                    });

        }
    }

    private boolean isPasswordValid(String password) {
        return password.length() >= 6;
    }

    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        switch (requestCode) {
            case ALBUM:
                //相册
                if (resultCode == RESULT_OK) {
                    cropPhoto(data.getData());// 裁剪图片
                }

                break;
            case PHOTO:
                //拍照
                if (resultCode == RESULT_OK) {
                    cropPhoto(Uri.fromFile(file));// 裁剪图片
                }
                break;
            case OVER:
                //裁剪完
                if (data != null) {
                    Bundle extras = data.getExtras();
                    if (extras != null) {
                        Bitmap headBitmap = extras.getParcelable("data");
                        if (headBitmap != null) {
                            if (isCard) {
//                                card.setImageBitmap(headBitmap);// 用ImageView显示出来
                                //讲证件图片放在sd中
                                path = FileUtil.saveBitmap(SD_CARD, headBitmap);
                                GlideUtils.loadImageViewCache(this,path,card);
                            } else {
//                                view.setImageBitmap(headBitmap);// 用ImageView显示出来
                                //讲图片放在sd中
                                path1 = FileUtil.saveBitmap(SD_HEAD, headBitmap);
                                GlideUtils.loadImageViewCache(this,path1,view);
                                LogUtils.i(path1);
                            }
                        }
                    } else {
                        ToastUtil.showToast(RegisterActivity.this, "没裁剪完成");
                    }

                }
        }

    }

    /**
     * 调用系统的裁剪
     *
     * @param uri
     */
    public void cropPhoto(Uri uri) {
        PackageManager packageManager = this.getPackageManager();
        if (packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            Intent intent = new Intent("com.android.camera.action.CROP");
            intent.setDataAndType(uri, "image/*");
            intent.putExtra("crop", "true");
            if (isCard) {
                // outputX outputY 是裁剪图片宽高
                intent.putExtra("outputX", 358);
                intent.putExtra("outputY", 441);
            } else {
                // aspectX aspectY 是宽高的比例
                intent.putExtra("aspectX", 1);
                intent.putExtra("aspectY", 1);
                intent.putExtra("outputX", 250);
                intent.putExtra("outputY", 250);
            }
            intent.putExtra("return-data", true);
            intent.putExtra("uri", uri.toString());
            startActivityForResult(intent, OVER);
        }
    }

    private void initPopu() {
        pop = new PopupWindow(RegisterActivity.this);
        View view = getLayoutInflater().inflate(R.layout.item_popupwindows, null);
        ll_popup = (LinearLayout) view.findViewById(R.id.ll_popup);
        pop.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        pop.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        pop.setBackgroundDrawable(new BitmapDrawable());
        pop.setFocusable(true);
        pop.setOutsideTouchable(true);
        pop.setContentView(view);
        RelativeLayout parent = (RelativeLayout) view.findViewById(R.id.parent);
        Button bt1 = (Button) view.findViewById(R.id.item_popupwindows_camera);
        Button bt2 = (Button) view.findViewById(R.id.item_popupwindows_Photo);
        Button bt3 = (Button) view.findViewById(R.id.item_popupwindows_cancel);
        parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pop.dismiss();
                ll_popup.clearAnimation();
            }
        });
        bt1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                photo();
                pop.dismiss();
                ll_popup.clearAnimation();
            }
        });
        bt2.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.ECLAIR)
            public void onClick(View v) {
                Intent intent1 = new Intent(Intent.ACTION_PICK, null);
                intent1.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(intent1, ALBUM);
                pop.dismiss();
                ll_popup.clearAnimation();
            }
        });
        bt3.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                pop.dismiss();
                ll_popup.clearAnimation();
            }
        });
        pop.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                backgroundAlpha(1f);
            }
        });
        ll_popup.startAnimation(AnimationUtils.loadAnimation(
                RegisterActivity.this, R.anim.activity_translate_in));
        pop.showAtLocation(relativeLayout81, Gravity.CENTER, 10, 10);
    }

    private void photo() {
        String SDState = Environment.getExternalStorageState();
        if (SDState.equals(Environment.MEDIA_MOUNTED)) {
            try {
                Intent intent2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent2.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(FileUtil.createFile(SD_HEAD)));
                startActivityForResult(intent2, PHOTO);
            } catch (Exception e) {
                LogUtils.i("异常" + e);
            }

        } else {
            Toast.makeText(getApplicationContext(), "内存卡不存在",
                    Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
