package com.smart.urban.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.smart.urban.R;
import com.smart.urban.base.BaseViewHolder;
import com.smart.urban.base.CommonAdapter;
import com.smart.urban.bean.UrbanListBean;

import java.util.List;

/**
 * Created by root on 18-3-30.
 */

public class InfoListAdapter extends CommonAdapter<UrbanListBean> {

    private Context context;

    public InfoListAdapter(@NonNull Context context, int layoutResId, @NonNull List<UrbanListBean> dataList) {
        super(context, layoutResId, dataList);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, UrbanListBean bean, int position) {
        ImageView img_info = (ImageView) baseViewHolder.getViewByViewId(R.id.img_info);
        TextView tv_dynamic_title = (TextView) baseViewHolder.getViewByViewId(R.id.tv_dynamic_title);
        TextView tv_comment_number = (TextView) baseViewHolder.getViewByViewId(R.id.tv_comment_number);
        TextView tv_info_time = (TextView) baseViewHolder.getViewByViewId(R.id.tv_info_time);
        tv_info_time.setText(bean.getCreateTime());
        tv_comment_number.setText(bean.getViewCount() + "");
        tv_dynamic_title.setText(bean.getTitle());
        if (bean.getImages().size() > 0) {//bean.getImages().get(0).getAddress()
            Glide.with(context).load(bean.getImages().get(0).getAddress()).error(R.drawable.icon_info_list_empty).placeholder(R.drawable.icon_info_list_empty).into(img_info);
        } else {
            Glide.with(context).load("").error(R.drawable.icon_info_list_empty).placeholder(R.drawable.icon_info_list_empty).into(img_info);
        }

    }
}
