package com.smart.urban.view;

import com.smart.urban.base.IBaseView;
import com.smart.urban.bean.RegisterBean;

/**
 * Created by root on 06.04.18.
 */

public interface IRegisterView extends IBaseView {
    void OnCaptchaCode(RegisterBean bean);

    void onRegisterSuccess();
}
