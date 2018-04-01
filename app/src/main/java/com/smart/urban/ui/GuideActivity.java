package com.smart.urban.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.smart.urban.R;
import com.smart.urban.base.BaseActivity;
import com.smart.urban.base.BasePresenter;
import com.smart.urban.ui.adapter.GuideListAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by root on 01.04.18.
 */

public class GuideActivity extends BaseActivity implements OnRefreshListener, OnLoadmoreListener {
    @BindView(R.id.lv_guide_list)
    ListView lv_guide_list;
    @BindView(R.id.smart_layout)
    SmartRefreshLayout smart_layout;
    GuideListAdapter adapter;
    List<String> list = new ArrayList<>();

    @Override
    protected int getContentViewId() {
        return R.layout.activity_guide;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setTitle("办事指南");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        smart_layout.setOnRefreshListener(this);
        smart_layout.setOnLoadmoreListener(this);
        adapter = new GuideListAdapter(this, R.layout.item_guide_list, list);
        lv_guide_list.setAdapter(adapter);
    }

    @Override
    protected void onBackward(View backwardView) {
        super.onBackward(backwardView);
        finish();
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        smart_layout.finishRefresh(1000);

    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        smart_layout.finishLoadmore(1000);

    }
}
