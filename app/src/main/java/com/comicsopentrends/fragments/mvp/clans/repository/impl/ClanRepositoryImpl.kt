package com.comicsopentrends.fragments.mvp.clans.repository.impl

import android.text.TextUtils

import com.comicsopentrends.fragments.mvp.clans.presenter.OnFinishClansListener
import com.comicsopentrends.fragments.mvp.clans.repository.ClanRepository
import com.comicsopentrends.model.ResponseClans
import com.comicsopentrends.rest.ApiClient
import com.comicsopentrends.rest.ApiInterface
import com.comicsopentrends.util.Constants

import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class ClanRepositoryImpl(private val onFinishClansListener: OnFinishClansListener) : ClanRepository {
    private val apiService: ApiInterface

    init {
        apiService = ApiClient.client.create(ApiInterface::class.java!!)
    }

    override fun getSearchClan(query: String?) {
        apiService.searchClan(query).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .distinct()
                .subscribe(object : Observer<ResponseClans> {
                    override fun onSubscribe(d: Disposable) {

                    }

                    override fun onNext(response: ResponseClans) {
                        onFinishClansListener.onSuccessSearchClan(response)
                    }

                    override fun onError(e: Throwable) {
                        onFinishClansListener.onFailureSearchClan(e)

                    }

                    override fun onComplete() {

                    }
                })
    }

    override fun getClans(limit: Int, warFrequency: String, afterPaging: String?) {
        var responseClansObservable: Observable<ResponseClans>? = null
        if (TextUtils.isEmpty(afterPaging)) {
            responseClansObservable = apiService.getClans(Constants.LIMIT, Constants.WAR_FREQUENCY)
        } else {
            responseClansObservable = apiService.getClans(Constants.LIMIT, Constants.WAR_FREQUENCY, afterPaging)
        }

        responseClansObservable!!.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .distinct()
                .subscribe(object : Observer<ResponseClans> {
                    override fun onSubscribe(d: Disposable) {

                    }

                    override fun onNext(response: ResponseClans) {
                        onFinishClansListener.successClans(response)
                    }

                    override fun onError(e: Throwable) {
                        onFinishClansListener.onFailureClans(e)
                    }

                    override fun onComplete() {
                        onFinishClansListener.onCompleteClans()
                    }
                })
    }
}
