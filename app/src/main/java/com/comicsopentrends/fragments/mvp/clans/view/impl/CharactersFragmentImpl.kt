package com.comicsopentrends.fragments.mvp.clans.view.impl

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.MenuItemCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.view.*
import android.view.animation.AnimationUtils
import com.comicsopentrends.CharacterDetailActivity
import com.comicsopentrends.R
import com.comicsopentrends.fragments.mvp.clans.adapter.ClanAdapter
import com.comicsopentrends.fragments.mvp.clans.presenter.CharactersFragmentPresenter
import com.comicsopentrends.fragments.mvp.clans.presenter.impl.CharactersFragmentPresenterImpl
import com.comicsopentrends.fragments.mvp.clans.view.CharactersFragment
import com.comicsopentrends.fragments.mvp.clans.view.ClansListener
import com.comicsopentrends.model.ItemsItem
import com.comicsopentrends.util.Constants
import com.comicsopentrends.util.EndlessScrollListener
import com.comicsopentrends.util.Utils
import kotlinx.android.synthetic.main.fragment_error_red.*
import kotlinx.android.synthetic.main.layout_fragment_characters.*


/**
 * Created by Juan Martin Bernal on 20/10/2017.
 */
class CharactersFragmentImpl : Fragment(), CharactersFragment, SwipeRefreshLayout.OnRefreshListener, ClansListener {

    private var charactersFragmentPresenter: CharactersFragmentPresenter? = null
    private var adapter: ClanAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.layout_fragment_characters, container, false)
        charactersFragmentPresenter = CharactersFragmentPresenterImpl(this)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val linearLayoutManager = LinearLayoutManager(context)
        recycler_view!!.layoutManager = linearLayoutManager
        recycler_view!!.setHasFixedSize(true)
        swipeRefreshLayout!!.setOnRefreshListener(this)

        val scrollListener = object : EndlessScrollListener(linearLayoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list
                charactersFragmentPresenter!!.onLoadMore()
            }
        }

        recycler_view!!.addOnScrollListener(scrollListener)
        adapter = ClanAdapter(this)
        recycler_view!!.adapter = adapter

        charactersFragmentPresenter!!.loadList()
    }

    override fun show() {
        imgVerseLoading!!.visibility = View.VISIBLE
        imgVerseLoading!!.startAnimation(AnimationUtils.loadAnimation(context, R.anim.animation_loading))
    }

    override fun hide() {
        swipeRefreshLayout!!.isRefreshing = false
        imgVerseLoading!!.visibility = View.GONE
        imgVerseLoading!!.clearAnimation()
        hideScreenError()
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater!!.inflate(R.menu.main, menu)

        val searchItem = menu!!.findItem(R.id.action_search)
        val searchView = MenuItemCompat.getActionView(searchItem) as SearchView
        // See above
        MenuItemCompat.setOnActionExpandListener(searchItem, SearchViewExpandListener(context))
        MenuItemCompat.setActionView(searchItem, searchView)

        searchView.setOnCloseListener {
            charactersFragmentPresenter!!.resetVariables()
            charactersFragmentPresenter!!.loadList()
            false
        }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(query: String): Boolean {
                if (query.length >= Constants.NUM_MIN_EXECUTE_SEARCH) {
                    charactersFragmentPresenter!!.searchCharacter(query)
                }
                return true
            }
        })
    }

    override fun setDataClans(dataClans: List<ItemsItem>) {
        if (adapter != null) {
            adapter!!.setData(dataClans)
        }
    }

    /**
     * Método encargado de actualizar el subtitulo de la toolbar para indicar la cantidad de objectos en el listado.
     *
     * @param text
     */
    override fun updateToolbar(text: String) {
        if (activity != null) {
            (activity as AppCompatActivity).supportActionBar!!.subtitle = "" + text
        }
    }

    override fun showScreenError(message: String?) {
        containerError!!.visibility = View.VISIBLE
        txtError!!.text = message
    }

    override fun hideScreenError() {
        containerError!!.visibility = View.GONE
    }

    override fun setRefreshing(refresh: Boolean) {
        swipeRefreshLayout!!.isRefreshing = refresh
    }

    override fun onItemClick(item: ItemsItem) {
        //ir al detalle
        val intent = Intent(context, CharacterDetailActivity::class.java)
        intent.putExtra(CharactersFragment.CLAN_TAG, item.tag)
        startActivity(intent)
    }

    /**
     * Método encargado de previsualizar la imagen del personaje
     *
     * @param url
     * @param name
     */
    override fun seeImageCharacter(url: String?, name: String?) {
        // custom dialog
        Utils.showDialogPreviewImage(activity, url, name)
    }

    override fun onRefresh() {
        charactersFragmentPresenter!!.onRefresh()
    }

    // See above
    private inner class SearchViewExpandListener(private val context: Context?) : MenuItemCompat.OnActionExpandListener {

        override fun onMenuItemActionExpand(item: MenuItem): Boolean {
            (context as AppCompatActivity).supportActionBar!!.setDisplayShowHomeEnabled(true)
            return false
        }

        override fun onMenuItemActionCollapse(item: MenuItem): Boolean {
            (context as AppCompatActivity).supportActionBar!!.setDisplayShowHomeEnabled(false)
            return false
        }
    }


}