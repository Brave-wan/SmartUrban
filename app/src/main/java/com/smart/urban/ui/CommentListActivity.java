package com.smart.urban.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.smart.urban.R;
import com.smart.urban.base.BaseActivity;
import com.smart.urban.base.BasePresenter;
import com.smart.urban.bean.CommentListBean;
import com.smart.urban.http.ApiCallback;
import com.smart.urban.http.BaseResult;
import com.smart.urban.http.HttpManager;
import com.smart.urban.present.CommentListPresent;
import com.smart.urban.ui.adapter.CommentListAdapter;
import com.smart.urban.utils.LoadingLayout;
import com.smart.urban.utils.SharedPreferencesUtils;
import com.smart.urban.view.ICommentListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by root on 03.04.18.
 */

public class CommentListActivity extends BaseActivity<ICommentListView, CommentListPresent> implements ICommentListView, OnLoadmoreListener, OnRefreshListener {
    @BindView(R.id.lv_comment_list)
    ListView lv_comment_list;
    @BindView(R.id.smart_layout)
    SmartRefreshLayout smart_layout;
    @BindView(R.id.tv_comment_send)
    TextView tv_comment_send;
    @BindView(R.id.ed_comment)
    EditText ed_comment;
    @BindView(R.id.layout_root)
    LoadingLayout layout_root;
    CommentListAdapter adapter;
    List<CommentListBean> list = new ArrayList<>();
    String belongId;
    String urban;


    @Override
    protected int getContentViewId() {
        return R.layout.activity_comment_list;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setTitle("评论");
        layout_root.setStatus(LoadingLayout.Loading);
        belongId = getIntent().getStringExtra("id");
        urban = getIntent().getStringExtra("type");
        adapter = new CommentListAdapter(this, R.layout.item_comment_list, list);
        lv_comment_list.setAdapter(adapter);
        smart_layout.setOnLoadmoreListener(this);
        smart_layout.setOnRefreshListener(this);
        getUrBanMap(page);
    }

    int page = 1;

    @Override
    protected void onBackward(View backwardView) {
        super.onBackward(backwardView);
        finish();
    }

    @Override
    public CommentListPresent initPresenter() {
        return new CommentListPresent(this);
    }

    @Override
    public void onLoadmore(RefreshLayout refreshlayout) {
        smart_layout.finishLoadmore(1000);
        page++;
        getUrBanMap(page);
    }

    @Override
    public void onRefresh(RefreshLayout refreshlayout) {
        smart_layout.finishRefresh(1000);
        list.clear();
        page = 1;
        getUrBanMap(page);
    }

    public void getUrBanMap(int page) {
        //获取评论列表
        Map<String, Object> map = new HashMap<>();
        map.put("userId", SharedPreferencesUtils.init(this).getString("userId"));
        map.put("token", SharedPreferencesUtils.init(this).getString("token"));
        map.put("page", page);
        map.put("rows", 20);
        map.put("createUserId", SharedPreferencesUtils.init(this).getString("userId"));
        map.put("belongId", belongId);
        if (urban.equals("urban")) {
            presenter.getDynamicCommentList(map);
        } else {
            presenter.getForumCommentList(map);
        }
    }


    @OnClick({R.id.tv_comment_send})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_comment_send:
                if (StringUtils.isEmpty(ed_comment.getText().toString().trim())) {
                    ToastUtils.showShort("请输入评论内容!");
                    return;
                }
                getAddCommentMap(ed_comment.getText().toString().trim());
                break;
        }
    }


    public void getAddCommentMap(String content) {
        //添加评论
        Map<String, Object> map = new HashMap<>();
        map.put("userId", SharedPreferencesUtils.init(this).getString("userId"));
        map.put("token", SharedPreferencesUtils.init(this).getString("token"));
        map.put("createUserId", SharedPreferencesUtils.init(this).getString("userId"));
        map.put("belongId", belongId);
        map.put("content", content);

        if (urban.equals("urban")) {
            //动态评论
            presenter.getDynamicAddComment(map);
        } else {
            //文章评论
            presenter.getForumAddComment(map);
        }
    }

    @Override
    public void onCommentListBean(List<CommentListBean> listBeans) {
        list.addAll(listBeans);
        adapter.setDataList(list);
        layout_root.setStatus(list.size() > 0 ? LoadingLayout.Success : LoadingLayout.Empty);
    }

    @Override
    public void onCommentSuccess() {
        list.clear();
        page = 1;
        getUrBanMap(page);
        dismissProgressDialog();
        smart_layout.autoRefresh();
        ed_comment.setText("");
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
