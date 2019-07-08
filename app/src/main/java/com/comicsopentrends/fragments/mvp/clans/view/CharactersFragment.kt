package com.comicsopentrends.fragments.mvp.clans.view

import com.comicsopentrends.model.ItemsItem

interface CharactersFragment {
    fun show()
    fun hide()
    fun setDataClans(dataClans: List<ItemsItem>)
    fun updateToolbar(text: String)

    fun showScreenError(message: String?)
    fun hideScreenError()

    fun setRefreshing(refresh: Boolean)

    companion object {
        val CLAN_TAG = "clanTag"
    }
}
