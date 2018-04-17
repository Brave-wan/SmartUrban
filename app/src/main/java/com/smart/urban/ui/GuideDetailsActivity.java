package com.smart.urban.ui;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import com.smart.urban.R;
import com.smart.urban.base.BaseActivity;
import com.smart.urban.base.BasePresenter;
import com.smart.urban.base.MyApplication;
import com.smart.urban.bean.GuideDetailsBean;
import com.smart.urban.bean.GuideListBean;
import com.smart.urban.config.Constants;
import com.smart.urban.http.ApiCallback;
import com.smart.urban.http.BaseResult;
import com.smart.urban.http.HttpManager;
import com.smart.urban.utils.MyWebViewClient;
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
    @BindView(R.id.tv_guide_title)
    TextView tv_guide_title;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_guide_details;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setTitle("报事详情");
        bean = (GuideListBean) getIntent().getSerializableExtra("bean");

        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        webView.setWebViewClient(new MyWebViewClient(this));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING);
        } else {
            settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
        }
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
                Log.i("wan", "content===>" + model.getData().getContent());
                tv_guide_title.setText(model.getData().getTitle());
                webView.loadData(Constants.getHtmlData(model.getData().getContent()), "text/html;charset=utf-8", "utf-8");
            }

            @Override
            public void onFailure(BaseResult result) {

            }
        });

    }
}
