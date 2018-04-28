package com.smart.urban.ui;

import android.os.Bundle;
import android.view.View;

import com.blankj.utilcode.util.ToastUtils;
import com.smart.urban.R;
import com.smart.urban.base.BaseActivity;
import com.smart.urban.base.BasePresenter;

import butterknife.OnClick;

/**
 * Created by root on 03.04.18.
 */

public class AboutUsActivity extends BaseActivity {
    @Override
    protected int getContentViewId() {
        return R.layout.activity_about_us;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setTitle("关于我们");

    }

    @Override
    protected void onBackward(View backwardView) {
        super.onBackward(backwardView);
        finish();
    }

    @OnClick({R.id.btn_version})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_version:
                ToastUtils.showShort("已经是最新版本!");
                break;
        }
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }
}
