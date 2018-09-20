package com.example.rxandroidmvp.networking;


import com.example.rxandroidmvp.models.NewsModel;

import retrofit2.http.GET;
import rx.Observable;


public interface NetworkService {

    @GET("s/2iodh4vg0eortkl/facts.json")
    Observable<NewsModel> getNewslistNetworkService();

}
