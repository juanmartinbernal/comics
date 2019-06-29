package com.comicsopentrends.fragments.mvp.clans.interactor;

import io.reactivex.Observable;

public interface ClanDetailInteractor {
    Observable getDetail(String clantaTag);
}
