package com.musicdistribution.albumdistribution.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.musicdistribution.albumdistribution.R
import com.musicdistribution.albumdistribution.data.GenreData

class SearchFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fillRecyclerView(view)

        view.findViewById<Button>(R.id.btnEnableSearch).setOnClickListener {
            findNavController().navigate(R.id.action_searchFragment_to_searchItemFragment)
        }
    }

    private fun fillRecyclerView(view: View) {
        val genreAdapter = SearchBlockAdapter(GenreData.data)
        val genreRecyclerView =
            view.findViewById<RecyclerView>(R.id.mainGenreRecyclerView)
        genreRecyclerView.layoutManager = GridLayoutManager(requireActivity(), 2)
        genreRecyclerView.adapter = genreAdapter
    }
}