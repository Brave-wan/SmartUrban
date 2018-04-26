package com.smart.urban.ui;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.smart.urban.R;
import com.smart.urban.base.BaseActivity;
import com.smart.urban.base.BasePresenter;
import com.smart.urban.bean.InfoDetailsBean;
import com.smart.urban.config.Constants;
import com.smart.urban.http.ApiCallback;
import com.smart.urban.http.BaseResult;
import com.smart.urban.http.HttpManager;
import com.smart.urban.ui.dialog.ShareWindow;
import com.smart.urban.utils.LoadingLayout;
import com.smart.urban.utils.MyWebViewClient;
import com.smart.urban.utils.SharedPreferencesUtils;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

/**
 * Created by root on 16.04.18.
 */

public class InfoDetailsActivity extends BaseActivity {
    private String id;
    @BindView(R.id.webView)
    WebView webView;
    @BindView(R.id.tv_create_time)
    TextView tv_create_time;
    @BindView(R.id.tv_see_number)
    TextView tv_see_number;
    @BindView(R.id.layout_root)
    LoadingLayout layout_root;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_info_details;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setTitle("详情");
        layout_root.setStatus(LoadingLayout.Loading);
        id = getIntent().getStringExtra("id");
        setRightImage(R.drawable.share_icon);
        getMessageById();
        initData();
    }

    private void initData() {

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
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected void onBackward(View backwardView) {
        super.onBackward(backwardView);
        finish();
    }

    ShareWindow window;

    @Override
    protected void onForward(View forwardView) {
        super.onForward(forwardView);
        window = new ShareWindow(this);
        window.showWindow(layout_titleBar);
    }

    public void getMessageById() {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", SharedPreferencesUtils.init(this).getString("userId"));
        map.put("token", SharedPreferencesUtils.init(this).getString("token"));
        map.put("id", id);
        HttpManager.get().addSubscription(HttpManager.get().getApiStores().getMessageById(map), new ApiCallback<BaseResult<InfoDetailsBean>>() {
            @Override
            public void onSuccess(BaseResult<InfoDetailsBean> model) {
                layout_root.setStatus(LoadingLayout.Success);
                tv_create_time.setText(model.getData().getCreateTime());
                tv_see_number.setText(model.getData().getViewCount() + "");
                webView.loadData(Constants.getHtmlData(model.getData().getContent()), "text/html;charset=utf-8", "utf-8");
            }

            @Override
            public void onFailure(BaseResult result) {
                ToastUtils.showShort(result.errmsg);
                layout_root.setStatus(LoadingLayout.Error);
            }
        });
    }
}
