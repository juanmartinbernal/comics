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
    private static final String TOKEN_CLANS = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiIsImtpZCI6IjI4YTMxOGY3LTAwMDAtYTFlYi03ZmExLTJjNzQzM2M2Y2NhNSJ9.eyJpc3MiOiJzdXBlcmNlbGwiLCJhdWQiOiJzdXBlcmNlbGw6Z2FtZWFwaSIsImp0aSI6IjFlYTU2YWZlLTU3MzMtNDg1OC04ZTg3LTYwOWU4ZWE2YmE5NyIsImlhdCI6MTU2MTgxMjk4Mywic3ViIjoiZGV2ZWxvcGVyLzc1N2E2OTRiLTZiMmMtNjQ2ZS04NTFmLTE5YzM5ZWI2MmNkZiIsInNjb3BlcyI6WyJjbGFzaCJdLCJsaW1pdHMiOlt7InRpZXIiOiJkZXZlbG9wZXIvc2lsdmVyIiwidHlwZSI6InRocm90dGxpbmcifSx7ImNpZHJzIjpbIjQ2LjI3LjE1MS4yMTciXSwidHlwZSI6ImNsaWVudCJ9XX0.uCialURHl_y5wVwQdPOFIEksgM5Fn37BC1BoBzHh17lV8UDgR5V2QVroUVi8qaYz5wbqdkCLs8KBSooJfwPoFQ";

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
