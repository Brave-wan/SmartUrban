package com.smart.urban.ui;

import android.os.Bundle;
import android.view.View;

import com.smart.urban.R;
import com.smart.urban.base.BaseActivity;
import com.smart.urban.present.UrbanDetailsPresent;
import com.smart.urban.view.IUrbanDetailsView;

/**
 * Created by root on 18-4-11.
 */

public class UrbanDetailsActivity extends BaseActivity<IUrbanDetailsView, UrbanDetailsPresent> implements IUrbanDetailsView {
    @Override
    protected int getContentViewId() {
        return R.layout.activity_urban_details;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setTitle("详情");

    }

    @Override
    protected void onBackward(View backwardView) {
        super.onBackward(backwardView);
        finish();
    }

    @Override
    public UrbanDetailsPresent initPresenter() {
        return new UrbanDetailsPresent(this);
    }
}
