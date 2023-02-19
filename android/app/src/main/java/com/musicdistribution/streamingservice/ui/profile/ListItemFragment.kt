package com.musicdistribution.streamingservice.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.musicdistribution.streamingservice.constant.*
import com.musicdistribution.streamingservice.listener.SearchItemClickListener
import com.musicdistribution.streamingservice.model.enums.EntityType
import com.musicdistribution.streamingservice.model.search.SearchItem
import com.musicdistribution.streamingservice.model.search.enums.CategoryItemType
import com.musicdistribution.streamingservice.model.search.enums.CategoryListType
import com.musicdistribution.streamingservice.ui.home.HomeActivity
import com.musicdistribution.streamingservice.ui.search.SearchItemAdapter
import com.musicdistribution.streamingservice.util.ApiUtils
import com.musicdistribution.streamingservice.viewmodel.EntityViewModel
import com.musicdistribution.streamingservice.viewmodel.FavouriteItemViewModel
import streamingservice.R

class ListItemFragment : Fragment(), SearchItemClickListener {

    private lateinit var fragmentView: View
    private lateinit var favouriteItemViewModel: FavouriteItemViewModel
    private lateinit var entityViewModel: EntityViewModel

    private lateinit var catItemType: CategoryItemType
    private lateinit var catListType: CategoryListType
    private var currentPage = 0
    private var currentSize = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list_item, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentView = view

        val listingType = arguments?.get(SearchConstants.LISTING_TYPE) as CategoryItemType?
        val categoryListType =
            arguments?.get(SearchConstants.CATEGORY_LISTING_TYPE) as CategoryListType?
        if (listingType == null || categoryListType == null) {
            navigateOut()
        } else {
            favouriteItemViewModel =
                ViewModelProvider(this)[FavouriteItemViewModel::class.java]
            entityViewModel =
                ViewModelProvider(this)[EntityViewModel::class.java]

            val heading = fragmentView.findViewById<TextView>(R.id.txtProfileItemHeading)
            val adapter = fillRecyclerView(heading)
            catListType = categoryListType
            catItemType = listingType

            if (categoryListType == CategoryListType.FAVOURITE_ITEMS) {
                fetchFavouritesData(heading, adapter)
            } else {
                fetchData(heading, adapter)
            }
            fragmentView.findViewById<Button>(R.id.btnBackProfileItem).setOnClickListener {
                val navigationPath = if (categoryListType == CategoryListType.ALL_ITEMS)
                    R.id.action_listItemFragment_to_homeFragment
                else R.id.action_listItemFragment_to_profileFragment
                findNavController().navigate(navigationPath)
            }
        }
    }

    private fun fillRecyclerView(heading: TextView): SearchItemAdapter {
        val searchItemAdapter = SearchItemAdapter(mutableListOf(), this)
        val searchItemRecyclerView =
            fragmentView.findViewById<RecyclerView>(R.id.profileListRecyclerView)
        searchItemRecyclerView.layoutManager =
            LinearLayoutManager(requireActivity(), RecyclerView.VERTICAL, false)
        searchItemAdapter.emptyData()
        searchItemRecyclerView.adapter = searchItemAdapter
        searchItemRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                if (!recyclerView.canScrollVertically(1)
                    && newState == RecyclerView.SCROLL_STATE_IDLE
                    && currentSize % PaginationConstants.DEFAULT_PAGE_SIZE == 0
                ) {
                    currentPage++
                    if (catListType == CategoryListType.ALL_ITEMS) {
                        fetchData(heading, searchItemAdapter)
                    } else {
                        fetchFavouritesData(heading, searchItemAdapter)
                    }
                }
            }
        })
        return searchItemAdapter
    }

    private fun fetchData(
        heading: TextView,
        adapter: SearchItemAdapter
    ) {
        when (catItemType) {
            CategoryItemType.ARTIST -> {
                updateHeading(heading, MessageConstants.EXPLORE_ARTISTS)
                entityViewModel.fetchArtists(currentPage)
                fetchArtists(adapter)
            }
            CategoryItemType.ALBUM -> {
                updateHeading(heading, MessageConstants.EXPLORE_ALBUMS)
                entityViewModel.fetchAlbums(currentPage)
                fetchAlbums(adapter)
            }
            CategoryItemType.SONG -> {
                updateHeading(heading, MessageConstants.EXPLORE_SONGS)
                entityViewModel.fetchSongs(currentPage)
                fetchSongs(adapter)
            }
            else -> {}
        }
    }

    private fun fetchFavouritesData(
        heading: TextView,
        adapter: SearchItemAdapter
    ) {
        when (catItemType) {
            CategoryItemType.ARTIST -> {
                updateHeading(heading, MessageConstants.FAVOURITE_ARTISTS)
                favouriteItemViewModel.fetchFavouritesData(EntityType.ARTISTS)
                fetchArtists(adapter)
            }
            CategoryItemType.ALBUM -> {
                updateHeading(heading, MessageConstants.FAVOURITE_ALBUMS)
                favouriteItemViewModel.fetchFavouritesData(EntityType.ALBUMS)
                fetchAlbums(adapter)
            }
            CategoryItemType.SONG -> {
                updateHeading(heading, MessageConstants.FAVOURITE_SONGS)
                favouriteItemViewModel.fetchFavouritesData(EntityType.SONGS)
                fetchSongs(adapter)
            }
            else -> {}
        }
    }

    private fun updateHeading(heading: TextView, message: String) {
        if (heading.text == getString(R.string.profile_item_placeholder)) {
            heading.text = message
        }
    }

    private fun fetchArtists(searchAdapter: SearchItemAdapter) {
        val artistsLiveData =
            if (catListType == CategoryListType.ALL_ITEMS)
                entityViewModel.getArtistsLiveData()
            else favouriteItemViewModel.getArtistsLiveData()
        artistsLiveData
            .observe(
                viewLifecycleOwner
            ) { artists ->
                if (artists != null && artists.isNotEmpty()) {
                    artists.map { a ->
                        SearchItem(
                            a.id,
                            a.userPersonalInfo.artName.ifBlank { a.userContactInfo.email.fullAddress },
                            a.userContactInfo.email.fullAddress,
                            CategoryItemType.ARTIST,
                            ApiUtils.getArtistCoverUrl(a)
                        )
                    }.forEach { si -> searchAdapter.updateDataItem(si) }
                    currentSize += artists.size
                }
            }
    }

    private fun fetchAlbums(searchAdapter: SearchItemAdapter) {
        val albumsLiveData =
            if (catListType == CategoryListType.ALL_ITEMS)
                entityViewModel.getAlbumsLiveData()
            else favouriteItemViewModel.getAlbumsLiveData()
        albumsLiveData
            .observe(
                viewLifecycleOwner
            ) { albums ->
                if (albums != null && albums.isNotEmpty()) {
                    albums.map { a ->
                        SearchItem(
                            a.id,
                            a.albumName,
                            a.totalLength.formattedString,
                            CategoryItemType.ALBUM,
                            ApiUtils.getAlbumCoverUrl(a)
                        )
                    }.forEach { si -> searchAdapter.updateDataItem(si) }
                    currentSize += albums.size
                }
            }
    }

    private fun fetchSongs(searchAdapter: SearchItemAdapter) {
        val songsLiveData =
            if (catListType == CategoryListType.ALL_ITEMS)
                entityViewModel.getSongsLiveData()
            else favouriteItemViewModel.getSongsLiveData()
        songsLiveData
            .observe(
                viewLifecycleOwner
            ) { songs ->
                if (songs != null && songs.isNotEmpty()) {
                    songs.map { s ->
                        SearchItem(
                            s.id,
                            s.songName,
                            s.songLength.formattedString,
                            CategoryItemType.SONG,
                            ApiUtils.getSongCoverUrl(s)
                        )
                    }.forEach { si -> searchAdapter.updateDataItem(si) }
                    currentSize += songs.size
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
                    .navigate(R.id.action_listItemFragment_to_artistFragment, bundle)
            }
            CategoryItemType.ALBUM -> {
                val bundle = bundleOf(
                    SearchConstants.SELECTED_ALBUM_ID to searchItem.searchItemId,
                    SearchConstants.ITEM_TYPE to CategoryItemType.ALBUM
                )
                findNavController()
                    .navigate(R.id.action_listItemFragment_to_albumFragment, bundle)
            }
            CategoryItemType.SONG -> {
                val bundle = bundleOf(
                    SearchConstants.SELECTED_SONG_ID to searchItem.searchItemId,
                    SearchConstants.ITEM_TYPE to CategoryItemType.SONG
                )
                findNavController()
                    .navigate(R.id.action_listItemFragment_to_songFragment, bundle)
            }
            else -> {}
        }
    }

    private fun navigateOut() {
        val intent = Intent(requireActivity(), HomeActivity::class.java)
        startActivity(intent)
        requireActivity().finish()
    }
}