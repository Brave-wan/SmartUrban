package com.smart.urban.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.amap.api.navi.AMapNavi;
import com.amap.api.navi.AMapNaviView;
import com.amap.api.navi.AMapNaviViewOptions;
import com.amap.api.navi.enums.NaviType;
import com.smart.urban.R;
import com.smart.urban.base.BaseLocationActivity;
import com.smart.urban.present.LocationPresent;
import com.smart.urban.view.ILocationView;

/**
 * Created by root on 18-3-28.
 */

public class LocationActivity extends BaseLocationActivity implements ILocationView {
    AMapNavi mAMapNavi;
    LocationPresent presenter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        mAMapNaviView = (AMapNaviView) findViewById(R.id.mapView);
        mAMapNaviView.onCreate(savedInstanceState);// 此方法必须重写
        initView();

    }

    private void initView() {
        presenter = new LocationPresent(this, this);
        mAMapNavi = AMapNavi.getInstance(getApplicationContext());
        presenter.initMap(mAMapNaviView);
        mAMapNaviView.setAMapNaviViewListener(this);
        AMapNaviViewOptions options = new AMapNaviViewOptions();
        options.setTilt(0);
        mAMapNaviView.setViewOptions(options);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
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
        int strategy = 0;
        try {
            //再次强调，最后一个参数为true时代表多路径，否则代表单路径
            strategy = mAMapNavi.strategyConvert(true, false, false, false, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
     mAMapNavi.calculateDriveRoute(sList, eList, mWayPointList, strategy);
    }


    @Override
    public void onCalculateRouteSuccess(int[] ids) {
        super.onCalculateRouteSuccess(ids);
//        mAMapNavi.startNavi(NaviType.EMULATOR);
        mAMapNavi.startNavi(NaviType.GPS);
    }
}
