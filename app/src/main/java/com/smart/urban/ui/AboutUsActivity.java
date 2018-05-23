package com.smart.urban.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.smart.urban.R;
import com.smart.urban.base.BaseActivity;
import com.smart.urban.base.BasePresenter;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.beta.Beta;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by root on 03.04.18.
 */

public class AboutUsActivity extends BaseActivity {
    @BindView(R.id.tv_version)
    TextView tv_version;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_about_us;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setTitle("关于我们");
        AppUtils.AppInfo info = AppUtils.getAppInfo();
        tv_version.setText(info.getVersionName());
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
                Beta.checkUpgrade();
                break;
        }
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }
}
