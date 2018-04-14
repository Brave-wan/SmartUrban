package com.smart.urban.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.smart.urban.R;
import com.smart.urban.base.BaseActivity;
import com.smart.urban.base.BasePresenter;
import com.smart.urban.bean.DynamicListBean;
import com.smart.urban.config.Constants;
import com.smart.urban.ui.adapter.DynamicImgListAdapter;
import com.smart.urban.widget.MyListView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by root on 18-4-10.
 */

public class DynamicDetailsActivity extends BaseActivity {
    @BindView(R.id.lv_dynamic_list)
    ListView lv_dynamic_list;
    @BindView(R.id.tv_dynamic_details_title)
    TextView tv_dynamic_details_title;
    @BindView(R.id.tv_dynamic_details_content)
    TextView tv_dynamic_details_content;
    DynamicImgListAdapter adapter;
    @BindView(R.id.tv_see_number)
    TextView tv_see_number;
    @BindView(R.id.tv_comment_number)
    TextView tv_comment_number;
    @BindView(R.id.tv_create_time)
    TextView tv_create_time;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_dynamic_details;
    }

    DynamicListBean bean;

    @Override
    protected void initView(Bundle savedInstanceState) {
        setTitle("详情");
        bean = (DynamicListBean) getIntent().getSerializableExtra("bean");
        tv_dynamic_details_title.setText(bean.getTitle());
        tv_dynamic_details_content.setText(bean.getContent());
        adapter = new DynamicImgListAdapter(this, R.layout.item_dynamic_details_img, bean.getImages());
        lv_dynamic_list.setAdapter(adapter);
        tv_comment_number.setText(bean.getCommentCount() + "");
        tv_see_number.setText(bean.getViewCount() + "");
        tv_create_time.setText(bean.getCreateTime());

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

    @OnClick({R.id.rl_bottom_root})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_bottom_root:
                Intent intent = new Intent(this, CommentListActivity.class);
                intent.putExtra("id", bean.getId() + "");
                intent.putExtra("type", "dynamic");
                startActivity(intent);
                break;
        }
    }
}
