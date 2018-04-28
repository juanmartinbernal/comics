package com.comicsopentrends.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.comicsopentrends.R;
import com.comicsopentrends.fragments.mvp.characteres.CharactersFragment;
import com.comicsopentrends.model.Character;
import com.comicsopentrends.model.Comic;
import com.comicsopentrends.model.ItemComic;
import com.comicsopentrends.model.ItemEvent;
import com.comicsopentrends.model.Thumbnail;
import com.comicsopentrends.util.CircleTransform;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Juan Martin Bernal on 20/10/2017.
 * Clase que permite pintar los personajes en una lista
 */
public class CharacterAdapter extends RecyclerView.Adapter<CharacterAdapter.ViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(Character item);
    }

    private final OnItemClickListener listener;
    private List<Character> characterList;
    private List<ItemComic> comicList;
    private List<ItemEvent> eventList;
    private CharactersFragment charactersFragment;

    public CharacterAdapter(List<Character> characterList, OnItemClickListener listener, CharactersFragment charactersFragment) {
        this.listener = listener;
        this.characterList = characterList;
        this.charactersFragment = charactersFragment;
    }

    public CharacterAdapter(List<ItemComic> comicList, List<ItemEvent> eventList) {
        this.comicList = comicList;
        this.eventList = eventList;
        listener = null;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_character, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (eventList != null) {
            holder.bind(eventList.get(position));
        } else if (comicList != null) {
            holder.bind(comicList.get(position));
        } else {
            holder.bind(characterList.get(position), listener);
        }
    }

    @Override
    public int getItemCount() {
        if (eventList != null) {
            return eventList.size();
        } else if (comicList != null) {
            return comicList.size();
        } else {
            return characterList.size();
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.txtName)
        TextView name;
        @BindView(R.id.txtComic)
        TextView txtComic;
        @BindView(R.id.imgCharacter)
        ImageView image;
        @BindView(R.id.progressBarCharacter)
        ProgressBar progressBar;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(final ItemEvent itemEvent) {
            progressBar.setVisibility(View.GONE);
            name.setText(itemEvent.name);
        }

        public void bind(final ItemComic itemComic) {
            progressBar.setVisibility(View.GONE);
            name.setText(itemComic.name);
        }

        public void bind(final Character item, final OnItemClickListener listener) {
            name.setText(item.name);

            Comic comic = item.comic;
            if (comic != null)
                txtComic.setText((comic.items != null && comic.items.size() > 0) ? comic.items.get(0).name : "No tiene comics");

            final Thumbnail thumbnail = item.thumbnail;
            String url = "";
            if (thumbnail != null)
                url = thumbnail.path + "." + thumbnail.extension;

            if (!TextUtils.isEmpty(url)) {
                progressBar.setVisibility(View.VISIBLE);
                Picasso.get().load(url).resize(150, 150).centerCrop().transform(new CircleTransform()).into(image, new Callback() {
                    @Override
                    public void onSuccess() {
                        progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Exception e) {
                        progressBar.setVisibility(View.GONE);
                    }
                });
            }

            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String url = thumbnail.path + "." + thumbnail.extension;
                    charactersFragment.seeImageCharacter(url, item.name);
                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }
    }

}