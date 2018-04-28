package com.smart.urban.present;

import android.app.Activity;
import android.content.Intent;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.StringUtils;
import com.smart.urban.base.BasePresenter;
import com.smart.urban.ui.LoginActivity;
import com.smart.urban.ui.MainActivity;
import com.smart.urban.utils.SharedPreferencesUtils;
import com.smart.urban.view.IStartupPageView;

/**
 * Created by root on 18-3-28.
 */

public class StartupPagePresent extends BasePresenter<IStartupPageView> {

    private Activity mContext;

    public StartupPagePresent(Activity mContext) {
        this.mContext = mContext;
    }


    public void startAnimation(LinearLayout startup) {
        //启动页动画
        AlphaAnimation animation = new AlphaAnimation(0.3f, 1.0f);
        animation.setDuration(2000);
        startup.startAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationEnd(Animation arg0) {
                String userName = SharedPreferencesUtils.init(mContext).getString("userName");
                mContext.startActivity(new Intent(mContext, StringUtils.isEmpty(userName) ? LoginActivity.class : MainActivity.class));
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
