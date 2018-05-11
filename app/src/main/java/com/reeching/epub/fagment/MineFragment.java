package com.reeching.epub.fagment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.koolearn.android.util.LogUtils;
import com.koolearn.android.util.SharedPreferencesUtil;
import com.reeching.epub.R;
import com.reeching.epub.activity.DownloadActivity;
import com.reeching.epub.activity.LoginActivity;
import com.reeching.epub.activity.MyBooksActivity;
import com.reeching.epub.activity.MyIntegralActivity;
import com.reeching.epub.activity.RegisterActivity;
import com.reeching.epub.activity.SettingActivity;
import com.reeching.epub.utils.GlideUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.reeching.epub.constant.Constant.LOGIN_ID;
import static com.reeching.epub.constant.Constant.LOGIN_NICKNAME;
import static com.reeching.epub.constant.Constant.LOGIN_PASSWORD;
import static com.reeching.epub.constant.Constant.LOGIN_PHONENUMBER;
import static com.reeching.epub.constant.Constant.LOGIN_PHOTO;
import static com.reeching.epub.constant.Constant.LOGIN_SEX;
import static com.reeching.epub.constant.Constant.SERVIECE_DOWNLOND;
import static com.reeching.epub.utils.ActivityUtil.openAct;


/**
 * A simple {@link Fragment} subclass.
 * 我的页面
 */
public class MineFragment extends Fragment {
    @Bind(R.id.circle)
    CircleImageView circle;
    @Bind(R.id.tv_login)
    TextView tv_login;
    @Bind(R.id.books)
    RelativeLayout books;
    @Bind(R.id.integral)
    RelativeLayout integral;
    @Bind(R.id.set)
    RelativeLayout set;
    @Bind(R.id.my_downlond)
    RelativeLayout my_downlond;
    private View view = null;
    private String nickName;
    private String passWord;
    private String phone;
    private String photo;
    private String sex;
    boolean isLogin=false;
    private String user_Id;


    public MineFragment() {
        // Required empty public constructor
    }

    public static Fragment newInstance() {
        MineFragment f = new MineFragment();
        return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (view == null) {
            view = inflater.inflate(R.layout.fragment_mine, null);
            ButterKnife.bind(this, view);
        }
        LogUtils.i("onCreateView");

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        LogUtils.i("onStart");
        nickName= SharedPreferencesUtil.getInstance().getString(LOGIN_NICKNAME,"");
        user_Id= SharedPreferencesUtil.getInstance().getString(LOGIN_ID,"");
        passWord=SharedPreferencesUtil.getInstance().getString(LOGIN_PASSWORD,"");
        phone=SharedPreferencesUtil.getInstance().getString(LOGIN_PHONENUMBER,"");
        photo=SharedPreferencesUtil.getInstance().getString(LOGIN_PHOTO,"");
        sex=SharedPreferencesUtil.getInstance().getString(LOGIN_SEX,"");

        if (!nickName.equals("")){
            tv_login.setText(nickName);
            isLogin=true;
            String photoUrl = SERVIECE_DOWNLOND + photo;
            GlideUtils.loadImageViewDiskCache(getActivity(),photoUrl,circle,R.mipmap.hand);
        }else {
            tv_login.setText(R.string.login_no);
            isLogin=false;
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        LogUtils.i("onResume");

    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @OnClick({R.id.circle, R.id.books, R.id.integral, R.id.set,R.id.my_downlond})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.circle:
                //已登录
                if (isLogin){
                    openAct(getActivity(), RegisterActivity.class);//注册/修改
                }else {
                    openAct(getActivity(), LoginActivity.class);
                }
                break;
            case R.id.books:
                openAct(getActivity(), MyBooksActivity.class);
                break;
            case R.id.integral:
                openAct(getActivity(), MyIntegralActivity.class);
                break;
            case R.id.set:
                openAct(getActivity(), SettingActivity.class);
                break;
            case R.id.my_downlond:
                openAct(getActivity(), DownloadActivity.class);
                break;
        }
    }


}
