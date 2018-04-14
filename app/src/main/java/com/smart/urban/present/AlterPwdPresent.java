package com.smart.urban.present;

import android.content.Context;

import com.blankj.utilcode.util.ToastUtils;
import com.smart.urban.base.BasePresenter;
import com.smart.urban.http.ApiCallback;
import com.smart.urban.http.BaseResult;
import com.smart.urban.http.HttpManager;
import com.smart.urban.view.IAlterPwdView;

import java.util.Map;

/**
 * Created by root on 18-4-9.
 */

public class AlterPwdPresent extends BasePresenter<IAlterPwdView> {
    private Context mContext;

    public AlterPwdPresent(Context mContext) {
        this.mContext = mContext;
    }


    public void getResetPwd(Map<String, Object> map) {
        if (mView != null) {
            mView.showLoading();
            HttpManager.get().addSubscription(HttpManager.get().getApiStores().getResetPwd(map), new ApiCallback() {
                @Override
                public void onSuccess(Object model) {
                    mView.hitLoading();
                    mView.onSuccess();
                }

                @Override
                public void onFailure(BaseResult result) {
                    mView.hitLoading();
                    ToastUtils.showShort(result.getErrmsg());
                }
            });
        }

    }
}
