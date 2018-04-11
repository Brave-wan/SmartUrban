package com.smart.urban.present;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.ToastUtils;
import com.mylhyl.acp.Acp;
import com.mylhyl.acp.AcpListener;
import com.mylhyl.acp.AcpOptions;
import com.smart.urban.base.BasePresenter;
import com.smart.urban.ui.LocationActivity;
import com.smart.urban.ui.LoginActivity;
import com.smart.urban.ui.MainActivity;
import com.smart.urban.view.IStartupPageView;

import java.util.List;

/**
 * Created by root on 18-3-28.
 */

public class StartupPagePresent extends BasePresenter<IStartupPageView> {

    private Activity mContext;

    public StartupPagePresent(Activity mContext) {
        this.mContext = mContext;
        getCameraPermission();
    }

    public void getCameraPermission() {
        //获取相机权限
        Acp.getInstance(mContext).request(new AcpOptions.Builder()
                        .setPermissions(Manifest.permission.CAMERA,
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.ACCESS_FINE_LOCATION)
                        .build(),
                new AcpListener() {
                    @Override
                    public void onGranted() {
                    }

                    @Override
                    public void onDenied(List<String> permissions) {
                        ToastUtils.showShort(permissions.toString() + "权限拒绝");
                    }
                });
    }


    public void startAnimation(LinearLayout startup) {
        //启动页动画
        AlphaAnimation animation = new AlphaAnimation(0.3f, 1.0f);
        animation.setDuration(2000);
        startup.startAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationEnd(Animation arg0) {
                mContext.startActivity(new Intent(mContext, LoginActivity.class));
                mContext.finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationStart(Animation animation) {
            }
        });
    }
}
