package com.smart.urban.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.Selection;
import android.text.Spannable;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.blankj.utilcode.util.ToastUtils;
import com.smart.urban.R;
import com.smart.urban.base.BaseActivity;
import com.smart.urban.bean.RegisterBean;
import com.smart.urban.present.LoginPresent;
import com.smart.urban.utils.SharedPreferencesUtils;
import com.smart.urban.view.ILoginView;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by root on 18-3-30.
 */

public class LoginActivity extends BaseActivity<ILoginView, LoginPresent> implements ILoginView {
    @BindView(R.id.ed_login_user)
    EditText ed_login_user;
    @BindView(R.id.ed_login_pass)
    EditText ed_login_pass;
    @BindView(R.id.img_pwd_open)
    ImageView img_pwd_open;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setTitle("登陆");
        presenter.fetchAuthResultWithBundle(savedInstanceState);
        img_pwd_open.setBackground(getResources().getDrawable(R.drawable.icon_login_pwd_open));
    }


    @Override
    public LoginPresent initPresenter() {
        return new LoginPresent(this);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        UMShareAPI.get(this).onSaveInstanceState(outState);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        UMShareAPI.get(this).release();
    }

    @Override
    protected void onResume() {
        super.onResume();
        ed_login_user.setText(SharedPreferencesUtils.init(this).getString("userName"));
        ed_login_pass.setText(SharedPreferencesUtils.init(this).getString("userPass"));
    }

    boolean isShow = true;

    @OnClick({R.id.img_login_wchat, R.id.img_login_qq, R.id.tv_login_in, R.id.img_pwd_open, R.id.tv_login_not_register, R.id.tv_login_find_pwd})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_login_in:
//                startActivity(new Intent(this, MainActivity.class));

                presenter.getLogin(ed_login_user, ed_login_pass);
                break;

            case R.id.img_login_wchat:
//                ToastUtils.showShort("暂未开放");
                presenter.AuthLogin(SHARE_MEDIA.WEIXIN);
                break;

            case R.id.img_login_qq:
//                presenter.AuthLogin(SHARE_MEDIA.QQ);
                ToastUtils.showShort("暂未开放");
                break;

            case R.id.tv_login_find_pwd:
                startActivity(new Intent(this, FindPwdActivity.class));
                break;

            case R.id.tv_login_not_register:
                startActivity(new Intent(this, RegisterActivity.class));
                break;

            case R.id.img_pwd_open:
                ed_login_pass.setTransformationMethod(isShow ? HideReturnsTransformationMethod.getInstance() : PasswordTransformationMethod.getInstance());
                isShow = isShow ? false : true;
                ed_login_pass.postInvalidate();
                img_pwd_open.setBackground(getResources().getDrawable(isShow ? R.drawable.icon_login_pwd_open : R.drawable.icon_login_pwd_close));

                CharSequence text = ed_login_pass.getText();
                if (text instanceof Spannable) {
                    Spannable spanText = (Spannable) text;
                    Selection.setSelection(spanText, text.length());
                }
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
    public void OnLoginSuccess(RegisterBean bean) {
        //登陆成功,保存用户token和用户id
        SharedPreferencesUtils.init(this)
                .put("token", bean.getToken())
                .put("userId", bean.getUserId())
                .put("userName", ed_login_user.getText().toString().trim())
                .put("userPass", ed_login_pass.getText().toString().trim());

        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
