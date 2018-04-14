package com.smart.urban.present;

import android.content.Context;

import com.smart.urban.base.BasePresenter;
import com.smart.urban.view.IUrbanDetailsView;

/**
 * Created by root on 18-4-11.
 */

public class UrbanDetailsPresent extends BasePresenter<IUrbanDetailsView> {
    private Context mContext;

    public UrbanDetailsPresent(Context mContext) {
        this.mContext = mContext;
    }
}
