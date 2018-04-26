package com.smart.urban.ui;

import android.os.Bundle;

import com.amap.api.navi.AMapNaviView;
import com.amap.api.navi.enums.NaviType;
import com.amap.api.navi.model.NaviLatLng;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.smart.urban.R;
import com.smart.urban.base.BaseLocationActivity;
import com.smart.urban.bean.LocationListBean;
import com.smart.urban.present.LocationPresent;
import com.smart.urban.utils.SharedPreferencesUtils;
import com.smart.urban.view.ILocationView;

import java.util.List;

/**
 * 步行导航
 */
public class WalkRouteCalculateActivity extends BaseLocationActivity implements ILocationView {
    LocationPresent presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basic_navi);
        mAMapNaviView = (AMapNaviView) findViewById(R.id.navi_view);
        mAMapNaviView.onCreate(savedInstanceState);
        mAMapNaviView.setAMapNaviViewListener(this);
        mAMapNaviView.setNaviMode(AMapNaviView.NORTH_UP_MODE);
        presenter = new LocationPresent(this, this);
        presenter.initMap(mAMapNaviView);
    }

    @Override
    public void onInitNaviSuccess() {
        super.onInitNaviSuccess();
        LogUtils.i("计算驾车规划路线");
        //起点
        NaviLatLng startLng = new NaviLatLng(Double.valueOf(SharedPreferencesUtils.init(this).getString("start_lat")),
                Double.valueOf(SharedPreferencesUtils.init(this).getString("start_lon")));
        //终点
        NaviLatLng endLng = new NaviLatLng(Double.valueOf(Double.valueOf(SharedPreferencesUtils.init(this).getString("end_lat"))),
                Double.valueOf(SharedPreferencesUtils.init(this).getString("end_lon")));
        mAMapNavi.calculateWalkRoute(startLng, endLng);

    }

    @Override
    public void onCalculateRouteSuccess(int[] ids) {
        super.onCalculateRouteSuccess(ids);
        mAMapNavi.startNavi(NaviType.GPS);
        LogUtils.i("显示路径或开启导航");
    }

    @Override
    public void onCalculateRouteFailure(int errorInfo) {
        super.onCalculateRouteFailure(errorInfo);
        switch (errorInfo) {
            case 20:
                ToastUtils.showShort("起点/终点/途经点的距离太长(距离＞6000km),建议驾车前往");
                break;
        }
    }

    @Override
    public void onLocationList(List<LocationListBean> beans, boolean state) {

    }

    @Override
    public void onLocationView() {

    }
}
