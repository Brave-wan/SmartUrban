package com.smart.urban.present;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;

import com.blankj.utilcode.util.ToastUtils;
import com.smart.urban.R;
import com.smart.urban.base.BasePresenter;
import com.smart.urban.view.IPersonInformationView;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import java.util.ArrayList;

import cn.addapp.pickers.listeners.OnItemPickListener;
import cn.addapp.pickers.picker.SinglePicker;

import static com.smart.urban.present.CameraPresent.REQUEST_CODE_CHOOSE;

/**
 * Created by root on 03.04.18.
 */

public class PersonInformationPresent extends BasePresenter<IPersonInformationView> {


    public void getTakePhoto(Activity activity) {
        Matisse.from(activity)
                .choose(MimeType.allOf()) // 选择 mime 的类型
                .capture(true)
                .countable(true)//自动增长的数目
                .maxSelectable(1) // 图片选择的最多数量
                .captureStrategy(new CaptureStrategy(true, "com.szt.myapplicationee.fileprovider"))
                .gridExpectedSize(activity.getResources().getDimensionPixelSize(R.dimen.base_dimen_240))
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                .thumbnailScale(0.85f) // 缩略图的比例
                .imageEngine(new GlideEngine()) // 使用的图片加载引擎
                .forResult(REQUEST_CODE_CHOOSE); // 设置作为标记的请求码
    }


    public void onOptionPicker(Activity mContext) {
        if (mView != null) {
            ArrayList<String> list = new ArrayList<>();
            list.add("男");
            list.add("女");
            SinglePicker<String> picker = new SinglePicker<>(mContext, list);
            picker.setCanLoop(false);//不禁用循环
            picker.setLineVisible(true);
            picker.setTextSize(18);
            picker.setSelectedIndex(8);
            picker.setWheelModeEnable(false);
            //启用权重 setWeightWidth 才起作用
            picker.setWeightEnable(true);
            picker.setWeightWidth(1);
            picker.setSelectedTextColor(mContext.getResources().getColor(R.color.black));//前四位值是透明度
            picker.setUnSelectedTextColor(mContext.getResources().getColor(R.color.login_txt_gray));

            picker.setOnItemPickListener(new OnItemPickListener<String>() {
                @Override
                public void onItemPicked(int index, String item) {
                    mView.onSex(item);
                }
            });
            picker.show();
        }
    }
}
