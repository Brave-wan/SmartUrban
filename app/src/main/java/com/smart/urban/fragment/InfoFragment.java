package com.smart.urban.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.autonavi.rtbt.IFrameForRTBT;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
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
import com.smart.urban.ui.widget.RecycleViewDivider;
import com.smart.urban.ui.widget.RecyclerManagerView;
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

public class InfoFragment extends BaseFragment implements OnLoadmoreListener, OnRefreshListener, BaseQuickAdapter.OnItemClickListener {
    @BindView(R.id.lv_info_list)
    RecyclerView lv_info_list;
    @BindView(R.id.smart_layout)
    SmartRefreshLayout smart_layout;
    @BindView(R.id.layout_root)
    LoadingLayout layout_root;
    InfoListAdapter adapter;
    private List<UrbanListBean> list = new ArrayList<>();
    private int page = 1;
    BaseQuickAdapter<UrbanListBean, BaseViewHolder> baseQuickAdapter;

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
        page = 1;
        layout_root.setStatus(LoadingLayout.Loading);
        getMessageList(page);

        initAdapter();
    }

    private void initAdapter() {

        lv_info_list.setLayoutManager(new RecyclerManagerView(getActivity()));
        lv_info_list.addItemDecoration(new RecycleViewDivider(getActivity(), LinearLayoutManager.HORIZONTAL, 2, getResources().getColor(R.color.gray_line)));
        baseQuickAdapter = new BaseQuickAdapter<UrbanListBean, BaseViewHolder>(R.layout.item_info_list_two, list) {
            @Override
            protected void convert(BaseViewHolder helper, UrbanListBean bean) {
                ImageView img_info = (ImageView) helper.itemView.findViewById(R.id.img_info);
                TextView tv_dynamic_title = (TextView) helper.itemView.findViewById(R.id.tv_dynamic_title);
                TextView tv_comment_number = (TextView) helper.itemView.findViewById(R.id.tv_comment_number);
                TextView tv_info_time = (TextView) helper.itemView.findViewById(R.id.tv_info_time);
                TextView tx_dynamic_subject = (TextView) helper.itemView.findViewById(R.id.tx_dynamic_subject);
                tv_info_time.setText(bean.getCreateTime());
                if (!StringUtils.isEmpty(bean.getSubtitle())) {
                    tx_dynamic_subject.setVisibility(View.VISIBLE);
                    tx_dynamic_subject.setText(bean.getSubtitle());
                }

                tv_comment_number.setText(bean.getViewCount() + "");
                tv_dynamic_title.setText(bean.getTitle());
                if (bean.getImages().size() > 0) {//bean.getImages().get(0).getAddress()
                    Glide.with(getActivity()).load(bean.getImages().get(0).getAddress()).error(R.drawable.icon_info_list_empty).placeholder(R.drawable.icon_info_list_empty).into(img_info);
                } else {
                    Glide.with(getActivity()).load("").error(R.drawable.icon_info_list_empty).placeholder(R.drawable.icon_info_list_empty).into(img_info);
                }
            }
        };

        lv_info_list.setAdapter(baseQuickAdapter);
        baseQuickAdapter.setOnItemClickListener(this);
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


    public void getMessageList(int page) {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", SharedPreferencesUtils.init(getActivity()).getString("userId"));
        map.put("token", SharedPreferencesUtils.init(getActivity()).getString("token"));
        map.put("page", page);
        map.put("rows", 20);
        HttpManager.get().addSubscription(HttpManager.get().getApiStores().getMessageList(map), new ApiCallback<BaseResult<List<UrbanListBean>>>() {
            @Override
            public void onSuccess(BaseResult<List<UrbanListBean>> model) {
                if (model.getData().size() > 0) {
                    list.addAll(model.getData());
                    baseQuickAdapter.notifyDataSetChanged();
                }
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
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        UrbanListBean bean = (UrbanListBean) adapter.getItem(position);
        Intent intent = new Intent(getActivity(), InfoDetailsActivity.class);
        intent.putExtra("id", bean.getId() + "");
        startActivity(intent);
    }
}
