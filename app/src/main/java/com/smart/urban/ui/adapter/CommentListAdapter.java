package com.smart.urban.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.smart.urban.R;
import com.smart.urban.base.BaseViewHolder;
import com.smart.urban.base.CommonAdapter;
import com.smart.urban.bean.CommentListBean;
import com.smart.urban.utils.GlideCircleTransform;

import java.util.List;

/**
 * Created by root on 03.04.18.
 */

public class CommentListAdapter extends CommonAdapter<CommentListBean> {
    private Context context;

    public CommentListAdapter(@NonNull Context context, int layoutResId, @NonNull List<CommentListBean> dataList) {
        super(context, layoutResId, dataList);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, CommentListBean bean, int position) {
        ImageView img_comment_head = (ImageView) baseViewHolder.getViewByViewId(R.id.img_comment_head);
        TextView tv_comment_nam = (TextView) baseViewHolder.getViewByViewId(R.id.tv_comment_name);
        TextView tv_comment_content = (TextView) baseViewHolder.getViewByViewId(R.id.tv_comment_content);
        if (bean.getUserInfo() != null && bean.getUserInfo().getAvatar().size() > 0) {

            Glide.with(context).load(bean.getUserInfo().getAvatar()
                    .get(0).getAddress())
                    .error(R.drawable.icon_my_portraits)
                    .placeholder(R.drawable.icon_my_portraits)
                    .bitmapTransform(new GlideCircleTransform(context))
                    .into(img_comment_head);
            tv_comment_nam.setText(bean.getUserInfo().getNickName());
            tv_comment_content.setText(bean.getContent());

        }
    }
}
