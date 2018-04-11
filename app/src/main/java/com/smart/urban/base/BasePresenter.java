package com.smart.urban.base;

/**
 * Created by root on 18-1-19.
 */

public abstract class BasePresenter<T> {
    public T mView;

    public void attach(T mView){
        this.mView = mView;
    }

    public void dettach(){
        mView = null;
    }
}
