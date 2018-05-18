package com.smart.urban.ui;


import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.blankj.utilcode.util.ToastUtils;
import com.smart.urban.R;
import com.smart.urban.base.BaseActivity;
import com.smart.urban.base.BasePresenter;
import com.smart.urban.bean.CameraPicBean;
import com.smart.urban.fragment.CameraFragment;
import com.smart.urban.fragment.CenterFragment;
import com.smart.urban.fragment.HomeFragment;
import com.smart.urban.fragment.InfoFragment;
import com.smart.urban.utils.GlideLoader;
import com.smart.urban.utils.SharedPreferencesUtils;
import com.yancy.imageselector.ImageConfig;
import com.yancy.imageselector.ImageSelector;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import java.util.ArrayList;
import java.util.List;

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
    public static MainActivity instance = null;
    public static List<CameraPicBean> list = new ArrayList<>();

    @Override
    protected int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        rg_main_bottom.setOnCheckedChangeListener(this);
        list.clear();
        list.add(new CameraPicBean());
        manager = getSupportFragmentManager();
        transaction = manager.beginTransaction();
        instance = this;
        setDefaultFragment();
    }

    public void setDefaultFragment() {
        if (homeFragment == null) {
            homeFragment = new HomeFragment();
        }
        transaction.replace(R.id.main_layout, homeFragment);
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        SharedPreferencesUtils.init(this).put("type_name", "").put("type_id", "");
    }

    private long mExitTime;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                ToastUtils.showShort("连续点击两次,退出应用");
                mExitTime = System.currentTimeMillis();
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
