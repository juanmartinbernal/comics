package com.comicsopentrends

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

import com.comicsopentrends.fragments.mvp.clans.view.impl.CharactersFragmentImpl
import com.comicsopentrends.util.Utils


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        Utils.replaceFragment(CharactersFragmentImpl(), R.id.charactersFragment, supportFragmentManager)
    }
}
