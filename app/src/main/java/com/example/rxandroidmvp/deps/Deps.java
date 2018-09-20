package com.example.rxandroidmvp.deps;


import com.example.rxandroidmvp.home.HomeActivity;
import com.example.rxandroidmvp.networking.NetworkModule;

import javax.inject.Singleton;

import dagger.Component;


@Singleton
@Component(modules = {NetworkModule.class,})
public interface Deps {
    void inject(HomeActivity homeActivity);
}
