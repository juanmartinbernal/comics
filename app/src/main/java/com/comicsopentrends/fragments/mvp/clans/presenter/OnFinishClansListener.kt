package com.comicsopentrends.fragments.mvp.clans.presenter

import com.comicsopentrends.model.ResponseClans

interface OnFinishClansListener {
    fun onFailureSearchClan(throwable: Throwable)
    fun onSuccessSearchClan(response: ResponseClans)

    fun successClans(response: ResponseClans)
    fun onCompleteClans()
    fun onFailureClans(throwable: Throwable)
}
