package com.comicsopentrends.fragments.mvp.clans.repository.impl

import com.comicsopentrends.fragments.mvp.clans.presenter.OnFinishedDetailListener
import com.comicsopentrends.fragments.mvp.clans.repository.ClanDetailRepository
import com.comicsopentrends.model.ItemsItem
import com.comicsopentrends.rest.ApiClient
import com.comicsopentrends.rest.ApiInterface

import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class ClanDetailRepositoryImpl(private val onFinishedDetailListener: OnFinishedDetailListener) : ClanDetailRepository {

    private val apiService: ApiInterface

    init {
        apiService = ApiClient.client.create(ApiInterface::class.java!!)
    }

    override fun getDetailClan(clanTag: String?) {
        apiService.getClanDetails(clanTag).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .distinct()
                .subscribe(object : Observer<ItemsItem> {
                    override fun onSubscribe(d: Disposable) {

                    }

                    override fun onNext(response: ItemsItem) {
                        onFinishedDetailListener.successDetail(response)
                    }

                    override fun onError(e: Throwable) {
                        onFinishedDetailListener.onFailedDetail(e)

                    }

                    override fun onComplete() {

                    }
                })
    }
}
