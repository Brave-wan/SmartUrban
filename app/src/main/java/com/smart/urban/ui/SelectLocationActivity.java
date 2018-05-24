package com.smart.urban.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.amap.api.maps.MapView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.iflytek.cloud.thirdparty.V;
import com.smart.urban.R;
import com.smart.urban.base.BaseActivity;
import com.smart.urban.bean.SelectLocationBean;
import com.smart.urban.present.SelectLocationPresent;
import com.smart.urban.utils.slidinguppanel.SlidingUpPanelLayout;
import com.smart.urban.view.ISelectLocationView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.smart.urban.fragment.CameraFragment.requestCode;

/**
 * Created by root on 18-5-23.
 */

public class SelectLocationActivity extends BaseActivity<ISelectLocationView, SelectLocationPresent> implements BaseQuickAdapter.OnItemClickListener, ISelectLocationView {
    @BindView(R.id.map)
    MapView mAMapNaviView;
    @BindView(R.id.lv_location_list)
    RecyclerView lv_location_list;
    @BindView(R.id.ed_location)
    EditText ed_location;
    @BindView(R.id.sliding_layout)
    SlidingUpPanelLayout sliding_layout;

    List<SelectLocationBean> list = new ArrayList<>();
    BaseQuickAdapter<SelectLocationBean, BaseViewHolder> adapter;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_select_location;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setTitle("位置");
        mAMapNaviView = (MapView) findViewById(R.id.map);
        mAMapNaviView.onCreate(savedInstanceState);// 此方法必须重写
        sliding_layout.setTouchEnabled(false);
        //初始化地图
        presenter.initMap(mAMapNaviView);

    }

    @Override
    public SelectLocationPresent initPresenter() {
        return new SelectLocationPresent(this);
    }

    @Override
    public void onLocationAddress(List<SelectLocationBean> address) {
        this.list = address;
        lv_location_list.setLayoutManager(new LinearLayoutManager(this));
        adapter = new BaseQuickAdapter<SelectLocationBean, BaseViewHolder>(R.layout.item_select_location_lit, list) {
            @Override
            protected void convert(BaseViewHolder helper, SelectLocationBean item) {
                TextView tx_location_name = (TextView) helper.itemView.findViewById(R.id.tx_location_name);
                ImageView img_location_check = (ImageView) helper.itemView.findViewById(R.id.img_location_check);
                tx_location_name.setText(item.getTitle());
                tx_location_name.setTextColor(getResources().getColor(item.isCheck() ? R.color.login_login_bt : R.color.black));
                img_location_check.setVisibility(item.isCheck() ? View.VISIBLE : View.GONE);
            }
        };
        lv_location_list.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
    }


    @OnClick({R.id.tv_map_search})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_map_search:
                Intent intent = new Intent();
                intent.putExtra("address", ed_location.getText().toString().trim());
                setResult(requestCode, intent);
                finish();
                break;
        }
    }

    @Override
    protected void onBackward(View backwardView) {
        super.onBackward(backwardView);
        Intent intent = new Intent();
        intent.putExtra("address", ed_location.getText().toString().trim());
        setResult(requestCode, intent);
        finish();
    }

    @Override
    public void onMobileLocation(String s) {
        ed_location.setText(s);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        list = adapter.getData();
        for (SelectLocationBean bean : list) {
            bean.setCheck(false);
        }
        list.get(position).setCheck(true);
        adapter.notifyDataSetChanged();
        ed_location.setText(list.get(position).getTitle());
    }
}
