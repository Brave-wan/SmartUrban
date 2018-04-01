package com.smart.urban.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.smart.urban.R;
import com.smart.urban.base.BaseViewHolder;
import com.smart.urban.base.CommonAdapter;
import com.smart.urban.bean.CameraPicBean;

import java.util.List;

/**
 * Created by root on 18-3-29.
 */

public class CameraListAdapter extends CommonAdapter<CameraPicBean> {
    Context context;

    public CameraListAdapter(@NonNull Context context, int layoutResId, @NonNull List<CameraPicBean> dataList) {
        super(context, layoutResId, dataList);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, CameraPicBean bean, int position) {
        ImageView item_camera = (ImageView) baseViewHolder.getViewByViewId(R.id.item_camera);
        Glide.with(context).
                load(bean.getPic() == null ? R.mipmap.ic_launcher : bean.getPic()).error(R.mipmap.ic_launcher)
                .into(item_camera);
    }
}
