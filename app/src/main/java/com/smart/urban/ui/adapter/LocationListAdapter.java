package com.smart.urban.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.TextView;

import com.smart.urban.R;
import com.smart.urban.base.BaseViewHolder;
import com.smart.urban.base.CommonAdapter;
import com.smart.urban.bean.LocationListBean;

import java.util.List;

/**
 * Created by root on 18-4-13.
 */

public class LocationListAdapter extends CommonAdapter<LocationListBean> {
    public LocationListAdapter(@NonNull Context context, int layoutResId, @NonNull List<LocationListBean> dataList) {
        super(context, layoutResId, dataList);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, LocationListBean bean, int position) {
        TextView tx_location_name = (TextView) baseViewHolder.getViewByViewId(R.id.tx_location_name);
        tx_location_name.setText(bean.getName());
    }
}
