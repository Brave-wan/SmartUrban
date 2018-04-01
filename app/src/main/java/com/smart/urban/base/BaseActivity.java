package com.smart.urban.base;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;

import com.smart.urban.http.HttpManager;

import org.loader.autohideime.HideIMEUtil;

import butterknife.ButterKnife;


public abstract class BaseActivity<V, T extends BasePresenter<V>> extends TitleActivity {
    private Handler handler = new Handler();
    public T presenter;

    protected abstract int getContentViewId();

    protected abstract void initView(Bundle savedInstanceState);

    public abstract T initPresenter();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewId());
        ButterKnife.bind(this);
        HideIMEUtil.wrap(this);
        presenter = initPresenter();
        if (null != presenter)
            presenter.attach((V) this);
        initView(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        HttpManager.get().onUnsubscribe();
        if (null != handler) handler.removeCallbacks(null);
        if (null != presenter)
            presenter.dettach();
    }

    public ProgressDialog progressDialog;

    public ProgressDialog showProgressDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("加载中...");
        progressDialog.show();
        return progressDialog;
    }

    public ProgressDialog showProgressDialog(CharSequence message) {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(message);
        progressDialog.show();
        return progressDialog;
    }

    public ProgressDialog showProgressDialog(boolean out) {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("加载中...");
        progressDialog.setCanceledOnTouchOutside(out);
        progressDialog.show();
        return progressDialog;
    }

    public void dismissProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

}
