package com.smart.urban.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.smart.urban.R;
import com.smart.urban.base.BaseActivity;
import com.smart.urban.base.BasePresenter;
import com.smart.urban.bean.PersonalBean;
import com.smart.urban.http.ApiCallback;
import com.smart.urban.http.BaseResult;
import com.smart.urban.http.HttpManager;
import com.smart.urban.utils.SharedPreferencesUtils;

import java.util.HashMap;
import java.util.Map;

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
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_nick_save:
                if (StringUtils.isEmpty(ed_input_name.getText().toString().trim())) {
                    ToastUtils.showShort("输入不合法,请重新输入!");
                    return;
                }
                Map<String, Object> map = new HashMap<>();
                map.put("id", SharedPreferencesUtils.init(this).getString("userId"));
                map.put("token", SharedPreferencesUtils.init(this).getString("token"));
                map.put("nickName", ed_input_name.getText().toString().trim());
                getEditInfo(map);
                break;
        }

    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }


    public void getEditInfo(Map<String, Object> map) {
        //编辑个人资料
        HttpManager.get().addSubscription(HttpManager.get().getApiStores().getEditInfo(map), new ApiCallback<BaseResult<String>>() {
            @Override
            public void onSuccess(BaseResult<String> model) {
                SharedPreferencesUtils.init(MyNickActivity.this).put("center_name", ed_input_name.getText().toString().trim());
                finish();
            }

            @Override
            public void onFailure(BaseResult result) {
                ToastUtils.showShort(result.getErrmsg());
            }
        });
    }
}
