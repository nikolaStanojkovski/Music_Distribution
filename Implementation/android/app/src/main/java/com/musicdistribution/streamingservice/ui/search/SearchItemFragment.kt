package com.musicdistribution.streamingservice.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.os.bundleOf
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.musicdistribution.streamingservice.constant.ApiConstants
import com.musicdistribution.streamingservice.constant.EntityConstants
import com.musicdistribution.streamingservice.constant.FileConstants
import com.musicdistribution.streamingservice.constant.SearchConstants
import com.musicdistribution.streamingservice.listener.SearchItemClickListener
import com.musicdistribution.streamingservice.model.enums.Genre
import com.musicdistribution.streamingservice.model.search.CategoryItemType
import com.musicdistribution.streamingservice.model.search.SearchItem
import com.musicdistribution.streamingservice.viewmodel.SearchViewModel
import streamingservice.R

class SearchItemFragment : Fragment(), SearchItemClickListener {

    private lateinit var fragmentView: View
    private lateinit var searchViewModel: SearchViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search_item, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentView = view

        searchViewModel =
            ViewModelProvider(this)[SearchViewModel::class.java]
        val searchAdapter = fillRecyclerView()

        val selectedGenre = arguments?.get(SearchConstants.SELECTED_GENRE) as Genre?
        if (selectedGenre != null) {
            fillGenreData(selectedGenre, searchAdapter)
        }

        fillSearchData(searchAdapter)
    }

    private fun fillRecyclerView(): SearchItemAdapter {
        val searchItemAdapter = SearchItemAdapter(mutableListOf(), this)
        val searchItemRecyclerView =
            fragmentView.findViewById<RecyclerView>(R.id.searchListRecyclerView)
        searchItemRecyclerView.layoutManager =
            LinearLayoutManager(requireActivity(), RecyclerView.VERTICAL, false)
        searchItemAdapter.emptyData()
        searchItemRecyclerView.adapter = searchItemAdapter
        return searchItemAdapter
    }

    private fun fillGenreData(selectedGenre: Genre, searchAdapter: SearchItemAdapter) {
        searchViewModel.fetchGenreData(selectedGenre)
        searchAdapter.emptyData()

        searchViewModel.getGenreAlbumResultsLiveData()
            .observe(viewLifecycleOwner,
                { albums ->
                    if (albums != null && albums.isNotEmpty()) {
                        albums.map { a ->
                            SearchItem(
                                a.id,
                                a.albumName,
                                EntityConstants.ALBUM,
                                CategoryItemType.ALBUM,
                                "${ApiConstants.BASE_URL}${ApiConstants.API_STREAM_ALBUMS}/${a.id}${FileConstants.PNG_EXTENSION}"
                            )
                        }.forEach { si -> searchAdapter.updateDataItem(si) }
                    }
                })
        searchViewModel.getGenreSongResultsLiveData()
            .observe(viewLifecycleOwner,
                { songs ->
                    if (songs != null && songs.isNotEmpty()) {
                        songs.map { s ->
                            SearchItem(
                                s.id,
                                s.songName,
                                EntityConstants.SONG,
                                CategoryItemType.SONG,
                                "${ApiConstants.BASE_URL}${ApiConstants.API_STREAM_SONGS}/${s.id}${FileConstants.PNG_EXTENSION}"
                            )
                        }.forEach { si -> searchAdapter.updateDataItem(si) }
                    }
                })
    }

    private fun fillSearchData(searchAdapter: SearchItemAdapter) {
        val searchInput = fragmentView.findViewById<EditText>(R.id.inputSearch)

        searchInput.addTextChangedListener {
            val searchTerm = searchInput.text

            if (!searchTerm.isNullOrEmpty()) {
                searchAdapter.emptyData()
                searchViewModel.fetchSearchData(searchTerm.toString())

                searchViewModel.getArtistsLiveData()
                    .observe(viewLifecycleOwner,
                        { artists ->
                            if (artists != null && artists.isNotEmpty()) {
                                artists.map { a ->
                                    SearchItem(
                                        a.id,
                                        if (a.userPersonalInfo.artName.isNotBlank())
                                            a.userPersonalInfo.artName
                                        else a.userContactInfo.email.fullAddress,
                                        EntityConstants.ARTIST,
                                        CategoryItemType.ARTIST,
                                        "${ApiConstants.BASE_URL}${ApiConstants.API_STREAM_ARTISTS}/${a.id}${FileConstants.PNG_EXTENSION}"
                                    )
                                }.forEach { si -> searchAdapter.updateDataItem(si) }
                            }
                        })
                searchViewModel.getAlbumsLiveData()
                    .observe(viewLifecycleOwner,
                        { albums ->
                            if (albums != null && albums.isNotEmpty()) {
                                albums.map { a ->
                                    SearchItem(
                                        a.id,
                                        a.albumName,
                                        EntityConstants.ALBUM,
                                        CategoryItemType.ALBUM,
                                        "${ApiConstants.BASE_URL}${ApiConstants.API_STREAM_ALBUMS}/${a.id}${FileConstants.PNG_EXTENSION}"
                                    )
                                }.forEach { si -> searchAdapter.updateDataItem(si) }
                            }
                        })
                searchViewModel.getSongsLiveData()
                    .observe(viewLifecycleOwner,
                        { songs ->
                            if (songs != null && songs.isNotEmpty()) {
                                songs.map { s ->
                                    SearchItem(
                                        s.id,
                                        s.songName,
                                        EntityConstants.SONG,
                                        CategoryItemType.SONG,
                                        "${ApiConstants.BASE_URL}${ApiConstants.API_STREAM_SONGS}/${s.id}${FileConstants.PNG_EXTENSION}"
                                    )
                                }.forEach { si -> searchAdapter.updateDataItem(si) }
                            }
                        })
            }
        }
    }

    override fun onClick(searchItem: SearchItem) {
        when (searchItem.searchItemType) {
            CategoryItemType.ARTIST -> {
                val bundle = bundleOf(
                    SearchConstants.SELECTED_ARTIST_ID to searchItem.searchItemId,
                    SearchConstants.ITEM_TYPE to CategoryItemType.ARTIST

                )
                findNavController()
                    .navigate(R.id.action_searchItemFragment_to_artistFragment, bundle)
            }
            CategoryItemType.ALBUM -> {
                val bundle = bundleOf(
                    SearchConstants.SELECTED_ALBUM_ID to searchItem.searchItemId,
                    SearchConstants.ITEM_TYPE to CategoryItemType.ALBUM
                )
                findNavController()
                    .navigate(R.id.action_searchItemFragment_to_albumFragment, bundle)
            }
            CategoryItemType.SONG -> {
                val bundle = bundleOf(
                    SearchConstants.SELECTED_SONG_ID to searchItem.searchItemId,
                    SearchConstants.ITEM_TYPE to CategoryItemType.SONG
                )
                findNavController()
                    .navigate(R.id.action_searchItemFragment_to_songFragment, bundle)
            }
        }
    }
}