package com.comicsopentrends.fragments.mvp.clans.presenter;

import com.comicsopentrends.model.ItemsItem;

public interface OnFinishedDetailListener {
    void successDetail(ItemsItem itemsItem);
    void onFailedDetail(Throwable throwable);
}
