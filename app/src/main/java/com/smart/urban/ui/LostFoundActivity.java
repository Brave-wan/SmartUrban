package com.smart.urban.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.smart.urban.R;
import com.smart.urban.base.BaseActivity;
import com.smart.urban.base.BasePresenter;
import com.smart.urban.bean.CameraPicBean;
import com.smart.urban.config.Constants;
import com.smart.urban.present.CameraPresent;
import com.smart.urban.present.LostFoundPresent;
import com.smart.urban.ui.adapter.CameraListAdapter;
import com.smart.urban.ui.widget.ShowImageWindow;
import com.smart.urban.utils.PhotoUtils;
import com.smart.urban.view.ICameraView;
import com.smart.urban.view.ILostFoundView;
import com.zhihu.matisse.Matisse;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MultipartBody;

import static com.smart.urban.present.CameraPresent.REQUEST_CODE_CHOOSE;

/**
 * Created by root on 03.04.18.
 */

public class LostFoundActivity extends BaseActivity<ILostFoundView, LostFoundPresent> implements ILostFoundView, AdapterView.OnItemClickListener {
    @BindView(R.id.gv_lost_found)
    GridView gv_lost_found;
    @BindView(R.id.ed_lost_content)
    EditText ed_lost_content;
    @BindView(R.id.ed_lost_phone)
    EditText ed_lost_phone;
    @BindView(R.id.tx_lost_submit)
    TextView tx_lost_submit;

    private List<CameraPicBean> list = new ArrayList<>();
    private CameraListAdapter adapter;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_lost_found;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setTitle("上传失物招领");
        list.clear();
        list.add(new CameraPicBean());
        adapter = new CameraListAdapter(this, R.layout.item_camera_list, list);
        gv_lost_found.setAdapter(adapter);
        gv_lost_found.setOnItemClickListener(this);
    }

    @Override
    protected void onBackward(View backwardView) {
        super.onBackward(backwardView);
        finish();
    }

    @Override
    public LostFoundPresent initPresenter() {
        return new LostFoundPresent(this);
    }

    List<Uri> mSelected;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {
            mSelected = Matisse.obtainResult(data);
            if (mSelected != null && mSelected.size() > 0) {
                for (Uri uri : mSelected) {
                    String path = PhotoUtils.getRealPathFromUri(this, uri);
                    CameraPicBean bean = new CameraPicBean();
                    bean.setPic(path);
                    list.add(0, bean);
                }
                adapter.setDataList(list);
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        CameraPicBean bean = (CameraPicBean) adapter.getItem(position);
        if (bean.getPic() == null) {
            if (list.size() >= 4) {
                ToastUtils.showShort("最多只能上传三张图片!");
                return;
            } else {
                presenter.getTakePhoto(this, 4 - list.size());
            }


        } else {
            ShowImageWindow window = new ShowImageWindow(this, bean.getPic());
            window.showWindow(layout_titleBar);
        }
    }


    @OnClick({R.id.tx_lost_submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tx_lost_submit:
                presenter.getPicList();
                if (StringUtils.isEmpty(ed_lost_content.getText().toString().trim())) {
                    ToastUtils.showShort("请输入物品丢失地点和物品描述!");
                    return;
                }

                List<CameraPicBean> cameraPicBeans = adapter.dataList;
                if (cameraPicBeans.size() <= 1) {
                    ToastUtils.showShort("请添加丢失地点图片描述!");
                    return;
                }

                if (StringUtils.isEmpty(ed_lost_phone.getText().toString().trim())) {
                    ToastUtils.showShort("请输入您的联系方式!");
                    return;
                }
                presenter.showProgressDialog();
                MultipartBody.Part[] parts = Constants.getFileMaps(cameraPicBeans);
                presenter.getUpFiles(parts, ed_lost_content.getText().toString().trim(), ed_lost_phone.getText().toString().trim());

                break;
        }
    }

    @Override
    public void onUpSuccess() {
        List<CameraPicBean> cameraPicBeans = adapter.dataList;
        cameraPicBeans.clear();
        cameraPicBeans.add(new CameraPicBean());
        adapter.setDataList(cameraPicBeans);
        ed_lost_content.setText("");
        ed_lost_phone.setText("");
        startActivity(new Intent(this, LostActivity.class));
        finish();
    }
}
