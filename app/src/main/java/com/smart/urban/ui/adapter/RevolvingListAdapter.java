package com.smart.urban.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
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
        TextView tv_revolving_state = (TextView) baseViewHolder.getViewByViewId(R.id.tv_revolving_state);
        TextView tv_revolving_content = (TextView) baseViewHolder.getViewByViewId(R.id.tv_revolving_content);
        tv_revolving_title.setText(bean.getContent());
        tv_revolving_content.setText(bean.getContent());

        Log.i("wan", "type:" + bean.getState());
        if (bean.getImages() != null && bean.getImages().size() > 0) {
            Glide.with(context).load(bean.getImages().get(0).getAddress()).placeholder(R.drawable.icon_pic_empty).error(R.drawable.icon_pic_empty).into(img_revolving_head);
        }
        switch (bean.getState()) {
            case "1":
                tv_revolving_state.setText("待审核");
                tv_revolving_state.setBackground(context.getResources().getDrawable(R.drawable.shape_my_revolving_review));
                break;

            case "2":
                tv_revolving_state.setText("跟进中");
                tv_revolving_state.setBackground(context.getResources().getDrawable(R.drawable.shape_my_revolving_following_up));
                break;

            case "3":
                tv_revolving_state.setText("处理完成");
                tv_revolving_state.setBackground(context.getResources().getDrawable(R.drawable.shape_my_revolving_finish));
                break;
        }

    }
}
