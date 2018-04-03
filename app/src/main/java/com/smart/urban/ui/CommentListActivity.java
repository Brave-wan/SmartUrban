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
import com.smart.urban.ui.adapter.CommentListAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by root on 03.04.18.
 */

public class CommentListActivity extends BaseActivity implements OnLoadmoreListener, OnRefreshListener {
    @BindView(R.id.lv_comment_list)
    ListView lv_comment_list;
    @BindView(R.id.smart_layout)
    SmartRefreshLayout smart_layout;
    CommentListAdapter adapter;
    List<String> list = new ArrayList<>();


    @Override
    protected int getContentViewId() {
        return R.layout.activity_comment_list;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setTitle("评论");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        adapter = new CommentListAdapter(this, R.layout.item_comment_list, list);
        lv_comment_list.setAdapter(adapter);
        smart_layout.setOnLoadmoreListener(this);
        smart_layout.setOnRefreshListener(this);
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
    public void onLoadmore(RefreshLayout refreshlayout) {
        smart_layout.finishLoadmore(1000);
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        smart_layout.finishRefresh(1000);
    }

}
