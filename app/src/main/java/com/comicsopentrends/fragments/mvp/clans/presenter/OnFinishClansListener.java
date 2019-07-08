package com.comicsopentrends.fragments.mvp.clans.presenter;

import com.comicsopentrends.model.ResponseClans;

public interface OnFinishClansListener {
    void onFailureSearchClan(Throwable throwable);
    void onSuccessSearchClan(ResponseClans response);

    void successClans(ResponseClans response);
    void onCompleteClans();
    void onFailureClans(Throwable throwable);
}
