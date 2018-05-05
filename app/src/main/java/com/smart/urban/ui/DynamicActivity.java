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
import com.smart.urban.bean.DynamicListBean;
import com.smart.urban.http.ApiCallback;
import com.smart.urban.http.BaseResult;
import com.smart.urban.http.HttpManager;
import com.smart.urban.ui.adapter.DynamicListAdapter;
import com.smart.urban.utils.LoadingLayout;
import com.smart.urban.utils.SharedPreferencesUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by root on 01.04.18.
 */

public class DynamicActivity extends BaseActivity implements OnRefreshListener, OnLoadmoreListener, AdapterView.OnItemClickListener {
    @BindView(R.id.smart_layout)
    SmartRefreshLayout smart_layout;
    @BindView(R.id.lv_dynamic_list)
    ListView lv_dynamic_list;
    @BindView(R.id.layout_root)
    LoadingLayout layout_root;
    List<DynamicListBean> list = new ArrayList<>();
    DynamicListAdapter adapter;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_dynamic_list;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setTitle("社区论坛");
        layout_root.setStatus(LoadingLayout.Loading);
        adapter = new DynamicListAdapter(this, R.layout.item_dynamic_list, list);
        lv_dynamic_list.setAdapter(adapter);
        smart_layout.setOnRefreshListener(this);
        smart_layout.setOnLoadmoreListener(this);
        lv_dynamic_list.setOnItemClickListener(this);
        getForumList(page);
    }

    @OnClick({R.id.tv_my_article})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_my_article:
                startActivity(new Intent(this, MyArticleActivity.class));
                break;
        }
    }

    private int page = 1;

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
        list.clear();
        page = 1;
        getForumList(page);

    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        smart_layout.finishLoadmore(1000);
        page++;
        getForumList(page);
    }


    public void getForumList(int page) {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", SharedPreferencesUtils.init(this).getString("userId"));
        map.put("token", SharedPreferencesUtils.init(this).getString("token"));
        map.put("page", page);
        map.put("rows", 20);
        HttpManager.get().addSubscription(HttpManager.get().getApiStores().getForumList(map), new ApiCallback<BaseResult<List<DynamicListBean>>>() {
            @Override
            public void onSuccess(BaseResult<List<DynamicListBean>> model) {
                list.addAll(model.data);
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        DynamicListBean bean = (DynamicListBean) adapter.getItem(position);
        Intent intent = new Intent(this, DynamicDetailsActivity.class);
        intent.putExtra("bean", bean);
        startActivity(intent);
    }
}
