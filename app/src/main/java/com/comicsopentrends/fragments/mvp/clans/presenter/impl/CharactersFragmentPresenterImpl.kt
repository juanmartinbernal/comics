package com.comicsopentrends.fragments.mvp.clans.presenter.impl

import com.comicsopentrends.fragments.mvp.clans.presenter.CharactersFragmentPresenter
import com.comicsopentrends.fragments.mvp.clans.presenter.OnFinishClansListener
import com.comicsopentrends.fragments.mvp.clans.repository.ClanRepository
import com.comicsopentrends.fragments.mvp.clans.repository.impl.ClanRepositoryImpl
import com.comicsopentrends.fragments.mvp.clans.view.CharactersFragment
import com.comicsopentrends.model.ItemsItem
import com.comicsopentrends.model.ResponseClans
import com.comicsopentrends.util.Constants
import java.util.*

/**
 * Created by Juan Martín Bernal on 20/10/2017.
 */

class CharactersFragmentPresenterImpl(private val charactersFragment: CharactersFragment) : CharactersFragmentPresenter, OnFinishClansListener {

    private val characters = ArrayList<ItemsItem>()
    //paginación
    private var beforePaging: String? = null
    private var afterPaging: String? = ""
    private val clanRepository: ClanRepository

    init {
        this.clanRepository = ClanRepositoryImpl(this)
    }

    /**
     * Método encargado de realizar una busqueda donde el nombre comience por la cadena dada.
     *
     * @param query
     */
    override fun searchCharacter(query: String) {
        charactersFragment.hideScreenError()
        charactersFragment.show()
        clanRepository.getSearchClan(query)

    }

    /**
     * Método encargado de cargar los clanes
     */
    override fun loadList() {
        charactersFragment.show()
        clanRepository.getClans(Constants.LIMIT, Constants.WAR_FREQUENCY, afterPaging)

    }

    /**
     * Método encargado de realizar la paginación y cargar los nuevos clanes en el listado.
     */
    override fun onLoadMore() {
        loadList()
    }

    override fun resetVariables() {
        characters.clear()
        beforePaging = ""
        afterPaging = ""
    }

    override fun onRefresh() {
        charactersFragment.hideScreenError()
        charactersFragment.setRefreshing(true)
        resetVariables()
        loadList()
    }

    override fun onFailureSearchClan(throwable: Throwable) {
        charactersFragment.hide()
        charactersFragment.showScreenError(throwable.message)
    }

    override fun onSuccessSearchClan(response: ResponseClans) {
        characters.clear()
        val itemsItems = response.items
        characters.addAll(itemsItems!!)
        charactersFragment.setDataClans(characters)

        charactersFragment.hide()
    }

    override fun successClans(response: ResponseClans) {
        beforePaging = response.paging!!.cursors!!.before
        afterPaging = response.paging!!.cursors!!.after
        characters.addAll(response.items!!)
        charactersFragment.setDataClans(characters)
    }

    override fun onCompleteClans() {
        // Updates UI with data
        charactersFragment.updateToolbar(characters.size.toString())
        charactersFragment.hide()
    }

    override fun onFailureClans(throwable: Throwable) {
        charactersFragment.hide()
        charactersFragment.showScreenError(throwable.message)

    }
}
