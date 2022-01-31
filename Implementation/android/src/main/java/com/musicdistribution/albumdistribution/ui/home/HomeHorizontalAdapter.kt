package com.musicdistribution.albumdistribution.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.musicdistribution.albumdistribution.R
import com.musicdistribution.albumdistribution.model.CategoryItem

class HomeHorizontalAdapter(categoryItemList: MutableList<CategoryItem>) :
    RecyclerView.Adapter<HomeHorizontalAdapter.ViewHolder>() {

    private var activityContext: Context? = null
    private var categoryData: MutableList<CategoryItem> = categoryItemList

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val categoryImageControl: ImageView = view.findViewById(R.id.categoryItemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.category_item_adapter, parent, false)
        activityContext = parent.context
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentCategoryItem: CategoryItem = categoryData[position]
        holder.categoryImageControl.setImageResource(currentCategoryItem.imageUrL)
    }

    override fun getItemCount(): Int {
        return categoryData.size
    }

    fun updateData(categorryItems: MutableList<CategoryItem>) {
        this.categoryData = categorryItems
        this.notifyDataSetChanged()
    }

}