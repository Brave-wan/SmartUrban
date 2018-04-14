package com.smart.urban.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

import com.smart.urban.R;
import com.smart.urban.base.BaseActivity;
import com.smart.urban.bean.UrbanListBean;
import com.smart.urban.present.UrbanDetailsPresent;
import com.smart.urban.view.IUrbanDetailsView;

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
    UrbanListBean bean;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_urban_details;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setTitle("详情");
        bean = (UrbanListBean) getIntent().getSerializableExtra("bean");
        tv_comment_number.setText(bean.getCommentCount() + "");
        tv_see_number.setText(bean.getViewCount() + "");
        tv_create_time.setText(bean.getCreateTime() + "");
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
