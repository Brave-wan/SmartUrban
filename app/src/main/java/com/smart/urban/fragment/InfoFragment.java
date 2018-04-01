package com.smart.urban.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.smart.urban.R;
import com.smart.urban.base.BaseFragment;
import com.smart.urban.base.BasePresenter;
import com.smart.urban.ui.adapter.InfoListAdapter;

import java.util.ArrayList;
import java.util.List;

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
    private List<String> list = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_info;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        setTitle("政策资讯");
        smart_layout.setOnLoadmoreListener(this);
        smart_layout.setOnRefreshListener(this);
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");


        adapter = new InfoListAdapter(getActivity(), R.layout.item_info_list, list);

        lv_info_list.setAdapter(adapter);
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
