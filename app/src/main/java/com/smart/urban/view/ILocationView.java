package com.smart.urban.view;

import com.smart.urban.bean.LocationListBean;

import java.util.List;

/**
 * Created by root on 18-3-28.
 */

public interface ILocationView {
    void onLocationList(List<LocationListBean> beans,boolean state);

    void onLocationView();
}
