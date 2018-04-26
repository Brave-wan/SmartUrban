package com.smart.urban.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.smart.urban.R;
import com.smart.urban.base.BaseActivity;
import com.smart.urban.bean.RegisterBean;
import com.smart.urban.present.RegisterPresent;
import com.smart.urban.utils.CountDownTimerUtils;
import com.smart.urban.utils.SharedPreferencesUtils;
import com.smart.urban.view.IRegisterView;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by root on 01.04.18.
 */

public class FindPwdActivity extends BaseActivity<IRegisterView, RegisterPresent> implements IRegisterView {
    @BindView(R.id.ed_register_code)
    EditText ed_register_code;
    @BindView(R.id.ed_register_user)
    EditText ed_register_user;
    @BindView(R.id.ed_register_pass)
    EditText ed_register_pass;
    @BindView(R.id.img_pwd_open)
    ImageView img_pwd_open;
    @BindView(R.id.tv_sms_code)
    TextView tv_sms_code;
    @BindView(R.id.ed_register_pass_sure)
    EditText ed_register_pass_sure;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_find_pwd;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setTitle("找回密码");
        img_pwd_open.setBackground(getResources().getDrawable(R.drawable.icon_login_pwd_open));
    }


    @Override
    protected void onBackward(View backwardView) {
        super.onBackward(backwardView);
        finish();
    }

    @Override
    public RegisterPresent initPresenter() {
        return new RegisterPresent(this);
    }

    private boolean isShow = true;

    @OnClick({R.id.tv_sms_code, R.id.img_pwd_open, R.id.tv_register, R.id.tv_have_account})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_have_account:
                finish();
                break;
            case R.id.tv_sms_code:
                if (ed_register_user.getText().toString().trim().length() != 11) {
                    ToastUtils.showShort("手机号输入不合法!");
                    return;
                }
                Map<String, Object> map = new HashMap<>();
                map.put("mobilePhone", ed_register_user.getText().toString().trim());
                map.put("flag","2");
                presenter.getCode(map);
                //设置倒计时
                new CountDownTimerUtils(this, tv_sms_code, 60000, 1000).start();

                break;

            case R.id.img_pwd_open:
                presenter.setEditOpenState(ed_register_pass, img_pwd_open, isShow);
                isShow = isShow ? false : true;
                break;

            case R.id.tv_register:
                if (ed_register_user.getText().toString().trim().length() != 11) {
                    ToastUtils.showShort("手机号输入不合法!");
                    return;
                }

                if (ed_register_pass.getText().toString().length() < 6) {
                    ToastUtils.showShort("密码输入不合法!");
                    return;
                }

                if (!ed_register_pass_sure.getText().toString().trim().equals(ed_register_pass.getText().toString().trim())) {
                    ToastUtils.showShort("两次密码输入不一致!");
                    return;
                }

                if (StringUtils.isEmpty(ed_register_code.getText().toString().trim())) {
                    ToastUtils.showShort("请输入您的验证码!");
                    return;
                }

                Map<String, Object> register = new HashMap<>();
                register.put("mobilePhone", ed_register_user.getText().toString().trim());
                register.put("password", ed_register_pass.getText().toString().trim());
                register.put("code", ed_register_code.getText().toString().trim());
                presenter.getFindPwd(register);
                break;
        }
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
    public void OnCaptchaCode(RegisterBean bean) {
        //显示获取到的验证码
//        ed_register_code.setText(bean.getCode());
    }

    @Override
    public void onRegisterSuccess() {
        //注册成功
        finish();
        SharedPreferencesUtils.init(this)
                .put("userName", ed_register_user.getText().toString().trim())
                .put("userPass", ed_register_pass.getText().toString().trim());
    }

}
