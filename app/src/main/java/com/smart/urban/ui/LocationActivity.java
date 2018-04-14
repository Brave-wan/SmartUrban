package com.smart.urban.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.navi.AMapNavi;
import com.amap.api.navi.AMapNaviView;
import com.amap.api.navi.AMapNaviViewOptions;
import com.amap.api.navi.enums.NaviType;
import com.blankj.utilcode.util.ToastUtils;
import com.smart.urban.R;
import com.smart.urban.base.BaseLocationActivity;
import com.smart.urban.present.LocationPresent;
import com.smart.urban.ui.adapter.LocationListAdapter;
import com.smart.urban.utils.slidinguppanel.SlidingUpPanelLayout;
import com.smart.urban.view.ILocationView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.http.GET;


/**
 * Created by root on 18-3-28.
 */

public class LocationActivity extends BaseLocationActivity implements ILocationView, View.OnClickListener, AdapterView.OnItemClickListener {
    AMapNavi mAMapNavi;
    LocationPresent presenter;
    ListView lv_location_list;
    TextView tv_map_search;
    LocationListAdapter adapter;
    SlidingUpPanelLayout sliding_layout;
    RelativeLayout rl_bottom, rl_transportation_state;
    ImageView img_location_back;
    private List<String> list = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        mAMapNaviView = (AMapNaviView) findViewById(R.id.mapView);
        mAMapNaviView.onCreate(savedInstanceState);// 此方法必须重写
        initView();

    }

    private void initView() {
        img_location_back = (ImageView) findViewById(R.id.img_location_back);
        rl_transportation_state = (RelativeLayout) findViewById(R.id.rl_transportation_state);
        rl_bottom = (RelativeLayout) findViewById(R.id.rl_bottom);
        sliding_layout = (SlidingUpPanelLayout) findViewById(R.id.sliding_layout);
        tv_map_search = (TextView) findViewById(R.id.tv_map_search);
        lv_location_list = (ListView) findViewById(R.id.lv_location_list);
        presenter = new LocationPresent(this, this);
        mAMapNavi = AMapNavi.getInstance(getApplicationContext());
        presenter.initMap(mAMapNaviView);
        mAMapNaviView.setAMapNaviViewListener(this);
        AMapNaviViewOptions options = new AMapNaviViewOptions();
        options.setTilt(0);
        mAMapNaviView.setViewOptions(options);
        tv_map_search.setOnClickListener(this);
        rl_transportation_state.setOnClickListener(this);
        img_location_back.setOnClickListener(this);
        initData();
    }

    private void initData() {
        adapter = new LocationListAdapter(this, R.layout.item_location_list, list);
        lv_location_list.setAdapter(adapter);
        lv_location_list.setOnItemClickListener(this);


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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_map_search:
                for (int i = 0; i < 10; i++) {
                    list.add("");
                }
                adapter.setDataList(list);
                sliding_layout.setPanelHeight(600);
                break;
            case R.id.rl_transportation_state:
                break;
            case R.id.img_location_back:
                rl_bottom.setVisibility(View.VISIBLE);
                rl_transportation_state.setVisibility(View.GONE);
                lv_location_list.setVisibility(View.VISIBLE);
                sliding_layout.setPanelHeight(600);
                sliding_layout.setTouchEnabled(true);
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        rl_bottom.setVisibility(View.GONE);
        rl_transportation_state.setVisibility(View.VISIBLE);
        lv_location_list.setVisibility(View.GONE);
        sliding_layout.setPanelHeight(rl_transportation_state.getLayoutParams().height);
        sliding_layout.setTouchEnabled(false);
    }
}
