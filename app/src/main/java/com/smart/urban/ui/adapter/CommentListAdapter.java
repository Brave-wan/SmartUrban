package com.smart.urban.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.ImageView;
import android.widget.TextView;

import com.smart.urban.R;
import com.smart.urban.base.BaseViewHolder;
import com.smart.urban.base.CommonAdapter;
import com.smart.urban.bean.CommentListBean;

import java.util.List;

/**
 * Created by root on 03.04.18.
 */

public class CommentListAdapter extends CommonAdapter<CommentListBean> {
    public CommentListAdapter(@NonNull Context context, int layoutResId, @NonNull List<CommentListBean> dataList) {
        super(context, layoutResId, dataList);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, CommentListBean bean, int position) {
        ImageView img_comment_head = (ImageView) baseViewHolder.getViewByViewId(R.id.img_comment_head);
        TextView tv_comment_nam = (TextView) baseViewHolder.getViewByViewId(R.id.tv_comment_name);
        TextView tv_comment_content = (TextView) baseViewHolder.getViewByViewId(R.id.tv_comment_content);
    }
}
