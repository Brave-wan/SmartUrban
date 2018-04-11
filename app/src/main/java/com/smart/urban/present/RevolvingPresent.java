package com.smart.urban.present;

import android.content.Context;

import com.blankj.utilcode.util.ToastUtils;
import com.smart.urban.base.BasePresenter;
import com.smart.urban.bean.RevolvingListBean;
import com.smart.urban.http.ApiCallback;
import com.smart.urban.http.BaseResult;
import com.smart.urban.http.HttpManager;
import com.smart.urban.utils.SharedPreferencesUtils;
import com.smart.urban.view.IRevolvingView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by root on 18-4-10.
 */

public class RevolvingPresent extends BasePresenter<IRevolvingView> {
    private Context mContext;

    public RevolvingPresent(Context mContext) {

    }

    public void getPhotoList(int page) {
        if (mView != null) {

            Map<String, Object> map = new HashMap<>();
            map.put("userId", SharedPreferencesUtils.init(mContext).getString("userId"));
            map.put("token", SharedPreferencesUtils.init(mContext).getString("token"));
            map.put("page", page);
            map.put("rows", 20);
            map.put("createUserId", SharedPreferencesUtils.init(mContext).getString("userId"));
            HttpManager.get().addSubscription(HttpManager.get().getApiStores().getPhotoList(map), new ApiCallback<BaseResult<List<RevolvingListBean>>>() {
                @Override
                public void onSuccess(BaseResult<List<RevolvingListBean>> model) {
                    mView.onRevolvingList(model.getData());
                }

                @Override
                public void onFailure(BaseResult result) {
                    ToastUtils.showShort(result.errmsg);
                    mView.onFiled();
                }
            });
        }
    }
}
