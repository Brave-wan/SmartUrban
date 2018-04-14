package com.smart.urban.ui.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.smart.urban.R;
import com.smart.urban.utils.ScaleView;

/**
 * Created by root on 18-4-12.
 */

public class ShowImageWindow extends PopupWindow {

    private PopupWindow window;
    private Context mContext;
    private LayoutInflater inflater;
    private String path;

    public ShowImageWindow(Context mContext, String path) {
        this.mContext = mContext;
        this.path = path;
        inflater = LayoutInflater.from(mContext);
        initView();
    }

    private void initView() {
        View view = inflater.inflate(R.layout.window_show_imager, null);
        window = new PopupWindow(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        window.setContentView(view);
        final ImageView window_image = (ImageView) view.findViewById(R.id.window_image);
        LinearLayout window_root = (LinearLayout) view.findViewById(R.id.window_root);
        window.setOutsideTouchable(true);
        window.setAnimationStyle(R.style.AnimationPreview);
        Glide.with(mContext).load(path).error(R.drawable.icon_pic_empty).placeholder(R.drawable.icon_pic_empty).into(window_image);  //强制转换Bitmap
        window_root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                window.dismiss();
            }
        });

    }

    public void showWindow(View view) {
        if (window != null) {
            window.showAsDropDown(view);
        }

    }


}
