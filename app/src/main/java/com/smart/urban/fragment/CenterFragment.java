package com.smart.urban.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.smart.urban.R;
import com.smart.urban.base.BaseFragment;
import com.smart.urban.base.BasePresenter;
import com.smart.urban.ui.AlterPwdActivity;
import com.smart.urban.ui.LostActivity;
import com.smart.urban.ui.PersonInformationActivity;

import butterknife.OnClick;

/**
 * Created by root on 18-3-29.
 */

public class CenterFragment extends BaseFragment {
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_center;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        setTitle("我的");
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @OnClick({R.id.img_center_head, R.id.tv_my_alter_pwd, R.id.tv_my_lost})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_my_alter_pwd:
                startActivity(new Intent(getActivity(), AlterPwdActivity.class));
                break;
            case R.id.img_center_head:
                startActivity(new Intent(getActivity(), PersonInformationActivity.class));
                break;
            case R.id.tv_my_lost:
                startActivity(new Intent(getActivity(),LostActivity.class));
                break;
        }
    }
}
