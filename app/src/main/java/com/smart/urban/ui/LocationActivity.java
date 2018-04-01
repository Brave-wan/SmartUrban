package com.smart.urban.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.MarkerOptions;
import com.smart.urban.R;
import com.smart.urban.base.BaseActivity;
import com.smart.urban.base.BasePresenter;
import com.smart.urban.present.LocationPresent;
import com.smart.urban.view.ILocationView;

import butterknife.BindView;

/**
 * Created by root on 18-3-28.
 */

public class LocationActivity extends Activity {
    MapView mapView;
    LocationPresent present;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        mapView = (MapView) findViewById(R.id.mapView);
        present = new LocationPresent(this);
        mapView.onCreate(savedInstanceState);// 此方法必须重写
        initView();

    }


    protected void initView() {
        present.initMap(mapView);
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
