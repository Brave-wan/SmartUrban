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

/**
 * Created by root on 18-4-17.
 */

public class ArticlesPublishedActivity extends BaseActivity implements OnLoadmoreListener, OnRefreshListener, AdapterView.OnItemClickListener {
    @BindView(R.id.layout_root)
    LoadingLayout layout_root;
    @BindView(R.id.lv_articles_list)
    ListView lv_articles_list;
    @BindView(R.id.smart_layout)
    SmartRefreshLayout smart_layout;
    DynamicListAdapter adapter;
    List<DynamicListBean> list = new ArrayList<>();
    int page = 1;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_articles_published;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setTitle("我的文章");
        layout_root.setStatus(LoadingLayout.Loading);
        adapter = new DynamicListAdapter(this, R.layout.item_dynamic_list, list);
        lv_articles_list.setAdapter(adapter);
        lv_articles_list.setOnItemClickListener(this);
        smart_layout.setOnLoadmoreListener(this);
        smart_layout.setOnRefreshListener(this);
        getForumList(page);
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected void onBackward(View backwardView) {
        super.onBackward(backwardView);
        finish();
    }

    public void getForumList(int page) {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", SharedPreferencesUtils.init(this).getString("userId"));
        map.put("token", SharedPreferencesUtils.init(this).getString("token"));
        map.put("page", page);
        map.put("rows", 20);
        map.put("createUserId", SharedPreferencesUtils.init(this).getString("userId"));
        HttpManager.get().addSubscription(HttpManager.get().getApiStores().getForumList(map), new ApiCallback<BaseResult<List<DynamicListBean>>>() {
            @Override
            public void onSuccess(BaseResult<List<DynamicListBean>> model) {
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

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        smart_layout.finishLoadmore(1000);
        page++;
        getForumList(page);
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        smart_layout.finishRefresh(1000);
        list.clear();
        page = 1;
        getForumList(page);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        DynamicListBean bean = (DynamicListBean) adapter.getItem(position);
        Intent intent = new Intent(this, DynamicDetailsActivity.class);
        intent.putExtra("bean", bean);
        startActivity(intent);

    }
}
