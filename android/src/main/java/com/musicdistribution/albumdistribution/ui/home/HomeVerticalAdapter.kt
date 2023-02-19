package com.musicdistribution.streamingservice.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.musicdistribution.streamingservice.R
import com.musicdistribution.streamingservice.data.CategoryData
import com.musicdistribution.streamingservice.model.Category
import com.musicdistribution.streamingservice.model.CategoryItem

class HomeVerticalAdapter(categoryList: MutableList<Category>) :
    RecyclerView.Adapter<HomeVerticalAdapter.ViewHolder>() {

    private var activityContext: Context? = null
    private var categoryData: MutableList<Category> = categoryList

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
        val horizontalAdapter = HomeHorizontalAdapter(categoryItemList)
        recyclerView.layoutManager =
            LinearLayoutManager(activityContext, RecyclerView.HORIZONTAL, false)
        recyclerView.adapter = horizontalAdapter
    }

    fun updateData(categories: MutableList<Category>) {
        this.categoryData = categories
        this.notifyDataSetChanged()
    }

}