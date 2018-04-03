package com.smart.urban.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.smart.urban.R;
import com.smart.urban.base.BaseActivity;
import com.smart.urban.base.BasePresenter;
import com.smart.urban.utils.SharedPreferencesUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by root on 03.04.18.
 */

public class MyNickActivity extends BaseActivity {
    @BindView(R.id.ed_input_name)
    EditText ed_input_name;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_my_nick;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setTitle("填写昵称");
    }


    @Override
    protected void onBackward(View backwardView) {
        super.onBackward(backwardView);
        finish();
    }

    @OnClick({R.id.tv_nick_save})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.tv_nick_save:
                SharedPreferencesUtils.init(this).put("my_nick","xian");
                break;
        }

    }
    @Override
    public BasePresenter initPresenter() {
        return null;
    }
}
