package com.smart.urban.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.smart.urban.R;
import com.smart.urban.base.BaseViewHolder;
import com.smart.urban.base.CommonAdapter;
import com.smart.urban.bean.RevolvingBean;
import com.smart.urban.bean.RevolvingListBean;

import java.util.List;

/**
 * Created by root on 18-4-10.
 */

public class RevolvingListAdapter extends CommonAdapter<RevolvingListBean> {
    private Context context;

    public RevolvingListAdapter(@NonNull Context context, int layoutResId, @NonNull List<RevolvingListBean> dataList) {
        super(context, layoutResId, dataList);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, RevolvingListBean bean, int position) {
        ImageView img_revolving_head = (ImageView) baseViewHolder.getViewByViewId(R.id.img_revolving_head);
        TextView tv_revolving_title = (TextView) baseViewHolder.getViewByViewId(R.id.tv_revolving_title);
        Glide.with(context).load(bean.getImages().get(0).getAddress()).placeholder(R.drawable.icon_pic_empty).error(R.drawable.icon_pic_empty).into(img_revolving_head);
        tv_revolving_title.setText(bean.getContent());

    }
}
