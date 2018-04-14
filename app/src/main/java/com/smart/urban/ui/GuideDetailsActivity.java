package com.smart.urban.ui;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;

import com.smart.urban.R;
import com.smart.urban.base.BaseActivity;
import com.smart.urban.base.BasePresenter;
import com.smart.urban.base.MyApplication;
import com.smart.urban.bean.GuideDetailsBean;
import com.smart.urban.bean.GuideListBean;
import com.smart.urban.http.ApiCallback;
import com.smart.urban.http.BaseResult;
import com.smart.urban.http.HttpManager;
import com.smart.urban.utils.SharedPreferencesUtils;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

/**
 * Created by root on 18-4-9.
 */

public class GuideDetailsActivity extends BaseActivity {
    private GuideListBean bean;
    @BindView(R.id.webView)
    WebView webView;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_guide_details;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setTitle("报事详情");
        bean = (GuideListBean) getIntent().getSerializableExtra("bean");
        if (bean != null)
            getGuideById();
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

    public void getGuideById() {
        //获取报事详情
        Map<String, Object> map = new HashMap<>();
        map.put("userId", SharedPreferencesUtils.init(this).getString("userId"));
        map.put("token", SharedPreferencesUtils.init(this).getString("token"));
        map.put("id", bean.getId());
        HttpManager.get().addSubscription(HttpManager.get().getApiStores().getGuideById(map), new ApiCallback<BaseResult<GuideDetailsBean>>() {
            @Override
            public void onSuccess(BaseResult<GuideDetailsBean> model) {
                webView.loadUrl("http://www.baidu.com");
            }

            @Override
            public void onFailure(BaseResult result) {

            }
        });

    }
}
