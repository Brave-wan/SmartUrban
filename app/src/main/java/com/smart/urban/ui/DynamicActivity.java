package com.smart.urban.ui;

import android.content.Intent;
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
import com.smart.urban.ui.adapter.DynamicListAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by root on 01.04.18.
 */

public class DynamicActivity extends BaseActivity implements OnRefreshListener, OnLoadmoreListener {
    @BindView(R.id.smart_layout)
    SmartRefreshLayout smart_layout;
    @BindView(R.id.lv_dynamic_list)
    ListView lv_dynamic_list;
    List<String> list = new ArrayList<>();
    DynamicListAdapter adapter;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_dynamic_list;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setTitle("社区论坛");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        adapter = new DynamicListAdapter(this, R.layout.item_dynamic_list, list);
        lv_dynamic_list.setAdapter(adapter);

    }

    @OnClick({R.id.tv_my_article})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_my_article:
                startActivity(new Intent(this, MyArticleActivity.class));
                break;
        }
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
