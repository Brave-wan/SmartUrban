package com.smart.urban.present;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;

import com.blankj.utilcode.util.ToastUtils;
import com.smart.urban.base.BasePresenter;
import com.smart.urban.view.ILoginView;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.utils.SocializeUtils;

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


}
