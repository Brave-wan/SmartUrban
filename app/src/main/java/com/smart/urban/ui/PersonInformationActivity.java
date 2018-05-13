package com.smart.urban.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.smart.urban.R;
import com.smart.urban.base.BaseActivity;
import com.smart.urban.bean.CameraPicBean;
import com.smart.urban.config.Constants;
import com.smart.urban.present.PersonInformationPresent;
import com.smart.urban.utils.GlideCircleTransform;
import com.smart.urban.utils.PhotoUtils;
import com.smart.urban.utils.SharedPreferencesUtils;
import com.smart.urban.view.IPersonInformationView;
import com.yancy.imageselector.ImageSelector;
import com.yancy.imageselector.ImageSelectorActivity;
import com.zhihu.matisse.Matisse;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MultipartBody;

import static com.smart.urban.present.CameraPresent.REQUEST_CODE_CHOOSE;

/**
 * Created by root on 18-3-30.
 */

public class PersonInformationActivity extends BaseActivity<IPersonInformationView, PersonInformationPresent> implements IPersonInformationView {
    @BindView(R.id.img_my_info_head)
    ImageView img_my_info_head;
    @BindView(R.id.tv_info_sex)
    TextView tv_info_sex;
    @BindView(R.id.tv_info_nick)
    TextView tv_info_nick;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_personal_information;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setTitle("个人资料");
    }

    @Override
    protected void onBackward(View backwardView) {
        super.onBackward(backwardView);
        finish();
    }

    @Override
    public PersonInformationPresent initPresenter() {
        return new PersonInformationPresent(this);
    }

    @OnClick({R.id.rl_up_head, R.id.rl_info_sex, R.id.rl_info_nick,})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_up_head:
                Constants.takePhoto(PersonInformationActivity.this, 1);
                break;
            case R.id.rl_info_sex:
                presenter.onOptionPicker(this);
                break;
            case R.id.rl_info_nick:
                startActivity(new Intent(this, MyNickActivity.class));
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ImageSelector.IMAGE_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            List<String> pathList = data.getStringArrayListExtra(ImageSelectorActivity.EXTRA_RESULT);
            if (pathList != null && pathList.size() > 0) {
                String path = pathList.get(0);
                MultipartBody.Part[] parts = new MultipartBody.Part[1];
                parts[0] = Constants.prepareFilePart("files", path);
                presenter.getUpFile(parts);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //加载本地缓存
        tv_info_nick.setText(SharedPreferencesUtils.init(this).getString("center_name"));
        tv_info_sex.setText(SharedPreferencesUtils.init(this).getString("center_sex"));

        String url = SharedPreferencesUtils.init(this).getString("center_img");
        if (url.indexOf("http") == -1) {
            url = Constants.BASE_URL + SharedPreferencesUtils.init(this).getString("center_img");
        } else {
            url = SharedPreferencesUtils.init(this).getString("center_img");
        }
        Glide.with(this).load(url)
                .error(R.drawable.icon_my_portraits)
                .placeholder(R.drawable.icon_my_portraits)
                .bitmapTransform(new GlideCircleTransform(this))
                .into(img_my_info_head);
    }

    @Override
    public void onSex(String sex) {
        tv_info_sex.setText(sex);
    }

    @Override
    public void onHeadImage(String sex) {
        Glide.with(this).load(Constants.BASE_URL+sex).error(R.drawable.icon_my_portraits).bitmapTransform(new GlideCircleTransform(this)).into(img_my_info_head);
    }

    @Override
    public void showLoading() {
        showProgressDialog();
    }

    @Override
    public void hitLoading() {
        dismissProgressDialog();
    }
}
