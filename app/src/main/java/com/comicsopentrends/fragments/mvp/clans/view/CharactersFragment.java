package com.comicsopentrends.fragments.mvp.clans.view;

import com.comicsopentrends.model.ItemsItem;

import java.util.List;

public interface CharactersFragment {
    String CLAN_TAG = "clanTag";
    void show();
    void hide();
    void setDataClans(List<ItemsItem> dataClans);
    void updateToolbar(String text);

    void showScreenError(String message);
    void hideScreenError();

    void setRefreshing(boolean refresh);
}
