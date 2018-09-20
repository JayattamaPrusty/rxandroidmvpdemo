package com.example.rxandroidmvp.home;

import com.example.rxandroidmvp.models.NewsModel;


public interface HomeView {
    void showWait();

    void removeWait();

    void onFailure(String appErrorMessage);

    void getNewslistSuccess(NewsModel cityListResponse);

}
