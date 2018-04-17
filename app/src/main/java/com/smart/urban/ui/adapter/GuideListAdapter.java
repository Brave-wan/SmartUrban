package com.smart.urban.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.TextView;

import com.smart.urban.R;
import com.smart.urban.base.BaseViewHolder;
import com.smart.urban.base.CommonAdapter;
import com.smart.urban.bean.GuideListBean;

import java.util.List;

/**
 * Created by root on 01.04.18.
 */

public class GuideListAdapter extends CommonAdapter<GuideListBean> {
    public GuideListAdapter(@NonNull Context context, int layoutResId, @NonNull List<GuideListBean> dataList) {
        super(context, layoutResId, dataList);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, GuideListBean bean, int position) {
        TextView tv_guide_title = (TextView) baseViewHolder.getViewByViewId(R.id.tv_guide_title);
        TextView tv_guide_content = (TextView) baseViewHolder.getViewByViewId(R.id.tv_guide_content);
        tv_guide_content.setText(bean.getSubtitle());
        tv_guide_title.setText(bean.getTitle());
    }
}
