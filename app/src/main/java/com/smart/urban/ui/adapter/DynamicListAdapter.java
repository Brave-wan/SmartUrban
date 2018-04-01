package com.smart.urban.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.smart.urban.R;
import com.smart.urban.base.BaseViewHolder;
import com.smart.urban.base.CommonAdapter;

import java.util.List;

/**
 * Created by root on 01.04.18.
 */

public class DynamicListAdapter extends CommonAdapter<String> {
    private Context context;

    public DynamicListAdapter(@NonNull Context context, int layoutResId, @NonNull List<String> dataList) {
        super(context, layoutResId, dataList);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, String s, int position) {
        ImageView img_lost_head = (ImageView) baseViewHolder.getViewByViewId(R.id.img_lost_head);
        Glide.with(context).load("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=3586898888,816047016&fm=27&gp=0.jpg").into(img_lost_head);
    }
}
