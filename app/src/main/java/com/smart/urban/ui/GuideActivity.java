package com.smart.urban.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.blankj.utilcode.util.ToastUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.smart.urban.R;
import com.smart.urban.base.BaseActivity;
import com.smart.urban.base.BasePresenter;
import com.smart.urban.bean.GuideListBean;
import com.smart.urban.http.ApiCallback;
import com.smart.urban.http.BaseResult;
import com.smart.urban.http.HttpManager;
import com.smart.urban.ui.adapter.GuideListAdapter;
import com.smart.urban.utils.LoadingLayout;
import com.smart.urban.utils.SharedPreferencesUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * Created by root on 01.04.18.
 */

public class GuideActivity extends BaseActivity implements OnRefreshListener, OnLoadmoreListener, AdapterView.OnItemClickListener {
    @BindView(R.id.lv_guide_list)
    ListView lv_guide_list;
    @BindView(R.id.smart_layout)
    SmartRefreshLayout smart_layout;
    @BindView(R.id.layout_root)
    LoadingLayout layout_root;
    GuideListAdapter adapter;
    List<GuideListBean> list = new ArrayList<>();
    private int page = 1;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_guide;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setTitle("办事指南");
        layout_root.setStatus(LoadingLayout.Loading);
        smart_layout.setOnRefreshListener(this);
        smart_layout.setOnLoadmoreListener(this);
        adapter = new GuideListAdapter(this, R.layout.item_guide_list, list);
        lv_guide_list.setAdapter(adapter);
        lv_guide_list.setOnItemClickListener(this);
        getGuideList(page);
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
        page = 1;
        list.clear();
        getGuideList(page);

    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        smart_layout.finishLoadmore(1000);
        page++;
        getGuideList(page);
    }

    public void getGuideList(int page) {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", SharedPreferencesUtils.init(this).getString("userId"));
        map.put("token", SharedPreferencesUtils.init(this).getString("token"));
        map.put("page", page);
        map.put("rows", 20);
        HttpManager.get().addSubscription(HttpManager.get().getApiStores().getGuideList(map), new ApiCallback<BaseResult<List<GuideListBean>>>() {
            @Override
            public void onSuccess(BaseResult<List<GuideListBean>> model) {
                list.addAll(model.getData());
                adapter.setDataList(list);
                layout_root.setStatus(list.size() > 0 ? LoadingLayout.Success : LoadingLayout.Empty);
            }

            @Override
            public void onFailure(BaseResult result) {
                ToastUtils.showShort(result.getErrmsg());
                layout_root.setStatus(LoadingLayout.Error);
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        GuideListBean bean = (GuideListBean) adapter.getItem(position);
        Intent intent = new Intent(this, GuideDetailsActivity.class);
        intent.putExtra("bean", bean);
        startActivity(intent);
    }
}
