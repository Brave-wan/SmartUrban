package com.smart.urban.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.Guideline;
import android.view.View;

import com.smart.urban.R;
import com.smart.urban.base.BaseFragment;
import com.smart.urban.base.BasePresenter;
import com.smart.urban.bean.DynamicListBean;
import com.smart.urban.bean.UrbanListBean;
import com.smart.urban.present.HomePresent;
import com.smart.urban.ui.DynamicActivity;
import com.smart.urban.ui.GuideActivity;
import com.smart.urban.ui.LocationActivity;
import com.smart.urban.ui.LostFoundActivity;
import com.smart.urban.ui.LostListActivity;
import com.smart.urban.ui.MainActivity;
import com.smart.urban.ui.UrbanActivity;
import com.smart.urban.ui.adapter.DynamicListAdapter;
import com.smart.urban.ui.adapter.InfoListAdapter;
import com.smart.urban.ui.adapter.UrbanListAdapter;
import com.smart.urban.utils.GlideImageLoader;
import com.smart.urban.view.IHomeView;
import com.smart.urban.widget.MyListView;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by root on 18-3-29.
 */

public class HomeFragment extends BaseFragment<IHomeView, HomePresent> implements IHomeView {
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.lv_main_list)
    MyListView lv_main_list;
    MainActivity mainActivity;
    List<UrbanListBean> listBeans = new ArrayList<>();
    private List<String> list = new ArrayList<>();
    UrbanListAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        setTitle("智慧城管");
        initBanner();
    }

    public void initBanner() {
        list.clear();
        list.add("http://ww4.sinaimg.cn/large/006uZZy8jw1faic21363tj30ci08ct96.jpg");
        list.add("http://ww4.sinaimg.cn/large/006uZZy8jw1faic259ohaj30ci08c74r.jpg");
        list.add("http://ww4.sinaimg.cn/large/006uZZy8jw1faic2b16zuj30ci08cwf4.jpg");
        list.add("http://ww4.sinaimg.cn/large/006uZZy8jw1faic2e7vsaj30ci08cglz.jpg");
        banner.setImages(list)
                .setDelayTime(3000)
                .setImageLoader(new GlideImageLoader())
                .setIndicatorGravity(BannerConfig.CENTER)
                .start();
        adapter = new UrbanListAdapter(getActivity(), R.layout.item_info_list, listBeans);
        lv_main_list.setAdapter(adapter);
        presenter.getForumList();
    }

    @OnClick({R.id.tv_home_navigation, R.id.tv_home_guide, R.id.tv_home_lost_found,
            R.id.tv_home_dynamic, R.id.tv_home_camera, R.id.tv_dynamic_list})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_home_dynamic:
                startActivity(new Intent(getActivity(), DynamicActivity.class));
                break;
            case R.id.tv_home_navigation:
                startActivity(new Intent(getActivity(), LocationActivity.class));
                break;
            //办事指南
            case R.id.tv_home_guide:
                startActivity(new Intent(getActivity(), GuideActivity.class));
                break;
            //随手拍
            case R.id.tv_home_camera:
                mainActivity.getCamera();
                break;

            //失物招领
            case R.id.tv_home_lost_found:
                startActivity(new Intent(getActivity(), LostListActivity.class));
                break;
            //城管动态
            case R.id.tv_dynamic_list:
                startActivity(new Intent(getActivity(), UrbanActivity.class));
                break;
        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context != null) {
            mainActivity = (MainActivity) context;
        }
    }

    @Override
    public HomePresent initPresenter() {
        return new HomePresent(getActivity());
    }

    @Override
    public void onDynamicList(List<UrbanListBean> data) {
        listBeans.addAll(data);
        adapter.setDataList(listBeans);
    }
}
