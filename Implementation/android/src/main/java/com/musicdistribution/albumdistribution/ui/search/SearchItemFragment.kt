package com.musicdistribution.albumdistribution.ui.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.musicdistribution.albumdistribution.R
import com.musicdistribution.albumdistribution.data.GenreData
import com.musicdistribution.albumdistribution.model.SearchItem

class SearchItemFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search_item, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fillRecyclerView(view)
        fillSearchFunctionality(view)
    }

    private fun fillRecyclerView(view: View) {
        // TODO: Replace with real data
        val searchItemAdapter = SearchItemAdapter(mutableListOf())
        val searchItemRecyclerView =
            view.findViewById<RecyclerView>(R.id.searchListRecyclerView)
        searchItemRecyclerView.layoutManager = LinearLayoutManager(requireActivity())
        searchItemRecyclerView.adapter = searchItemAdapter
    }

    private fun fillSearchFunctionality(view: View) {
        val searchInput = view.findViewById<EditText>(R.id.inputSearch)
        // TODO: Implement
        searchInput.addTextChangedListener {
            val searchValue = searchInput.text
        }
    }
}