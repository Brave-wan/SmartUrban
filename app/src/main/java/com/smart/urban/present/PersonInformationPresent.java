package com.smart.urban.present;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;

import com.blankj.utilcode.util.ToastUtils;
import com.smart.urban.R;
import com.smart.urban.base.BasePresenter;
import com.smart.urban.bean.PersonalBean;
import com.smart.urban.bean.UpFileBean;
import com.smart.urban.http.ApiCallback;
import com.smart.urban.http.BaseResult;
import com.smart.urban.http.HttpManager;
import com.smart.urban.utils.SharedPreferencesUtils;
import com.smart.urban.view.IPersonInformationView;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.addapp.pickers.listeners.OnItemPickListener;
import cn.addapp.pickers.picker.SinglePicker;
import okhttp3.MultipartBody;

import static com.smart.urban.present.CameraPresent.REQUEST_CODE_CHOOSE;

/**
 * Created by root on 03.04.18.
 */

public class PersonInformationPresent extends BasePresenter<IPersonInformationView> {
    Context mContext;

    public PersonInformationPresent(Context mContext) {
        this.mContext = mContext;
    }

    public void getTakePhoto(Activity activity) {
        Matisse.from(activity)
                .choose(MimeType.allOf()) // 选择 mime 的类型
                .capture(false)
                .countable(true)//自动增长的数目
                .maxSelectable(1) // 图片选择的最多数量
                .captureStrategy(new CaptureStrategy(true, "com.smart.urban.fileprovider"))
                .gridExpectedSize(activity.getResources().getDimensionPixelSize(R.dimen.base_dimen_240))
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                .thumbnailScale(0.85f) // 缩略图的比例
                .imageEngine(new GlideEngine()) // 使用的图片加载引擎
                .forResult(REQUEST_CODE_CHOOSE); // 设置作为标记的请求码
    }


    public void onOptionPicker(final Activity mContext) {
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
                    Map<String, Object> map = new HashMap<>();
                    map.put("id", SharedPreferencesUtils.init(mContext).getString("userId"));
                    map.put("token", SharedPreferencesUtils.init(mContext).getString("token"));
                    map.put("sex", item);
                    getEditInfo(map, 0, item);
                }
            });
            picker.show();
        }
    }


    public void getEditInfo(Map<String, Object> map, final int item, final String sex) {
        if (mView != null) {//编辑个人资料
            HttpManager.get().addSubscription(HttpManager.get().getApiStores().getEditInfo(map), new ApiCallback<BaseResult<PersonalBean>>() {
                @Override
                public void onSuccess(BaseResult<PersonalBean> model) {
                    switch (item) {
                        case 0:
                            //更新本地性别缓存
                            SharedPreferencesUtils.init(mContext).put("center_sex", sex);
                            break;
                        //更新本地的图片缓存
                        case 1:
                            SharedPreferencesUtils.init(mContext).put("center_img", sex);
                            break;
                    }

                }

                @Override
                public void onFailure(BaseResult result) {
                    ToastUtils.showShort(result.errmsg);
                }
            });
        }
    }

    public void getUpFile(MultipartBody.Part[] part) {
        if (mView != null) {//上传文件
            mView.showLoading();
            HttpManager.get().addSubscription(HttpManager.get().getApiStores().getUpdateImage(part), new ApiCallback<BaseResult<List<UpFileBean>>>() {
                @Override
                public void onSuccess(BaseResult<List<UpFileBean>> model) {
                    mView.hitLoading();
                    List<UpFileBean> list = model.getData();
                    if (list.size() > 0) {
                        Map<String, Object> map = new HashMap<>();
                        map.put("id", SharedPreferencesUtils.init(mContext).getString("userId"));
                        map.put("token", SharedPreferencesUtils.init(mContext).getString("token"));
                        map.put("imgAdress", model.data.get(0).getPath());
                        getEditInfo(map, 1, model.data.get(0).getPath());
                    }

                }

                @Override
                public void onFailure(BaseResult result) {
                    ToastUtils.showShort(result.errmsg);
                    mView.hitLoading();
                }
            });

        }

    }
}
