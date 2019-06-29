package com.comicsopentrends.fragments.mvp.clans.repository.impl;

import com.comicsopentrends.fragments.mvp.clans.presenter.OnFinishedDetailListener;
import com.comicsopentrends.fragments.mvp.clans.repository.ClanDetailRepository;
import com.comicsopentrends.model.ItemsItem;
import com.comicsopentrends.rest.ApiClient;
import com.comicsopentrends.rest.ApiInterface;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ClanDetailRepositoryImpl implements ClanDetailRepository {

    private ApiInterface apiService;
    private OnFinishedDetailListener onFinishedDetailListener;
    public ClanDetailRepositoryImpl(OnFinishedDetailListener onFinishedDetailListener) {
        apiService = ApiClient.getClient().create(ApiInterface.class);
        this.onFinishedDetailListener = onFinishedDetailListener;
    }

    @Override
    public void getDetailClan(String clanTag) {
         apiService.getClanDetails(clanTag).
                subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .distinct()
                .subscribe(new Observer<ItemsItem>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ItemsItem response) {
                        onFinishedDetailListener.successDetail(response);
                    }

                    @Override
                    public void onError(Throwable e) {
                        onFinishedDetailListener.onFailedDetail(e);

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
