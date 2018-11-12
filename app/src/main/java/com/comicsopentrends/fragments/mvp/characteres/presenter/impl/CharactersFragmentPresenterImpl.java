package com.comicsopentrends.fragments.mvp.characteres.presenter.impl;

import com.comicsopentrends.fragments.mvp.characteres.CharactersFragment;
import com.comicsopentrends.fragments.mvp.characteres.presenter.CharactersFragmentPresenter;
import com.comicsopentrends.model.Character;
import com.comicsopentrends.model.CharacterResponse;
import com.comicsopentrends.rest.ApiClient;
import com.comicsopentrends.rest.ApiInterface;

import java.util.ArrayList;
import java.util.List;

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

    private List<Character> characters = new ArrayList<>();
    private ApiInterface apiService;
    private int totalCharacteres = -1;
    List<Character> results;

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
        charactersFragment.show();
        Call<CharacterResponse> call = apiService.searchComic(query);
        call.enqueue(new Callback<CharacterResponse>() {
            @Override
            public void onResponse(Call<CharacterResponse> call, Response<CharacterResponse> response) {
                if (response.code() == 200) {
                    characters.clear();
                    final List<Character> movies = response.body().data.results;
                    totalCharacteres = response.body().data.total;
                    characters.addAll(movies);
                    charactersFragment.refreshListScroll();
                }
                charactersFragment.hide();

            }

            @Override
            public void onFailure(Call<CharacterResponse> call, Throwable t) {
                // Log error here since request failed
                charactersFragment.hide();
            }
        });
    }

    /**
     * Método encargado de cargar los personajes dado el offset para hacer la paginación
     *
     * @param offset
     */
    @Override
    public void loadList(int offset) {

        charactersFragment.show();
        apiService.getComics(offset)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CharacterResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(CharacterResponse response) {
                        //allCurrencyList = new ArrayList<>(coinList.getData().values());
                       // results = new ArrayList<>(response.data.results);
                        totalCharacteres = response.data.total;
                        characters.addAll(response.data.results);
                        charactersFragment.refreshListScroll();
                    }

                    @Override
                    public void onError(Throwable e) {
                        charactersFragment.hide();

                    }

                    @Override
                    public void onComplete() {
                        // Updates UI with data

                        charactersFragment.updateToolbar(characters.size() + "/" + totalCharacteres);
                        charactersFragment.hide();
                        //cPresenter.updateCoinList(allCurrencyList);
                    }
                });
/*        Call<CharacterResponse> call = apiService.getComics(offset);
        call.enqueue(new Callback<CharacterResponse>() {
            @Override
            public void onResponse(Call<CharacterResponse> call, Response<CharacterResponse> response) {
                int statusCode = response.code();
                if (statusCode == 200) {
                    totalCharacteres = response.body().data.total;
                    List<Character> results = response.body().data.results;
                    characters.addAll(results);
                    charactersFragment.refreshListScroll();
                    charactersFragment.updateToolbar(characters.size() + "/" + totalCharacteres);
                }
                charactersFragment.hide();

            }

            @Override
            public void onFailure(Call<CharacterResponse> call, Throwable t) {
                // Log error here since request failed
                charactersFragment.hide();
            }
        });*/
    }

    @Override
    public List<Character> getCharacteresAcum() {
        return characters;
    }

    /**
     * Método encargado de realizar la paginación y cargar los nuevos personajes en el listado.
     *
     * @param offset
     */
    @Override
    public void onLoadMore(int offset) {
        offset = offset * 20;
        if (offset <= totalCharacteres) {
            loadList(offset);
        }
    }

}
