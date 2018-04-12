package com.smart.urban.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.smart.urban.R;
import com.smart.urban.base.BaseActivity;
import com.smart.urban.base.BasePresenter;
import com.smart.urban.bean.LostBean;
import com.smart.urban.ui.adapter.LostDetailsAdapter;
import com.smart.urban.ui.widget.ShowImageWindow;

import butterknife.BindView;

/**
 * Created by root on 18-4-12.
 */

public class LostDetailsActivity extends BaseActivity implements AdapterView.OnItemClickListener {
    @BindView(R.id.gv_lost_details)
    GridView gv_lost_details;
    @BindView(R.id.tv_details_title)
    TextView tv_details_title;
    @BindView(R.id.tv_details_phone)
    TextView tv_details_phone;
    LostBean bean;
    LostDetailsAdapter adapter;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_lost_details;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setTitle("详情");
        bean = (LostBean) getIntent().getSerializableExtra("bean");
        tv_details_title.setText(bean.getContent());
        tv_details_phone.setText("联系方式:" + bean.getContact());
        adapter = new LostDetailsAdapter(this, R.layout.item_camera_list, bean.getImages());
        gv_lost_details.setAdapter(adapter);
        gv_lost_details.setOnItemClickListener(this);
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        LostBean.ImagesBean bean = (LostBean.ImagesBean) adapter.getItem(position);
        ShowImageWindow window = new ShowImageWindow(this, bean.getAddress());
        window.showWindow(view);
    }

    @Override
    protected void onBackward(View backwardView) {
        super.onBackward(backwardView);
        finish();
    }
}
