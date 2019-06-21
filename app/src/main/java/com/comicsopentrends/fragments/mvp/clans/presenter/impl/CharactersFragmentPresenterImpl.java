package com.comicsopentrends.fragments.mvp.clans.presenter.impl;

import android.text.TextUtils;

import com.comicsopentrends.fragments.mvp.clans.presenter.CharactersFragmentPresenter;
import com.comicsopentrends.fragments.mvp.clans.view.CharactersFragment;
import com.comicsopentrends.model.ItemsItem;
import com.comicsopentrends.model.ResponseClans;
import com.comicsopentrends.rest.ApiClient;
import com.comicsopentrends.rest.ApiInterface;
import com.comicsopentrends.util.Constants;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Juan Martín Bernal on 20/10/2017.
 */

public class CharactersFragmentPresenterImpl implements CharactersFragmentPresenter {

    private List<ItemsItem> characters = new ArrayList<>();
    private ApiInterface apiService;
    //paginación
    private String beforePaging;
    private String afterPaging = "";

    private CharactersFragment charactersFragment;

    public CharactersFragmentPresenterImpl(CharactersFragment charactersFragment) {
        this.charactersFragment = charactersFragment;
        apiService = ApiClient.getClient().create(ApiInterface.class);
    }

    /**
     * Método encargado de realizar una busqueda donde el nombre comience por la cadena dada.
     *
     * @param query
     */
    @Override
    public void searchCharacter(String query) {
        charactersFragment.hideScreenError();
        charactersFragment.show();
        Call<ResponseClans> call = apiService.searchClan(query);
        call.enqueue(new Callback<ResponseClans>() {
            @Override
            public void onResponse(Call<ResponseClans> call, Response<ResponseClans> response) {
                if (response.code() == 200) {
                    characters.clear();
                    List<ItemsItem> itemsItems = response.body().getItems();
                    characters.addAll(itemsItems);
                    charactersFragment.setDataClans(characters);
                }
                charactersFragment.hide();

            }

            @Override
            public void onFailure(Call<ResponseClans> call, Throwable t) {
                // Log error here since request failed
                charactersFragment.hide();
                charactersFragment.showScreenError(t.getMessage());
            }
        });
    }

    /**
     * Método encargado de cargar los clanes
     */
    @Override
    public void loadList() {
        Observable<ResponseClans> responseClansObservable = null;
        charactersFragment.show();
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
                        beforePaging = response.getPaging().getCursors().getBefore();
                        afterPaging = response.getPaging().getCursors().getAfter();
                        characters.addAll(response.getItems());
                        charactersFragment.setDataClans(characters);
                    }

                    @Override
                    public void onError(Throwable e) {
                        charactersFragment.hide();
                        charactersFragment.showScreenError(e.getMessage());

                    }

                    @Override
                    public void onComplete() {
                        // Updates UI with data
                        charactersFragment.updateToolbar(String.valueOf(characters.size()));
                        charactersFragment.hide();
                    }
                });

    }

    /**
     * Método encargado de realizar la paginación y cargar los nuevos clanes en el listado.
     */
    @Override
    public void onLoadMore() {
        loadList();
    }

    @Override
    public void resetVariables() {
        characters.clear();
        beforePaging = "";
        afterPaging = "";
    }

    @Override
    public void onRefresh() {
        charactersFragment.hideScreenError();
        charactersFragment.setRefreshing(true);
        resetVariables();
        loadList();
    }

}
