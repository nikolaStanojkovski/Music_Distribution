package com.musicdistribution.streamingservice.ui.search

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.musicdistribution.streamingservice.model.search.GenreItem
import com.musicdistribution.streamingservice.listeners.GenreItemClickListener
import streamingservice.R

class SearchBlockAdapter(
    genreItemsList: MutableList<GenreItem>,
    genreItemClickListener: GenreItemClickListener
) :
    RecyclerView.Adapter<SearchBlockAdapter.ViewHolder>() {

    private var activityContext: Context? = null
    private var genreData: MutableList<GenreItem> = genreItemsList

    private var parentFragment = genreItemClickListener

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        val genreImageControl: ImageView = view.findViewById(R.id.favouriteGenreImage)

        init {
            view.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = layoutPosition
            if (position != RecyclerView.NO_POSITION) {
                val clickedItem = genreData[position]
                parentFragment.onClick(clickedItem.genreName)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.genre_adapter, parent, false)
        activityContext = parent.context
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentGenreItem: GenreItem = genreData[position]

        holder.genreImageControl.setImageResource(currentGenreItem.genreImage)
    }

    override fun getItemCount(): Int {
        return genreData.size
    }
}