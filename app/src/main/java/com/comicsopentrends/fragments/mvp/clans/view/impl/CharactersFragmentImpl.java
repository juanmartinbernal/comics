package com.comicsopentrends.fragments.mvp.clans.view.impl;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.comicsopentrends.CharacterDetailActivity;
import com.comicsopentrends.R;
import com.comicsopentrends.fragments.mvp.clans.adapter.ClanAdapter;
import com.comicsopentrends.fragments.mvp.clans.presenter.CharactersFragmentPresenter;
import com.comicsopentrends.fragments.mvp.clans.presenter.impl.CharactersFragmentPresenterImpl;
import com.comicsopentrends.fragments.mvp.clans.view.CharactersFragment;
import com.comicsopentrends.fragments.mvp.clans.view.ClansListener;
import com.comicsopentrends.model.ItemsItem;
import com.comicsopentrends.util.Constants;
import com.comicsopentrends.util.EndlessScrollListener;
import com.comicsopentrends.util.Utils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by Juan Martin Bernal on 20/10/2017.
 */
public class CharactersFragmentImpl extends Fragment implements CharactersFragment, SwipeRefreshLayout.OnRefreshListener, ClansListener {

    private CharactersFragmentPresenter charactersFragmentPresenter;
    private ClanAdapter adapter;

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.progressBarLoadUsers)
    ProgressBar progressBarLoadUsers;
    @BindView(R.id.imgVerseLoading)
    ImageView imgVerseLoading;
    @BindView(R.id.containerError)
    LinearLayout containerError;
    @BindView(R.id.txtError)
    TextView txtError;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_fragment_characters, container, false);
        ButterKnife.bind(this, view);
        charactersFragmentPresenter = new CharactersFragmentPresenterImpl(this);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        swipeRefreshLayout.setOnRefreshListener(this);

        EndlessScrollListener scrollListener = new EndlessScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list
                charactersFragmentPresenter.onLoadMore();
            }
        };

        recyclerView.addOnScrollListener(scrollListener);
        adapter = new ClanAdapter(this);
        recyclerView.setAdapter(adapter);

        charactersFragmentPresenter.loadList();
    }

    @Override
    public void show() {
        imgVerseLoading.setVisibility(View.VISIBLE);
        imgVerseLoading.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.animation_loading));
    }

    @Override
    public void hide() {
        swipeRefreshLayout.setRefreshing(false);
        imgVerseLoading.setVisibility(View.GONE);
        imgVerseLoading.clearAnimation();
        hideScreenError();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        // See above
        MenuItemCompat.setOnActionExpandListener(searchItem, new SearchViewExpandListener(getContext()));
        MenuItemCompat.setActionView(searchItem, searchView);

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                charactersFragmentPresenter.resetVariables();
                charactersFragmentPresenter.loadList();
                return false;
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                if (query.length() >= Constants.NUM_MIN_EXECUTE_SEARCH) {
                    charactersFragmentPresenter.searchCharacter(query);
                }
                return true;
            }
        });
    }

    @Override
    public void setDataClans(List<ItemsItem> dataClans) {
        if (adapter != null) {
            adapter.setData(dataClans);
        }
    }

    /**
     * Método encargado de actualizar el subtitulo de la toolbar para indicar la cantidad de objectos en el listado.
     *
     * @param text
     */
    @Override
    public void updateToolbar(String text) {
        if (getActivity() != null) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setSubtitle("" + text);
        }
    }

    @Override
    public void showScreenError(String message) {
        containerError.setVisibility(View.VISIBLE);
        txtError.setText(message);
    }

    @Override
    public void hideScreenError() {
        containerError.setVisibility(View.GONE);
    }

    @Override
    public void setRefreshing(boolean refresh) {
        swipeRefreshLayout.setRefreshing(refresh);
    }

    @Override
    public void onItemClick(ItemsItem item) {
        //ir al detalle
        Intent intent = new Intent(getContext(), CharacterDetailActivity.class);
        intent.putExtra(CLAN_TAG, item.getTag());
        startActivity(intent);
    }

    /**
     * Método encargado de previsualizar la imagen del personaje
     *
     * @param url
     * @param name
     */
    @Override
    public void seeImageCharacter(String url, String name) {
        // custom dialog
        Utils.showDialogPreviewImage(getActivity(), url, name);
    }

    @Override
    public void onRefresh() {
        charactersFragmentPresenter.onRefresh();
    }

    // See above
    private class SearchViewExpandListener implements MenuItemCompat.OnActionExpandListener {

        private Context context;

        public SearchViewExpandListener(Context context) {
            this.context = context;
        }

        @Override
        public boolean onMenuItemActionExpand(MenuItem item) {
            ((AppCompatActivity) context).getSupportActionBar().setDisplayShowHomeEnabled(true);
            return false;
        }

        @Override
        public boolean onMenuItemActionCollapse(MenuItem item) {
            ((AppCompatActivity) context).getSupportActionBar().setDisplayShowHomeEnabled(false);
            return false;
        }
    }


}