package com.smart.urban.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.smart.urban.R;
import com.youth.banner.loader.ImageLoader;

/**
 * Created by root on 18-3-29.
 */
public class GlideImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        //Glide 加载图片简单用法
        Glide.with(context).load((String) path).placeholder(R.drawable.icon_pic_empty).error(R.drawable.icon_pic_empty).into(imageView);
    }
}
