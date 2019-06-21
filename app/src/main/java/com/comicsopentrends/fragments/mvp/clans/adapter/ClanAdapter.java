package com.comicsopentrends.fragments.mvp.clans.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.comicsopentrends.R;
import com.comicsopentrends.fragments.mvp.clans.view.ClansListener;
import com.comicsopentrends.model.ItemsItem;
import com.comicsopentrends.util.CircleTransform;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Juan Martin Bernal on 20/10/2017.
 * Clase que permite pintar los personajes en una lista
 */
public class ClanAdapter extends RecyclerView.Adapter<ClanAdapter.ViewHolder> {


    private List<ItemsItem> characterList;
    private ClansListener clansListener;

    public ClanAdapter(ClansListener clansListener) {
        this.characterList = new ArrayList<>();
        this.clansListener = clansListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_character, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(characterList.get(position), clansListener);
    }

    public void setData(List<ItemsItem> characterList){
        this.characterList = characterList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return characterList.size();
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

        public void bind(final ItemsItem item, final ClansListener listener) {
            name.setText(item.getName());
            String url = item.getBadgeUrls().getSmall();
            if (!TextUtils.isEmpty(url)) {
                progressBar.setVisibility(View.VISIBLE);
                Picasso.get().load(url).transform(new CircleTransform()).into(image, new Callback() {
                    @Override
                    public void onSuccess() {
                        progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Exception e) {
                        progressBar.setVisibility(View.GONE);
                        image.setImageResource(R.drawable.verse_logo);
                    }
                });
            }

            image.setOnClickListener(view -> {
                String url1 = item.getBadgeUrls().getLarge();
                listener.seeImageCharacter(url1, item.getName());
            });

            itemView.setOnClickListener(v -> listener.onItemClick(item));
        }
    }

}