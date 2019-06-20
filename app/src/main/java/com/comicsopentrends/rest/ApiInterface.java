package com.comicsopentrends.rest;

import com.comicsopentrends.model.ItemsItem;
import com.comicsopentrends.model.ResponseClans;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Juan Martín Bernal on 20/10/2017.
 */

public interface ApiInterface {

    @GET("clans/{clanTag}")
    Call<ItemsItem> getClanDetails(@Path("clanTag") String clanTag);

    @GET("clans")
    Call<ResponseClans> searchClan(@Query("name") String query);

    @GET("clans")
    Observable<ResponseClans> getClans(@Query("limit") int limit, @Query("warFrequency") String war);

    @GET("clans")
    Observable<ResponseClans> getClans(@Query("limit") int limit, @Query("warFrequency") String war, @Query("after") String after);
}

