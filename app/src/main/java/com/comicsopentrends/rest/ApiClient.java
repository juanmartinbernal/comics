package com.comicsopentrends.rest;


import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Juan Martín Bernal on 20/10/2017.
 */

public class ApiClient {

    private static final String BASE_URL = "https://api.clashofclans.com/v1/";
    private static final String TOKEN_CLANS = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiIsImtpZCI6IjI4YTMxOGY3LTAwMDAtYTFlYi03ZmExLTJjNzQzM2M2Y2NhNSJ9.eyJpc3MiOiJzdXBlcmNlbGwiLCJhdWQiOiJzdXBlcmNlbGw6Z2FtZWFwaSIsImp0aSI6IjY0NTJmNTk4LWJlZTUtNDY4Zi1hOTBhLTBiNGVhNDgxMDk0NiIsImlhdCI6MTU2MjU4MTgzNywic3ViIjoiZGV2ZWxvcGVyLzc1N2E2OTRiLTZiMmMtNjQ2ZS04NTFmLTE5YzM5ZWI2MmNkZiIsInNjb3BlcyI6WyJjbGFzaCJdLCJsaW1pdHMiOlt7InRpZXIiOiJkZXZlbG9wZXIvc2lsdmVyIiwidHlwZSI6InRocm90dGxpbmcifSx7ImNpZHJzIjpbIjMxLjQuMTg0LjUiXSwidHlwZSI6ImNsaWVudCJ9XX0.kXmJgv2ERUyVcMwOdzyguZBFJTFPUub8J9VGvwQTcsh0jtXPRNmQyyiaWDaruxvSP5Xef5EwjBEsVxXchCwwjA";

    public static Retrofit getClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .addInterceptor(new AuthInterceptor(TOKEN_CLANS));
        OkHttpClient client = builder.build();

        return new Retrofit.Builder().baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }
}
