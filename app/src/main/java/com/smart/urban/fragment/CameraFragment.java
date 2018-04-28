package com.smart.urban.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;

import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.smart.urban.R;
import com.smart.urban.base.BaseFragment;
import com.smart.urban.bean.CameraPicBean;
import com.smart.urban.config.Constants;
import com.smart.urban.present.CameraPresent;
import com.smart.urban.ui.MainActivity;
import com.smart.urban.ui.RevolvingActivity;
import com.smart.urban.ui.adapter.CameraListAdapter;
import com.smart.urban.ui.dialog.UpDynamicDialog;
import com.smart.urban.ui.widget.LifePaymentWindow;
import com.smart.urban.ui.widget.ShowImageWindow;
import com.smart.urban.utils.PhotoUtils;
import com.smart.urban.view.ICameraView;
import com.zhihu.matisse.Matisse;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MultipartBody;

import static android.app.Activity.RESULT_OK;
import static com.smart.urban.present.CameraPresent.REQUEST_CODE_CHOOSE;

/**
 * Created by root on 18-3-29.
 */

public class CameraFragment extends BaseFragment<ICameraView, CameraPresent>
        implements AdapterView.OnItemClickListener, ICameraView {

    @BindView(R.id.gv_camera_list)
    GridView gv_camera_list;
    @BindView(R.id.ed_camera_content)
    EditText ed_camera_content;
    private List<CameraPicBean> list = new ArrayList<>();
    private CameraListAdapter adapter;
    private MainActivity mainActivity;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_camera;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        setTitle("随手拍");
        list.clear();
        list.add(new CameraPicBean());
        adapter = new CameraListAdapter(getActivity(), R.layout.item_camera_list, list);
        gv_camera_list.setAdapter(adapter);
        gv_camera_list.setOnItemClickListener(this);
    }

    @Override
    public CameraPresent initPresenter() {
        return new CameraPresent(getActivity());
    }

    List<Uri> mSelected;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {
            mSelected = Matisse.obtainResult(data);
            if (mSelected != null && mSelected.size() > 0) {
                for (Uri uri : mSelected) {
                    String path = PhotoUtils.getRealPathFromUri(getActivity(), uri);
                    CameraPicBean bean = new CameraPicBean();
                    bean.setPic(path);
                    list.add(0, bean);
                }
                adapter.setDataList(list);
            }
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context != null) {
            mainActivity = (MainActivity) context;
        }
    }

    @OnClick({R.id.tv_camera_submit})
    public void onClick(View view) {
        switch (view.getId()) {
            //提交随手拍
            case R.id.tv_camera_submit:
                presenter.getPicList();

                if (StringUtils.isEmpty(ed_camera_content.getText().toString().trim())) {
                    ToastUtils.showShort("请输入问题描述!");
                    return;
                }

                List<CameraPicBean> cameraPicBeans = adapter.dataList;

                if (cameraPicBeans.size() <= 1) {
                    ToastUtils.showShort("请至少添加一张图片");
                    return;
                }

                presenter.showProgressDialog();
                MultipartBody.Part[] parts = Constants.getFileMaps(cameraPicBeans);
                presenter.getUpFiles(parts, ed_camera_content.getText().toString().trim());
                break;
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
                mainActivity.getTakePhoto(4 - list.size());
            }
        } else {
            ShowImageWindow window = new ShowImageWindow(getActivity(), bean.getPic());
            window.showWindow(view);
        }
    }

    @Override
    public void onUpSuccess(UpDynamicDialog dynamicDialog) {
        list = adapter.dataList;
        list.clear();
        list.add(new CameraPicBean());
        adapter.setDataList(list);
        ed_camera_content.setText("");
        startActivity(new Intent(getActivity(), RevolvingActivity.class));
        dynamicDialog.dismiss();
    }


}
