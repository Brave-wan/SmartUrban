package com.smart.urban.view;

import com.smart.urban.bean.UrbanDetailsBean;
import com.smart.urban.http.ApiCallback;
import com.smart.urban.http.BaseResult;
import com.smart.urban.http.HttpManager;
import com.smart.urban.utils.SharedPreferencesUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by root on 18-4-23.
 */

public interface IBannerDynamicDetailsView {
    void onUrbanDetailsBean(UrbanDetailsBean bean);


}
