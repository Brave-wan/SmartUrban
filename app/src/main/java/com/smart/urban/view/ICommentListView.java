package com.smart.urban.view;

import com.smart.urban.base.IBaseView;
import com.smart.urban.bean.CommentListBean;

import java.util.List;

/**
 * Created by root on 18-4-13.
 */

public interface ICommentListView  extends IBaseView{
    void onCommentListBean(List<CommentListBean> listBeans);
    void onCommentSuccess();

}
