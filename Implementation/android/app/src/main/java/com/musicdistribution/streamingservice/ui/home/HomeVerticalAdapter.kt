package com.musicdistribution.streamingservice.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.musicdistribution.streamingservice.model.search.Category
import com.musicdistribution.streamingservice.model.search.CategoryItem
import com.musicdistribution.streamingservice.listeners.CategoryItemClickListener
import streamingservice.R

class HomeVerticalAdapter(
    categoryList: MutableList<Category>,
    fragment: CategoryItemClickListener
) :
    RecyclerView.Adapter<HomeVerticalAdapter.ViewHolder>() {

    private var activityContext: Context? = null
    private var categoryData: MutableList<Category> = categoryList
    private var parentFragment = fragment

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val categoryTitleControl: TextView = view.findViewById(R.id.txtCategoryTitle)
        val categoryListControl: RecyclerView = view.findViewById(R.id.horizontalHomeRecyclerView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.category_adapter, parent, false)
        activityContext = parent.context
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentCategory: Category = categoryData[position]

        holder.categoryTitleControl.text = currentCategory.title
        setCartItemView(holder.categoryListControl, currentCategory.categoryItems)
    }

    override fun getItemCount(): Int {
        return categoryData.size
    }

    private fun setCartItemView(
        recyclerView: RecyclerView,
        categoryItemList: MutableList<CategoryItem>
    ) {
        val horizontalAdapter = HomeHorizontalAdapter(categoryItemList, parentFragment)
        recyclerView.layoutManager =
            LinearLayoutManager(activityContext, RecyclerView.HORIZONTAL, false)
        recyclerView.adapter = horizontalAdapter
    }

    fun clearData() {
        this.categoryData = mutableListOf()
        this.notifyDataSetChanged()
    }

    fun emptyData(category: Category) {
        if (this.categoryData.any { item -> item.id == category.id }) {
            this.categoryData.filter { item -> item.id == category.id }[0].categoryItems.clear()
            this.notifyDataSetChanged()
        }
    }

    fun updateCategory(category: Category) {
        if (this.categoryData.none { item -> item.id == category.id }) {
            this.categoryData.add(category)
            this.notifyDataSetChanged()
        }
    }

    fun updateData(category: Category, categoryItem: CategoryItem) {
        this.categoryData.filter { item -> item.id == category.id }[0].categoryItems.add(
            categoryItem
        )
        this.notifyDataSetChanged()
    }
}