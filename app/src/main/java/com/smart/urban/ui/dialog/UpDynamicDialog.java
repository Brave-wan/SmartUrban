package com.smart.urban.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.smart.urban.R;

/**
 * Created by root on 18-4-10.
 */

public class UpDynamicDialog extends Dialog {
    private TextView tv_content;
    Context mContext;

    public UpDynamicDialog(Context mContext) {
        super(mContext, R.style.dialog);
        this.mContext = mContext;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_up_deynamic);
        tv_content = (TextView) findViewById(R.id.tv_content);

        setCanceledOnTouchOutside(true);
        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        DisplayMetrics d = mContext.getResources().getDisplayMetrics(); // 获取屏幕宽、高用
        lp.width = (int) (d.widthPixels * 0.8); // 高度设置为屏幕的0.6
        dialogWindow.setAttributes(lp);
    }

    public void setContent(String content) {
        tv_content.setText(content);
    }
}
