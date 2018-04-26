package com.smart.urban.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.smart.urban.R;
import com.smart.urban.base.BaseActivity;
import com.smart.urban.base.BasePresenter;
import com.smart.urban.present.AlterPwdPresent;
import com.smart.urban.utils.SharedPreferencesUtils;
import com.smart.urban.view.IAlterPwdView;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by root on 01.04.18.
 */

public class AlterPwdActivity extends BaseActivity<IAlterPwdView, AlterPwdPresent> implements IAlterPwdView {

    @BindView(R.id.ed_old_pwd)
    EditText ed_old_pwd;
    @BindView(R.id.ed_new_pwd)
    EditText ed_new_pwd;
    @BindView(R.id.ed_sure_pwd)
    EditText ed_sure_pwd;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_alter_pwd;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setTitle("修改密码");
    }

    @Override
    protected void onBackward(View backwardView) {
        super.onBackward(backwardView);
        finish();
    }

    @OnClick({R.id.tv_alter_submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_alter_submit:
                if (StringUtils.isEmpty(ed_old_pwd.getText().toString().trim())) {
                    ToastUtils.showShort("请输入原始密码!");
                    return;
                }

                if (StringUtils.isEmpty(ed_new_pwd.getText().toString().trim())) {
                    ToastUtils.showShort("请输入新密码!");
                    return;
                }

                if (StringUtils.isEmpty(ed_sure_pwd.getText().toString().trim())) {
                    ToastUtils.showShort("请再次确认密码!");
                    return;
                }

                if (!ed_new_pwd.getText().toString().trim().equals(ed_sure_pwd.getText().toString().trim())) {
                    ToastUtils.showShort("两次密码输入不一至,请重新输入!");
                    return;
                }
                Map<String, Object> map = new HashMap<>();
                map.put("id", SharedPreferencesUtils.init(this).getString("userId"));
                map.put("token", SharedPreferencesUtils.init(this).getString("token"));
                map.put("password", ed_old_pwd.getText().toString().trim());
                map.put("newPwd", ed_sure_pwd.getText().toString().trim());
                presenter.getResetPwd(map);
                break;

        }
    }

    @Override
    public AlterPwdPresent initPresenter() {
        return new AlterPwdPresent(this);
    }

    @Override
    public void showLoading() {
        showProgressDialog();
    }

    @Override
    public void hitLoading() {
        dismissProgressDialog();
    }

    @Override
    public void onSuccess() {
        if (MainActivity.instance != null) {
            MainActivity.instance.finish();
            SharedPreferencesUtils.init(this).put("userPass", "");
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }
    }
}
