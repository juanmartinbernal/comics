package com.comicsopentrends.fragments.mvp.clans.repository.impl;

import android.text.TextUtils;

import com.comicsopentrends.fragments.mvp.clans.presenter.OnFinishClansListener;
import com.comicsopentrends.fragments.mvp.clans.repository.ClanRepository;
import com.comicsopentrends.model.ResponseClans;
import com.comicsopentrends.rest.ApiClient;
import com.comicsopentrends.rest.ApiInterface;
import com.comicsopentrends.util.Constants;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ClanRepositoryImpl implements ClanRepository {
    private ApiInterface apiService;
    private OnFinishClansListener onFinishClansListener;

    public ClanRepositoryImpl(OnFinishClansListener onFinishClansListener) {
        this.onFinishClansListener = onFinishClansListener;
        apiService = ApiClient.getClient().create(ApiInterface.class);
    }

    @Override
    public void getSearchClan(String query) {
        apiService.searchClan(query).
                subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .distinct()
                .subscribe(new Observer<ResponseClans>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResponseClans response) {
                        onFinishClansListener.onSuccessSearchClan(response);
                    }

                    @Override
                    public void onError(Throwable e) {
                        onFinishClansListener.onFailureSearchClan(e);

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void getClans(int limit, String warFrequency, String afterPaging) {
        Observable<ResponseClans> responseClansObservable = null;
        if (TextUtils.isEmpty(afterPaging)) {
            responseClansObservable = apiService.getClans(Constants.LIMIT, Constants.WAR_FREQUENCY);
        } else {
            responseClansObservable = apiService.getClans(Constants.LIMIT, Constants.WAR_FREQUENCY, afterPaging);
        }

        responseClansObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .distinct()
                .subscribe(new Observer<ResponseClans>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResponseClans response) {
                        onFinishClansListener.successClans(response);
                    }

                    @Override
                    public void onError(Throwable e) {
                        onFinishClansListener.onFailureClans(e);
                    }

                    @Override
                    public void onComplete() {
                        onFinishClansListener.onCompleteClans();
                    }
                });
    }
}
