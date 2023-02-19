package com.musicdistribution.streamingservice.ui.search

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.musicdistribution.streamingservice.R
import com.musicdistribution.streamingservice.model.SearchItem

class SearchItemAdapter(searchItemsList: MutableList<SearchItem>) :
    RecyclerView.Adapter<SearchItemAdapter.ViewHolder>() {

    private var activityContext: Context? = null
    private var searchItemData: MutableList<SearchItem> = searchItemsList

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val searchItemTitleControl: TextView = view.findViewById(R.id.searchItemTitle)
        val searchItemTypeControl: TextView = view.findViewById(R.id.searchItemType)
        val searchItemImageControl: ImageView = view.findViewById(R.id.searchItemImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.search_item_adapter, parent, false)
        activityContext = parent.context
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentGenreItem: SearchItem = searchItemData[position]

        holder.searchItemTitleControl.text = currentGenreItem.searchItemTitle
        holder.searchItemTypeControl.text = currentGenreItem.searchItemType
        holder.searchItemImageControl.setImageResource(currentGenreItem.searchItemImage)
    }

    override fun getItemCount(): Int {
        return searchItemData.size
    }

    fun updateData(searchItemList: MutableList<SearchItem>) {
        this.searchItemData = searchItemList
        this.notifyDataSetChanged()
    }

}