package com.comicsopentrends.data.remote

import com.comicsopentrends.data.remote.dto.ClanDto
import com.comicsopentrends.data.remote.dto.ClanListResponseDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ClanApiService {

    @GET("clans/{clanTag}")
    suspend fun getClanDetails(@Path("clanTag") clanTag: String): ClanDto

    @GET("clans")
    suspend fun searchClans(@Query("name") name: String): ClanListResponseDto

    @GET("clans")
    suspend fun getClans(
        @Query("limit") limit: Int,
        @Query("warFrequency") warFrequency: String,
        @Query("after") after: String? = null,
    ): ClanListResponseDto

    companion object {
        const val DEFAULT_PAGE_SIZE = 20
        const val DEFAULT_WAR_FREQUENCY = "always"
    }
}
