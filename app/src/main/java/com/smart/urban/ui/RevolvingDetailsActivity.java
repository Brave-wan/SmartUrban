package com.smart.urban.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;

import com.smart.urban.R;
import com.smart.urban.base.BaseActivity;
import com.smart.urban.base.BasePresenter;
import com.smart.urban.bean.RevolvingListBean;
import com.smart.urban.ui.adapter.RevolvingDetailsListAdapter;

import butterknife.BindView;

/**
 * Created by root on 18-4-10.
 */

public class RevolvingDetailsActivity extends BaseActivity {
    RevolvingListBean bean;
    @BindView(R.id.gv_revolving_details)
    GridView gv_revolving_details;
    @BindView(R.id.tv_revolving_title)
    TextView tv_revolving_title;
    RevolvingDetailsListAdapter adapter;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_revolving_details;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setTitle("详情");
        bean = (RevolvingListBean) getIntent().getSerializableExtra("bean");
        tv_revolving_title.setText(bean.getContent());
        adapter = new RevolvingDetailsListAdapter(this, R.layout.item_camera_list, bean.getImages());
        gv_revolving_details.setAdapter(adapter);
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
}
