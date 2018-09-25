package com.example.rxandroidmvp;

import android.app.Application;
import android.os.Bundle;

import com.example.rxandroidmvp.deps.DaggerDeps;
import com.example.rxandroidmvp.deps.Deps;
import com.example.rxandroidmvp.networking.NetworkModule;

import java.io.File;

public class RxAndroidMvpApplication extends Application {

    Deps deps;

    @Override public void onCreate() {
        super.onCreate();
        File cacheFile = new File(getCacheDir(), "responses");
        deps = DaggerDeps.builder().networkModule(new NetworkModule(cacheFile)).build();
    }


    public Deps getDeps() {
        return deps;
    }

    public void setDeps(Deps deps) {
        this.deps = deps;
    }
}
