package com.smart.urban.ui;

import android.os.Bundle;

import com.amap.api.navi.AMapNaviView;
import com.amap.api.navi.AMapNaviViewOptions;
import com.amap.api.navi.enums.NaviType;
import com.amap.api.navi.model.NaviLatLng;
import com.blankj.utilcode.util.ToastUtils;
import com.smart.urban.R;
import com.smart.urban.base.BaseLocationActivity;
import com.smart.urban.bean.LocationListBean;
import com.smart.urban.present.LocationPresent;
import com.smart.urban.utils.SharedPreferencesUtils;
import com.smart.urban.view.ILocationView;

import java.util.ArrayList;
import java.util.List;

/**
 * 驾车单路线导航
 */
public class SingleRouteCalculateActivity extends BaseLocationActivity implements ILocationView {
    LocationPresent presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_basic_navi);
        mAMapNaviView = (AMapNaviView) findViewById(R.id.navi_view);
        mAMapNaviView.onCreate(savedInstanceState);
        mAMapNaviView.setAMapNaviViewListener(this);

        AMapNaviViewOptions options = new AMapNaviViewOptions();
        options.setTilt(0);
        mAMapNaviView.setViewOptions(options);

        presenter = new LocationPresent(this, this);
        presenter.initMap(mAMapNaviView);
    }

    @Override
    public void onInitNaviSuccess() {
        super.onInitNaviSuccess();
        /**
         * 方法: int strategy=mAMapNavi.strategyConvert(congestion, avoidhightspeed, cost, hightspeed, multipleroute); 参数:
         *
         * @congestion 躲避拥堵
         * @avoidhightspeed 不走高速
         * @cost 避免收费
         * @hightspeed 高速优先
         * @multipleroute 多路径
         *
         *  说明: 以上参数都是boolean类型，其中multipleroute参数表示是否多条路线，如果为true则此策略会算出多条路线。
         *  注意: 不走高速与高速优先不能同时为true 高速优先与避免收费不能同时为true
         */
        List<NaviLatLng> start = new ArrayList<>();
        List<NaviLatLng> end = new ArrayList<>();
        //起点
        NaviLatLng startLng = new NaviLatLng(Double.valueOf(SharedPreferencesUtils.init(this).getString("start_lat")),
                Double.valueOf(SharedPreferencesUtils.init(this).getString("start_lon")));
        start.add(startLng);
        //终点
        NaviLatLng endLng = new NaviLatLng(Double.valueOf(Double.valueOf(SharedPreferencesUtils.init(this).getString("end_lat"))),
                Double.valueOf(SharedPreferencesUtils.init(this).getString("end_lon")));
        end.add(endLng);

        int strategy = 0;
        try {
            //再次强调，最后一个参数为true时代表多路径，否则代表单路径
            strategy = mAMapNavi.strategyConvert(true, false, false, false, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mAMapNavi.calculateDriveRoute(start, end, mWayPointList, strategy);
    }

    @Override
    public void onCalculateRouteSuccess(int[] ids) {
        super.onCalculateRouteSuccess(ids);
        mAMapNavi.startNavi(NaviType.GPS);
    }

    @Override
    public void onCalculateRouteFailure(int errorInfo) {
        super.onCalculateRouteFailure(errorInfo);
        ToastUtils.showShort("路径规划失败");
    }

    @Override
    public void onLocationList(List<LocationListBean> beans, boolean state) {

    }

    @Override
    public void onLocationView() {

    }
}
