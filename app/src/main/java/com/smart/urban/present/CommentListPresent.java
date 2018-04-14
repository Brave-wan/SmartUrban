package com.smart.urban.present;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.blankj.utilcode.util.ToastUtils;
import com.google.gson.Gson;
import com.smart.urban.R;
import com.smart.urban.base.BasePresenter;
import com.smart.urban.bean.CommentListBean;
import com.smart.urban.config.Constants;
import com.smart.urban.http.ApiCallback;
import com.smart.urban.http.BaseResult;
import com.smart.urban.http.HttpManager;
import com.smart.urban.ui.dialog.UpDynamicDialog;
import com.smart.urban.utils.SharedPreferencesUtils;
import com.smart.urban.view.ICommentListView;

import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by root on 18-4-13.
 */

public class CommentListPresent extends BasePresenter<ICommentListView> {

    Context mContext;

    public CommentListPresent(Context mContext) {
        this.mContext = mContext;
    }

    /**
     * 动态评论列表接口
     *
     * @param page
     */
    public void getDynamicCommentList(Map<String, Object> page) {
        if (mView != null) {
            HttpManager.get().addSubscription(HttpManager.get().getApiStores().getDynamicCommentList(page), new ApiCallback<BaseResult<List<CommentListBean>>>() {
                @Override
                public void onSuccess(BaseResult<List<CommentListBean>> model) {
                    mView.onCommentListBean(model.data);

                }

                @Override
                public void onFailure(BaseResult result) {
                    ToastUtils.showShort(result.errmsg);
                }
            });
        }

    }

    public void getForumCommentList(Map<String, Object> map) {
        if (mView != null) {
            //文章评论列表　
            HttpManager.get().addSubscription(HttpManager.get().getApiStores().getForumCommentList(map), new ApiCallback<BaseResult<List<CommentListBean>>>() {
                @Override
                public void onSuccess(BaseResult<List<CommentListBean>> model) {
                    mView.onCommentListBean(model.data);
                }

                @Override
                public void onFailure(BaseResult result) {
                    ToastUtils.showShort(result.errmsg);
                }
            });
        }

    }

    /**
     * 添加论坛文章评论
     *
     * @param map
     */
    public void getForumAddComment(Map<String, Object> map) {
        if (mView != null) {
            getRevolving(map, "cityApp/forum/addComment");
            mView.showLoading();
        }
    }

    /**
     * 添加动态评论
     *
     * @param map
     */
    public void getDynamicAddComment(Map<String, Object> map) {
        if (mView != null) {
            mView.showLoading();
            getRevolving(map, "cityApp/dynamic/addComment");
        }
    }

    public void getRevolving(Map<String, Object> map, String url) {
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
                    .url(Constants.BASE_URL + url)//请求的url
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

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch ((int) msg.obj) {
                case 0x12:
                    mView.onCommentSuccess();
                    break;
            }
        }
    };
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
}
