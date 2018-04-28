package com.comicsopentrends.fragments.mvp.characteres.presenter.impl;

import com.comicsopentrends.fragments.mvp.characteres.DetailCharacterFragment;
import com.comicsopentrends.fragments.mvp.characteres.presenter.CharacteresFragmentDetailPresenter;
import com.comicsopentrends.model.Character;
import com.comicsopentrends.model.CharacterResponse;
import com.comicsopentrends.rest.ApiClient;
import com.comicsopentrends.rest.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Asus on 20/10/2017.
 */

public class CharacteresFragmentDetailPresenterImpl implements CharacteresFragmentDetailPresenter {

    private DetailCharacterFragment detailCharacterFragment;
    private ApiInterface apiService;

    public CharacteresFragmentDetailPresenterImpl(DetailCharacterFragment detailCharacterFragment) {
        this.detailCharacterFragment = detailCharacterFragment;
        apiService = ApiClient.getClient().create(ApiInterface.class);
    }

    /**
     * Método encargado de obtener el detalle de un personaje dado su id.
     * @param characterId
     */
    @Override
    public void goToDetail(int characterId) {

        Call<CharacterResponse> call = apiService.getComicDetails(characterId);
        call.enqueue(new Callback<CharacterResponse>() {
            @Override
            public void onResponse(Call<CharacterResponse> call, Response<CharacterResponse> response) {
                // String detail = response.body().toString();
                int code = response.code();
                if(code == 200) {
                    Character character = response.body().data.results.get(0);
                    detailCharacterFragment.loadData(character);
                }
            }

            @Override
            public void onFailure(Call<CharacterResponse> call, Throwable t) {
                // Log error here since request failed

            }
        });

    }
}
