package com.kxg.livewallpaper.api;

import com.kxg.livewallpaper.constant.Constant;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by kuangxiaoguo on 2018/3/21.
 */

public class ChatApi {

    private static final int TIME_OUT = 8;
    public ApiService mApiService;
    private static volatile ChatApi instance;

    private ChatApi(OkHttpClient client) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constant.BASE_URL)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mApiService = retrofit.create(ApiService.class);
    }

    public static ChatApi getInstance() {
        if (instance == null) {
            synchronized (ChatApi.class) {
                if (instance == null) {
                    OkHttpClient client = new OkHttpClient.Builder()
                            .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                            .build();
                    instance = new ChatApi(client);
                }
            }
        }
        return instance;
    }
}
