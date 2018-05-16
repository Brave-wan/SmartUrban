package com.smart.urban.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;

import com.blankj.utilcode.util.LogUtils;
import com.bumptech.glide.Glide;
import com.smart.urban.R;
import com.smart.urban.base.BaseViewHolder;
import com.smart.urban.base.CommonAdapter;
import com.smart.urban.bean.CameraPicBean;
import com.smart.urban.bean.RevolvingListBean;
import com.smart.urban.config.Constants;

import java.util.List;

/**
 * Created by root on 18-3-29.
 */

public class RevolvingDetailsListAdapter extends CommonAdapter<RevolvingListBean.ImagesBean> {
    Context context;

    public RevolvingDetailsListAdapter(@NonNull Context context, int layoutResId, @NonNull List<RevolvingListBean.ImagesBean> dataList) {
        super(context, layoutResId, dataList);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, final RevolvingListBean.ImagesBean bean, int position) {
        ImageView item_camera = (ImageView) baseViewHolder.getViewByViewId(R.id.item_camera);
        ImageView img_item_delete = (ImageView) baseViewHolder.getViewByViewId(R.id.img_item_delete);
        LogUtils.i("img" + bean.getAddress());
        Glide.with(context).load(bean.getAddress() == null ? R.drawable.icon_up_loading_photo_btn : bean.getAddress()).placeholder(R.drawable.icon_pic_empty).error(R.drawable.icon_pic_empty).into(item_camera);
    }
}
