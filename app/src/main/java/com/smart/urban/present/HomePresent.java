package com.smart.urban.present;

import android.content.Context;

import com.blankj.utilcode.util.ToastUtils;
import com.smart.urban.base.BasePresenter;
import com.smart.urban.bean.BannerBean;
import com.smart.urban.bean.UrbanListBean;
import com.smart.urban.http.ApiCallback;
import com.smart.urban.http.BaseResult;
import com.smart.urban.http.HttpManager;
import com.smart.urban.utils.SharedPreferencesUtils;
import com.smart.urban.view.IHomeView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by root on 18-4-11.
 */

public class HomePresent extends BasePresenter<IHomeView> {
    Context mContext;

    public HomePresent(Context mContext) {
        this.mContext = mContext;

    }


    public void getForumList() {
        final Map<String, Object> map = new HashMap<>();
        map.put("userId", SharedPreferencesUtils.init(mContext).getString("userId"));
        map.put("token", SharedPreferencesUtils.init(mContext).getString("token"));
        map.put("page", 1);
        map.put("rows", 5);
        HttpManager.get().addSubscription(HttpManager.get().getApiStores().getDynameicList(map), new ApiCallback<BaseResult<List<UrbanListBean>>>() {
            @Override
            public void onSuccess(BaseResult<List<UrbanListBean>> model) {
                if (model.getData() != null) {
                    mView.onDynamicList(model.getData());
                }
            }

            @Override
            public void onFailure(BaseResult result) {
                ToastUtils.showShort(result.errmsg);

            }
        });
    }

    public void getBannerList() {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", SharedPreferencesUtils.init(mContext).getString("userId"));
        map.put("token", SharedPreferencesUtils.init(mContext).getString("token"));
        HttpManager.get().addSubscription(HttpManager.get().getApiStores().getBannerList(map), new ApiCallback<BaseResult<List<BannerBean>>>() {
            @Override
            public void onSuccess(BaseResult<List<BannerBean>> model) {
                mView.onBannerList(model.getData());

            }

            @Override
            public void onFailure(BaseResult result) {
                ToastUtils.showShort(result.errmsg);
            }
        });

    }
}
