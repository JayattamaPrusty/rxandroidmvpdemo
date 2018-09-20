package com.example.rxandroidmvp.utils;


import com.example.rxandroidmvp.models.NewsModel;

public interface RecyclerItemClickListener {
    void onItemClick(NewsModel.Row row);
}