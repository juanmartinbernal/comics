package com.comicsopentrends.fragments.mvp.clans.view

import com.comicsopentrends.model.ItemsItem

interface ClansListener {
    fun onItemClick(item: ItemsItem)
    fun seeImageCharacter(url: String?, name: String?)
}
