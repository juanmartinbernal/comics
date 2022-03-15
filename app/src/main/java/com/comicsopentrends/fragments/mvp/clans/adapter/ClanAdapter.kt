package com.comicsopentrends.fragments.mvp.clans.adapter

import androidx.recyclerview.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.comicsopentrends.R
import com.comicsopentrends.fragments.mvp.clans.view.ClansListener
import com.comicsopentrends.model.ItemsItem
import com.comicsopentrends.util.CircleTransform
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.list_item_character.view.*
import java.util.*

/**
 * Created by Juan Martin Bernal on 20/10/2017.
 * Clase que permite pintar los personajes en una lista
 */
class ClanAdapter(private val clansListener: ClansListener) : RecyclerView.Adapter<ClanAdapter.ViewHolder>() {

    private var characterList: List<ItemsItem>? = null

    init {
        this.characterList = ArrayList()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item_character, parent, false)

        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(characterList!![position], clansListener)
    }

    fun setData(characterList: List<ItemsItem>) {
        this.characterList = characterList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return characterList!!.size
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: ItemsItem, listener: ClansListener) {
            itemView.txtName!!.text = item.name
            val url = item.badgeUrls!!.small
            if (!TextUtils.isEmpty(url)) {
                itemView.progressBarCharacter!!.visibility = View.VISIBLE
                Picasso.get().load(url).transform(CircleTransform()).into(itemView.imgCharacter!!, object : Callback {
                    override fun onSuccess() {
                        itemView.progressBarCharacter!!.visibility = View.GONE
                    }

                    override fun onError(e: Exception) {
                        itemView.progressBarCharacter!!.visibility = View.GONE
                        itemView.imgCharacter!!.setImageResource(R.drawable.verse_logo)
                    }
                })
            }

            itemView.imgCharacter!!.setOnClickListener { view ->
                val url1 = item.badgeUrls!!.large
                listener.seeImageCharacter(url1, item.name)
            }

            itemView.setOnClickListener { v -> listener.onItemClick(item) }
        }
    }
}
