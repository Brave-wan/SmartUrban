package com.smart.urban.fragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;
import com.bumptech.glide.Glide;
import com.smart.urban.R;
import com.smart.urban.base.BaseFragment;
import com.smart.urban.base.BasePresenter;
import com.smart.urban.config.Constants;
import com.smart.urban.ui.AboutUsActivity;
import com.smart.urban.ui.AlterPwdActivity;
import com.smart.urban.ui.ArticlesPublishedActivity;
import com.smart.urban.ui.LoginActivity;
import com.smart.urban.ui.LostActivity;
import com.smart.urban.ui.PersonInformationActivity;
import com.smart.urban.ui.RevolvingActivity;
import com.smart.urban.utils.GlideCircleTransform;
import com.smart.urban.utils.SharedPreferencesUtils;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.utils.SocializeUtils;

import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by root on 18-3-29.
 */

public class CenterFragment extends BaseFragment {
    @BindView(R.id.img_center_head)
    ImageView img_center_head;
    @BindView(R.id.tv_center_name)
    TextView tv_center_name;
    @BindView(R.id.tv_center_sex)
    TextView tv_center_sex;
    @BindView(R.id.tv_my_pr)
    TextView tv_my_pr;
    @BindView(R.id.tv_my_alter_pwd)
    TextView tv_my_alter_pwd;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_center;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        setTitle("我的");
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("正在获取授权...");
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    String url;

    @Override
    public void onResume() {
        super.onResume();
        String userName = SharedPreferencesUtils.init(getActivity()).getString("center_name");
        tv_center_name.setText("昵称:" + userName);
        tv_center_sex.setText("性别:" + SharedPreferencesUtils.init(getActivity()).getString("center_sex"));
        tv_center_name.setVisibility(StringUtils.isEmpty(userName) ? View.GONE : View.VISIBLE);
        tv_center_sex.setVisibility(StringUtils.isEmpty(userName) ? View.GONE : View.VISIBLE);
        tv_my_pr.setVisibility(StringUtils.isEmpty(userName) ? View.VISIBLE : View.GONE);

        url = SharedPreferencesUtils.init(getActivity()).getString("center_img");
        if (url.indexOf("http") == -1) {
            url = Constants.BASE_URL + SharedPreferencesUtils.init(getActivity()).getString("center_img");
        } else {
            url = SharedPreferencesUtils.init(getActivity()).getString("center_img");
        }

        tv_my_alter_pwd.setVisibility(StringUtils.isEmpty(SharedPreferencesUtils.init(getActivity()).getString("type")) ? View.VISIBLE : View.GONE);

        Glide.with(getActivity()).load(url)
                .error(R.drawable.icon_my_portraits)
                .placeholder(R.drawable.icon_my_portraits)
                .bitmapTransform(new GlideCircleTransform(getActivity()))
                .into(img_center_head);

    }

    @OnClick({R.id.img_center_head, R.id.tv_my_alter_pwd, R.id.tv_my_lost,
            R.id.tv_about_us, R.id.tv_center_revolving, R.id.tv_center_logout, R.id.tv_articles_published})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_center_revolving:
                startActivity(new Intent(getActivity(), RevolvingActivity.class));
                break;
            case R.id.tv_my_alter_pwd:
                startActivity(new Intent(getActivity(), AlterPwdActivity.class));
                break;
            case R.id.img_center_head:
                startActivity(new Intent(getActivity(), PersonInformationActivity.class));
                break;
            case R.id.tv_my_lost:
                startActivity(new Intent(getActivity(), LostActivity.class));
                break;
            case R.id.tv_about_us:
                startActivity(new Intent(getActivity(), AboutUsActivity.class));
                break;
            case R.id.tv_center_logout:
                getLogOut();
                break;
            case R.id.tv_articles_published:
                startActivity(new Intent(getActivity(), ArticlesPublishedActivity.class));
                break;
        }
    }

    private ProgressDialog progressDialog;

    public void getLogOut() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("你确定要退出当前账号？")
                .setNegativeButton("取消", null)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String type = SharedPreferencesUtils.init(getActivity()).getString("type");
                        UMShareAPI.get(getActivity()).deleteOauth(getActivity(), type.equals("2") ? SHARE_MEDIA.WEIXIN : SHARE_MEDIA.QQ, new UMAuthListener() {
                            @Override
                            public void onStart(SHARE_MEDIA share_media) {
                                SocializeUtils.safeShowDialog(progressDialog);
                            }

                            @Override
                            public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
                                SocializeUtils.safeCloseDialog(progressDialog);
                                SharedPreferencesUtils.init(getActivity()).clear();
                                Intent intent = new Intent(getActivity(), LoginActivity.class);
                                startActivity(intent);
                                getActivity().finish();
                            }

                            @Override
                            public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
                                SocializeUtils.safeCloseDialog(progressDialog);
                            }

                            @Override
                            public void onCancel(SHARE_MEDIA share_media, int i) {
                                SocializeUtils.safeCloseDialog(progressDialog);
                            }
                        });

                    }
                })
                .setTitle("退出提示")
                .show();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(getActivity()).onActivityResult(requestCode, resultCode, data);
    }
}
