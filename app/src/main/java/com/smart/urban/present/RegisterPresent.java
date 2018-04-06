package com.smart.urban.present;

import android.content.Context;
import android.text.Selection;
import android.text.Spannable;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.widget.EditText;
import android.widget.ImageView;

import com.blankj.utilcode.util.ToastUtils;
import com.smart.urban.R;
import com.smart.urban.base.BasePresenter;
import com.smart.urban.bean.RegisterBean;
import com.smart.urban.http.ApiCallback;
import com.smart.urban.http.BaseResult;
import com.smart.urban.http.HttpManager;
import com.smart.urban.view.IRegisterView;

import java.util.Map;

/**
 * Created by root on 06.04.18.
 */

public class RegisterPresent extends BasePresenter<IRegisterView> {


    private Context mContext;

    public RegisterPresent(Context context) {
        this.mContext = context;
    }

    /**
     * 设置密码是否可见状态
     *
     * @param ed_register_pass
     * @param img_pwd_open
     * @param isShow
     */
    public void setEditOpenState(EditText ed_register_pass, ImageView img_pwd_open, boolean isShow) {
        ed_register_pass.setTransformationMethod(isShow ?
                HideReturnsTransformationMethod.getInstance()
                : PasswordTransformationMethod.getInstance());
        isShow = isShow ? false : true;
        ed_register_pass.postInvalidate();
        img_pwd_open.setBackground(mContext.getResources().getDrawable(isShow ? R.drawable.icon_login_pwd_open : R.drawable.icon_login_pwd_close));
        CharSequence text = ed_register_pass.getText();
        if (text instanceof Spannable) {
            Spannable spanText = (Spannable) text;
            Selection.setSelection(spanText, text.length());
        }
    }

    /**
     * 获取验证码
     *
     * @param map 请求参数集合
     */
    public void getCode(Map<String, Object> map) {
        if (mView != null) {
            mView.showLoading();
            HttpManager.get().addSubscription(HttpManager.get().getApiStores().getCaptchaCode(map), new ApiCallback<BaseResult<RegisterBean>>() {
                @Override
                public void onSuccess(BaseResult<RegisterBean> model) {
                    mView.OnCaptchaCode(model.getData());
                    mView.hitLoading();
                }

                @Override
                public void onFailure(BaseResult result) {
                    ToastUtils.showShort(result.errmsg);
                    mView.hitLoading();
                }
            });
        }
    }

    public void toRegister(Map<String, Object> map) {
        if (mView != null) {
            mView.showLoading();
            HttpManager.get().addSubscription(HttpManager.get().getApiStores().getToRegister(map), new ApiCallback<BaseResult<RegisterBean>>() {
                @Override
                public void onSuccess(BaseResult<RegisterBean> model) {
                    mView.onRegisterSuccess();
                    mView.hitLoading();
                }

                @Override
                public void onFailure(BaseResult result) {
                    ToastUtils.showShort(result.getErrmsg());
                    mView.hitLoading();
                }
            });
        }

    }
}
