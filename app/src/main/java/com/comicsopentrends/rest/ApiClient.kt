package com.comicsopentrends.rest


import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by Juan Martín Bernal on 20/10/2017.
 */

object ApiClient {

    private const val BASE_URL = "https://api.clashofclans.com/v1/"
    private const val TOKEN_CLANS = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiIsImtpZCI6IjI4YTMxOGY3LTAwMDAtYTFlYi03ZmExLTJjNzQzM2M2Y2NhNSJ9.eyJpc3MiOiJzdXBlcmNlbGwiLCJhdWQiOiJzdXBlcmNlbGw6Z2FtZWFwaSIsImp0aSI6IjA1YTFhZGU3LWU2NjgtNDUyOS1iM2RmLTFiNjY4NWZmYWU5YSIsImlhdCI6MTY2ODc4NTIyNywic3ViIjoiZGV2ZWxvcGVyLzc1N2E2OTRiLTZiMmMtNjQ2ZS04NTFmLTE5YzM5ZWI2MmNkZiIsInNjb3BlcyI6WyJjbGFzaCJdLCJsaW1pdHMiOlt7InRpZXIiOiJkZXZlbG9wZXIvc2lsdmVyIiwidHlwZSI6InRocm90dGxpbmcifSx7ImNpZHJzIjpbIjUuMjI1Ljk0LjgwIl0sInR5cGUiOiJjbGllbnQifV19.Es0mQ0VtATyjdczAjtcMyvifteyxdWRnEI0ESNYqTFLjezWaJP8XiTVXg47pWcVJg1YPajrm5OC2eHvPcjwL7A"

    val client: Retrofit
        get() {
            val builder = OkHttpClient.Builder()
                    .addInterceptor(AuthInterceptor(TOKEN_CLANS))
            val client = builder.build()

            return Retrofit.Builder().baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()
        }
}
