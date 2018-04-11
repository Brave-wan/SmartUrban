package com.smart.urban.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.blankj.utilcode.util.ToastUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.smart.urban.R;
import com.smart.urban.base.BaseActivity;
import com.smart.urban.base.BasePresenter;
import com.smart.urban.bean.LostBean;
import com.smart.urban.http.ApiCallback;
import com.smart.urban.http.BaseResult;
import com.smart.urban.http.HttpManager;
import com.smart.urban.ui.adapter.LostListAdapter;
import com.smart.urban.utils.LoadingLayout;
import com.smart.urban.utils.SharedPreferencesUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by root on 18-4-11.
 */

public class LostListActivity extends BaseActivity implements OnRefreshListener, OnLoadmoreListener {
    List<LostBean> list = new ArrayList<>();
    @BindView(R.id.layout_root)
    LoadingLayout layout_root;
    @BindView(R.id.smart_layout)
    SmartRefreshLayout smart_layout;
    LostListAdapter adapter;
    @BindView(R.id.lv_lost_list)
    ListView lv_lost_list;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_lost_list;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setTitle("失物招领");
        adapter = new LostListAdapter(this, R.layout.item_lost_list, list);
        lv_lost_list.setAdapter(adapter);
        smart_layout.setOnRefreshListener(this);
        smart_layout.setOnLoadmoreListener(this);
        getLostList(page);
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    int page = 1;

    public void getLostList(int page) {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", SharedPreferencesUtils.init(this).getString("key"));
        map.put("token", SharedPreferencesUtils.init(this).getString("token"));
        map.put("page", page);
        map.put("rows", 20);
        map.put("createUserId", SharedPreferencesUtils.init(this).getString("key"));
        HttpManager.get().addSubscription(HttpManager.get().getApiStores().getLostList(map), new ApiCallback<BaseResult<List<LostBean>>>() {
            @Override
            public void onSuccess(BaseResult<List<LostBean>> model) {
                list.addAll(model.getData());
                adapter.setDataList(list);
                layout_root.setStatus(list.size() > 0 ? LoadingLayout.Success : LoadingLayout.Empty);
            }

            @Override
            public void onFailure(BaseResult result) {
                ToastUtils.showShort(result.errmsg);
                layout_root.setStatus(LoadingLayout.Error);
            }
        });
    }

    @OnClick({R.id.tv_my_lost})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_my_lost:
                startActivity(new Intent(this, LostFoundActivity.class));
                break;
        }
    }

    @Override
    protected void onBackward(View backwardView) {
        super.onBackward(backwardView);
        finish();
    }


    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        smart_layout.finishRefresh(1000);
        list.clear();
        page = 1;
        getLostList(page);

    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        smart_layout.finishLoadmore(1000);
        page++;
        getLostList(page);
    }
}
