package com.comicsopentrends.fragments.mvp.clans.presenter

import com.comicsopentrends.model.ItemsItem

interface OnFinishedDetailListener {
    fun successDetail(itemsItem: ItemsItem)
    fun onFailedDetail(throwable: Throwable)
}
