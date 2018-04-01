package com.smart.urban.ui;

import android.os.Bundle;
import android.view.View;

import com.smart.urban.R;
import com.smart.urban.base.BaseActivity;
import com.smart.urban.base.BasePresenter;

/**
 * Created by root on 18-3-30.
 */

public class PersonInformationActivity extends BaseActivity {
    @Override
    protected int getContentViewId() {
        return R.layout.activity_personal_information;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setTitle("个人资料");
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
