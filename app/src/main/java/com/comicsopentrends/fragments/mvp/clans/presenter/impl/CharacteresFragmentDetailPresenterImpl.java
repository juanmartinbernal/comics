package com.comicsopentrends.fragments.mvp.clans.presenter.impl;

import com.comicsopentrends.fragments.mvp.clans.presenter.CharacteresFragmentDetailPresenter;
import com.comicsopentrends.fragments.mvp.clans.presenter.OnFinishedDetailListener;
import com.comicsopentrends.fragments.mvp.clans.repository.ClanDetailRepository;
import com.comicsopentrends.fragments.mvp.clans.repository.impl.ClanDetailRepositoryImpl;
import com.comicsopentrends.fragments.mvp.clans.view.impl.DetailCharacterFragmentImpl;
import com.comicsopentrends.model.ItemsItem;

/**
 * Created by Asus on 20/10/2017.
 */

public class CharacteresFragmentDetailPresenterImpl implements CharacteresFragmentDetailPresenter, OnFinishedDetailListener {

    private DetailCharacterFragmentImpl detailCharacterFragment;
    private ClanDetailRepository clanDetailRepository;

    public CharacteresFragmentDetailPresenterImpl(DetailCharacterFragmentImpl detailCharacterFragment) {
        this.detailCharacterFragment = detailCharacterFragment;
        clanDetailRepository = new ClanDetailRepositoryImpl(this);
    }

    /**
     * Método encargado de obtener el detalle de un personaje dado su id.
     *
     * @param characterId
     */
    @Override
    public void goToDetail(String characterId) {
        clanDetailRepository.getDetailClan(characterId);
    }

    @Override
    public void successDetail(ItemsItem itemsItem) {
        detailCharacterFragment.loadData(itemsItem);
    }

    @Override
    public void onFailedDetail(Throwable throwable) {
        //
    }
}
