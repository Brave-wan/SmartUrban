package com.smart.urban.base;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.smart.urban.R;


public class TitleActivity extends FragmentActivity {

    private TextView textTitle;
    private TextView buttonBackward;
    private TextView buttonForward;
    private TextView titleLine;
    private RelativeLayout layout_titleBar;
    private FrameLayout layoutContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupViews();   //加载 activity_title 布局 ，并获取标题及两侧按钮
    }


    private void setupViews() {
        super.setContentView(R.layout.activity_title);
        textTitle = (TextView) findViewById(R.id.text_title);
        titleLine = (TextView) findViewById(R.id.layout_title_bar_line);
        layoutContent = (FrameLayout) findViewById(R.id.layout_content);
        buttonBackward = (TextView) findViewById(R.id.button_backward);
        layout_titleBar = (RelativeLayout) findViewById(R.id.layout_title_bar);
        buttonForward = (TextView) findViewById(R.id.button_forward);

        buttonBackward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackward(v);
            }
        });
        buttonForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onForward(v);
            }
        });
    }

    /**
     * 隐藏标题栏线
     */
    protected void hindTitleLine() {
        titleLine.setVisibility(View.GONE);
    }

    /**
     * 设置标题栏背景色
     *
     * @param titleBarBgColor
     */
    protected void setTitleBarBgColor(int titleBarBgColor) {
        layout_titleBar.setBackgroundResource(titleBarBgColor);
    }

    protected void setRightText(String tx) {
        buttonForward.setText(tx);
        buttonForward.setVisibility(View.VISIBLE);
    }

    protected void setRightImage(int res) {
        buttonForward.setText("");
        buttonForward.setVisibility(View.VISIBLE);
        Drawable drawable = getResources().getDrawable(res);
        /// 这一步必须要做,否则不会显示.
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        buttonForward.setCompoundDrawables(drawable, null, null, null);
    }

    /**
     * 是否显示返回按钮
     *
     * @param backwardResid 文字
     * @param show          true则显示
     */
    protected void showBackwardView(int backwardResid, boolean show) {
        if (buttonBackward != null) {
            if (show) {
                buttonBackward.setText(backwardResid);
                buttonBackward.setVisibility(View.VISIBLE);
            } else {
                buttonBackward.setVisibility(View.INVISIBLE);
            }
        }
    }

    /**
     * 返回按钮显示图标
     *
     * @param backwardResid 显示图标
     */
    protected void showBackwardViewIco(int backwardResid) {
        buttonBackward.setVisibility(View.VISIBLE);
        buttonBackward.setText("");
        buttonBackward.setCompoundDrawablesRelativeWithIntrinsicBounds(backwardResid, 0, 0, 0);
    }


    /**
     * 是否显示返回按钮
     *
     * @param backwardResid 文字
     * @param show          true则显示
     */
    protected void showBackwardView(String backwardResid, boolean show) {
        if (buttonBackward != null) {
            if (show) {
                buttonBackward.setText(backwardResid);
                buttonBackward.setVisibility(View.VISIBLE);
            } else {
                buttonBackward.setVisibility(View.INVISIBLE);
            }
        }
    }

    /**
     * 设置返回按钮字颜色
     *
     * @param viewColor
     */
    protected void showBackwardViewColor(int viewColor) {
        buttonBackward.setTextColor(viewColor);
    }

    /**
     * 提供是否显示提交按钮
     *
     * @param forwardResId 文字
     */
    protected void showForwardView(int forwardResId) {
        if (buttonForward != null) {
            buttonForward.setVisibility(View.VISIBLE);
            buttonForward.setText(forwardResId);

        }
    }

    /**
     * 提供是否显示提交按钮
     *
     * @param forwardResId 文字
     * @param show         true则显示
     */
    protected void showForwardView(String forwardResId, boolean show) {
        if (buttonForward != null) {
            if (show) {
                buttonForward.setVisibility(View.VISIBLE);
                buttonForward.setText(forwardResId);
            } else {
                buttonForward.setVisibility(View.INVISIBLE);
            }
        }
    }

    /**
     * 设置提交按钮字颜色
     *
     * @param viewColor
     */
    protected void showForwardViewColor(int viewColor) {
        buttonForward.setTextColor(viewColor);
    }

    /**
     * 返回按钮点击后触发
     *
     * @param backwardView
     */
    protected void onBackward(View backwardView) {
    }

    /**
     * 提交按钮点击后触发
     *
     * @param forwardView
     */
    protected void onForward(View forwardView) {
    }

    /**
     * 设置标题内容
     *
     * @param titleId
     */
    @Override
    public void setTitle(int titleId) {
        buttonBackward.setVisibility(View.VISIBLE);
        textTitle.setText(titleId);
        layout_titleBar.setVisibility(View.VISIBLE);
        titleLine.setVisibility(View.VISIBLE);
    }

    /**
     * 设置标题内容
     *
     * @param title
     */
    @Override
    public void setTitle(CharSequence title) {
        buttonBackward.setVisibility(View.VISIBLE);
        textTitle.setText(title);
        layout_titleBar.setVisibility(View.VISIBLE);
        titleLine.setVisibility(View.VISIBLE);
    }

    /**
     * 设置标题文字颜色
     *
     * @param textColor
     */
    @Override
    public void setTitleColor(int textColor) {
        textTitle.setTextColor(textColor);
    }


    //取出FrameLayout并调用父类removeAllViews()方法
    @Override
    public void setContentView(int layoutResID) {
        layoutContent.removeAllViews();
        View.inflate(this, layoutResID, layoutContent);
        onContentChanged();
    }

    @Override
    public void setContentView(View view) {
        layoutContent.removeAllViews();
        layoutContent.addView(view);
        onContentChanged();
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        layoutContent.removeAllViews();
        layoutContent.addView(view, params);
        onContentChanged();
    }

}