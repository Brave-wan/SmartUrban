package com.smart.urban.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.smart.urban.R;
import com.smart.urban.base.BaseActivity;
import com.smart.urban.base.BasePresenter;
import com.smart.urban.bean.UrbanDetailsBean;
import com.smart.urban.bean.UrbanListBean;
import com.smart.urban.http.ApiCallback;
import com.smart.urban.http.BaseResult;
import com.smart.urban.http.HttpManager;
import com.smart.urban.ui.adapter.InfoListAdapter;
import com.smart.urban.ui.adapter.UrbanListAdapter;
import com.smart.urban.utils.LoadingLayout;
import com.smart.urban.utils.SharedPreferencesUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * Created by root on 18-4-9.
 */

public class UrbanActivity extends BaseActivity implements OnRefreshListener, OnLoadmoreListener, AdapterView.OnItemClickListener {
    @BindView(R.id.smart_layout)
    SmartRefreshLayout smart_layout;
    @BindView(R.id.lv_urban_list)
    ListView lv_urban_list;
    @BindView(R.id.layout_root)
    LoadingLayout layout_root;

    private UrbanListAdapter adapter;
    private List<UrbanListBean> list = new ArrayList<>();

    @Override
    protected int getContentViewId() {
        return R.layout.activity_urban_list;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setTitle("城管动态");
        adapter = new UrbanListAdapter(this, R.layout.item_info_list, list);
        lv_urban_list.setAdapter(adapter);
        layout_root.setStatus(LoadingLayout.Loading);
        smart_layout.setOnRefreshListener(this);
        smart_layout.setOnLoadmoreListener(this);
        lv_urban_list.setOnItemClickListener(this);
        getDynamicList(start);
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

    private int start = 1;

    public void getDynamicList(int start) {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", SharedPreferencesUtils.init(this).getString("userId"));
        map.put("token", SharedPreferencesUtils.init(this).getString("token"));
        map.put("page", start);
        map.put("rows", 20);
        HttpManager.get().addSubscription(HttpManager.get().getApiStores().getDynameicList(map), new ApiCallback<BaseResult<List<UrbanListBean>>>() {
            @Override
            public void onSuccess(BaseResult<List<UrbanListBean>> model) {
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
    public void onRefresh(RefreshLayout refreshlayout) {
        smart_layout.finishRefresh(1000);
        list.clear();
        start = 1;
        getDynamicList(start);
    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        smart_layout.finishLoadmore(1000);
        start++;
        getDynamicList(start);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        UrbanListBean bean = (UrbanListBean) adapter.getItem(position);
        Intent intent = new Intent(this, UrbanDetailsActivity.class);
        intent.putExtra("id", bean.getId() + "");
        startActivity(intent);
    }
}
