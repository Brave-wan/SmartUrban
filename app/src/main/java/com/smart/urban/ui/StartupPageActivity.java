package com.smart.urban.ui;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.smart.urban.R;
import com.smart.urban.base.BaseActivity;
import com.smart.urban.present.StartupPagePresent;
import com.smart.urban.view.IStartupPageView;

import butterknife.BindView;

/**
 * Created by root on 18-3-28.
 */

public class StartupPageActivity extends BaseActivity<IStartupPageView, StartupPagePresent> {
    @BindView(R.id.img_start_page)
    ImageView img_start_page;
    @BindView(R.id.ll_start_up)
    LinearLayout ll_start_up;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_startup_pagee;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        presenter.startAnimation(ll_start_up);
    }

    @Override
    public StartupPagePresent initPresenter() {
        return new StartupPagePresent(this);
    }
}
