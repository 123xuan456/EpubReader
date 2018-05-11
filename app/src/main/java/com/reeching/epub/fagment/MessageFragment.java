package com.reeching.epub.fagment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.reeching.epub.R;
import com.reeching.epub.adapter.NewsFragmentPagerAdapter;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * 消息页面
 */
public class MessageFragment extends Fragment implements View.OnClickListener{
    private View view=null;
    private ViewPager mViewPager;
    private TextView view1;
    private TextView view2;
    private View flag0;
    private View flag1;
    private int mCurrentOption = 0;
    private ArrayList fragments;
    public static Fragment newInstance() {
        MessageFragment f = new MessageFragment();
        return f;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view==null){
            view=inflater.inflate(R.layout.fragment_message, null);
            mViewPager=(ViewPager)view.findViewById(R.id.vpNewsList);
            initViewPager();
            initView();
        }
        return view;
    }

    private void initView() {
        view1 = (TextView) view.findViewById(R.id.tv_hot);
        view2 = (TextView) view.findViewById(R.id.tv_news);
        flag0 = view.findViewById(R.id.activity_order_flag_all);
        flag1 = view.findViewById(R.id.activity_order_flag_uncomplete);
        view1.setOnClickListener(this);
        view2.setOnClickListener(this);
    }

    private void initViewPager() {
        fragments = new ArrayList<Fragment>();
        Fragment fragmentHot = new MessageInformFragment(); //通知
        Fragment fragmentNews = new MessageNewsFragment();//公告
        fragments.add(fragmentHot);
        fragments.add(fragmentNews);
        mViewPager.setAdapter(new NewsFragmentPagerAdapter(getChildFragmentManager(), fragments));
        mViewPager.setCurrentItem(0);
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i2) {
            }
            @Override
            public void onPageSelected(int i) {
                switch (i){
                    case 0:
                        flag0.setVisibility(View.VISIBLE);
                        flag1.setVisibility(View.INVISIBLE);
                        view1.setTextColor(getResources().getColor(R.color.colorPrimary));
                        view2.setTextColor(getResources().getColor(R.color.colorBlack));
                        break;
                    case 1:
                        flag0.setVisibility(View.INVISIBLE);
                        flag1.setVisibility(View.VISIBLE);
                        view1.setTextColor(getResources().getColor(R.color.colorBlack));
                        view2.setTextColor(getResources().getColor(R.color.colorPrimary));
                        break;
                    default:
                        return;
                }
            }
            @Override
            public void onPageScrollStateChanged(int i) {
            }
        });

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_hot:
                if(mCurrentOption == 0) return;
                mCurrentOption = 0;
                flag0.setVisibility(View.VISIBLE);
                flag1.setVisibility(View.INVISIBLE);
                mViewPager.setCurrentItem(mCurrentOption);
                view1.setTextColor(getResources().getColor(R.color.colorPrimary));
                view2.setTextColor(getResources().getColor(R.color.colorBlack));
                break;
            case R.id.tv_news:
                if(mCurrentOption == 1) return;
                mCurrentOption = 1;
                flag0.setVisibility(View.INVISIBLE);
                flag1.setVisibility(View.VISIBLE);
                mViewPager.setCurrentItem(mCurrentOption);
                view1.setTextColor(getResources().getColor(R.color.colorBlack));
                view2.setTextColor(getResources().getColor(R.color.colorPrimary));
                break;

            default:
                return;
        }
    }
}
