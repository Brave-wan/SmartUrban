package com.smart.urban.fragment;

import android.os.Bundle;
import android.view.View;
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
import com.smart.urban.ui.adapter.InfoListAdapter;
import com.smart.urban.utils.SharedPreferencesUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * Created by root on 18-3-29.
 */

public class InfoFragment extends BaseFragment implements OnLoadmoreListener, OnRefreshListener {
    @BindView(R.id.lv_info_list)
    ListView lv_info_list;
    @BindView(R.id.smart_layout)
    SmartRefreshLayout smart_layout;
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
        getMessageList(page);
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
            }

            @Override
            public void onFailure(BaseResult result) {
                ToastUtils.showShort(result.errmsg);
            }
        });
    }
}
