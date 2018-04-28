package com.comicsopentrends.fragments.mvp.characteres;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.comicsopentrends.R;
import com.comicsopentrends.fragments.mvp.characteres.presenter.CharacteresFragmentDetailPresenter;
import com.comicsopentrends.fragments.mvp.characteres.presenter.impl.CharacteresFragmentDetailPresenterImpl;
import com.comicsopentrends.model.Character;
import com.comicsopentrends.model.UrlCharacter;
import com.comicsopentrends.util.CircleTransform;
import com.comicsopentrends.util.Constants;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Juan Martin Bernal on 20/10/2017.
 */
public class DetailCharacterFragment extends Fragment {

    public int characterId = -1;
    private Character character;
    private CharacteresFragmentDetailPresenter characteresFragmentDetailPresenter;
    @BindView(R.id.imgCharacterDetail)
    ImageView imgCharacterDetail;
    @BindView(R.id.txtNameCharacter)
    TextView txtNameCharacter;
    @BindView(R.id.txtDescription)
    TextView txtDescription;
    @BindView(R.id.btnDetail)
    Button btnDetail;
    @BindView(R.id.pager)
    ViewPager mPager;
    /**
     * The number of pages (wizard steps) to show in this demo.
     */
    private static final int NUM_PAGES = 2;
    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */
    private PagerAdapter mPagerAdapter;

    public DetailCharacterFragment() {
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        characteresFragmentDetailPresenter = new CharacteresFragmentDetailPresenterImpl(this);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            characterId = bundle.getInt("characterId");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_detail_fragment, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        characteresFragmentDetailPresenter.goToDetail(characterId);
    }

    /**
     * Método encargado de pintar los datos del caracter en la vista
     * @param character
     */
    public void loadData(Character character) {
        this.character = character;
        mPagerAdapter = new ScreenSlidePagerAdapter(getChildFragmentManager(),character);
        mPager.setAdapter(mPagerAdapter);
        txtNameCharacter.setText("" + character.name);
        txtDescription.setText(TextUtils.isEmpty(character.description) ? "No tiene descripción :(" : "" + character.description);
        Picasso.get().load(character.thumbnail.path + "." + character.thumbnail.extension).transform(new CircleTransform()).into(imgCharacterDetail);
    }

    @OnClick(R.id.btnDetail)
    public void seeDetail(View view) {
        if (character != null) {
            openLink(getUrl(character.urls, Constants.LINK_TYPE_DETAIL), Constants.LINK_TYPE_DETAIL);
        }
    }

    @OnClick(R.id.btnWiki)
    public void seeWiki(View view) {
        if (character != null) {
            openLink(getUrl(character.urls, Constants.LINK_TYPE_WIKI), Constants.LINK_TYPE_WIKI);
        }
    }

    @OnClick(R.id.btnComics)
    public void seeComics(View view) {
        // TODO submit data to server...
        if (character != null) {
            openLink(getUrl(character.urls, Constants.LINK_TYPE_COMIC), Constants.LINK_TYPE_COMIC);
        }
    }

    /**
     * Método encargado de obtener la url(link) dependiendo del tipo.
     * @param urls
     * @param type
     * @return
     */
    private String getUrl(List<UrlCharacter> urls, String type) {
        String url = "";
        for (UrlCharacter urlCharacter : urls) {
            if (TextUtils.equals(urlCharacter.type, type)) {
                url = urlCharacter.url;
                break;
            }
        }
        return url;
    }

    /**
     * Método encargado de ejecutar el fragment para mostrar el contenido del enlace
     * @param url
     * @param title
     */
    private void openLink(String url, String title) {
        if (TextUtils.isEmpty(url)) {
            Toast.makeText(getContext(), "No hay enlace disponible para " + title, Toast.LENGTH_SHORT).show();
        } else {
            Bundle bundle = new Bundle();
            bundle.putString("url", url);
            bundle.putString("title", title);
            getInstance(bundle).show(getChildFragmentManager(), "web");
        }
    }

    /**
     * Método encragado de crear una instancia del  web dialog para cargar el link
     *
     * @param data
     * @return
     */
    public static final DetailWebviewDialogFragment getInstance(Bundle data) {
        DetailWebviewDialogFragment fragment = new DetailWebviewDialogFragment();
        fragment.setArguments(data);
        return fragment;
    }

    /**
     * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
     * sequence.
     */
    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        private Character mCharacter;
        public ScreenSlidePagerAdapter(FragmentManager fm,Character character) {
            super(fm);
            this.mCharacter = character;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return InformationFragment.newInstance(0, "Comics", new Gson().toJson(mCharacter));
                case 1:
                    return InformationFragment.newInstance(1, "Events", new Gson().toJson(mCharacter));
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }

        // Returns the page title for the top indicator
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Comics";
                case 1:
                    return "Events";
                default:
                    return null;
            }
        }
    }
}
