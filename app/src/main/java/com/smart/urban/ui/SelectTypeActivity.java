package com.smart.urban.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.smart.urban.R;
import com.smart.urban.base.BaseActivity;
import com.smart.urban.base.BasePresenter;
import com.smart.urban.bean.SelectTypeBean;
import com.smart.urban.http.ApiCallback;
import com.smart.urban.http.BaseResult;
import com.smart.urban.http.HttpManager;
import com.smart.urban.utils.LoadingLayout;
import com.smart.urban.utils.SharedPreferencesUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * Created by root on 16.05.18.
 */

public class SelectTypeActivity extends BaseActivity implements OnRefreshListener, OnLoadmoreListener, BaseQuickAdapter.OnItemClickListener {
    @BindView(R.id.recycler_view)
    RecyclerView recycler_view;
    @BindView(R.id.smart_layout)
    SmartRefreshLayout smart_layout;
    @BindView(R.id.load_layout)
    LoadingLayout load_layout;

    BaseQuickAdapter<SelectTypeBean, BaseViewHolder> adapter;
    List<SelectTypeBean> list = new ArrayList<>();

    @Override
    protected int getContentViewId() {
        return R.layout.activity_select_type;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setTitle("选择分类");
        smart_layout.setOnRefreshListener(this);
        smart_layout.setOnLoadmoreListener(this);
        load_layout.setStatus(LoadingLayout.Loading);
        getTypeList(page);
        initAdapter();
    }


    @Override
    protected void onBackward(View backwardView) {
        super.onBackward(backwardView);
        finish();
    }

    private void initAdapter() {

        recycler_view.setLayoutManager(new LinearLayoutManager(this));
        adapter = new BaseQuickAdapter<SelectTypeBean, BaseViewHolder>(R.layout.item_select_type, list) {
            @Override
            protected void convert(BaseViewHolder helper, SelectTypeBean item) {
                TextView tx_select_type = (TextView) helper.itemView.findViewById(R.id.tx_select_type);
                tx_select_type.setText(item.getName());
            }
        };
        adapter.setOnItemClickListener(this);
        recycler_view.setAdapter(adapter);
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
        getTypeList(page);

    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        smart_layout.finishLoadmore(1000);
        page++;
        getTypeList(page);
    }

    private int page = 1;

    public void getTypeList(int page) {
        Map<String, Object> map = new HashMap<>();
        map.put("page", page);
        map.put("rows", 20);
        HttpManager.get().addSubscription(HttpManager.get().getApiStores().getTypeList(map), new ApiCallback<BaseResult<List<SelectTypeBean>>>() {
            @Override
            public void onSuccess(BaseResult<List<SelectTypeBean>> model) {
                list.addAll(model.getData());
                adapter.notifyDataSetChanged();
                load_layout.setStatus(list.size() > 0 ? LoadingLayout.Success : LoadingLayout.Empty);
            }

            @Override
            public void onFailure(BaseResult result) {
                ToastUtils.showShort(result.getErrmsg());
            }
        });
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        SelectTypeBean bean = (SelectTypeBean) adapter.getItem(position);
        SharedPreferencesUtils.init(this).put("type_id", bean.getId() + "")
                .put("type_name", bean.getName());
        finish();
    }
}
