package com.smart.urban.http;

import com.smart.urban.bean.BannerBean;
import com.smart.urban.bean.CommentListBean;
import com.smart.urban.bean.DynamicListBean;
import com.smart.urban.bean.GuideDetailsBean;
import com.smart.urban.bean.GuideListBean;
import com.smart.urban.bean.InfoDetailsBean;
import com.smart.urban.bean.LocationListBean;
import com.smart.urban.bean.LostBean;
import com.smart.urban.bean.PersonalBean;
import com.smart.urban.bean.RegisterBean;
import com.smart.urban.bean.RevolvingListBean;
import com.smart.urban.bean.UpFileBean;
import com.smart.urban.bean.UrbanListBean;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.QueryMap;
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

    @FormUrlEncoded
    @POST("cityApp/editInfo")
    Observable<BaseResult<PersonalBean>> getEditInfo(@FieldMap Map<String, Object> map);

    @Multipart
    @POST("cityApp/file/upload")
    Observable<BaseResult<UpFileBean>> getUploadFile(@Part MultipartBody.Part file);

    //多张图片上传
    @Multipart
    @POST("cityApp/file/upload")
    Observable<BaseResult<List<UpFileBean>>> getUpdateImage(@Part MultipartBody.Part[] file);

    @FormUrlEncoded
    @POST("cityApp/editPwd")
    Observable<BaseResult<PersonalBean>> getResetPwd(@FieldMap Map<String, Object> map);

    @FormUrlEncoded
    @POST("cityApp/resetPwd")
    Observable<BaseResult<PersonalBean>> getFindPwd(@FieldMap Map<String, Object> map);

    @FormUrlEncoded
    @POST("cityApp/dynamic/getList")
    Observable<BaseResult<List<UrbanListBean>>> getDynameicList(@FieldMap Map<String, Object> map);

    @FormUrlEncoded
    @POST("cityApp/guide/getList")
    Observable<BaseResult<List<GuideListBean>>> getGuideList(@FieldMap Map<String, Object> map);

    @FormUrlEncoded
    @POST("cityApp/guide/getById")
    Observable<BaseResult<GuideDetailsBean>> getGuideById(@FieldMap Map<String, Object> map);

    @FormUrlEncoded
    @POST("cityApp/photo/add")
    Observable<BaseResult<GuideDetailsBean>> getPhotoAdd(@FieldMap Map<String, Object> map);

    @FormUrlEncoded
    @POST("cityApp/message/getList")
    Observable<BaseResult<List<UrbanListBean>>> getMessageList(@FieldMap Map<String, Object> map);


    @FormUrlEncoded
    @POST("cityApp/forum/getList")
    Observable<BaseResult<List<DynamicListBean>>> getForumList(@FieldMap Map<String, Object> map);


    @FormUrlEncoded
    @POST("cityApp/lost/getList")
    Observable<BaseResult<List<LostBean>>> getLostList(@FieldMap Map<String, Object> map);

    @FormUrlEncoded
    @POST("cityApp/photo/getList")
    Observable<BaseResult<List<RevolvingListBean>>> getPhotoList(@FieldMap Map<String, Object> map);

    @FormUrlEncoded
    @POST("cityApp/lost/add")
    Observable<BaseResult<List<RevolvingListBean>>> getLostAdd(@FieldMap Map<String, Object> map);

    @GET("cityApp/dynamic/getCommentList")
    Observable<BaseResult<List<CommentListBean>>> getDynamicCommentList(@QueryMap Map<String, Object> map);

    @GET("cityApp/forum/getCommentList")
    Observable<BaseResult<List<CommentListBean>>> getForumCommentList(@QueryMap Map<String, Object> map);

    @GET("cityApp/position/search")
    Observable<BaseResult<List<LocationListBean>>> getLocationSearch(@QueryMap Map<String, Object> map);

    @FormUrlEncoded
    @POST("cityApp/dynamic/getById")
    Observable<BaseResult<UrbanListBean>> getDynamicById(@FieldMap Map<String, Object> map);

    @FormUrlEncoded
    @POST("cityApp/message/getById")
    Observable<BaseResult<InfoDetailsBean>> getMessageById(@FieldMap Map<String, Object> map);

    @GET("cityApp/banner/getList")
    Observable<BaseResult<List<BannerBean>>> getBannerList(@QueryMap Map<String, Object> map);

    @GET("cityApp/photo/getById")
    Observable<BaseResult<List<BannerBean>>> getForumById(@QueryMap Map<String, Object> map);
}
