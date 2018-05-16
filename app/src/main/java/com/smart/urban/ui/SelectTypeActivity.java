package com.smart.urban.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.smart.urban.R;
import com.smart.urban.base.BaseActivity;
import com.smart.urban.base.BasePresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by root on 16.05.18.
 */

public class SelectTypeActivity extends BaseActivity implements OnRefreshListener, OnLoadmoreListener {
    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;
    @BindView(R.id.smart_layout)
    SmartRefreshLayout smart_layout;
    BaseQuickAdapter<String, BaseViewHolder> adapter;
    List<String> list = new ArrayList<>();

    @Override
    protected int getContentViewId() {
        return R.layout.activity_select_type;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setTitle("选择分类");
        smart_layout.setOnRefreshListener(this);
        smart_layout.setOnLoadmoreListener(this);
        initAdapter();
    }


    @Override
    protected void onBackward(View backwardView) {
        super.onBackward(backwardView);
        finish();
    }

    private void initAdapter() {

        list.add("占道经营");
        list.add("占道经营");
        list.add("占道经营");
        list.add("占道经营");

        recycler_view.setLayoutManager(new LinearLayoutManager(this));
        adapter = new BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_select_type, list) {
            @Override
            protected void convert(BaseViewHolder helper, String item) {
                TextView tx_select_type = (TextView) helper.itemView.findViewById(R.id.tx_select_type);
            }
        };

        recycler_view.setAdapter(adapter);
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
