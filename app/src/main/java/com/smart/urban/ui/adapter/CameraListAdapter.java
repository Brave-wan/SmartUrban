package com.smart.urban.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.smart.urban.R;
import com.smart.urban.base.BaseViewHolder;
import com.smart.urban.base.CommonAdapter;
import com.smart.urban.bean.CameraPicBean;
import com.smart.urban.utils.impl.OnRemovePicListener;

import java.util.List;

/**
 * Created by root on 18-3-29.
 */

public class CameraListAdapter extends CommonAdapter<CameraPicBean> {
    Context context;
    OnRemovePicListener listener;
    public void setOnRemovePicListener(OnRemovePicListener listener){
        this.listener=listener;
    }
    public CameraListAdapter(@NonNull Context context, int layoutResId, @NonNull List<CameraPicBean> dataList) {
        super(context, layoutResId, dataList);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, final CameraPicBean bean, int position) {
        ImageView item_camera = (ImageView) baseViewHolder.getViewByViewId(R.id.item_camera);
        ImageView img_item_delete = (ImageView) baseViewHolder.getViewByViewId(R.id.img_item_delete);
        img_item_delete.setVisibility(bean.getPic() == null ? View.GONE : View.VISIBLE);
        Glide.with(context)
                .load(bean.getPic() == null ? R.drawable.icon_up_loading_photo_btn : bean.getPic())
                .error(R.drawable.icon_up_loading_photo_btn)
                .into(item_camera);
        img_item_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener!=null){
                    listener.removePic(bean);
                }

            }
        });
    }

}
