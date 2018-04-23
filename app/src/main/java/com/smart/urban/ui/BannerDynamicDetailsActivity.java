package com.smart.urban.ui;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.smart.urban.R;
import com.smart.urban.base.BaseActivity;
import com.smart.urban.base.BasePresenter;
import com.smart.urban.bean.DynamicListBean;
import com.smart.urban.bean.UrbanDetailsBean;
import com.smart.urban.config.Constants;
import com.smart.urban.http.ApiCallback;
import com.smart.urban.http.BaseResult;
import com.smart.urban.http.HttpManager;
import com.smart.urban.present.BannerDynamicDetailsPresent;
import com.smart.urban.ui.adapter.DynamicImgListAdapter;
import com.smart.urban.ui.widget.ShowImageWindow;
import com.smart.urban.utils.MyWebViewClient;
import com.smart.urban.utils.SharedPreferencesUtils;
import com.smart.urban.view.IBannerDynamicDetailsView;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by root on 18-4-10.
 */

public class BannerDynamicDetailsActivity extends BaseActivity<IBannerDynamicDetailsView, BannerDynamicDetailsPresent> implements IBannerDynamicDetailsView {
    @BindView(R.id.webView)
    WebView webView;

    String id;

    String type;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_banner_dynamic_details;
    }


    @Override
    protected void initView(Bundle savedInstanceState) {
        setTitle("详情");
        id = getIntent().getStringExtra("id");
        type=getIntent().getStringExtra("type");
        if (type.equals("1")) {
            //动态
            presenter.getBannerDetails(id);
        } else {
            //咨询
            presenter.getMessageById(id);

        }
        initData();
    }


    private void initData() {
        setRightImage(R.drawable.share_icon);
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
    }

    @Override
    protected void onBackward(View backwardView) {
        super.onBackward(backwardView);
        finish();
    }

    @Override
    public BannerDynamicDetailsPresent initPresenter() {
        return new BannerDynamicDetailsPresent(this);
    }

    @Override
    public void onUrbanDetailsBean(UrbanDetailsBean bean) {

        webView.loadData(Constants.getHtmlData(bean.getContent()), "text/html;charset=utf-8", "utf-8");


    }
}
