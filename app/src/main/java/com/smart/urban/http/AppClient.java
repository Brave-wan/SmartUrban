package com.smart.urban.http;


import com.smart.urban.config.Constants;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 作者：Rance on 2016/11/18 15:19
 * 邮箱：rance935@163.com
 */
public class AppClient {
    public static Retrofit mRetrofit;
    private static OkHttpClient okHttpClient;

    public static Retrofit getRetrofit() {
        if (mRetrofit == null) {
            mRetrofit = new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .client(getOkHttpClient())
                    .build();
        }
        return mRetrofit;
    }

    public static OkHttpClient getOkHttpClient() {
        if (okHttpClient == null) {
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            if (Constants.DEBUG) {
                // Log信息拦截器
                HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
                loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                //添加头文件
//                builder.addInterceptor(chain -> {
//                    Request request = chain.request().newBuilder()
//                            .addHeader("appid", "78022410")
//                            .addHeader("appsecret", "yLUdxzgzBNiDCtrPPDoralkuSuoHRGPG").build();
//                    return chain.proceed(request);
//                });
                //设置 Debug Log 模式
                builder.addInterceptor(loggingInterceptor);
                builder.addInterceptor(new LogInterceptor());
            }
            okHttpClient = builder.build();
        }
        return okHttpClient;
    }

}
