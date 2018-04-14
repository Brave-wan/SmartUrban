package com.smart.urban.view;

import com.smart.urban.bean.RevolvingListBean;

import java.util.List;

/**
 * Created by root on 18-4-10.
 */

public interface IRevolvingView{
    void onRevolvingList(List<RevolvingListBean> listBeans);
    void onFiled();
}
