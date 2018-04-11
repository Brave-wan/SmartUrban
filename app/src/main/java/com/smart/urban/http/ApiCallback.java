package com.smart.urban.http;

import android.text.TextUtils;


import com.smart.urban.base.MyApplication;
import com.smart.urban.utils.NetWorkUtils;

import java.net.SocketTimeoutException;

import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;

/**
 * 作者：Rance on 2016/11/18 15:19
 * 邮箱：rance935@163.com
 */
public abstract class ApiCallback<M> extends Subscriber<M> {

    public abstract void onSuccess(M model);

    public abstract void onFailure(BaseResult result);


    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
        BaseResult errorMessageInfo = new BaseResult();
        if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            int code = httpException.code();
            errorMessageInfo.setErrmsg(httpException.getMessage());
            errorMessageInfo.setErrcode(String.valueOf(code));
        } else if (e instanceof SocketTimeoutException) {
            int code = 1000;
            errorMessageInfo.setErrmsg("连接超时");
            errorMessageInfo.setErrcode(String.valueOf(code));
        }
        if (!NetWorkUtils.isNetworkAvailable(MyApplication.instance)) {
            errorMessageInfo.setErrmsg("请检查网络连接");
        }
        if (!TextUtils.isEmpty(errorMessageInfo.getErrmsg())) {
            onFailure(errorMessageInfo);
        }
    }

    @Override
    public void onNext(M model) {
        String code = ((BaseResult) model).getErrcode();
        BaseResult baseResult = new BaseResult();
        switch (Integer.valueOf(code)) {
            case 200:
                onSuccess(model);
                break;
            default:
                baseResult.setErrcode(code);
                baseResult.setErrmsg(((BaseResult) model).getErrmsg());
                onFailure(baseResult);
                break;
        }
    }

    @Override
    public void onCompleted() {
    }

}
