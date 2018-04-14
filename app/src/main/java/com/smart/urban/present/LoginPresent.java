package com.smart.urban.present;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.EditText;

import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.smart.urban.base.BasePresenter;
import com.smart.urban.bean.PersonalBean;
import com.smart.urban.bean.RegisterBean;
import com.smart.urban.http.ApiCallback;
import com.smart.urban.http.BaseResult;
import com.smart.urban.http.HttpManager;
import com.smart.urban.utils.SharedPreferencesUtils;
import com.smart.urban.view.ILoginView;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.utils.SocializeUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by root on 18-3-30.
 */

public class LoginPresent extends BasePresenter<ILoginView> implements UMAuthListener {
    private Activity mContext;
    private ProgressDialog dialog;

    public LoginPresent(Activity mContext) {
        this.mContext = mContext;
        dialog = new ProgressDialog(mContext);
    }

    public void AuthLogin(SHARE_MEDIA e) {
        //初始化shareApi
        UMShareAPI.get(mContext).doOauthVerify(mContext, e, this);
    }

    public void fetchAuthResultWithBundle(Bundle savedInstanceState) {
        UMShareAPI.get(mContext).fetchAuthResultWithBundle(mContext, savedInstanceState, new UMAuthListener() {
            @Override
            public void onStart(SHARE_MEDIA platform) {
                SocializeUtils.safeShowDialog(dialog);
            }

            @Override
            public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
                ToastUtils.showShort("onRestoreInstanceState Authorize succeed");
                SocializeUtils.safeCloseDialog(dialog);
            }

            @Override
            public void onError(SHARE_MEDIA platform, int action, Throwable t) {
                ToastUtils.showShort("onRestoreInstanceState Authorize onError");
                SocializeUtils.safeCloseDialog(dialog);
            }

            @Override
            public void onCancel(SHARE_MEDIA platform, int action) {
                ToastUtils.showShort("onRestoreInstanceState Authorize onCancel");
                SocializeUtils.safeCloseDialog(dialog);
            }
        });
    }

    @Override
    public void onStart(SHARE_MEDIA share_media) {
        ToastUtils.showShort("onStart");
    }

    @Override
    public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
        ToastUtils.showShort("onComplete");
    }

    @Override
    public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
        ToastUtils.showShort("onError");
    }

    @Override
    public void onCancel(SHARE_MEDIA share_media, int i) {
        ToastUtils.showShort("onCancel");
    }

    /**
     * 用户登陆
     *
     * @param userName 　用户名
     * @param pass     　登陆密码
     */
    public void getLogin(EditText userName, EditText pass) {
        if (mView != null) {

            Map<String, Object> map = new HashMap<>();
            if (userName.getText().toString().trim().length() != 11) {
                ToastUtils.showShort("手机号输入不合法,请重新输入");
                return;
            }
            if (StringUtils.isEmpty(pass.getText().toString().trim())) {
                ToastUtils.showShort("请输入登陆密码!");
                return;
            }
            mView.showLoading();
            map.put("loginAcct", userName.getText().toString().trim());
            map.put("password", pass.getText().toString().trim());
            HttpManager.get().addSubscription(HttpManager.get().getApiStores().getToLogin(map), new ApiCallback<BaseResult<RegisterBean>>() {
                @Override
                public void onSuccess(BaseResult<RegisterBean> model) {
                    mView.OnLoginSuccess(model.getData());
                    mView.hitLoading();
                    //获取用户的个人信息
                    getMyDetails(model.getData().getToken(), model.getData().getUserId());
                }

                @Override
                public void onFailure(BaseResult result) {
                    ToastUtils.showShort(result.errmsg);
                    mView.hitLoading();
                }
            });
        }
    }

    /**
     * 获取用户基本信息
     *
     * @param token
     * @param id
     */
    public void getMyDetails(String token, String id) {
        if (mView != null) {
            final Map<String, Object> map = new HashMap<>();
            map.put("id", id);
            map.put("token", token);
            HttpManager.get().addSubscription(HttpManager.get().getApiStores().getMyDetails(map), new ApiCallback<BaseResult<PersonalBean>>() {
                @Override
                public void onSuccess(BaseResult<PersonalBean> model) {
                    PersonalBean bean = model.data;
                    SharedPreferencesUtils.init(mContext).put("center_sex", bean.getSex())
                            .put("center_name", bean.getNickName())
                            .put("center_head", bean.getImgAdress());

                }

                @Override
                public void onFailure(BaseResult result) {
                    ToastUtils.showShort(result.getErrmsg());
                }
            });
        }
    }


}
