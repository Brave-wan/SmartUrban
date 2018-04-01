package com.smart.urban.ui;

import android.os.Bundle;
import android.text.Selection;
import android.text.Spannable;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.smart.urban.R;
import com.smart.urban.base.BaseActivity;
import com.smart.urban.base.BasePresenter;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by root on 01.04.18.
 */

public class RegisterActivity extends BaseActivity {
    @BindView(R.id.ed_register_code)
    EditText ed_register_code;
    @BindView(R.id.ed_register_pass)
    EditText ed_register_pass;
    @BindView(R.id.img_pwd_open)
    ImageView img_pwd_open;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_register;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setTitle("注册");
        img_pwd_open.setBackground(getResources().getDrawable(R.drawable.icon_login_pwd_open));
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

    private boolean isShow = true;

    @OnClick({R.id.tv_sms_code, R.id.img_pwd_open, R.id.tv_register, R.id.tv_have_account})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_have_account:
                finish();
                break;
            case R.id.tv_sms_code:
                break;

            case R.id.img_pwd_open:
                ed_register_pass.setTransformationMethod(isShow ?
                        HideReturnsTransformationMethod.getInstance()
                        : PasswordTransformationMethod.getInstance());
                isShow = isShow ? false : true;
                ed_register_pass.postInvalidate();
                img_pwd_open.setBackground(getResources().getDrawable(isShow ? R.drawable.icon_login_pwd_open : R.drawable.icon_login_pwd_close));
                CharSequence text = ed_register_pass.getText();
                if (text instanceof Spannable) {
                    Spannable spanText = (Spannable) text;
                    Selection.setSelection(spanText, text.length());
                }
                break;

            case R.id.tv_register:
                finish();
                break;
        }
    }
}
