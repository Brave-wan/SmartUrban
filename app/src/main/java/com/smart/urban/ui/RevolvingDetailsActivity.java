package com.smart.urban.ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.smart.urban.R;
import com.smart.urban.base.BaseActivity;
import com.smart.urban.base.BasePresenter;
import com.smart.urban.bean.BannerBean;
import com.smart.urban.bean.RevolvingDetailsBean;
import com.smart.urban.bean.RevolvingListBean;
import com.smart.urban.http.ApiCallback;
import com.smart.urban.http.BaseResult;
import com.smart.urban.http.HttpManager;
import com.smart.urban.ui.adapter.RevolvingDetailsListAdapter;
import com.smart.urban.ui.widget.ShowImageWindow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * Created by root on 18-4-10.
 */

public class RevolvingDetailsActivity extends BaseActivity implements AdapterView.OnItemClickListener {
    RevolvingListBean bean;
    @BindView(R.id.gv_revolving_details)
    GridView gv_revolving_details;
    @BindView(R.id.tv_revolving_title)
    TextView tv_revolving_title;
    @BindView(R.id.lv_revolving_comment)
    RecyclerView lv_revolving_comment;
    RevolvingDetailsListAdapter adapter;
    List<RevolvingDetailsBean.AllStateBean> list = new ArrayList<>();
    BaseQuickAdapter<RevolvingDetailsBean.AllStateBean, BaseViewHolder> baseQuickAdapter;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_revolving_details;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setTitle("详情");
        bean = (RevolvingListBean) getIntent().getSerializableExtra("bean");
        tv_revolving_title.setText(bean.getContent());
        lv_revolving_comment.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RevolvingDetailsListAdapter(this, R.layout.item_camera_list, bean.getImages());
        gv_revolving_details.setAdapter(adapter);
        gv_revolving_details.setOnItemClickListener(this);
        getForumById();

        baseQuickAdapter = new BaseQuickAdapter<RevolvingDetailsBean.AllStateBean, BaseViewHolder>(R.layout.item_revolving_detail, list) {
            @Override
            protected void convert(BaseViewHolder helper, RevolvingDetailsBean.AllStateBean item) {

                TextView tv_revolving_name = (TextView) helper.itemView.findViewById(R.id.tv_revolving_name);
                TextView tv_revolving_content = (TextView) helper.itemView.findViewById(R.id.tv_revolving_content);
                TextView tv_revolving_time = (TextView) helper.itemView.findViewById(R.id.tv_revolving_time);

                tv_revolving_name.setText(StringUtils.isEmpty(item.getName()) ? "暂无" : item.getName());
                tv_revolving_time.setText(item.getModifyTime());
                tv_revolving_content.setText(item.getContent()+"");
            }
        };

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
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        RevolvingListBean.ImagesBean bean = (RevolvingListBean.ImagesBean) adapter.getItem(position);
        ShowImageWindow window = new ShowImageWindow(this, bean.getAddress());
        window.showWindow(layout_titleBar);
    }

    public void getForumById() {
        Map<String, Object> map = new HashMap<>();
        map.put("id", bean.getId() + "");
        HttpManager.get().addSubscription(HttpManager.get().getApiStores().getForumById(map), new ApiCallback<BaseResult<RevolvingDetailsBean>>() {
            @Override
            public void onSuccess(BaseResult<RevolvingDetailsBean> model) {
                list.addAll(model.getData().getAllState());
                lv_revolving_comment.setAdapter(baseQuickAdapter);
            }

            @Override
            public void onFailure(BaseResult result) {
                ToastUtils.showShort(result.errmsg);
            }
        });

    }
}
