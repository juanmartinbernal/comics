package com.comicsopentrends.fragments.mvp.clans.view.impl


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.comicsopentrends.R
import com.comicsopentrends.fragments.mvp.clans.presenter.CharacteresFragmentDetailPresenter
import com.comicsopentrends.fragments.mvp.clans.presenter.impl.CharacteresFragmentDetailPresenterImpl
import com.comicsopentrends.fragments.mvp.clans.view.CharactersFragment.Companion.CLAN_TAG
import com.comicsopentrends.fragments.mvp.clans.view.DetailCharacterFragment
import com.comicsopentrends.model.ItemsItem
import com.comicsopentrends.util.CircleTransform
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.layout_detail_fragment.*


/**
 * Created by Juan Martin Bernal on 20/10/2017.
 */
/**
 * The pager adapter, which provides the pages to the view pager widget.
 */

class DetailCharacterFragmentImpl : Fragment(), DetailCharacterFragment {

    var clanTag: String? = ""
    private var characteresFragmentDetailPresenter: CharacteresFragmentDetailPresenter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        characteresFragmentDetailPresenter = CharacteresFragmentDetailPresenterImpl(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.layout_detail_fragment, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val bundle = this.arguments
        if (bundle != null) {
            clanTag = bundle.getString(CLAN_TAG)
        }
        characteresFragmentDetailPresenter!!.goToDetail(clanTag)
    }

    /**
     * Método encargado de pintar los datos del clan en la vista
     *
     * @param clan
     */
    fun loadData(clan: ItemsItem) {
        txtNameCharacter!!.text = clan.name
        txtDescription!!.text = clan.type
        txtTag!!.text = clan.tag
        txtMembers!!.text = getString(R.string.prompt_members, clan.members)
        txtPoints!!.text = getString(R.string.prompt_points, clan.clanPoints)
        txtWarWin!!.text = getString(R.string.prompt_war_wins, clan.warWins)
        txtWarLose!!.text = getString(R.string.prompt_war_lose, clan.warLosses)
        progressBar!!.visibility = View.VISIBLE
        Picasso.get().load(clan.badgeUrls!!.medium).transform(CircleTransform()).into(imgCharacterDetail!!, object : Callback {
            override fun onSuccess() {
                progressBar!!.visibility = View.GONE
            }

            override fun onError(e: Exception) {
                progressBar!!.visibility = View.GONE
                imgCharacterDetail!!.setImageResource(R.drawable.verse_logo)
            }
        })
    }
}
