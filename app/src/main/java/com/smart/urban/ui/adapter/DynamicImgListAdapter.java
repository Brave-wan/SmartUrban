package com.smart.urban.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.smart.urban.R;
import com.smart.urban.base.BaseViewHolder;
import com.smart.urban.base.CommonAdapter;
import com.smart.urban.bean.DynamicListBean;

import java.util.List;

/**
 * Created by root on 18-4-10.
 */

public class DynamicImgListAdapter extends CommonAdapter<DynamicListBean.ImagesBean> {
    Context context;

    public DynamicImgListAdapter(@NonNull Context context, int layoutResId, @NonNull List<DynamicListBean.ImagesBean> dataList) {
        super(context, layoutResId, dataList);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, DynamicListBean.ImagesBean imagesBean, int position) {
        ImageView img_dynamic = (ImageView) baseViewHolder.getViewByViewId(R.id.img_dynamic);
        Glide.with(context).load(imagesBean.getAddress()).placeholder(R.drawable.icon_pic_empty).error(R.drawable.icon_pic_empty).into(img_dynamic);

    }
}
