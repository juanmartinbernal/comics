package com.comicsopentrends

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

import com.comicsopentrends.fragments.mvp.clans.view.CharactersFragment.Companion.CLAN_TAG
import com.comicsopentrends.fragments.mvp.clans.view.impl.DetailCharacterFragmentImpl
import com.comicsopentrends.util.Utils

/**
 * Created by Asus on 20/10/2017.
 */

class CharacterDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val bundle = intent.extras
        if (bundle != null) {
            val clanTag = bundle.getString(CLAN_TAG)
            val data = Bundle()
            data.putString(CLAN_TAG, clanTag)
            Utils.replaceFragment(getInstance(data), R.id.charactersFragment, supportFragmentManager)
        }

    }

    companion object {

        /**
         * Método encargado de crear una instancia para ejecutar el fragmento de detalle del CLAN
         * @param data
         * @return
         */
        fun getInstance(data: Bundle): DetailCharacterFragmentImpl {
            val fragment = DetailCharacterFragmentImpl()
            fragment.arguments = data
            return fragment
        }
    }
}
