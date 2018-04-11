package com.smart.urban.ui;

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
import com.smart.urban.base.BaseActivity;
import com.smart.urban.base.BasePresenter;
import com.smart.urban.bean.CameraPicBean;
import com.smart.urban.config.Constants;
import com.smart.urban.present.ArticlePresent;
import com.smart.urban.present.CameraPresent;
import com.smart.urban.ui.adapter.CameraListAdapter;
import com.smart.urban.utils.PhotoUtils;
import com.smart.urban.view.IArticleView;
import com.smart.urban.view.ICameraView;
import com.zhihu.matisse.Matisse;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.addapp.pickers.widget.WheelListView;
import okhttp3.MultipartBody;

import static com.smart.urban.present.CameraPresent.REQUEST_CODE_CHOOSE;

/**
 * Created by root on 01.04.18.
 */

public class MyArticleActivity extends BaseActivity<IArticleView, ArticlePresent> implements AdapterView.OnItemClickListener, IArticleView {
    @BindView(R.id.gv_article_list)
    GridView gv_article_list;
    @BindView(R.id.ed_article_title)
    EditText ed_article_title;
    @BindView(R.id.ed_article_content)
    EditText ed_article_content;
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
    public ArticlePresent initPresenter() {
        return new ArticlePresent(this);
    }

    private List<Uri> mSelected;

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


    @OnClick({R.id.tv_article_submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_article_submit:
                presenter.getPicList();

                if (StringUtils.isEmpty(ed_article_title.getText().toString().trim())) {
                    ToastUtils.showShort("请输入文章标题!");
                    return;
                }
                if (StringUtils.isEmpty(ed_article_content.getText().toString().trim())) {
                    ToastUtils.showShort("请输入文章内容!");
                    return;
                }

                List<CameraPicBean> cameraPicBeans = adapter.dataList;

                if (cameraPicBeans.size() < 1) {
                    ToastUtils.showShort("请添加图片描述!");
                    return;
                }

                presenter.showProgressDialog();
                MultipartBody.Part[] parts = Constants.getFileMaps(cameraPicBeans);
                presenter.getUpFiles(parts, ed_article_title.getText().toString().trim(), ed_article_content.getText().toString().trim());
                break;
        }
    }

    @Override
    public void onSuccess() {
        List<CameraPicBean> list = adapter.dataList;
        list.clear();
        adapter.setDataList(list);
        ed_article_title.setText("");
        ed_article_content.setText("");

    }
}
