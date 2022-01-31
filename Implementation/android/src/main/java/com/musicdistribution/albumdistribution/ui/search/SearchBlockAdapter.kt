package com.musicdistribution.albumdistribution.ui.search

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.musicdistribution.albumdistribution.R
import com.musicdistribution.albumdistribution.model.GenreItem

class SearchBlockAdapter(genreItemsList: MutableList<GenreItem>) :
    RecyclerView.Adapter<SearchBlockAdapter.ViewHolder>() {

    private var activityContext: Context? = null
    private var genreData: MutableList<GenreItem> = genreItemsList

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val genreImageControl: ImageView = view.findViewById(R.id.favouriteGenreImage)
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

    fun updateData(genreItemsList: MutableList<GenreItem>) {
        this.genreData = genreItemsList
        this.notifyDataSetChanged()
    }

}