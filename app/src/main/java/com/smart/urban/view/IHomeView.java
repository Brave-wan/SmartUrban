package com.smart.urban.view;

import com.smart.urban.bean.BannerBean;
import com.smart.urban.bean.DynamicListBean;
import com.smart.urban.bean.UrbanListBean;

import java.util.List;

/**
 * Created by root on 18-4-11.
 */

public interface IHomeView {
    void onDynamicList(List<UrbanListBean> data);

    void onBannerList(List<BannerBean> data);
}
