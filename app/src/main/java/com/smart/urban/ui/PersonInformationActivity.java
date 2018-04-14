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
import com.smart.urban.config.Constants;
import com.smart.urban.present.PersonInformationPresent;
import com.smart.urban.utils.GlideCircleTransform;
import com.smart.urban.utils.PhotoUtils;
import com.smart.urban.utils.SharedPreferencesUtils;
import com.smart.urban.view.IPersonInformationView;
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
                presenter.getTakePhoto(this);
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
        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {
            List<Uri> mSelected = Matisse.obtainResult(data);
            if (mSelected != null && mSelected.size() > 0) {
                String path = PhotoUtils.getRealPathFromUri(this, mSelected.get(0));
                Glide.with(this).load(path).error(R.drawable.icon_my_portraits).bitmapTransform(new GlideCircleTransform(this)).into(img_my_info_head);
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
        String img = SharedPreferencesUtils.init(this).getString("center_img");
        Glide.with(this).load(Constants.BASE_URL + img)
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
    public void showLoading() {
        showProgressDialog();
    }

    @Override
    public void hitLoading() {
        dismissProgressDialog();
    }
}
