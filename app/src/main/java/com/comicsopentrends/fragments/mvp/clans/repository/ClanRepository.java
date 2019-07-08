package com.comicsopentrends.fragments.mvp.clans.repository;

public interface ClanRepository {
    void getSearchClan(String query);

    void getClans(int limit, String warFrequency, String afterPaging);
}
