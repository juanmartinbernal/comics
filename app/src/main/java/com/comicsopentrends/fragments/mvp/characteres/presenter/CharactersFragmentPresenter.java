package com.comicsopentrends.fragments.mvp.characteres.presenter;

/**
 * Created by Asus on 20/10/2017.
 */

public interface CharactersFragmentPresenter {

    void searchCharacter(String query);

    void loadList();

    void onLoadMore();

    void resetVariables();
}
