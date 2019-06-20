package com.comicsopentrends.fragments.mvp.characteres.view.impl;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.comicsopentrends.R;
import com.comicsopentrends.fragments.mvp.characteres.presenter.CharacteresFragmentDetailPresenter;
import com.comicsopentrends.fragments.mvp.characteres.presenter.impl.CharacteresFragmentDetailPresenterImpl;
import com.comicsopentrends.fragments.mvp.characteres.view.DetailCharacterFragment;
import com.comicsopentrends.model.ItemsItem;
import com.comicsopentrends.util.CircleTransform;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Juan Martin Bernal on 20/10/2017.
 */
public class DetailCharacterFragmentImpl extends Fragment implements DetailCharacterFragment {

    public String characterId = "";
    private ItemsItem character;
    private CharacteresFragmentDetailPresenter characteresFragmentDetailPresenter;
    @BindView(R.id.imgCharacterDetail)
    ImageView imgCharacterDetail;
    @BindView(R.id.txtNameCharacter)
    TextView txtNameCharacter;
    @BindView(R.id.txtDescription)
    TextView txtDescription;
    @BindView(R.id.btnDetail)
    Button btnDetail;

    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */

    public DetailCharacterFragmentImpl() {
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        characteresFragmentDetailPresenter = new CharacteresFragmentDetailPresenterImpl(this);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            characterId = bundle.getString("characterId");
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
    public void loadData(ItemsItem character) {
        this.character = character;
        txtNameCharacter.setText("" + character.getName());
        txtDescription.setText(character.getType());
        Picasso.get().load(character.getBadgeUrls().getLarge()).transform(new CircleTransform()).into(imgCharacterDetail);
    }

    @OnClick(R.id.btnDetail)
    public void seeDetail(View view) {
        if (character != null) {
           // openLink(getUrl(character.urls, Constants.LINK_TYPE_DETAIL), Constants.LINK_TYPE_DETAIL);
        }
    }

    @OnClick(R.id.btnWiki)
    public void seeWiki(View view) {
        if (character != null) {
            //openLink(getUrl(character.urls, Constants.LINK_TYPE_WIKI), Constants.LINK_TYPE_WIKI);
        }
    }

    @OnClick(R.id.btnComics)
    public void seeComics(View view) {
        // TODO submit data to server...
        if (character != null) {
            //openLink(getUrl(character.urls, Constants.LINK_TYPE_COMIC), Constants.LINK_TYPE_COMIC);
        }
    }

}
