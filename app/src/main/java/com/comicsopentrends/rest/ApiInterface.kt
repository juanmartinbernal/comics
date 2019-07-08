package com.comicsopentrends.rest

import com.comicsopentrends.model.ItemsItem
import com.comicsopentrends.model.ResponseClans

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by Juan Martín Bernal on 20/10/2017.
 */

interface ApiInterface {

    @GET("clans/{clanTag}")
    fun getClanDetails(@Path("clanTag") clanTag: String?): Observable<ItemsItem>

    @GET("clans")
    fun searchClan(@Query("name") query: String?): Observable<ResponseClans>

    @GET("clans")
    fun getClans(@Query("limit") limit: Int, @Query("warFrequency") war: String): Observable<ResponseClans>

    @GET("clans")
    fun getClans(@Query("limit") limit: Int, @Query("warFrequency") war: String, @Query("after") after: String?): Observable<ResponseClans>

}

