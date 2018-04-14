package com.smart.urban.ui;


import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.RadioGroup;

import com.smart.urban.R;
import com.smart.urban.base.BaseActivity;
import com.smart.urban.base.BasePresenter;
import com.smart.urban.fragment.CameraFragment;
import com.smart.urban.fragment.CenterFragment;
import com.smart.urban.fragment.HomeFragment;
import com.smart.urban.fragment.InfoFragment;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import butterknife.BindView;

import static com.smart.urban.present.CameraPresent.REQUEST_CODE_CHOOSE;

public class MainActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {
    @BindView(R.id.rg_main_bottom)
    RadioGroup rg_main_bottom;
    FragmentManager manager;
    FragmentTransaction transaction;
    HomeFragment homeFragment;
    InfoFragment infoFragment;
    CameraFragment cameraFragment;
    CenterFragment centerFragment;


    @Override
    protected int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        rg_main_bottom.setOnCheckedChangeListener(this);
        manager = getSupportFragmentManager();
        transaction = manager.beginTransaction();
        setDefaultFragment();
    }

    public void setDefaultFragment() {
        homeFragment = new HomeFragment();
        transaction.add(R.id.main_layout, homeFragment);
        transaction.commit();
        rg_main_bottom.check(R.id.radio_main_home);
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        manager = getSupportFragmentManager();
        transaction = manager.beginTransaction();
        switch (checkedId) {
            //首页
            case R.id.radio_main_home:
                if (homeFragment == null) {
                    homeFragment = new HomeFragment();
                }
                transaction.replace(R.id.main_layout, homeFragment);
                transaction.commit();
                break;
            //咨询
            case R.id.radio_main_info:
                if (infoFragment == null) {
                    infoFragment = new InfoFragment();
                }
                transaction.replace(R.id.main_layout, infoFragment);
                transaction.commit();
                break;
            //相机
            case R.id.radio_main_camera:
                if (cameraFragment == null) {
                    cameraFragment = new CameraFragment();
                }
                transaction.replace(R.id.main_layout, cameraFragment);
                transaction.commit();
                break;
            //个人中心
            case R.id.radio_main_center:
                if (centerFragment == null) {
                    centerFragment = new CenterFragment();
                }
                transaction.replace(R.id.main_layout, centerFragment);
                transaction.commit();
                break;
        }

    }


    public void getCamera() {
        manager = getSupportFragmentManager();
        transaction = manager.beginTransaction();
        if (cameraFragment == null) {
            cameraFragment = new CameraFragment();
        }
        transaction.replace(R.id.main_layout, cameraFragment);
        transaction.commit();
        rg_main_bottom.check(R.id.radio_main_camera);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        cameraFragment.onActivityResult(requestCode, resultCode, data);
    }

    public void getTakePhoto() {
        Matisse.from(this)
                .choose(MimeType.allOf()) // 选择 mime 的类型
                .capture(true)
                .countable(true)//自动增长的数目
                .maxSelectable(3) // 图片选择的最多数量
                .captureStrategy(new CaptureStrategy(true, "com.szt.myapplicationee.fileprovider"))
                .gridExpectedSize(getResources().getDimensionPixelSize(R.dimen.base_dimen_240))
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                .thumbnailScale(0.85f) // 缩略图的比例
                .imageEngine(new GlideEngine()) // 使用的图片加载引擎
                .forResult(REQUEST_CODE_CHOOSE); // 设置作为标记的请求码
    }


}
