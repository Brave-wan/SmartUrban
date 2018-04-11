package com.smart.urban.http;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by think on 2017/4/13.
 */

public class HttpManager {

    private ApiStores apiStores;
    private CompositeSubscription mCompositeSubscription;

    private static HttpManager instance;

    public static HttpManager get(){
        if(instance == null){
            instance = new HttpManager();
        }
        return instance;
    }

    public ApiStores getApiStores(){
        if (apiStores == null){
            apiStores = AppClient.getRetrofit().create(ApiStores.class);
        }
        return apiStores;
    }

    public void addSubscription(Observable observable, Subscriber subscriber) {
        if (mCompositeSubscription == null) {
            mCompositeSubscription = new CompositeSubscription();
        }
        mCompositeSubscription.add(observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber));
    }

    public void addSubscription(Subscription subscription) {
        if (mCompositeSubscription == null) {
            mCompositeSubscription = new CompositeSubscription();
        }
        mCompositeSubscription.add(subscription);
    }

    public void onUnsubscribe() {
        if (mCompositeSubscription != null && mCompositeSubscription.hasSubscriptions())
//            mCompositeSubscription.unsubscribe();使用unsubscribe后mCompositeSubscription不能继续使用所以使用clear
            mCompositeSubscription.clear();
    }
}
