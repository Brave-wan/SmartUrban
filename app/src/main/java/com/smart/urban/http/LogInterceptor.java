package com.smart.urban.http;

import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;

/**
 * 拦截器
 */
public class LogInterceptor implements Interceptor {

    @Override
    public okhttp3.Response intercept(Chain chain) throws IOException {
        okhttp3.Response response = chain.proceed(chain.request());
        okhttp3.MediaType mediaType = response.body().contentType();
        String content = response.body().string();
        Log.i("wan", "response:" + content);
        Log.i("wan", "back:" + response.headers().get("Content-Type"));
        return response.newBuilder()
                .body(okhttp3.ResponseBody.create(mediaType, content))
                .build();
    }
}
