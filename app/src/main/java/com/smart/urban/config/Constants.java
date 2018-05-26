package com.smart.urban.config;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.util.Log;
import android.view.View;

import com.blankj.utilcode.util.ToastUtils;
import com.smart.urban.R;
import com.smart.urban.bean.CameraPicBean;
import com.smart.urban.bean.RevolvingListBean;
import com.smart.urban.ui.MainActivity;
import com.smart.urban.ui.UrbanDetailsActivity;
import com.smart.urban.ui.dialog.SelectImageWindow;
import com.smart.urban.ui.widget.CustomPopWindows;
import com.smart.urban.utils.GlideLoader;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.yancy.imageselector.ImageConfig;
import com.yancy.imageselector.ImageSelector;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import java.io.File;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static com.smart.urban.present.CameraPresent.REQUEST_CODE_CHOOSE;

/**
 * Created by root on 18-3-28.
 */

public class Constants {
    //    http://111.231.222.163:8080/data/uploads/20180514/4e1eb6b26ded28b5763656ccb29aff77.html
    public static final String BASE_URL = "http://111.231.222.163:8080/";
//         public static final String BASE_URL = "http://47.105.53.173:8080/";
//     public static final String BASE_URL = "http://10.15.209.34:8080/city/";
//     public static final String BASE_URL = "http://qxzhcg.quanquanma.cn/";

    //是否开启打印日志信息
    public static final boolean DEBUG = true;

    public static MultipartBody.Part prepareFilePart(String partName, String path) {
        File file = new File(path);
        try {
            if (file.exists()) {
                RequestBody requestFile = RequestBody.create(MediaType.parse("image/png"), file);
                return MultipartBody.Part.createFormData(partName, file.getName(), requestFile);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 多张图片上传
     *
     * @param cameraPicBeans
     * @return
     */
    public static MultipartBody.Part[] getFileMaps(List<CameraPicBean> cameraPicBeans) {
        cameraPicBeans.remove(cameraPicBeans.size() - 1);
        MultipartBody.Part[] pars = new MultipartBody.Part[cameraPicBeans.size()];
        for (int i = 0; i < cameraPicBeans.size(); i++) {
            CameraPicBean bean = cameraPicBeans.get(i);
            if (bean.getPic() != null) {
                pars[i] = prepareFilePart("files", bean.getPic());
            }
        }
        return pars;
    }

    public static MultipartBody.Part[] getRevolvingMap(List<RevolvingListBean.ImagesBean> cameraPicBeans) {
        MultipartBody.Part[] pars = new MultipartBody.Part[cameraPicBeans.size()];
        for (int i = 0; i < cameraPicBeans.size(); i++) {
            RevolvingListBean.ImagesBean bean = cameraPicBeans.get(i);
            if (bean.getAddress() != null) {
                pars[i] = prepareFilePart("files", bean.getAddress());
            }
        }
        return pars;
    }

    public static String getHtmlData(String bodyHTML) {
        String head = "<head>" +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, user-scalable=no\"> " +
                "<style>html{padding:15px;} body{word-wrap:break-word;font-size:13px;padding:0px;margin:0px} p{padding:0px;margin:0px;font-size:13px;color:#222222;line-height:1.3;} img{padding:0px,margin:0px;max-width:100%; width:auto; height:auto;}</style>" +
                "</head>";
        return "<html>" + head + "<body>" + bodyHTML + "</body></html>";
    }

    /**
     * 分享
     *
     * @param activity 　当前窗口
     * @param title    　标题
     * @param desc     　内容
     * @param url      　分享链接
     */
    public static void getSharePlatform(Activity activity, String title, String desc, String url) {
        UMWeb web = new UMWeb(url);
        web.setTitle("石家庄桥西智慧城管");
        web.setThumb(new UMImage(activity, R.mipmap.ic_launcher));
        web.setDescription(desc);
        new ShareAction(activity).withMedia(web)
                .setPlatform(SHARE_MEDIA.WEIXIN)
                .setCallback(new UMShareListener() {
                    @Override
                    public void onStart(SHARE_MEDIA share_media) {
                        Log.i("wan", "onStart");
                    }

                    @Override
                    public void onResult(SHARE_MEDIA share_media) {
                        Log.i("wan", "onResult");
                    }

                    @Override
                    public void onError(SHARE_MEDIA share_media, Throwable throwable) {
                        Log.i("wan", "onError");
                    }

                    @Override
                    public void onCancel(SHARE_MEDIA share_media) {
                        Log.i("wan", "onCancel");
                    }
                }).share();
    }


    /**
     * @param mContext
     * @param layout_title_bar
     * @param number
     */
    public static void takePhoto(final Activity mContext, View layout_title_bar, final int number) {
        final SelectImageWindow window = new SelectImageWindow(mContext);
        window.showWindow(layout_title_bar);
        window.setOnSelectImageListener(new SelectImageWindow.OnSelectImageListener() {
            @Override
            public void onSelectType(int type) {
                ImageConfig imageConfig = new ImageConfig.Builder(new GlideLoader())
                        .steepToolBarColor(mContext.getResources().getColor(R.color.white))
                        .titleBgColor(mContext.getResources().getColor(R.color.white))
                        .titleSubmitTextColor(mContext.getResources().getColor(R.color.black))
                        .titleTextColor(mContext.getResources().getColor(R.color.black))
                        // 开启多选（默认为多选）
                        .mutiSelect()
                        // 多选时的最大数量（默认 9 张）
                        .mutiSelectMaxSize(number)
                        // 开启拍照功能 （默认关闭）
                        .showCamera(false)
                        .selectType(type == 1 ? true : false)//默认相册　true 为相机　false 相册
                        // 拍照后存放的图片路径（默认 /temp/picture） （会自动创建）
                        .build();
                ImageSelector.open(mContext, imageConfig);
                window.dismissWindow();
            }
        });


    }
}
