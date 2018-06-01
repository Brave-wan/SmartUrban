package com.smart.urban.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.smart.urban.R;
import com.smart.urban.base.BaseActivity;
import com.smart.urban.bean.RevolvingDetailsBean;
import com.smart.urban.bean.RevolvingListBean;
import com.smart.urban.config.Constants;
import com.smart.urban.present.RevolvingDetailsPresent;
import com.smart.urban.ui.adapter.RevolvingDetailsListAdapter;
import com.smart.urban.ui.widget.RecycleViewDivider;
import com.smart.urban.ui.widget.ShowImageWindow;
import com.smart.urban.view.IRevolvingDetailsView;
import com.yancy.imageselector.ImageSelector;
import com.yancy.imageselector.ImageSelectorActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MultipartBody;

/**
 * Created by root on 18-4-10.
 */

public class RevolvingDetailsActivity extends BaseActivity<IRevolvingDetailsView, RevolvingDetailsPresent> implements
        BaseQuickAdapter.OnItemClickListener, IRevolvingDetailsView {
    RevolvingListBean bean;
    @BindView(R.id.gv_revolving_details)
    RecyclerView gv_revolving_details;
    @BindView(R.id.tv_revolving_title)
    TextView tv_revolving_title;
    @BindView(R.id.lv_revolving_comment)
    RecyclerView lv_revolving_comment;
    @BindView(R.id.tx_revolving_delete)
    TextView tx_revolving_delete;
    @BindView(R.id.tx_details_type)
    TextView tx_details_type;
    @BindView(R.id.tx_address)
    TextView tx_address;
    @BindView(R.id.ll_operating_status)
    LinearLayout ll_operating_status;
    List<RevolvingDetailsBean.AllStateBean> list = new ArrayList<>();
    BaseQuickAdapter<RevolvingDetailsBean.AllStateBean, BaseViewHolder> baseQuickAdapter;
    List<RevolvingListBean.ImagesBean> imagesBeans = new ArrayList<>();
    BaseQuickAdapter<RevolvingListBean.ImagesBean, BaseViewHolder> picAdapter;
    private int picSize = 0;

    @Override
    protected int getContentViewId() {
        return R.layout.activity_revolving_details;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setTitle("详情");
        bean = (RevolvingListBean) getIntent().getSerializableExtra("bean");
        tv_revolving_title.setText(bean.getContent());
        imagesBeans = bean.getImages();
        picSize = imagesBeans.size();

        if (!bean.getState().equals("17")) {
            RevolvingListBean.ImagesBean imagesBean = new RevolvingListBean.ImagesBean();
            imagesBean.setCamrea(true);
            imagesBean.setAddress(null);
            imagesBean.setBelongId(System.currentTimeMillis());
            imagesBeans.add(imagesBean);
        }

        tx_details_type.setText(bean.getTypeName());
        String address = StringUtils.isEmpty(bean.getAddrName()) ? "暂无" : bean.getAddrName();
        tx_address.setText("问题位置:" + address);
        presenter.getForumById(bean.getId());
        initAdapter();
    }

    private void initAdapter() {
        tx_revolving_delete.setVisibility(bean.getState().equals("9") ? View.VISIBLE : View.GONE);
        ll_operating_status.setVisibility(bean.getState().equals("17") ? View.GONE : View.VISIBLE);
        lv_revolving_comment.setLayoutManager(new LinearLayoutManager(this));
        baseQuickAdapter = new BaseQuickAdapter<RevolvingDetailsBean.AllStateBean, BaseViewHolder>(R.layout.item_revolving_detail, list) {
            @Override
            protected void convert(BaseViewHolder helper, RevolvingDetailsBean.AllStateBean item) {
                TextView tv_revolving_name = (TextView) helper.itemView.findViewById(R.id.tv_revolving_name);
                TextView tv_revolving_content = (TextView) helper.itemView.findViewById(R.id.tv_revolving_content);
                TextView tv_revolving_time = (TextView) helper.itemView.findViewById(R.id.tv_revolving_time);
                tv_revolving_name.setText(item.getName());//修改的提示内容
                tv_revolving_time.setText(item.getModifyTime());//修改名称
                tv_revolving_content.setText(item.getContent());
            }
        };
        initPicAdapter();
    }


    public void initPicAdapter() {
        gv_revolving_details.setLayoutManager(new GridLayoutManager(this, 4));
        picAdapter = new BaseQuickAdapter<RevolvingListBean.ImagesBean, BaseViewHolder>(R.layout.item_camera_list, imagesBeans) {
            @Override
            protected void convert(BaseViewHolder helper, final RevolvingListBean.ImagesBean item) {
                ImageView item_camera = (ImageView) helper.itemView.findViewById(R.id.item_camera);
                ImageView img_item_delete = (ImageView) helper.itemView.findViewById(R.id.img_item_delete);
                //判断图片是否为空，如果不为空判断状态是是处理完成
                img_item_delete.setVisibility(item.getAddress() == null ? View.GONE : bean.getState().equals("17") ? View.GONE : View.VISIBLE);

                if (item.getAddress() != null) {
                    Glide.with(RevolvingDetailsActivity.this)
                            .load(item.isAdd() ? item.getAddress() : Constants.BASE_URL + item.getAddress())
                            .placeholder(R.drawable.icon_pic_empty)
                            .error(R.drawable.icon_pic_empty)
                            .into(item_camera);
                } else {
                    item_camera.setBackground(getResources().getDrawable(R.drawable.icon_up_loading_photo_btn));
                }

                img_item_delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        presenter.getDeletePicDialog(item);
                    }
                });
            }
        };

        picAdapter.setOnItemClickListener(this);
        gv_revolving_details.setAdapter(picAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ImageSelector.IMAGE_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            List<String> pathList = data.getStringArrayListExtra(ImageSelectorActivity.EXTRA_RESULT);
            for (String path : pathList) {
                RevolvingListBean.ImagesBean bean = new RevolvingListBean.ImagesBean();
                bean.setBelongId(System.currentTimeMillis());
                bean.setAdd(true);
                bean.setAddress(path);
                imagesBeans.add(0, bean);
            }
            initPicAdapter();
        }
    }

    @Override
    protected void onBackward(View backwardView) {
        super.onBackward(backwardView);
        finish();
    }

    @Override
    public RevolvingDetailsPresent initPresenter() {
        return new RevolvingDetailsPresent(this);
    }

    @Override
    public void removeImageBean(RevolvingListBean.ImagesBean bean) {
        if (imagesBeans.size() > 2) {
            for (int i = 0; i < imagesBeans.size(); i++) {
                RevolvingListBean.ImagesBean imagesBean = imagesBeans.get(i);
                if (bean.getAddress().equals(imagesBean.getAddress())) {
                    imagesBeans.remove(i);
                }
            }
            initPicAdapter();
        } else {
            ToastUtils.showShort("请至少保留一张图片!");
        }

    }

    @OnClick({R.id.tx_revolving_delete, R.id.tx_submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tx_revolving_delete:
                //删除问题
                presenter.getDeleteRevolvingDialog(bean.getId() + "");
                break;
            case R.id.tx_submit:
                List<RevolvingListBean.ImagesBean> list = picAdapter.getData();
                //添加的图片
                List<RevolvingListBean.ImagesBean> addList = new ArrayList<>();
                //原来的图片
                List<RevolvingListBean.ImagesBean> original = new ArrayList<>();
                for (RevolvingListBean.ImagesBean imagesBean : list) {
                    if (imagesBean.isAdd()) {
                        addList.add(imagesBean);
                    } else if (imagesBean.getAddress() != null) {
                        original.add(imagesBean);
                    }
                }
                //判断提交是否已经修改
                if (picSize == original.size() && addList.size() <= 0) {
                    ToastUtils.showShort("您未有任何修改，请修改后再进行提交!");
                    return;
                }

                MultipartBody.Part[] parts = null;
                if (addList.size() <= 0) {
                    presenter.getUpLoading(parts, bean, original);
                } else {
                    parts = Constants.getRevolvingMap(addList);
                    presenter.getUpLoading(parts, bean, original);
                }

                break;
        }
    }

    @Override
    public void onRevolvingDetails(RevolvingDetailsBean bean) {
        list.addAll(bean.getAllState());
        lv_revolving_comment.setAdapter(baseQuickAdapter);
    }

    @Override
    public void onDeleteSuccess() {
        //删除成功
        finish();
    }


    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        RevolvingListBean.ImagesBean bean = (RevolvingListBean.ImagesBean) picAdapter.getItem(position);
        if (bean.getAddress() != null) {
            ShowImageWindow window = new ShowImageWindow(this, Constants.BASE_URL + bean.getAddress());
            window.showWindow(layout_titleBar);
        } else {
            if (imagesBeans.size() >= 7) {
                ToastUtils.showShort("最多只能上传六张图片，请删除后再选择添加！");
            } else {
                Constants.takePhoto(RevolvingDetailsActivity.this, layout_titleBar, 7 - imagesBeans.size());
            }
        }
    }
}
