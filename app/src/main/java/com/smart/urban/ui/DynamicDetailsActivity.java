package com.smart.urban.ui;

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

    @Override
    protected int getContentViewId() {
        return R.layout.activity_dynamic_details;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setTitle("详情");
        DynamicListBean bean = (DynamicListBean) getIntent().getSerializableExtra("bean");
        tv_dynamic_details_title.setText(bean.getTitle());
        tv_dynamic_details_content.setText(bean.getContent());
        adapter = new DynamicImgListAdapter(this, R.layout.item_dynamic_details_img, bean.getImages());
        lv_dynamic_list.setAdapter(adapter);
//        for (DynamicListBean.ImagesBean imagesBean : bean.getImages()) {
//            View view = LayoutInflater.from(this).inflate(R.layout.item_dynamic_details_img, null);
//            ImageView imageView = (ImageView) view.findViewById(R.id.img_dynamic);
//            Glide.with(this).load(imagesBean.getAddress())
//                    .error(R.drawable.icon_pic_empty).placeholder(R.drawable.icon_pic_empty).into(imageView);
//            lv_dynamic_list.addView(view);
//        }
//        lv_dynamic_list.n
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
}
