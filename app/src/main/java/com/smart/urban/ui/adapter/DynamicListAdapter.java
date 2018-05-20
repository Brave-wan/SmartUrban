package com.smart.urban.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.smart.urban.R;
import com.smart.urban.base.BaseViewHolder;
import com.smart.urban.base.CommonAdapter;
import com.smart.urban.bean.DynamicListBean;
import com.smart.urban.utils.location.IFlyTTS;

import java.util.List;

/**
 * Created by root on 01.04.18.
 */

public class DynamicListAdapter extends CommonAdapter<DynamicListBean> {
    private Context context;

    public DynamicListAdapter(@NonNull Context context, int layoutResId, @NonNull List<DynamicListBean> dataList) {
        super(context, layoutResId, dataList);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, DynamicListBean bean, int position) {
        ImageView img_lost_head = (ImageView) baseViewHolder.getViewByViewId(R.id.img_lost_head);
        TextView tv_lost_title = (TextView) baseViewHolder.getViewByViewId(R.id.tv_lost_title);
        TextView tv_dynamic_content = (TextView) baseViewHolder.getViewByViewId(R.id.tv_dynamic_content);
        ImageView tx_dynamic_state = (ImageView) baseViewHolder.getViewByViewId(R.id.tx_dynamic_state);
        tv_dynamic_content.setText(bean.getContent());
        tv_lost_title.setText(bean.getTitle());
        tx_dynamic_state.setVisibility(bean.getIsCheck().equals("N") ? View.VISIBLE : View.GONE);


//        switch (bean.getIsCheck()) {
//            //审核通过
//            case "Y":
//                tx_dynamic_state.setBackground(context.getResources().getDrawable(R.drawable.icon_dynamic_shen_he_tong_guo));
//                break;
//            //审核未通过
//            case "N":
//                tx_dynamic_state.setBackground(context.getResources().getDrawable(R.drawable.icon_dynamic_shen_he_wei_tong_guo));
//                break;
//            //待审核
//            case "D":
//                tx_dynamic_state.setBackground(context.getResources().getDrawable(R.drawable.icon_dynamic_dai_shen_he));
//                break;
//        }
        if (bean.getIsCheck().equals("D")){
            tx_dynamic_state.setBackgroundColor(context.getResources().getColor(R.color.btn_cancel_color));
        }
        if (bean.getImages().size() > 0) {
            Glide.with(context).load(bean.getImages().get(0).getAddress()).placeholder(R.drawable.icon_pic_empty).error(R.drawable.icon_pic_empty).into(img_lost_head);
        } else {
            img_lost_head.setBackground(context.getResources().getDrawable(R.drawable.icon_pic_empty));
        }
    }
}
