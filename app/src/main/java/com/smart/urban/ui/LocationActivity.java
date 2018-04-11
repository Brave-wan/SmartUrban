package com.smart.urban.ui;

import android.os.Bundle;

import com.amap.api.maps.MapView;
import com.smart.urban.R;
import com.smart.urban.base.BaseActivity;
import com.smart.urban.present.LocationPresent;
import com.smart.urban.view.ILocationView;

import butterknife.BindView;

/**
 * Created by root on 18-3-28.
 */

public class LocationActivity extends BaseActivity<ILocationView, LocationPresent> implements ILocationView {
    @BindView(R.id.mapView)
    MapView mapView;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_location;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mapView.onCreate(savedInstanceState);// 此方法必须重写
        presenter.initMap(mapView);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public LocationPresent initPresenter() {
        return new LocationPresent(this);
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }
}
