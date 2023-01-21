package com.musicdistribution.streamingservice.ui.search

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.musicdistribution.streamingservice.listener.SearchItemClickListener
import com.musicdistribution.streamingservice.model.search.SearchItem
import streamingservice.R

class SearchItemAdapter(
    searchItemsList: MutableList<SearchItem>,
    searchItemClickListener: SearchItemClickListener
) :
    RecyclerView.Adapter<SearchItemAdapter.ViewHolder>() {

    private var activityContext: Context? = null
    private var searchItemData: MutableList<SearchItem> = searchItemsList
    private var parentFragment = searchItemClickListener

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        val searchItemTitleControl: TextView = view.findViewById(R.id.searchItemTitle)
        val searchItemTypeControl: TextView = view.findViewById(R.id.searchItemType)
        val searchItemImageControl: ImageView = view.findViewById(R.id.searchItemImage)

        init {
            view.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = layoutPosition
            if (position != RecyclerView.NO_POSITION) {
                val clickedItem = searchItemData[position]
                parentFragment.onClick(clickedItem)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.search_item_adapter, parent, false)
        activityContext = parent.context
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem: SearchItem = searchItemData[position]

        holder.searchItemTitleControl.text = currentItem.searchItemTitle
        holder.searchItemTypeControl.text = currentItem.searchItemInfo
        Glide.with(activityContext!!)
            .load(currentItem.searchItemImage)
            .placeholder(R.drawable.default_picture)
            .into(holder.searchItemImageControl)
    }

    override fun getItemCount(): Int {
        return searchItemData.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun emptyData() {
        this.searchItemData = mutableListOf()
        this.notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateDataItem(searchItem: SearchItem) {
        if (this.searchItemData.none { item -> item.searchItemId == searchItem.searchItemId }) {
            this.searchItemData.add(searchItem)
            this.notifyDataSetChanged()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(searchItemList: MutableList<SearchItem>) {
        this.searchItemData = searchItemList
        this.notifyDataSetChanged()
    }
}