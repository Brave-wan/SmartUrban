package com.smart.urban.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.smart.urban.R;
import com.smart.urban.base.BaseFragment;
import com.smart.urban.base.BasePresenter;
import com.smart.urban.config.Constants;
import com.smart.urban.ui.AboutUsActivity;
import com.smart.urban.ui.AlterPwdActivity;
import com.smart.urban.ui.LostActivity;
import com.smart.urban.ui.PersonInformationActivity;
import com.smart.urban.ui.RevolvingActivity;
import com.smart.urban.utils.GlideCircleTransform;
import com.smart.urban.utils.SharedPreferencesUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by root on 18-3-29.
 */

public class CenterFragment extends BaseFragment {
    @BindView(R.id.img_center_head)
    ImageView img_center_head;
    @BindView(R.id.tv_center_name)
    TextView tv_center_name;
    @BindView(R.id.tv_center_sex)
    TextView tv_center_sex;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_center;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        setTitle("我的");
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }


    @Override
    public void onResume() {
        super.onResume();
        tv_center_name.setText("昵称:" + SharedPreferencesUtils.init(getActivity()).getString("center_name"));
        tv_center_sex.setText("性别:" + SharedPreferencesUtils.init(getActivity()).getString("center_sex"));
        String url = Constants.BASE_URL + SharedPreferencesUtils.init(getActivity()).getString("center_img");
        Glide.with(getActivity()).load(url)
                .error(R.drawable.icon_my_portraits)
                .placeholder(R.drawable.icon_my_portraits)
                .bitmapTransform(new GlideCircleTransform(getActivity()))
                .into(img_center_head);
    }

    @OnClick({R.id.img_center_head, R.id.tv_my_alter_pwd, R.id.tv_my_lost, R.id.tv_about_us, R.id.tv_center_revolving})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_center_revolving:
                startActivity(new Intent(getActivity(), RevolvingActivity.class));
                break;
            case R.id.tv_my_alter_pwd:
                startActivity(new Intent(getActivity(), AlterPwdActivity.class));
                break;
            case R.id.img_center_head:
                startActivity(new Intent(getActivity(), PersonInformationActivity.class));
                break;
            case R.id.tv_my_lost:
                startActivity(new Intent(getActivity(), LostActivity.class));
                break;
            case R.id.tv_about_us:
                startActivity(new Intent(getActivity(), AboutUsActivity.class));
                break;
        }
    }
}
