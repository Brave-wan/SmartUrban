package com.smart.urban.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.smart.urban.R;
import com.smart.urban.base.BaseViewHolder;
import com.smart.urban.base.CommonAdapter;
import com.smart.urban.bean.LostBean;
import com.smart.urban.ui.LostDetailsActivity;

import java.util.List;

/**
 * Created by root on 01.04.18.
 */

public class LostListAdapter extends CommonAdapter<LostBean> {
    private Context context;

    public LostListAdapter(@NonNull Context context, int layoutResId, @NonNull List<LostBean> dataList) {
        super(context, layoutResId, dataList);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, final LostBean bean, int position) {
        TextView tv_lost_title = (TextView) baseViewHolder.getViewByViewId(R.id.tv_lost_title);
        TextView tv_lost_phone = (TextView) baseViewHolder.getViewByViewId(R.id.tv_lost_phone);
        ImageView img_lost_head = (ImageView) baseViewHolder.getViewByViewId(R.id.img_lost_head);
        RelativeLayout relativeLayout = (RelativeLayout) baseViewHolder.getViewByViewId(R.id.rl_lost_details);

        tv_lost_phone.setText("联系电话:" + bean.getContact());
        tv_lost_title.setText(bean.getContent());
        if (bean.getImages().size() > 0) {
            Glide.with(context).load(bean.getImages().get(0).getAddress()).error(R.drawable.icon_pic_empty).placeholder(R.drawable.icon_pic_empty).into(img_lost_head);
        } else {
            img_lost_head.setBackground(context.getResources().getDrawable(R.drawable.icon_pic_empty));
        }

        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, LostDetailsActivity.class);
                intent.putExtra("bean", bean);
                context.startActivity(intent);
            }
        });


    }
}
