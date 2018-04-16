package com.smart.urban.ui;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.smart.urban.R;
import com.smart.urban.base.BaseActivity;
import com.smart.urban.bean.UrbanListBean;
import com.smart.urban.config.Constants;
import com.smart.urban.http.ApiCallback;
import com.smart.urban.http.BaseResult;
import com.smart.urban.http.HttpManager;
import com.smart.urban.present.UrbanDetailsPresent;
import com.smart.urban.ui.dialog.ShareWindow;
import com.smart.urban.utils.LoadingLayout;
import com.smart.urban.utils.MyWebViewClient;
import com.smart.urban.utils.SharedPreferencesUtils;
import com.smart.urban.view.IUrbanDetailsView;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by root on 18-4-11.
 */

public class UrbanDetailsActivity extends BaseActivity<IUrbanDetailsView, UrbanDetailsPresent> implements IUrbanDetailsView {
    @BindView(R.id.webView)
    WebView webView;
    @BindView(R.id.tv_see_number)
    TextView tv_see_number;
    @BindView(R.id.tv_comment_number)
    TextView tv_comment_number;
    @BindView(R.id.tv_create_time)
    TextView tv_create_time;
    @BindView(R.id.layout_root)
    LoadingLayout layout_root;
    UrbanListBean bean;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_urban_details;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setTitle("详情");
        layout_root.setStatus(LoadingLayout.Loading);
        bean = (UrbanListBean) getIntent().getSerializableExtra("bean");
        tv_comment_number.setText(bean.getCommentCount() + "");
        tv_see_number.setText(bean.getViewCount() + "");
        tv_create_time.setText(bean.getCreateTime() + "");
        initData();
        getDynamicById();
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
    protected void onForward(View forwardView) {
        super.onForward(forwardView);
        ShareWindow window = new ShareWindow(this);
        window.showWindow(forwardView);
    }

    @Override
    protected void onBackward(View backwardView) {
        super.onBackward(backwardView);
        finish();
    }

    @Override
    public UrbanDetailsPresent initPresenter() {
        return new UrbanDetailsPresent(this);
    }


    public void getDynamicById() {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", SharedPreferencesUtils.init(this).getString("userId"));
        map.put("token", SharedPreferencesUtils.init(this).getString("token"));
        map.put("id", bean.getId());
        HttpManager.get().addSubscription(HttpManager.get().getApiStores().getDynamicById(map), new ApiCallback<BaseResult<UrbanListBean>>() {
            @Override
            public void onSuccess(BaseResult<UrbanListBean> model) {
                layout_root.setStatus(LoadingLayout.Success);
                webView.loadData(Constants.getHtmlData(model.getData().getContent()), "text/html;charset=utf-8", "utf-8");
            }

            @Override
            public void onFailure(BaseResult result) {
                ToastUtils.showShort(result.errmsg);
                layout_root.setStatus(LoadingLayout.Error);
            }
        });

    }

    @OnClick({R.id.rl_bottom_root})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_bottom_root:
                Intent intent = new Intent(this, CommentListActivity.class);
                intent.putExtra("id", bean.getId() + "");
                intent.putExtra("type", "urban");
                startActivity(intent);
                break;
        }
    }
}
