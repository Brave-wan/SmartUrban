package com.smart.urban.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.Guideline;
import android.view.View;
import android.widget.AdapterView;

import com.blankj.utilcode.util.ToastUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.smart.urban.R;
import com.smart.urban.base.BaseFragment;
import com.smart.urban.base.BasePresenter;
import com.smart.urban.bean.BannerBean;
import com.smart.urban.bean.DynamicListBean;
import com.smart.urban.bean.UrbanListBean;
import com.smart.urban.present.HomePresent;
import com.smart.urban.ui.BannerDetailActivity;
import com.smart.urban.ui.BannerDynamicDetailsActivity;
import com.smart.urban.ui.DynamicActivity;
import com.smart.urban.ui.GuideActivity;
import com.smart.urban.ui.InfoDetailsActivity;
import com.smart.urban.ui.LocationActivity;
import com.smart.urban.ui.LostFoundActivity;
import com.smart.urban.ui.LostListActivity;
import com.smart.urban.ui.MainActivity;
import com.smart.urban.ui.SingleRouteCalculateActivity;
import com.smart.urban.ui.UrbanActivity;
import com.smart.urban.ui.UrbanDetailsActivity;
import com.smart.urban.ui.adapter.DynamicListAdapter;
import com.smart.urban.ui.adapter.InfoListAdapter;
import com.smart.urban.ui.adapter.UrbanListAdapter;
import com.smart.urban.utils.GlideImageLoader;
import com.smart.urban.view.IHomeView;
import com.smart.urban.widget.MyListView;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by root on 18-3-29.
 */

public class HomeFragment extends BaseFragment<IHomeView, HomePresent> implements IHomeView, OnLoadmoreListener, AdapterView.OnItemClickListener, OnRefreshListener {
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.lv_main_list)
    MyListView lv_main_list;
    @BindView(R.id.smart_layout)
    SmartRefreshLayout smart_layout;
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
        presenter.getBannerList();
    }

    public void initBanner() {
        adapter = new UrbanListAdapter(getActivity(), R.layout.item_info_list, listBeans);
        lv_main_list.setAdapter(adapter);
        presenter.getForumList();
        lv_main_list.setOnItemClickListener(this);
        smart_layout.setOnRefreshListener(this);
        smart_layout.setOnLoadmoreListener(this);
    }

    @OnClick({R.id.tv_home_navigation, R.id.tv_home_guide, R.id.tv_home_lost_found,
            R.id.tv_home_dynamic, R.id.tv_home_camera, R.id.tv_dynamic_list})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_home_dynamic:
                startActivity(new Intent(getActivity(), DynamicActivity.class));
                break;
            //便民导航
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
        listBeans.clear();
        listBeans.addAll(data);
        adapter.setDataList(listBeans);
    }

    @Override
    public void onBannerList(final List<BannerBean> data) {
        list.clear();

        for (BannerBean bean : data) {
            if (bean.getImage() != null) {
                list.add(bean.getImage().getAddress());
            }
        }

        banner.setImages(list)
                .setDelayTime(3000)
                .setImageLoader(new GlideImageLoader())
                .setIndicatorGravity(BannerConfig.CENTER)
                .start();

        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                BannerBean bean = data.get(position);
                if (bean.getIsUrl().equals("Y")) {
                    Intent intent = new Intent(getActivity(), BannerDetailActivity.class);
                    intent.putExtra("url", bean.getUrlAddress());
                    startActivity(intent);
                } else {
                    //type=1 动态2是咨询
                    Intent intent = new Intent(getActivity(), bean.getArticleType().equals("1") ? UrbanDetailsActivity.class : InfoDetailsActivity.class);
                    intent.putExtra("id", bean.getArticleId() + "");
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        UrbanListBean bean = (UrbanListBean) adapter.getItem(position);
        Intent intent = new Intent(getActivity(), UrbanDetailsActivity.class);
        intent.putExtra("id", bean.getId() + "");
        startActivity(intent);
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        smart_layout.finishRefresh(1000);
        presenter.getBannerList();
        presenter.getForumList();

    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        smart_layout.finishLoadmore(1000);
        presenter.getBannerList();
        presenter.getForumList();
    }
}
