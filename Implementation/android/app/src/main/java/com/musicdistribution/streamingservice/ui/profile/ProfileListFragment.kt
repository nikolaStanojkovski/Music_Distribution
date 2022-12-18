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
import com.musicdistribution.streamingservice.constants.*
import com.musicdistribution.streamingservice.listeners.SearchItemClickListener
import com.musicdistribution.streamingservice.model.enums.EntityType
import com.musicdistribution.streamingservice.model.search.CategoryItemType
import com.musicdistribution.streamingservice.model.search.SearchItem
import com.musicdistribution.streamingservice.ui.home.HomeActivity
import com.musicdistribution.streamingservice.ui.search.SearchItemAdapter
import com.musicdistribution.streamingservice.viewmodel.FavouriteViewModel
import streamingservice.R

class ProfileListFragment : Fragment(), SearchItemClickListener {

    private lateinit var fragmentView: View
    private lateinit var favouriteViewModel: FavouriteViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentView = view

        val listingType = arguments?.get(SearchConstants.LISTING_TYPE) as CategoryItemType?
        if (listingType == null) {
            navigateOut()
        } else {
            favouriteViewModel =
                ViewModelProvider(this)[FavouriteViewModel::class.java]

            val adapter = fillRecyclerView()
            fetchData(listingType, adapter)
            fragmentView.findViewById<Button>(R.id.btnBackProfileItem).setOnClickListener {
                findNavController()
                    .navigate(R.id.action_profileListFragment_to_profileFragment)
            }
        }
    }

    private fun fillRecyclerView(): SearchItemAdapter {
        val searchItemAdapter = SearchItemAdapter(mutableListOf(), this)
        val searchItemRecyclerView =
            fragmentView.findViewById<RecyclerView>(R.id.profileListRecyclerView)
        searchItemRecyclerView.layoutManager =
            LinearLayoutManager(requireActivity(), RecyclerView.VERTICAL, false)
        searchItemAdapter.emptyData()
        searchItemRecyclerView.adapter = searchItemAdapter
        return searchItemAdapter
    }

    private fun fetchData(listingType: CategoryItemType, adapter: SearchItemAdapter) {
        val heading = fragmentView.findViewById<TextView>(R.id.txtProfileItemHeading)
        when (listingType) {
            CategoryItemType.ARTIST -> {
                heading.text =
                    MessageConstants.FAVOURITE_ARTISTS
                favouriteViewModel.fetchFavouritesData(EntityType.ARTISTS)
                fetchArtists(adapter)
            }
            CategoryItemType.ALBUM -> {
                heading.text =
                    MessageConstants.FAVOURITE_ALBUMS
                favouriteViewModel.fetchFavouritesData(EntityType.ALBUMS)
                fetchAlbums(adapter)
            }
            CategoryItemType.SONG -> {
                heading.text =
                    MessageConstants.FAVOURITE_SONGS
                favouriteViewModel.fetchFavouritesData(EntityType.SONGS)
                fetchSongs(adapter)
            }
        }
    }

    private fun fetchArtists(searchAdapter: SearchItemAdapter) {
        favouriteViewModel.getArtistsLiveData()
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
    }

    private fun fetchAlbums(searchAdapter: SearchItemAdapter) {
        favouriteViewModel.getAlbumsLiveData()
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
    }

    private fun fetchSongs(searchAdapter: SearchItemAdapter) {
        favouriteViewModel.getSongsLiveData()
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

    override fun onClick(searchItem: SearchItem) {
        when (searchItem.searchItemType) {
            CategoryItemType.ARTIST -> {
                val bundle = bundleOf(
                    SearchConstants.SELECTED_ARTIST_ID to searchItem.searchItemId,
                    SearchConstants.ITEM_TYPE to CategoryItemType.ARTIST
                )
                findNavController()
                    .navigate(R.id.action_profileListFragment_to_artistFragment, bundle)
            }
            CategoryItemType.ALBUM -> {
                val bundle = bundleOf(
                    SearchConstants.SELECTED_ALBUM_ID to searchItem.searchItemId,
                    SearchConstants.ITEM_TYPE to CategoryItemType.ALBUM
                )
                findNavController()
                    .navigate(R.id.action_profileListFragment_to_albumFragment, bundle)
            }
            CategoryItemType.SONG -> {
                val bundle = bundleOf(
                    SearchConstants.SELECTED_SONG_ID to searchItem.searchItemId,
                    SearchConstants.ITEM_TYPE to CategoryItemType.SONG
                )
                findNavController()
                    .navigate(R.id.action_profileListFragment_to_songFragment, bundle)
            }
        }
    }

    private fun navigateOut() {
        val intent = Intent(requireActivity(), HomeActivity::class.java)
        startActivity(intent)
        requireActivity().finish()
    }
}