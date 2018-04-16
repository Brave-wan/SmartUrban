package com.smart.urban.ui.dialog;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.smart.urban.R;

/**
 * Created by root on 16.04.18.
 */

public class ShareWindow extends PopupWindow implements View.OnClickListener {
    Context mContext;
    private PopupWindow popupWindow;
    LayoutInflater inflater;

    public ShareWindow(Context mContext) {
        this.mContext = mContext;
        inflater = LayoutInflater.from(mContext);
        initView();
    }

    private void initView() {
        View view = inflater.inflate(R.layout.window_share, null, false);
        popupWindow = new PopupWindow(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setContentView(view);

        RelativeLayout rl_window_root = (RelativeLayout) view.findViewById(R.id.rl_window_root);
        TextView tv_share_wechat = (TextView) view.findViewById(R.id.tv_share_wechat);
        TextView tv_share_qq = (TextView) view.findViewById(R.id.tv_share_qq);
        TextView tv_window_cancel = (TextView) view.findViewById(R.id.tv_window_cancel);
        tv_share_wechat.setOnClickListener(this);
        tv_share_qq.setOnClickListener(this);
        tv_window_cancel.setOnClickListener(this);

        rl_window_root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        // 设置PopupWindow的背景
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        // 设置PopupWindow是否能响应外部点击事件
        popupWindow.setOutsideTouchable(true);
        // 设置PopupWindow是否能响应点击事件
        popupWindow.setTouchable(true);
    }

    public void showWindow(View view) {
        if (popupWindow != null) {
            popupWindow.showAsDropDown(view);
        }
    }

    public void dismissWindow() {
        if (popupWindow != null) {
            popupWindow.dismiss();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_share_wechat:
                ToastUtils.showShort("暂未开通");
                break;
            case R.id.tv_share_qq:
                ToastUtils.showShort("暂未开通");
                break;
            case R.id.tv_window_cancel:
                dismissWindow();
                break;
        }

    }
}
