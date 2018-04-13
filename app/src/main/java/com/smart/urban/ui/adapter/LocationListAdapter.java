package com.smart.urban.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;

import com.smart.urban.base.BaseViewHolder;
import com.smart.urban.base.CommonAdapter;

import java.util.List;

/**
 * Created by root on 18-4-13.
 */

public class LocationListAdapter extends CommonAdapter<String> {
    public LocationListAdapter(@NonNull Context context, int layoutResId, @NonNull List<String> dataList) {
        super(context, layoutResId, dataList);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, String s, int position) {

    }
}
