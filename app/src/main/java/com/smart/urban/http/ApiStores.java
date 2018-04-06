package com.smart.urban.http;

import com.smart.urban.bean.PersonalBean;
import com.smart.urban.bean.RegisterBean;

import java.util.Map;

import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * 作者：Rance on 2016/11/18 15:19
 * 邮箱：rance935@163.com
 */
public interface ApiStores {
    @FormUrlEncoded
    @POST("cityApp/getCode")
    Observable<BaseResult<RegisterBean>> getCaptchaCode(@FieldMap Map<String, Object> map);

    @FormUrlEncoded
    @POST("cityApp/toRegister")
    Observable<BaseResult<RegisterBean>> getToRegister(@FieldMap Map<String, Object> map);

    @FormUrlEncoded
    @POST("cityApp/toLogin")
    Observable<BaseResult<RegisterBean>> getToLogin(@FieldMap Map<String, Object> map);

    @FormUrlEncoded
    @POST("cityApp/myDetails")
    Observable<BaseResult<PersonalBean>> getMyDetails(@FieldMap Map<String, Object> map);
}
