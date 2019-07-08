package com.comicsopentrends.fragments.mvp.clans.presenter.impl;

import com.comicsopentrends.fragments.mvp.clans.presenter.CharactersFragmentPresenter;
import com.comicsopentrends.fragments.mvp.clans.presenter.OnFinishClansListener;
import com.comicsopentrends.fragments.mvp.clans.repository.ClanRepository;
import com.comicsopentrends.fragments.mvp.clans.repository.impl.ClanRepositoryImpl;
import com.comicsopentrends.fragments.mvp.clans.view.CharactersFragment;
import com.comicsopentrends.model.ItemsItem;
import com.comicsopentrends.model.ResponseClans;
import com.comicsopentrends.util.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Juan Martín Bernal on 20/10/2017.
 */

public class CharactersFragmentPresenterImpl implements CharactersFragmentPresenter, OnFinishClansListener {

    private List<ItemsItem> characters = new ArrayList<>();
    //paginación
    private String beforePaging;
    private String afterPaging = "";
    private ClanRepository clanRepository;
    private CharactersFragment charactersFragment;

    public CharactersFragmentPresenterImpl(CharactersFragment charactersFragment) {
        this.charactersFragment = charactersFragment;
        this.clanRepository = new ClanRepositoryImpl(this);
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
        clanRepository.getSearchClan(query);

    }

    /**
     * Método encargado de cargar los clanes
     */
    @Override
    public void loadList() {
        charactersFragment.show();
        clanRepository.getClans(Constants.LIMIT, Constants.WAR_FREQUENCY, afterPaging);

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

    @Override
    public void onFailureSearchClan(Throwable throwable) {
        charactersFragment.hide();
        charactersFragment.showScreenError(throwable.getMessage());
    }

    @Override
    public void onSuccessSearchClan(ResponseClans response) {
        characters.clear();
        List<ItemsItem> itemsItems = response.getItems();
        characters.addAll(itemsItems);
        charactersFragment.setDataClans(characters);

        charactersFragment.hide();
    }

    @Override
    public void successClans(ResponseClans response) {
        beforePaging = response.getPaging().getCursors().getBefore();
        afterPaging = response.getPaging().getCursors().getAfter();
        characters.addAll(response.getItems());
        charactersFragment.setDataClans(characters);
    }

    @Override
    public void onCompleteClans() {
        // Updates UI with data
        charactersFragment.updateToolbar(String.valueOf(characters.size()));
        charactersFragment.hide();
    }

    @Override
    public void onFailureClans(Throwable throwable) {
        charactersFragment.hide();
        charactersFragment.showScreenError(throwable.getMessage());

    }
}
