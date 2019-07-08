package com.comicsopentrends.fragments.mvp.clans.repository

interface ClanRepository {
    fun getSearchClan(query: String?)

    fun getClans(limit: Int, warFrequency: String, afterPaging: String?)
}
