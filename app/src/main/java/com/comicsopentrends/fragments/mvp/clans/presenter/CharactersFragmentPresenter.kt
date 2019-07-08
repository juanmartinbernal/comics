package com.comicsopentrends.fragments.mvp.clans.presenter

/**
 * Created by Asus on 20/10/2017.
 */

interface CharactersFragmentPresenter {

    fun searchCharacter(query: String)

    fun loadList()

    fun onLoadMore()

    fun resetVariables()

    fun onRefresh()
}
