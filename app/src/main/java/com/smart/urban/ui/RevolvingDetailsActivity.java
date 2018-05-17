package com.smart.urban.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.smart.urban.R;
import com.smart.urban.base.BaseActivity;
import com.smart.urban.base.BasePresenter;
import com.smart.urban.bean.BannerBean;
import com.smart.urban.bean.CameraPicBean;
import com.smart.urban.bean.RevolvingDetailsBean;
import com.smart.urban.bean.RevolvingListBean;
import com.smart.urban.config.Constants;
import com.smart.urban.http.ApiCallback;
import com.smart.urban.http.BaseResult;
import com.smart.urban.http.HttpManager;
import com.smart.urban.ui.adapter.RevolvingDetailsListAdapter;
import com.smart.urban.ui.widget.ShowImageWindow;
import com.yancy.imageselector.ImageSelector;
import com.yancy.imageselector.ImageSelectorActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

/**
 * Created by root on 18-4-10.
 */

public class RevolvingDetailsActivity extends BaseActivity implements AdapterView.OnItemClickListener, RevolvingDetailsListAdapter.OnRemoveImageListener {
    RevolvingListBean bean;
    @BindView(R.id.gv_revolving_details)
    GridView gv_revolving_details;
    @BindView(R.id.tv_revolving_title)
    TextView tv_revolving_title;
    @BindView(R.id.lv_revolving_comment)
    RecyclerView lv_revolving_comment;
    RevolvingDetailsListAdapter adapter;
    List<RevolvingDetailsBean.AllStateBean> list = new ArrayList<>();
    BaseQuickAdapter<RevolvingDetailsBean.AllStateBean, BaseViewHolder> baseQuickAdapter;
    List<RevolvingListBean.ImagesBean> imagesBeans = new ArrayList<>();

    @Override
    protected int getContentViewId() {
        return R.layout.activity_revolving_details;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setTitle("详情");
        bean = (RevolvingListBean) getIntent().getSerializableExtra("bean");
        tv_revolving_title.setText(bean.getContent());
        lv_revolving_comment.setLayoutManager(new LinearLayoutManager(this));
        imagesBeans = bean.getImages();
        imagesBeans.add(new RevolvingListBean.ImagesBean());
        adapter = new RevolvingDetailsListAdapter(this, R.layout.item_camera_list, imagesBeans);
        gv_revolving_details.setAdapter(adapter);
        gv_revolving_details.setOnItemClickListener(this);
        adapter.setOnRemoveImageListener(this);//监听图片删除
        getForumById();

        baseQuickAdapter = new BaseQuickAdapter<RevolvingDetailsBean.AllStateBean, BaseViewHolder>(R.layout.item_revolving_detail, list) {
            @Override
            protected void convert(BaseViewHolder helper, RevolvingDetailsBean.AllStateBean item) {
                TextView tv_revolving_name = (TextView) helper.itemView.findViewById(R.id.tv_revolving_name);
                TextView tv_revolving_content = (TextView) helper.itemView.findViewById(R.id.tv_revolving_content);
                TextView tv_revolving_time = (TextView) helper.itemView.findViewById(R.id.tv_revolving_time);
                tv_revolving_name.setText(StringUtils.isEmpty(item.getName()) ? "暂无" : item.getName());
                tv_revolving_time.setText(item.getModifyTime());
                tv_revolving_content.setText(item.getContent() + "");
            }
        };
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ImageSelector.IMAGE_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            List<String> pathList = data.getStringArrayListExtra(ImageSelectorActivity.EXTRA_RESULT);
            for (String path : pathList) {
                RevolvingListBean.ImagesBean bean = new RevolvingListBean.ImagesBean();
                bean.setAdd(true);
                bean.setAddress(path);
                imagesBeans.add(0,bean);
            }
            adapter.setDataList(imagesBeans);
        }
    }

    @Override
    protected void onBackward(View backwardView) {
        super.onBackward(backwardView);
        finish();
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        RevolvingListBean.ImagesBean bean = (RevolvingListBean.ImagesBean) adapter.getItem(position);

        if (bean.getAddress() != null) {
            ShowImageWindow window = new ShowImageWindow(this, bean.getAddress());
            window.showWindow(layout_titleBar);
        } else {
            if (imagesBeans.size() >= 4) {
                ToastUtils.showShort("最多只能上传三张图片，请删除后再选择添加！");
            } else {
                Constants.takePhoto(RevolvingDetailsActivity.this, layout_titleBar, 4 - imagesBeans.size());
            }
        }


    }

    public void getForumById() {
        Map<String, Object> map = new HashMap<>();
        map.put("id", bean.getId() + "");
        HttpManager.get().addSubscription(HttpManager.get().getApiStores().getForumById(map), new ApiCallback<BaseResult<RevolvingDetailsBean>>() {
            @Override
            public void onSuccess(BaseResult<RevolvingDetailsBean> model) {
                list.addAll(model.getData().getAllState());
                lv_revolving_comment.setAdapter(baseQuickAdapter);
            }

            @Override
            public void onFailure(BaseResult result) {
                ToastUtils.showShort(result.errmsg);
            }
        });
    }


    public void getDelete(String id) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        HttpManager.get().addSubscription(HttpManager.get().getApiStores().getRemoveDate(map), new ApiCallback() {
            @Override
            public void onSuccess(Object model) {
            }

            @Override
            public void onFailure(BaseResult result) {
            }
        });

    }

    @Override
    public void remove(RevolvingListBean.ImagesBean bean) {
        for (int i = 0; i < imagesBeans.size(); i++) {
            RevolvingListBean.ImagesBean imagesBean = imagesBeans.get(i);

            if (imagesBean.getBelongId() == bean.getBelongId()) {
                imagesBeans.remove(imagesBean);
            }

            adapter.setDataList(imagesBeans);

        }


    }
}
