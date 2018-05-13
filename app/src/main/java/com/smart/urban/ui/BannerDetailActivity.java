package com.smart.urban.ui;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.smart.urban.R;
import com.smart.urban.base.BaseActivity;
import com.smart.urban.base.BasePresenter;
import com.smart.urban.config.Constants;
import com.smart.urban.ui.dialog.ShareWindow;
import com.smart.urban.utils.MyWebViewClient;

import butterknife.BindView;

public class BannerDetailActivity extends BaseActivity {
    @BindView(R.id.webView)
    WebView webView;
    private String url;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_banner_details;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setTitle("详情");
//        showForwardView(null, false);
        url = getIntent().getStringExtra("url");
        initData();

    }

    @Override
    protected void onBackward(View backwardView) {
        super.onBackward(backwardView);
        finish();
    }


    @Override
    protected void onForward(View forwardView) {
        super.onForward(forwardView);

        String musicurl = "https://y.qq.com/n/yqq/song/108782194_num.html?ADTAG=h5_playsong&no_redirect=1";
        String desc = "欢迎使用【友盟+】社会化组件U-Share，SDK包最小，集成成本最低，助力您的产品开发、运营与推广";
        String title = "石家庄桥西智慧城管";
        Constants.getSharePlatform(this, title, desc, musicurl);

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
        webView.loadUrl(url);
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }
}
