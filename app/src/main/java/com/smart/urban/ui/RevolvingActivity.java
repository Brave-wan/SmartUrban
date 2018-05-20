package com.smart.urban.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.smart.urban.R;
import com.smart.urban.base.BaseActivity;
import com.smart.urban.base.BasePresenter;
import com.smart.urban.bean.RevolvingListBean;
import com.smart.urban.present.RevolvingPresent;
import com.smart.urban.ui.adapter.RevolvingListAdapter;
import com.smart.urban.utils.LoadingLayout;
import com.smart.urban.view.IRevolvingView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by root on 18-4-10.
 */

public class RevolvingActivity extends BaseActivity<IRevolvingView, RevolvingPresent> implements IRevolvingView,
        OnRefreshListener, OnLoadmoreListener, AdapterView.OnItemClickListener {
    @BindView(R.id.lv_revolving_list)
    ListView lv_revolving_list;
    @BindView(R.id.smart_layout)
    SmartRefreshLayout smart_layout;
    @BindView(R.id.layout_root)
    LoadingLayout layout_content;

    List<RevolvingListBean> list = new ArrayList<>();
    RevolvingListAdapter adapter;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_revolving;
    }

    int page = 1;

    @Override
    protected void initView(Bundle savedInstanceState) {
        setTitle("我的随手拍");
        layout_content.setStatus(LoadingLayout.Loading);
        presenter.getPhotoList(page);
        adapter = new RevolvingListAdapter(this, R.layout.item_revolving_list, list);
        lv_revolving_list.setAdapter(adapter);
        smart_layout.setOnLoadmoreListener(this);
        smart_layout.setOnRefreshListener(this);
        lv_revolving_list.setOnItemClickListener(this);
    }


    @Override
    protected void onResume() {
        super.onResume();
        smart_layout.autoRefresh();
    }

    @Override
    protected void onBackward(View backwardView) {
        super.onBackward(backwardView);
        finish();
    }

    @Override
    public RevolvingPresent initPresenter() {
        return new RevolvingPresent(this);
    }

    @Override
    public void onRevolvingList(List<RevolvingListBean> listBeans) {
        list.addAll(listBeans);
        adapter.setDataList(list);
        layout_content.setStatus(list.size() > 0 ? LoadingLayout.Success : LoadingLayout.Empty);
    }

    @Override
    public void onFiled() {
        layout_content.setStatus(LoadingLayout.Empty);
    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        smart_layout.finishLoadmore(1000);
        page++;
        presenter.getPhotoList(page);
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        smart_layout.finishRefresh(1000);
        list.clear();
        page = 1;
        presenter.getPhotoList(page);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        RevolvingListBean bean = (RevolvingListBean) adapter.getItem(position);
        Intent intent = new Intent(this, RevolvingDetailsActivity.class);
        intent.putExtra("bean", bean);
        startActivity(intent);
    }

}
