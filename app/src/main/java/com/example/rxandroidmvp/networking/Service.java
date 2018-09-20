package com.example.rxandroidmvp.networking;


import android.content.Context;

import com.example.rxandroidmvp.models.NewsModel;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;


public class Service {
    private final NetworkService networkService;

    public Service(NetworkService networkService) {
        this.networkService = networkService;
    }

    public Subscription getNewslistFromService(Context context, final GetNewsListServiceCallback callback) {

      /*  if(Utility.isInternetAvailable(context)){


        }*/
        return networkService.getNewslistNetworkService()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext(new Func1<Throwable, Observable<? extends NewsModel>>() {
                    @Override
                    public Observable<? extends NewsModel> call(Throwable throwable) {
                        return Observable.error(throwable);
                    }
                })
                .subscribe(new Subscriber<NewsModel>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.onError(new NetworkError(e));

                    }

                    @Override
                    public void onNext(NewsModel cityListResponse) {
                        callback.onSuccess(cityListResponse);

                    }
                });
    }

    public interface GetNewsListServiceCallback {
        void onSuccess(NewsModel cityListResponse);

        void onError(NetworkError networkError);
    }
}
