package com.smart.urban.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.smart.urban.R;
import com.smart.urban.base.BaseActivity;
import com.smart.urban.base.BasePresenter;
import com.smart.urban.bean.CameraPicBean;
import com.smart.urban.present.CameraPresent;
import com.smart.urban.ui.adapter.CameraListAdapter;
import com.smart.urban.utils.PhotoUtils;
import com.smart.urban.view.ICameraView;
import com.zhihu.matisse.Matisse;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.addapp.pickers.widget.WheelListView;

import static com.smart.urban.present.CameraPresent.REQUEST_CODE_CHOOSE;

/**
 * Created by root on 01.04.18.
 */

public class MyArticleActivity extends BaseActivity<ICameraView, CameraPresent> implements AdapterView.OnItemClickListener {
    @BindView(R.id.gv_article_list)
    GridView gv_article_list;
    private CameraListAdapter adapter;
    private List<CameraPicBean> list = new ArrayList<>();

    @Override
    protected int getContentViewId() {
        return R.layout.activity_my_article;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setTitle("我的文章");
        list.clear();
        list.add(new CameraPicBean());
        adapter = new CameraListAdapter(this, R.layout.item_camera_list, list);
        gv_article_list.setAdapter(adapter);
        gv_article_list.setOnItemClickListener(this);
    }

    @Override
    protected void onBackward(View backwardView) {
        super.onBackward(backwardView);
        finish();
    }

    @Override
    public CameraPresent initPresenter() {
        return new CameraPresent(this);
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
            presenter.getTakePhoto(this);
        }
    }


}
