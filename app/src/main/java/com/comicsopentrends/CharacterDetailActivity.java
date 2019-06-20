package com.comicsopentrends;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.comicsopentrends.fragments.mvp.characteres.view.impl.DetailCharacterFragmentImpl;
import com.comicsopentrends.util.Utils;

/**
 * Created by Asus on 20/10/2017.
 */

public class CharacterDetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String characterId = bundle.getString("characterId");
            Bundle data = new Bundle();
            data.putString("characterId", characterId);
            Utils.replaceFragment(getInstance(data), R.id.charactersFragment, getSupportFragmentManager());
        }

    }

    /**
     * Método encargado de crear una instancia para ejecutar el fragmento de detalle del personaje
     * @param data
     * @return
     */
    public static final DetailCharacterFragmentImpl getInstance(Bundle data) {
        DetailCharacterFragmentImpl fragment = new DetailCharacterFragmentImpl();
        fragment.setArguments(data);
        return fragment;
    }
}
