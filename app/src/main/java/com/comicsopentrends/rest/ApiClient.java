package com.comicsopentrends.rest;



import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Juan Martín Bernal on 20/10/2017.
 */

public class ApiClient {

    public static final String API_KEY = "a97e89847d934d0d551f6252cb4be16f";
    public static final String PUBLIC_KEY = "a97e89847d934d0d551f6252cb4be16f";
    public static final String PRIVATE_KEY = "978985e55e35edf030a37de670b4ea650cf2e580";
    public static final String TIMESTAMP = "1";
    public static final String HASH = TIMESTAMP + PRIVATE_KEY + PUBLIC_KEY;
    //ApiClient.API_KEY, offset, Utils.md5(ApiClient.HASH), ApiClient.TIMESTAMP
    public static final String BASE_URL = "https://gateway.marvel.com:443/v1/public/";
    private static Retrofit retrofit = null;

    /*public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }*/

    public static Retrofit getClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .addInterceptor(new AuthInterceptor(PUBLIC_KEY, PRIVATE_KEY));


        OkHttpClient client = builder.build();

        return new Retrofit.Builder().baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }
}
