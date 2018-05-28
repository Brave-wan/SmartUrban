package com.smart.urban.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.autonavi.rtbt.IFrameForRTBT;
import com.blankj.utilcode.util.ToastUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.smart.urban.R;
import com.smart.urban.base.BaseFragment;
import com.smart.urban.base.BasePresenter;
import com.smart.urban.bean.UrbanListBean;
import com.smart.urban.http.ApiCallback;
import com.smart.urban.http.BaseResult;
import com.smart.urban.http.HttpManager;
import com.smart.urban.ui.InfoDetailsActivity;
import com.smart.urban.ui.adapter.InfoListAdapter;
import com.smart.urban.utils.LoadingLayout;
import com.smart.urban.utils.SharedPreferencesUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * Created by root on 18-3-29.
 */

public class InfoFragment extends BaseFragment implements OnLoadmoreListener, OnRefreshListener, AdapterView.OnItemClickListener {
    @BindView(R.id.lv_info_list)
    ListView lv_info_list;
    @BindView(R.id.smart_layout)
    SmartRefreshLayout smart_layout;
    @BindView(R.id.layout_root)
    LoadingLayout layout_root;
    InfoListAdapter adapter;
    private List<UrbanListBean> list = new ArrayList<>();
    private int page = 1;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_info;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        setTitle("政策资讯");
        list.clear();
        smart_layout.setOnLoadmoreListener(this);
        smart_layout.setOnRefreshListener(this);
        adapter = new InfoListAdapter(getActivity(), R.layout.item_info_list_two, list);
        lv_info_list.setAdapter(adapter);
        page=1;
        lv_info_list.setOnItemClickListener(this);
        layout_root.setStatus(LoadingLayout.Loading);
        getMessageList(page);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        smart_layout.finishLoadmore(1000);
        page++;
        getMessageList(page);
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        smart_layout.finishRefresh(1000);
        list.clear();
        page = 1;
        getMessageList(page);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        UrbanListBean bean = (UrbanListBean) adapter.getItem(position);
        Intent intent = new Intent(getActivity(), InfoDetailsActivity.class);
        intent.putExtra("id", bean.getId() + "");
        startActivity(intent);
    }


    public void getMessageList(int page) {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", SharedPreferencesUtils.init(getActivity()).getString("userId"));
        map.put("token", SharedPreferencesUtils.init(getActivity()).getString("token"));
        map.put("page", page);
        map.put("rows", 20);
        HttpManager.get().addSubscription(HttpManager.get().getApiStores().getMessageList(map), new ApiCallback<BaseResult<List<UrbanListBean>>>() {
            @Override
            public void onSuccess(BaseResult<List<UrbanListBean>> model) {
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
}
