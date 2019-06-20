package com.comicsopentrends.fragments.mvp.characteres.view;

import com.comicsopentrends.model.ItemsItem;

public interface ClansListener {
    void onItemClick(ItemsItem item);
    void seeImageCharacter(String url, String name);
}
