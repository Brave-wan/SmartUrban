package com.smart.urban.ui;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.blankj.utilcode.util.ToastUtils;
import com.smart.urban.R;
import com.smart.urban.base.BaseActivity;
import com.smart.urban.base.BasePresenter;
import com.smart.urban.bean.CameraPicBean;
import com.smart.urban.config.Constants;
import com.smart.urban.fragment.CameraFragment;
import com.smart.urban.fragment.CenterFragment;
import com.smart.urban.fragment.HomeFragment;
import com.smart.urban.fragment.InfoFragment;
import com.smart.urban.utils.Const;
import com.smart.urban.utils.GlideLoader;
import com.smart.urban.utils.SharedPreferencesUtils;
import com.tencent.bugly.beta.Beta;
import com.tencent.bugly.beta.UpgradeInfo;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.utils.SocializeUtils;
import com.yancy.imageselector.ImageConfig;
import com.yancy.imageselector.ImageSelector;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    protected void onResume() {
        super.onResume();
        loadUpgradeInfo();
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
                transaction.replace(R.id.main_layout, homeFragment, "homeFragment");
                transaction.commit();
                break;
            //咨询
            case R.id.radio_main_info:
                if (infoFragment == null) {
                    infoFragment = new InfoFragment();
                }
                transaction.replace(R.id.main_layout, infoFragment, "infoFragment");
                transaction.commit();
                break;
            //相机
            case R.id.radio_main_camera:
                if (cameraFragment == null) {
                    cameraFragment = new CameraFragment();
                }
                transaction.replace(R.id.main_layout, cameraFragment, "cameraFragment");
                transaction.commit();
                break;
            //个人中心
            case R.id.radio_main_center:
                if (centerFragment == null) {
                    centerFragment = new CenterFragment();
                }
                transaction.replace(R.id.main_layout, centerFragment, "centerFragment");
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

    private void loadUpgradeInfo() {
//        Beta.checkUpgrade();
        if (Constants.isUpDate) {
            Beta.checkUpgrade(false, false);

            /***** 获取升级信息 *****/
            UpgradeInfo upgradeInfo = Beta.getUpgradeInfo();
            if (upgradeInfo == null) {
                return;
            }

            Beta.checkUpgrade(false, true);

            StringBuilder info = new StringBuilder();
            info.append("id: ").append(upgradeInfo.id).append("\n");
            info.append("标题: ").append(upgradeInfo.title).append("\n");
            info.append("升级说明: ").append(upgradeInfo.newFeature).append("\n");
            info.append("versionCode: ").append(upgradeInfo.versionCode).append("\n");
            info.append("versionName: ").append(upgradeInfo.versionName).append("\n");
            info.append("发布时间: ").append(upgradeInfo.publishTime).append("\n");
            info.append("安装包Md5: ").append(upgradeInfo.apkMd5).append("\n");
            info.append("安装包下载地址: ").append(upgradeInfo.apkUrl).append("\n");
            info.append("安装包大小: ").append(upgradeInfo.fileSize).append("\n");
            info.append("弹窗间隔（ms）: ").append(upgradeInfo.popInterval).append("\n");
            info.append("弹窗次数: ").append(upgradeInfo.popTimes).append("\n");
            info.append("发布类型（0:测试 1:正式）: ").append(upgradeInfo.publishType).append("\n");
            info.append("弹窗类型（1:建议 2:强制 3:手工）: ").append(upgradeInfo.upgradeType).append("\n");
            info.append("图片地址：").append(upgradeInfo.imageUrl);
            Log.i("update", "更新方式:" + upgradeInfo.upgradeType);
            switch (upgradeInfo.upgradeType) {
                //建议
                case 1:
                    if (Constants.isUpDate) {
                        upVersion();
                    }
                    break;
                //强制
                case 2:
                    Beta.checkUpgrade();
                    break;
                //手工
                case 3:
                    Beta.checkUpgrade();
                    break;
            }
        }
    }

    public void upVersion() {
        //更新提醒
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("有新的版本升级更新,请前往我的-关于我们点击更新")
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Constants.isUpDate = false;
                        dialog.dismiss();
                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Constants.isUpDate = false;
                        startActivity(new Intent(MainActivity.this, AboutUsActivity.class));
                        dialog.dismiss();
                    }
                })
                //点击取消
                .setTitle("更新提醒")
                .setCancelable(true)
                .show();
    }

}
