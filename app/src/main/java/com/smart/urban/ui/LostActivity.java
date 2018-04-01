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
import com.smart.urban.ui.adapter.LostListAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by root on 01.04.18.
 */

public class LostActivity extends BaseActivity implements OnRefreshListener, OnLoadmoreListener {
    @BindView(R.id.smart_layout)
    SmartRefreshLayout smart_layout;
    @BindView(R.id.lv_lost_list)
    ListView lv_lost_list;
    LostListAdapter adapter;
    private List<String> list = new ArrayList<>();

    @Override
    protected int getContentViewId() {
        return R.layout.activity_my_lost;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setTitle("我的失物招领");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        adapter = new LostListAdapter(this, R.layout.item_lost_list, list);
        lv_lost_list.setAdapter(adapter);
        smart_layout.setOnRefreshListener(this);
        smart_layout.setOnLoadmoreListener(this);

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
