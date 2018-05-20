package com.smart.urban.view;

import com.smart.urban.bean.RevolvingDetailsBean;
import com.smart.urban.bean.RevolvingListBean;

/**
 * Created by root on 18-5-18.
 */

public interface IRevolvingDetailsView {
    void onRevolvingDetails(RevolvingDetailsBean bean);

    void onDeleteSuccess();

    void removeImageBean(RevolvingListBean.ImagesBean item);
}
