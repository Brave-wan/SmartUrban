package com.smart.urban.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.maps.MapView;
import com.amap.api.navi.AMapNavi;
import com.amap.api.navi.AMapNaviView;
import com.amap.api.navi.AMapNaviViewOptions;
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

public class LocationActivity extends Activity implements ILocationView, View.OnClickListener, AdapterView.OnItemClickListener {
    private LocationPresent presenter;
    ListView lv_location_list;
    EditText ed_location;
    TextView tv_map_search, tv_my_location, tv_location_name;
    RadioButton rb_riding_btn, rb_car_btn;
    LocationListAdapter adapter;
    SlidingUpPanelLayout sliding_layout;
    RelativeLayout rl_bottom, rl_transportation_state;
    ImageView img_location_back;
    private List<LocationListBean> list = new ArrayList<>();
    MapView mAMapNaviView;
    ImageView btn_map_switch;
    ImageView img_map_back;
    private boolean isAll = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        mAMapNaviView = (MapView) findViewById(R.id.map);
        mAMapNaviView.onCreate(savedInstanceState);// 此方法必须重写
        initView();
    }

    private void initView() {
        img_location_back = (ImageView) findViewById(R.id.img_location_back);
        img_map_back = (ImageView) findViewById(R.id.img_map_back);
        btn_map_switch = (ImageView) findViewById(R.id.btn_map_switch);
        rl_transportation_state = (RelativeLayout) findViewById(R.id.rl_transportation_state);
        rl_bottom = (RelativeLayout) findViewById(R.id.rl_bottom);
        sliding_layout = (SlidingUpPanelLayout) findViewById(R.id.sliding_layout);
        tv_map_search = (TextView) findViewById(R.id.tv_map_search);
        lv_location_list = (ListView) findViewById(R.id.lv_location_list);
        tv_my_location = (TextView) findViewById(R.id.tv_my_location);
        tv_location_name = (TextView) findViewById(R.id.tv_location_name);
        ed_location = (EditText) findViewById(R.id.ed_location);
        rb_car_btn = (RadioButton) findViewById(R.id.rb_car_btn);
        rb_riding_btn = (RadioButton) findViewById(R.id.rb_riding_btn);
        rb_riding_btn.setOnClickListener(this);
        rb_car_btn.setOnClickListener(this);
        presenter = new LocationPresent(this, this);
        presenter.initMap(mAMapNaviView);
        tv_map_search.setOnClickListener(this);
        rl_transportation_state.setOnClickListener(this);
        img_location_back.setOnClickListener(this);
        img_map_back.setOnClickListener(this);
        btn_map_switch.setOnClickListener(this);
        initData();
    }

    private void initData() {
        btn_map_switch.setBackgroundResource(!isAll ? R.drawable.icon_map_location_nearby : R.drawable.icon_map_location_all);
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
    public void onClick(View v) {
        switch (v.getId()) {
            //关键字搜索
            case R.id.tv_map_search:
                if (StringUtils.isEmpty(ed_location.getText().toString().trim())) {
                    ToastUtils.showShort("请输入你想搜索的关键字!");
                    return;
                }
                presenter.getLocationSearch(ed_location.getText().toString().trim(), true);
                break;
            case R.id.rl_transportation_state:
                break;
            //取消导航　
            case R.id.img_location_back:
                rl_bottom.setVisibility(View.VISIBLE);
                rl_transportation_state.setVisibility(View.GONE);
                lv_location_list.setVisibility(View.VISIBLE);
                sliding_layout.setPanelHeight(600);
                sliding_layout.setTouchEnabled(true);
                break;
            case R.id.rb_riding_btn:
                rb_car_btn.setChecked(false);
                rb_riding_btn.setChecked(true);
                Intent intent = new Intent(this, WalkRouteCalculateActivity.class);
                startActivity(intent);
                presenter.onDestroy();
                break;
            case R.id.rb_car_btn:
                rb_riding_btn.setChecked(false);
                rb_car_btn.setChecked(true);
                Intent car = new Intent(this, SingleRouteCalculateActivity.class);
                startActivity(car);
                presenter.onDestroy();
                break;
            case R.id.img_map_back:
                finish();
                break;
            //切换状态
            case R.id.btn_map_switch:
                btn_map_switch.setBackgroundResource(isAll ? R.drawable.icon_map_location_nearby : R.drawable.icon_map_location_all);
                presenter.getLocationSearch("", isAll ? false : true);
                isAll = isAll ? false : true;
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
        sliding_layout.setTouchEnabled(true);
        tv_location_name.setText("终点：" + bean.getName());
        String myLocation = StringUtils.isEmpty(SharedPreferencesUtils.init(this).getString("address"))
                ? "我的位置" : SharedPreferencesUtils.init(this).getString("address");
        tv_my_location.setText(myLocation);
        SharedPreferencesUtils.init(this).put("end_lat", bean.getLatitude()).put("end_lon", bean.getLongitude());
    }

    @Override
    public void onLocationList(List<LocationListBean> beans, boolean state) {
        //显示地图上搜索的坐标点
        list.clear();
        list.addAll(beans);
        adapter.setDataList(list);
        sliding_layout.setPanelHeight(600);
        KeyboardUtils.hideSoftInput(this);
    }


    @Override
    public void onLocationView() {
        rl_bottom.setVisibility(View.GONE);
        rl_transportation_state.setVisibility(View.VISIBLE);
        lv_location_list.setVisibility(View.GONE);
        sliding_layout.setPanelHeight(rl_transportation_state.getLayoutParams().height);
        sliding_layout.setTouchEnabled(false);
        tv_location_name.setText("终点：" + SharedPreferencesUtils.init(this).getString("title"));
        String myLocation = StringUtils.isEmpty(SharedPreferencesUtils.init(this).getString("address"))
                ? "我的位置" : SharedPreferencesUtils.init(this).getString("address");
        tv_my_location.setText(myLocation);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
