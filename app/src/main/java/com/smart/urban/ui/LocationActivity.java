package com.smart.urban.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.navi.AMapNavi;
import com.amap.api.navi.AMapNaviView;
import com.amap.api.navi.AMapNaviViewOptions;
import com.amap.api.navi.enums.NaviType;
import com.amap.api.navi.model.NaviLatLng;
import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.smart.urban.R;
import com.smart.urban.base.BaseLocationActivity;
import com.smart.urban.bean.LocationListBean;
import com.smart.urban.present.LocationPresent;
import com.smart.urban.ui.adapter.LocationListAdapter;
import com.smart.urban.utils.SharedPreferencesUtils;
import com.smart.urban.utils.slidinguppanel.SlidingUpPanelLayout;
import com.smart.urban.view.ILocationView;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by root on 18-3-28.
 */

public class LocationActivity extends BaseLocationActivity implements ILocationView, View.OnClickListener, RadioGroup.OnCheckedChangeListener, AdapterView.OnItemClickListener {
    AMapNavi mAMapNavi;
    LocationPresent presenter;
    ListView lv_location_list;
    EditText ed_location;
    RadioGroup gr_location_group;
    TextView tv_map_search, tv_my_location, tv_location_name;
    LocationListAdapter adapter;
    SlidingUpPanelLayout sliding_layout;
    RelativeLayout rl_bottom, rl_transportation_state;
    ImageView img_location_back;
    private List<LocationListBean> list = new ArrayList<>();

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
        tv_my_location = (TextView) findViewById(R.id.tv_my_location);
        tv_location_name = (TextView) findViewById(R.id.tv_location_name);
        gr_location_group = (RadioGroup) findViewById(R.id.gr_location_group);
        ed_location = (EditText) findViewById(R.id.ed_location);
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
        gr_location_group.setOnCheckedChangeListener(this);
        initData();
    }

    private void initData() {
        adapter = new LocationListAdapter(this, R.layout.item_location_list, list);
        lv_location_list.setAdapter(adapter);
        lv_location_list.setOnItemClickListener(this);
        presenter.getLocationSearch("", false);


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
         * @congestion 躲避拥堵
         * @avoidhightspeed 不走高速
         * @cost 避免收费
         * @hightspeed 高速优先
         * @multipleroute 多路径
         *
         *  说明: 以上参数都是boolean类型，其中multipleroute参数表示是否多条路线，如果为true则此策略会算出多条路线。
         *  注意: 不走高速与高速优先不能同时为true 高速优先与避免收费不能同时为true
         */
//        int strategy = 0;
//        try {
//            //再次强调，最后一个参数为true时代表多路径，否则代表单路径
//            strategy = mAMapNavi.strategyConvert(true, false, false, false, false);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        mAMapNavi.calculateDriveRoute(sList, eList, mWayPointList, strategy);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_map_search:
                if (StringUtils.isEmpty(ed_location.getText().toString().trim())) {
                    ToastUtils.showShort("请输入你想搜索的关键字!");
                    return;
                }
                presenter.getLocationSearch(ed_location.getText().toString().trim(), true);

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
        LocationListBean bean = (LocationListBean) adapter.getItem(position);
        rl_bottom.setVisibility(View.GONE);
        rl_transportation_state.setVisibility(View.VISIBLE);
        lv_location_list.setVisibility(View.GONE);
        sliding_layout.setPanelHeight(rl_transportation_state.getLayoutParams().height);
        sliding_layout.setTouchEnabled(false);
        tv_location_name.setText("终点：" + bean.getName());
        String myLocation = StringUtils.isEmpty(SharedPreferencesUtils.init(this).getString("address"))
                ? "我的位置" : SharedPreferencesUtils.init(this).getString("address");
        tv_my_location.setText(myLocation);
        SharedPreferencesUtils.init(this).put("end_lat", bean.getLatitude()).put("end_lon", bean.getLongitude());

    }

    @Override
    public void onLocationList(List<LocationListBean> beans, boolean state) {
        //显示地图上搜索的坐标点
        if (state) {
            list.addAll(beans);
            adapter.setDataList(list);
            sliding_layout.setPanelHeight(600);
            KeyboardUtils.hideSoftInput(this);
        }

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        //选择骑行方式
        switch (checkedId) {
            //步行或骑车导航
            case R.id.rb_riding_btn:
                Intent intent=new Intent(this,WalkRouteCalculateActivity.class);
                startActivity(intent);
                presenter.onDestroy();
                break;
            //开车导航
            case R.id.rb_car_btn:
                Intent car=new Intent(this,SingleRouteCalculateActivity.class);
                startActivity(car);
                presenter.onDestroy();
                break;
        }

    }



    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
