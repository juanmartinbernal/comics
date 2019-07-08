package com.comicsopentrends.fragments.mvp.clans.presenter.impl

import com.comicsopentrends.fragments.mvp.clans.presenter.CharacteresFragmentDetailPresenter
import com.comicsopentrends.fragments.mvp.clans.presenter.OnFinishedDetailListener
import com.comicsopentrends.fragments.mvp.clans.repository.ClanDetailRepository
import com.comicsopentrends.fragments.mvp.clans.repository.impl.ClanDetailRepositoryImpl
import com.comicsopentrends.fragments.mvp.clans.view.impl.DetailCharacterFragmentImpl
import com.comicsopentrends.model.ItemsItem

/**
 * Created by Asus on 20/10/2017.
 */

class CharacteresFragmentDetailPresenterImpl(private val detailCharacterFragment: DetailCharacterFragmentImpl) : CharacteresFragmentDetailPresenter, OnFinishedDetailListener {
    private val clanDetailRepository: ClanDetailRepository

    init {
        clanDetailRepository = ClanDetailRepositoryImpl(this)
    }

    /**
     * Método encargado de obtener el detalle de un personaje dado su id.
     *
     * @param characterId
     */
    override fun goToDetail(characterId: String?) {
        clanDetailRepository.getDetailClan(characterId)
    }

    override fun successDetail(itemsItem: ItemsItem) {
        detailCharacterFragment.loadData(itemsItem)
    }

    override fun onFailedDetail(throwable: Throwable) {
        //
    }
}
