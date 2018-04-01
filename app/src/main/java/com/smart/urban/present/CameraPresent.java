package com.smart.urban.present;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;

import com.smart.urban.R;
import com.smart.urban.base.BasePresenter;
import com.smart.urban.view.ICameraView;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

/**
 * Created by root on 18-3-29.
 */

public class CameraPresent extends BasePresenter<ICameraView> {
    public static int REQUEST_CODE_CHOOSE = 0x123;
    Context mContext;

    public CameraPresent(Context mContext) {
        this.mContext = mContext;
    }

    public void getTakePhoto(Activity activity) {
        Matisse.from(activity)
                .choose(MimeType.allOf()) // 选择 mime 的类型
                .capture(true)
                .countable(true)//自动增长的数目
                .maxSelectable(6) // 图片选择的最多数量
                .captureStrategy(new CaptureStrategy(true, "com.szt.myapplicationee.fileprovider"))
                .gridExpectedSize(activity.getResources().getDimensionPixelSize(R.dimen.base_dimen_240))
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                .thumbnailScale(0.85f) // 缩略图的比例
                .imageEngine(new GlideEngine()) // 使用的图片加载引擎
                .forResult(REQUEST_CODE_CHOOSE); // 设置作为标记的请求码
    }

}
