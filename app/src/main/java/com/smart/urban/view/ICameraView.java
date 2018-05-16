package com.smart.urban.view;

import com.smart.urban.ui.dialog.UpDynamicDialog;

/**
 * Created by root on 18-3-29.
 */

public interface ICameraView {
    void onUpSuccess(UpDynamicDialog dynamicDialog);

    void onLocationAddress(String address);
}
