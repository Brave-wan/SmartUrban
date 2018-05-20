package com.smart.urban.present;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.blankj.utilcode.util.ToastUtils;
import com.google.gson.Gson;
import com.smart.urban.R;
import com.smart.urban.base.BasePresenter;
import com.smart.urban.bean.ImagesBean;
import com.smart.urban.bean.RevolvingDetailsBean;
import com.smart.urban.bean.RevolvingListBean;
import com.smart.urban.bean.UpFileBean;
import com.smart.urban.config.Constants;
import com.smart.urban.http.ApiCallback;
import com.smart.urban.http.BaseResult;
import com.smart.urban.http.HttpManager;
import com.smart.urban.ui.dialog.UpDynamicDialog;
import com.smart.urban.utils.SharedPreferencesUtils;
import com.smart.urban.view.IRevolvingDetailsView;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
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
 * Created by root on 18-5-18.
 */

public class RevolvingDetailsPresent extends BasePresenter<IRevolvingDetailsView> {

    private Context mContext;

    public RevolvingDetailsPresent(Context mContext) {
        this.mContext = mContext;
    }

    /**
     * 删除随手拍
     *
     * @param id
     */
    public void getDeleteRevolving(String id) {
        if (mView != null) {
            showProgressDialog();
            Map<String, Object> map = new HashMap<>();
            map.put("id", id);
            HttpManager.get().addSubscription(HttpManager.get().getApiStores().getRemoveDate(map), new ApiCallback() {
                @Override
                public void onSuccess(Object model) {
                    mView.onDeleteSuccess();
                    dismissProgressDialog();
                }

                @Override
                public void onFailure(BaseResult result) {
                    ToastUtils.showShort(result.getErrmsg());
                    dismissProgressDialog();
                }
            });
        }
    }


    public void getForumById(long id) {
        if (mView != null) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", id);
            HttpManager.get().addSubscription(HttpManager.get().getApiStores().getForumById(map), new ApiCallback<BaseResult<RevolvingDetailsBean>>() {
                @Override
                public void onSuccess(BaseResult<RevolvingDetailsBean> model) {
                    mView.onRevolvingDetails(model.getData());

                }

                @Override
                public void onFailure(BaseResult result) {
                    ToastUtils.showShort(result.errmsg);
                }
            });
        }
    }

    private RevolvingListBean bean;
    private List<RevolvingListBean.ImagesBean> imagesBeans = new ArrayList<>();
    private String phone;
    private String toast;

    /**
     * 多张图片上传
     *
     * @param parts
     */
    public void getUpFiles(MultipartBody.Part[] parts, RevolvingListBean bean, List<RevolvingListBean.ImagesBean> imagesBeans) {
        this.bean = bean;
        this.imagesBeans = imagesBeans;
        toast = mContext.getResources().getString(R.string.camera_up_dynamic);
        if (mView != null && parts != null) {
            showProgressDialog();
            HttpManager.get().addSubscription(HttpManager.get().getApiStores().getUpdateImage(parts), new ApiCallback<BaseResult<List<UpFileBean>>>() {
                @Override
                public void onSuccess(BaseResult<List<UpFileBean>> model) {
                    list.clear();
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
                    dismissProgressDialog();
                }
            });
        } else {//删除图片并没有修改图片
            Message message = new Message();
            message.obj = 0x111;
            handler.sendMessage(message);
        }
    }

    List<ImagesBean> list = new ArrayList<>();

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch ((int) msg.obj) {
                case 0x111:
                    for (RevolvingListBean.ImagesBean imagesBean : imagesBeans) {
                        ImagesBean bean = new ImagesBean();
                        if (!imagesBean.isAdd() && imagesBean.getAddress() != null) {
                            bean.setAddress(imagesBean.getAddress());
                            imagesBean.setOrder(0);
                            list.add(bean);
                        }
                    }

                    Map<String, Object> map = new HashMap<>();
                    map.put("userId", SharedPreferencesUtils.init(mContext).getString("userId"));
                    map.put("token", SharedPreferencesUtils.init(mContext).getString("token"));
                    map.put("images", list);
                    map.put("content", bean.getContent());
                    map.put("addrName", bean.getAddrName());
                    map.put("id", bean.getId() + "");
                    map.put("createUserId", SharedPreferencesUtils.init(mContext).getString("userId"));
                    getRevolving(map);
                    break;

                case 0x12:
                    UpDynamicDialog dynamicDialog = new UpDynamicDialog(mContext);
                    dynamicDialog.show();
                    dynamicDialog.setContent(mContext.getString(R.string.camera_up_dynamic));
                    if (mView != null) {
                        mView.onDeleteSuccess();
                    }
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

    private ProgressDialog progressDialog;

    public ProgressDialog showProgressDialog() {
        progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage("加载中...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        return progressDialog;
    }


    public void dismissProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    /**
     * 删除随手拍对话框
     *
     * @param id
     */
    public void getDeleteRevolvingDialog(final String id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setMessage("请确认是否要删除此条随手拍？")
                .setNegativeButton("取消", null)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getDeleteRevolving(id);
                    }
                })
                .setTitle("提示")
                .show();
    }

    /**
     * 删除随手拍对话框
     *
     */
    public void getDeletePicDialog(final RevolvingListBean.ImagesBean item) {
        if (mView != null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
            builder.setMessage("请确认是否要删除此张图片？")
                    .setNegativeButton("取消", null)
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mView.removeImageBean(item);

                        }
                    })
                    .setTitle("提示")
                    .show();
        }

    }
}
