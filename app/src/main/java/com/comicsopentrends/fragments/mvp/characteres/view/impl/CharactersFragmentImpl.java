package com.comicsopentrends.fragments.mvp.characteres.view.impl;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
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
import android.view.Window;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.comicsopentrends.CharacterDetailActivity;
import com.comicsopentrends.R;
import com.comicsopentrends.adapter.CharacterAdapter;
import com.comicsopentrends.fragments.mvp.characteres.presenter.CharactersFragmentPresenter;
import com.comicsopentrends.fragments.mvp.characteres.presenter.impl.CharactersFragmentPresenterImpl;
import com.comicsopentrends.fragments.mvp.characteres.view.CharactersFragment;
import com.comicsopentrends.fragments.mvp.characteres.view.ClansListener;
import com.comicsopentrends.model.ItemsItem;
import com.comicsopentrends.util.Constants;
import com.comicsopentrends.util.EndlessScrollListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by Juan Martin Bernal on 20/10/2017.
 */
public class CharactersFragmentImpl extends Fragment implements CharactersFragment , ClansListener {

    private CharactersFragmentPresenter charactersFragmentPresenter;
    private CharacterAdapter adapter;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.progressBarLoadUsers)
    ProgressBar progressBarLoadUsers;

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
        setHasOptionsMenu(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);


        EndlessScrollListener scrollListener = new EndlessScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list
                charactersFragmentPresenter.onLoadMore();
            }
        };

        recyclerView.addOnScrollListener(scrollListener);
        adapter = new CharacterAdapter(this);
        recyclerView.setAdapter(adapter);

        charactersFragmentPresenter.loadList();
    }

    @Override
    public void show() {
        progressBarLoadUsers.setVisibility(View.VISIBLE);
    }

    @Override
    public void hide() {
        progressBarLoadUsers.setVisibility(View.GONE);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        // See above
        MenuItemCompat.setOnActionExpandListener(searchItem, new SearchViewExpandListener(getContext()));
        MenuItemCompat.setActionView(searchItem, searchView);

        searchView.setOnCloseListener(() -> {
            charactersFragmentPresenter.resetVariables();
            charactersFragmentPresenter.loadList();
            return false;
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
        if(adapter != null) {
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
    public void onItemClick(ItemsItem item) {
        //ir al detalle
        Intent intent = new Intent(getContext(), CharacterDetailActivity.class);
        intent.putExtra("characterId", item.getTag());
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
        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_photo_profile);

        // set the custom dialog components - text, image and button
        TextView text = dialog.findViewById(R.id.text);
        ProgressBar progressBar = dialog.findViewById(R.id.progressBar);
        text.setText(name);
        ImageView image = dialog.findViewById(R.id.image);
        progressBar.setVisibility(View.VISIBLE);
        Picasso.get().load(url).into(image, new Callback() {
            @Override
            public void onSuccess() {
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onError(Exception e) {
                progressBar.setVisibility(View.GONE);
            }
        });

        dialog.show();
    }

    // See above
    private class SearchViewExpandListener implements MenuItemCompat.OnActionExpandListener {

        private Context context;

        public SearchViewExpandListener(Context c) {
            context = c;
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