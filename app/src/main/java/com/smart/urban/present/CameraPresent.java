package com.smart.urban.present;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.blankj.utilcode.util.ToastUtils;
import com.google.gson.Gson;
import com.smart.urban.R;
import com.smart.urban.base.BasePresenter;
import com.smart.urban.bean.ImagesBean;
import com.smart.urban.bean.UpFileBean;
import com.smart.urban.config.Constants;
import com.smart.urban.http.ApiCallback;
import com.smart.urban.http.BaseResult;
import com.smart.urban.http.HttpManager;
import com.smart.urban.ui.dialog.UpDynamicDialog;
import com.smart.urban.utils.SharedPreferencesUtils;
import com.smart.urban.view.ICameraView;

import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by root on 18-3-29.
 */

public class CameraPresent extends BasePresenter<ICameraView> implements AMapLocationListener {
    public static int REQUEST_CODE_CHOOSE = 0x123;
    Context mContext;

    public CameraPresent(Context mContext) {
        this.mContext = mContext;
    }

    List<ImagesBean> list = new ArrayList<>();
    private String content;
    private String phone;
    private String toast;

    public void getUpFile(MultipartBody.Part part, final int position, final String content, final int length) {
        this.content = content;
        if (mView != null) {//上传文件
            HttpManager.get().addSubscription(HttpManager.get().getApiStores().getUploadFile(part), new ApiCallback<BaseResult<UpFileBean>>() {
                @Override
                public void onSuccess(BaseResult<UpFileBean> model) {
                    ImagesBean imagesBean = new ImagesBean();
                    imagesBean.setOrder(position);
                    //'1是资讯的图片，2是随手拍的图片，3是动态的图片，4是用户头像',
                    imagesBean.setAddress(model.data.getPath());
                    list.add(imagesBean);
                    //批量上传
                    if (length == position) {
                        Message message = new Message();
                        message.obj = 0x111;
                        handler.sendMessage(message);
                    }
                }

                @Override
                public void onFailure(BaseResult result) {
                    ToastUtils.showShort(result.errmsg);
                }
            });
        }
    }

    /**
     * 多张图片上传
     *
     * @param parts
     * @param content
     */
    public void getUpFiles(MultipartBody.Part[] parts, String content) {
        this.content = content;
        toast = mContext.getResources().getString(R.string.camera_up_dynamic);
        if (mView != null) {
            HttpManager.get().addSubscription(HttpManager.get().getApiStores().getUpdateImage(parts), new ApiCallback<BaseResult<List<UpFileBean>>>() {
                @Override
                public void onSuccess(BaseResult<List<UpFileBean>> model) {
                    for (UpFileBean bean : model.data) {
                        ImagesBean imagesBean = new ImagesBean();
                        imagesBean.setOrder(bean.getIndex());
                        //'1是资讯的图片，2是随手拍的图片，3是动态的图片，4是用户头像',
                        imagesBean.setAddress(bean.getPath());
                        list.add(imagesBean);
                    }
                    Message message = new Message();
                    message.obj = 0x111;
                    handler.sendMessage(message);
                }

                @Override
                public void onFailure(BaseResult result) {
                    ToastUtils.showShort(result.errmsg);
                }
            });
        }

    }


    public void getPicList() {
        list.clear();
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch ((int) msg.obj) {
                case 0x111:
                    Map<String, Object> map = new HashMap<>();
                    map.put("userId", SharedPreferencesUtils.init(mContext).getString("userId"));
                    map.put("token", SharedPreferencesUtils.init(mContext).getString("token"));
                    map.put("images", list);
                    map.put("content", content);
                    map.put("addrName",address);
                    map.put("createUserId", SharedPreferencesUtils.init(mContext).getString("userId"));
                    getRevolving(map);
                    break;

                case 0x12:
                    UpDynamicDialog dynamicDialog = new UpDynamicDialog(mContext);
                    dynamicDialog.show();
                    dynamicDialog.setContent(mContext.getString(R.string.camera_up_dynamic));
                    mView.onUpSuccess(dynamicDialog);
                    break;
            }
        }
    };


    public void getRevolving(Map<String, Object> map) {
        if (mView != null) {
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .writeTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(20, TimeUnit.SECONDS)
                    .build();
            Gson gson = new Gson();
            //使用Gson将对象转换为json字符串
            String json = gson.toJson(map);
            Log.i("wan", "json:" + json);
            //MediaType  设置Content-Type 标头中包含的媒体类型值
            final RequestBody requestBody = FormBody.create(MediaType.parse("application/json; charset=utf-8"), json);
            Request request = new Request.Builder()
                    .url(Constants.BASE_URL + "cityApp/photo/add")//请求的url
                    .post(requestBody)
                    .build();
            //创建/Call
            Call call = okHttpClient.newCall(request);
            //加入队列 异步操作
            call.enqueue(new Callback() {
                //请求错误回调方法
                @Override
                public void onFailure(Call call, IOException e) {
                    ToastUtils.showShort(e.getMessage());
                    dismissProgressDialog();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String body = response.body().string();
                    System.out.println(body);
                    dismissProgressDialog();
                    try {
                        JSONObject jsonObject = new JSONObject(body);
                        String code = jsonObject.getString("errcode");
                        if (code.equals("200")) {
                            Message message = new Message();
                            message.obj = 0x12;
                            handler.sendMessage(message);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    //声明mlocationClient对象
    public AMapLocationClient mlocationClient;
    public AMapLocationClientOption mLocationOption = null;

    public void initLocation() {
        mlocationClient = new AMapLocationClient(mContext);
        mLocationOption = new AMapLocationClientOption();
        mlocationClient.setLocationListener(this);
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setInterval(2000);
        mlocationClient.setLocationOption(mLocationOption);
        // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
        // 注意设置合适的定位时间的间隔（最小间隔支持为1000ms），并且在合适时间调用stopLocation()方法来取消定位请求
        // 在定位结束后，在合适的生命周期调用onDestroy()方法
        // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
        //启动定位
        mlocationClient.startLocation();
    }


    public void stopLocation() {
        if (mlocationClient != null) {
            mlocationClient.stopLocation();
        }
    }

    public ProgressDialog progressDialog;

    public ProgressDialog showProgressDialog() {
        progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("加载中...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        return progressDialog;
    }


    public void dismissProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            // progressDialog.hide();会导致android.view.WindowLeaked
            progressDialog.dismiss();
        }
    }

    String address = null;

    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (amapLocation != null) {
            if (amapLocation.getErrorCode() == 0) {
                //定位成功回调信息，设置相关消息
                amapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
                amapLocation.getLatitude();//获取纬度
                amapLocation.getLongitude();//获取经度
                amapLocation.getAccuracy();//获取精度信息
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = new Date(amapLocation.getTime());
                df.format(date);//定位时间
                address = amapLocation.getAddress();
                Log.i("wan", "lont" + amapLocation.getAddress());
            } else {
                //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                Log.e("AmapError", "location Error, ErrCode:" + amapLocation.getErrorCode() + ", errInfo:" + amapLocation.getErrorInfo());
            }
        }
    }
}
