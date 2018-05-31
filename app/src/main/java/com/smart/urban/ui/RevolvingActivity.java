package com.smart.urban.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.smart.urban.R;
import com.smart.urban.base.BaseActivity;
import com.smart.urban.base.BasePresenter;
import com.smart.urban.bean.RevolvingListBean;
import com.smart.urban.config.Constants;
import com.smart.urban.present.RevolvingPresent;
import com.smart.urban.ui.adapter.RevolvingListAdapter;
import com.smart.urban.ui.widget.RecycleViewDivider;
import com.smart.urban.utils.LoadingLayout;
import com.smart.urban.view.IRevolvingView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by root on 18-4-10.
 */

public class RevolvingActivity extends BaseActivity<IRevolvingView, RevolvingPresent> implements IRevolvingView,
        OnRefreshListener, OnLoadmoreListener, BaseQuickAdapter.OnItemClickListener {
    @BindView(R.id.lv_revolving_list)
    RecyclerView lv_revolving_list;
    @BindView(R.id.smart_layout)
    SmartRefreshLayout smart_layout;
    @BindView(R.id.layout_root)
    LoadingLayout layout_content;

    List<RevolvingListBean> list = new ArrayList<>();
    //    RevolvingListAdapter adapter;
    BaseQuickAdapter<RevolvingListBean, BaseViewHolder> baseQuickAdapter;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_revolving;
    }

    int page = 1;

    @Override
    protected void initView(Bundle savedInstanceState) {
        setTitle("我的随手拍");
        layout_content.setStatus(LoadingLayout.Loading);
        smart_layout.setOnLoadmoreListener(this);
        smart_layout.setOnRefreshListener(this);
        initAdapter();
    }

    private void initAdapter() {
        lv_revolving_list.setLayoutManager(new LinearLayoutManager(this));
        lv_revolving_list.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.HORIZONTAL, 2, getResources().getColor(R.color.gray_line)));
        baseQuickAdapter = new BaseQuickAdapter<RevolvingListBean, BaseViewHolder>(R.layout.item_revolving_list, list) {
            @Override
            protected void convert(BaseViewHolder helper, RevolvingListBean bean) {
                ImageView img_revolving_head = (ImageView) helper.itemView.findViewById(R.id.img_revolving_head);
                TextView tv_revolving_title = (TextView) helper.itemView.findViewById(R.id.tv_revolving_title);
                ImageView tv_revolving_state = (ImageView) helper.itemView.findViewById(R.id.tv_revolving_state);
                TextView tv_revolving_content = (TextView) helper.itemView.findViewById(R.id.tv_revolving_content);
                TextView tv_mode_time = (TextView) helper.itemView.findViewById(R.id.tv_mode_time);
                tv_revolving_title.setText(bean.getContent());

                Log.i("wan", "type:" + bean.getState());
                tv_mode_time.setText("创建时间:" + bean.getCreateTime());
                if (bean.getImages() != null && bean.getImages().size() > 0) {
                    Glide.with(RevolvingActivity.this).load(Constants.BASE_URL + bean.getImages().get(0).getAddress()).placeholder(R.drawable.icon_pic_empty).error(R.drawable.icon_pic_empty).into(img_revolving_head);
                } else {
                    img_revolving_head.setBackground(getResources().getDrawable(R.drawable.icon_pic_empty));
                }
                if (bean.getState() != null) {
                    switch (bean.getState()) {
                        case "9":
                            tv_revolving_state.setBackground(getResources().getDrawable(R.drawable.icon_dai_shen_he));
                            break;

                        case "10":
                            tv_revolving_state.setBackground(getResources().getDrawable(R.drawable.icon_shen_he_zhong));
                            break;

                        case "11":
                            tv_revolving_state.setBackground(getResources().getDrawable(R.drawable.icon_wei_shen_he_tong_tong_guo));
                            break;

                        case "16":
                            tv_revolving_state.setBackground(getResources().getDrawable(R.drawable.icon_chu_li_zhong));
                            break;

                        case "17":
                            tv_revolving_state.setBackground(getResources().getDrawable(R.drawable.icon_chu_li_wan_cheng));
                            break;
                    }
                }
            }
        };
        lv_revolving_list.setAdapter(baseQuickAdapter);
        baseQuickAdapter.setOnItemClickListener(this);
    }


    @Override
    protected void onResume() {
        super.onResume();
        layout_content.setStatus(LoadingLayout.Loading);
        list.clear();
        page = 1;
        presenter.getPhotoList(page);

    }

    @Override
    protected void onBackward(View backwardView) {
        super.onBackward(backwardView);
        finish();
    }

    @Override
    public RevolvingPresent initPresenter() {
        return new RevolvingPresent(this);
    }

    @Override
    public void onRevolvingList(List<RevolvingListBean> listBeans) {
        list.addAll(listBeans);
        baseQuickAdapter.notifyDataSetChanged();
        layout_content.setStatus(list.size() > 0 ? LoadingLayout.Success : LoadingLayout.Empty);
    }

    @Override
    public void onFiled() {
        layout_content.setStatus(LoadingLayout.Empty);
    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        smart_layout.finishLoadmore(1000);
        page++;
        presenter.getPhotoList(page);
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        smart_layout.finishRefresh(1000);
        list.clear();
        page = 1;
        presenter.getPhotoList(page);
    }


    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        RevolvingListBean bean = (RevolvingListBean) baseQuickAdapter.getItem(position);
        if (bean != null) {
            Intent intent = new Intent(this, RevolvingDetailsActivity.class);
            intent.putExtra("bean", bean);
            startActivity(intent);
        }
    }
}
