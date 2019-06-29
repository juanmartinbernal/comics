package com.comicsopentrends.fragments.mvp.clans.view.impl;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.comicsopentrends.R;
import com.comicsopentrends.fragments.mvp.clans.presenter.CharacteresFragmentDetailPresenter;
import com.comicsopentrends.fragments.mvp.clans.presenter.impl.CharacteresFragmentDetailPresenterImpl;
import com.comicsopentrends.fragments.mvp.clans.view.DetailCharacterFragment;
import com.comicsopentrends.model.ItemsItem;
import com.comicsopentrends.util.CircleTransform;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.comicsopentrends.fragments.mvp.clans.view.CharactersFragment.CLAN_TAG;

/**
 * Created by Juan Martin Bernal on 20/10/2017.
 */
public class DetailCharacterFragmentImpl extends Fragment implements DetailCharacterFragment {

    public String clanTag = "";
    private CharacteresFragmentDetailPresenter characteresFragmentDetailPresenter;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.imgCharacterDetail)
    ImageView imgCharacterDetail;
    @BindView(R.id.txtNameCharacter)
    TextView txtNameCharacter;
    @BindView(R.id.txtDescription)
    TextView txtDescription;


    @BindView(R.id.txtTag)
    TextView txtTag;
    @BindView(R.id.txtMembers)
    TextView txtMembers;
    @BindView(R.id.txtPoints)
    TextView txtPoints;
    @BindView(R.id.txtWarWin)
    TextView txtWarWin;
    @BindView(R.id.txtWarLose)
    TextView txtWarLose;


    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */

    public DetailCharacterFragmentImpl() {
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        characteresFragmentDetailPresenter = new CharacteresFragmentDetailPresenterImpl(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_detail_fragment, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            clanTag = bundle.getString(CLAN_TAG);
        }
        characteresFragmentDetailPresenter.goToDetail(clanTag);
    }

    /**
     * Método encargado de pintar los datos del clan en la vista
     *
     * @param clan
     */
    public void loadData(ItemsItem clan) {
        txtNameCharacter.setText(clan.getName());
        txtDescription.setText(clan.getType());
        txtTag.setText(clan.getTag());
        txtMembers.setText(getString(R.string.prompt_members, clan.getMembers()));
        txtPoints.setText(getString(R.string.prompt_points, clan.getClanPoints()));
        txtWarWin.setText(getString(R.string.prompt_war_wins, clan.getWarWins()));
        txtWarLose.setText(getString(R.string.prompt_war_lose, clan.getWarLosses()));
        progressBar.setVisibility(View.VISIBLE);
        Picasso.get().load(clan.getBadgeUrls().getMedium()).transform(new CircleTransform()).into(imgCharacterDetail, new Callback() {
            @Override
            public void onSuccess() {
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onError(Exception e) {
                progressBar.setVisibility(View.GONE);
                imgCharacterDetail.setImageResource(R.drawable.verse_logo);
            }
        });
    }
}
