package com.smart.urban.present;

import android.content.Context;

import com.blankj.utilcode.util.ToastUtils;
import com.smart.urban.base.BasePresenter;
import com.smart.urban.bean.RevolvingDetailsBean;
import com.smart.urban.http.ApiCallback;
import com.smart.urban.http.BaseResult;
import com.smart.urban.http.HttpManager;
import com.smart.urban.view.IRevolvingDetailsView;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by root on 18-5-18.
 */

public class RevolvingDetailsPresent extends BasePresenter<IRevolvingDetailsView> {

    private Context mContext;

    public RevolvingDetailsPresent(Context mContext) {
        this.mContext = mContext;

    }

    public void getDelete(String id) {
        if (mView != null) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", id);
            HttpManager.get().addSubscription(HttpManager.get().getApiStores().getRemoveDate(map), new ApiCallback() {
                @Override
                public void onSuccess(Object model) {
                }

                @Override
                public void onFailure(BaseResult result) {
                }
            });
        }
    }


    public void getForumById(long id) {
        if (mView!=null){
            Map<String, Object> map = new HashMap<>();
            map.put("id", id);
            HttpManager.get().addSubscription(HttpManager.get().getApiStores().getForumById(map), new ApiCallback<BaseResult<RevolvingDetailsBean>>() {
                @Override
                public void onSuccess(BaseResult<RevolvingDetailsBean> model) {
                    mView.onRevolvingDetails(model.getData());

                }

                @Override
                public void onFailure(BaseResult result) {
                    ToastUtils.showShort(result.errmsg);
                }
            });
        }
    }
}
