package com.comicsopentrends.fragments.mvp.characteres.view;

import com.comicsopentrends.model.ItemsItem;

import java.util.List;

public interface CharactersFragment {
    void show();
    void hide();
    void setDataClans(List<ItemsItem> dataClans);
    void updateToolbar(String text);
}
