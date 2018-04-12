package com.smart.urban.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.smart.urban.R;
import com.smart.urban.base.BaseViewHolder;
import com.smart.urban.base.CommonAdapter;
import com.smart.urban.bean.LostBean;

import java.util.List;

/**
 * Created by root on 18-4-12.
 */

public class LostDetailsAdapter extends CommonAdapter<LostBean.ImagesBean> {

    private Context context;

    public LostDetailsAdapter(@NonNull Context context, int layoutResId, @NonNull List<LostBean.ImagesBean> dataList) {
        super(context, layoutResId, dataList);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, LostBean.ImagesBean imagesBean, int position) {
        ImageView item_camera = (ImageView) baseViewHolder.getViewByViewId(R.id.item_camera);
        ImageView img_item_delete = (ImageView) baseViewHolder.getViewByViewId(R.id.img_item_delete);
        img_item_delete.setVisibility(View.GONE);
        Glide.with(context)
                .load(imagesBean.getAddress())
                .placeholder(R.drawable.icon_pic_empty)
                .error(R.drawable.icon_pic_empty)
                .into(item_camera);

    }
}
