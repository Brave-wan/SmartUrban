package com.smart.urban.view;

import com.smart.urban.base.IBaseView;
import com.smart.urban.bean.RegisterBean;

/**
 * Created by root on 18-3-30.
 */

public interface ILoginView  extends IBaseView{
    void OnLoginSuccess(RegisterBean bean);
}
