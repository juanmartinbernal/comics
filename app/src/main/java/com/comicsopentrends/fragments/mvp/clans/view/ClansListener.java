package com.comicsopentrends.fragments.mvp.clans.view;

import com.comicsopentrends.model.ItemsItem;

public interface ClansListener {
    void onItemClick(ItemsItem item);
    void seeImageCharacter(String url, String name);
}
