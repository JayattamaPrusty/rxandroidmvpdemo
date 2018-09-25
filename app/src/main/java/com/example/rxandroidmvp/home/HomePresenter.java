package com.example.rxandroidmvp.home;


import android.content.Context;

import com.example.rxandroidmvp.databaseHelper.MyLocalDb;
import com.example.rxandroidmvp.models.NewsModel;
import com.example.rxandroidmvp.networking.NetworkError;
import com.example.rxandroidmvp.networking.Service;
import com.example.rxandroidmvp.utils.Utility;

import java.util.ArrayList;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;


public class HomePresenter {
    private final Service service;
    private final HomeView view;
    private CompositeSubscription subscriptions;

    public HomePresenter(Service service, HomeView view) {
        this.service = service;
        this.view = view;
        this.subscriptions = new CompositeSubscription();
    }

    public void getNewslist(Context context) {

        final MyLocalDb localDb=new MyLocalDb(context);

        if(Utility.isInternetAvailable(context)){

            view.showWait();

            Subscription subscription = service.getNewslistFromService(context,new Service.GetNewsListServiceCallback() {
                @Override
                public void onSuccess(NewsModel cityListResponse) {

                    for(NewsModel.Row row:cityListResponse.getRows()){

                        if(row.getTitle()!=null){

                            if(!localDb.isRowExist(row.getTitle(),row.getDescription(),row.getImageHref())){
                                localDb.insertNewsRecords(row.getTitle(),row.getTitle(),row.getDescription(),row.getImageHref());
                            }

                        }

                    }
                    view.removeWait();
                    view.getNewslistSuccess(cityListResponse);
                }

                @Override
                public void onError(NetworkError networkError) {
                    view.removeWait();
                    view.onFailure(networkError.getAppErrorMessage());
                }

            });

            subscriptions.add(subscription);


        }else {


            ArrayList<NewsModel.Row> rowArrayList=localDb.getNewsList(context);

            NewsModel newsModel=new NewsModel();
            newsModel.setRows(rowArrayList);

            view.getNewslistSuccess(newsModel);

        }
    }
    public void onStop() {
        subscriptions.unsubscribe();
    }
}
