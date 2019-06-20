package com.comicsopentrends.fragments.mvp.characteres.presenter.impl;

import com.comicsopentrends.fragments.mvp.characteres.presenter.CharacteresFragmentDetailPresenter;
import com.comicsopentrends.fragments.mvp.characteres.view.impl.DetailCharacterFragmentImpl;
import com.comicsopentrends.model.ItemsItem;
import com.comicsopentrends.rest.ApiClient;
import com.comicsopentrends.rest.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Asus on 20/10/2017.
 */

public class CharacteresFragmentDetailPresenterImpl implements CharacteresFragmentDetailPresenter {

    private DetailCharacterFragmentImpl detailCharacterFragment;
    private ApiInterface apiService;

    public CharacteresFragmentDetailPresenterImpl(DetailCharacterFragmentImpl detailCharacterFragment) {
        this.detailCharacterFragment = detailCharacterFragment;
        apiService = ApiClient.getClient().create(ApiInterface.class);
    }

    /**
     * Método encargado de obtener el detalle de un personaje dado su id.
     * @param characterId
     */
    @Override
    public void goToDetail(String characterId) {

        Call<ItemsItem> call = apiService.getClanDetails(characterId);
        call.enqueue(new Callback<ItemsItem>() {
            @Override
            public void onResponse(Call<ItemsItem> call, Response<ItemsItem> response) {
                // String detail = response.body().toString();
                int code = response.code();
                if(code == 200) {
                    ItemsItem character = response.body();
                    detailCharacterFragment.loadData(character);
                }
            }

            @Override
            public void onFailure(Call<ItemsItem> call, Throwable t) {
                // Log error here since request failed

            }
        });

    }
}
