package com.smart.urban.view;

import com.smart.urban.bean.SelectLocationBean;

import java.util.List;

/**
 * Created by root on 18-5-23.
 */

public interface ISelectLocationView {
    void onLocationAddress(List<SelectLocationBean> address);

    void onMobileLocation(String s);
}
