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

public class SelectImageWindow extends PopupWindow implements View.OnClickListener {
    Context mContext;
    private PopupWindow popupWindow;
    LayoutInflater inflater;

    public SelectImageWindow(Context mContext) {
        this.mContext = mContext;
        inflater = LayoutInflater.from(mContext);
        initView();
    }

    private void initView() {
        View view = inflater.inflate(R.layout.select_image_window, null, false);
        popupWindow = new PopupWindow(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setContentView(view);

        RelativeLayout rl_window_root = (RelativeLayout) view.findViewById(R.id.rl_window_root);
        TextView tx_camera = (TextView) view.findViewById(R.id.tx_camera);
        TextView tx_album = (TextView) view.findViewById(R.id.tx_album);
        TextView tv_window_cancel = (TextView) view.findViewById(R.id.tv_window_cancel);
        tx_camera.setOnClickListener(this);
        tx_album.setOnClickListener(this);
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
            case R.id.tx_camera:
                if (listener != null) {
                    listener.onSelectType(1);
                }
                break;
            case R.id.tx_album:
                if (listener != null) {
                    listener.onSelectType(0);
                }
                break;
            case R.id.tv_window_cancel:
                dismissWindow();
                break;
        }
    }

    public OnSelectImageListener listener;

    public void setOnSelectImageListener(OnSelectImageListener listener) {
        this.listener = listener;
    }

    public interface OnSelectImageListener {
        void onSelectType(int type);
    }
}
