package com.smart.urban.present;

import android.content.Context;

import com.blankj.utilcode.util.ToastUtils;
import com.smart.urban.base.BasePresenter;
import com.smart.urban.bean.UrbanDetailsBean;
import com.smart.urban.http.ApiCallback;
import com.smart.urban.http.BaseResult;
import com.smart.urban.http.HttpManager;
import com.smart.urban.utils.SharedPreferencesUtils;
import com.smart.urban.view.IBannerDynamicDetailsView;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by root on 18-4-23.
 */

public class BannerDynamicDetailsPresent extends BasePresenter<IBannerDynamicDetailsView> {
    Context mContext;

    public BannerDynamicDetailsPresent(Context mContext) {
        this.mContext = mContext;
    }

    public void getBannerDetails(String id) {
        //获取动态详情
        Map<String, Object> map = new HashMap<>();
        map.put("userId", SharedPreferencesUtils.init(mContext).getString("userId"));
        map.put("token", SharedPreferencesUtils.init(mContext).getString("token"));
        map.put("id", id);
        HttpManager.get().addSubscription(HttpManager.get().getApiStores().getDynamicById(map), new ApiCallback<BaseResult<UrbanDetailsBean>>() {
            @Override
            public void onSuccess(BaseResult<UrbanDetailsBean> model) {
                mView.onUrbanDetailsBean(model.getData());
            }

            @Override
            public void onFailure(BaseResult result) {
                ToastUtils.showShort(result.errmsg);
            }
        });
    }


    public void getMessageById(String id) {
        //获取资讯详情
        Map<String, Object> map = new HashMap<>();
        map.put("userId", SharedPreferencesUtils.init(mContext).getString("userId"));
        map.put("token", SharedPreferencesUtils.init(mContext).getString("token"));
        map.put("id", id);
        HttpManager.get().addSubscription(HttpManager.get().getApiStores().getMessageById(map), new ApiCallback() {
            @Override
            public void onSuccess(Object model) {

            }

            @Override
            public void onFailure(BaseResult result) {

            }
        });
    }
}
