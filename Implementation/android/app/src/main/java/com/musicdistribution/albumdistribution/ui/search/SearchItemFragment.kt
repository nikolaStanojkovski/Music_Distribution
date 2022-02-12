package com.musicdistribution.albumdistribution.ui.search

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
import com.musicdistribution.albumdistribution.R
import com.musicdistribution.albumdistribution.data.firebase.storage.FirebaseStorage
import com.musicdistribution.albumdistribution.model.CategoryItemType
import com.musicdistribution.albumdistribution.model.Genre
import com.musicdistribution.albumdistribution.model.SearchItem
import com.musicdistribution.albumdistribution.util.listeners.SearchItemClickListener

class SearchItemFragment : Fragment(), SearchItemClickListener {

    private lateinit var fragmentView: View
    private lateinit var searchFragmentViewModel: SearchFragmentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search_item, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentView = view

        searchFragmentViewModel =
            ViewModelProvider(this)[SearchFragmentViewModel::class.java]
        val searchAdapter = fillRecyclerView()

        val selectedGenre = arguments?.get("selected_genre") as Genre?
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
        searchFragmentViewModel.fetchGenreData(selectedGenre)
        searchAdapter.emptyData()

        searchFragmentViewModel.getAlbumsGenreLiveData()
            .observe(viewLifecycleOwner,
                { albums ->
                    if (albums != null && albums.isNotEmpty()) {
                        for (album in albums) {
                            val gsReference =
                                FirebaseStorage.storage.getReferenceFromUrl("gs://album-distribution.appspot.com/album-images/${album.id}.jpg")
                            var link = ""
                            gsReference.downloadUrl.addOnCompleteListener { uri ->
                                if (uri.isSuccessful) {
                                    link = uri.result.toString()
                                }
                                val item = SearchItem(
                                    album.id,
                                    album.albumName,
                                    "Album",
                                    CategoryItemType.ALBUM,
                                    link
                                )
                                searchAdapter.updateDataItem(item)
                            }.addOnFailureListener { }
                        }
                    }
                })
    }

    private fun fillSearchData(searchAdapter: SearchItemAdapter) {
        val searchInput = fragmentView.findViewById<EditText>(R.id.inputSearch)
        searchInput.addTextChangedListener {
            val searchTerm = searchInput.text
            searchAdapter.emptyData()
            if (!searchTerm.isNullOrEmpty()) {
                searchAdapter.emptyData()
                searchFragmentViewModel.fetchSearchData(searchTerm.toString())
                searchFragmentViewModel.getArtistsLiveData()
                    .observe(viewLifecycleOwner,
                        { artists ->
                            if (artists != null && artists.isNotEmpty()) {
                                for (artist in artists) {
                                    val gsReference =
                                        FirebaseStorage.storage.getReferenceFromUrl("gs://album-distribution.appspot.com/profile-images/${artist.email}.jpg")
                                    var link = ""
                                    gsReference.downloadUrl.addOnCompleteListener { uri ->
                                        if (uri.isSuccessful) {
                                            link = uri.result.toString()
                                        }
                                        val item = SearchItem(
                                            artist.id!!,
                                            artist.artistPersonalInfo.fullName,
                                            "Artist",
                                            CategoryItemType.ARTIST,
                                            link
                                        )
                                        searchAdapter.updateDataItem(item)
                                    }.addOnFailureListener { }
                                }
                            }
                        })
                searchFragmentViewModel.getAlbumsLiveData()
                    .observe(viewLifecycleOwner,
                        { albums ->
                            if (albums != null && albums.isNotEmpty()) {
                                for (album in albums) {
                                    val gsReference =
                                        FirebaseStorage.storage.getReferenceFromUrl("gs://album-distribution.appspot.com/album-images/${album.id}.jpg")
                                    var link = ""
                                    gsReference.downloadUrl.addOnCompleteListener { uri ->
                                        if (uri.isSuccessful) {
                                            link = uri.result.toString()
                                        }
                                        val item = SearchItem(
                                            album.id,
                                            album.albumName,
                                            "Album",
                                            CategoryItemType.ALBUM,
                                            link
                                        )
                                        searchAdapter.updateDataItem(item)
                                    }.addOnFailureListener { }
                                }
                            }
                        })
                searchFragmentViewModel.getSongsLiveData()
                    .observe(viewLifecycleOwner,
                        { songs ->
                            if (songs != null && songs.isNotEmpty()) {
                                for (song in songs) {
                                    val gsReference =
                                        FirebaseStorage.storage.getReferenceFromUrl("gs://album-distribution.appspot.com/song-images/${song.id}.jpg")
                                    var link = ""
                                    gsReference.downloadUrl.addOnCompleteListener { uri ->
                                        if (uri.isSuccessful) {
                                            link = uri.result.toString()
                                        }
                                        val item = SearchItem(
                                            song.id,
                                            song.songName,
                                            "Song",
                                            CategoryItemType.SONG,
                                            link
                                        )
                                        searchAdapter.updateDataItem(item)
                                    }.addOnFailureListener { }
                                }
                            }
                        })
            }
        }
    }

    override fun onClick(searchItem: SearchItem) {
        when (searchItem.searchItemType) {
            CategoryItemType.ARTIST -> {
                val bundle = bundleOf("selected_artist_id" to searchItem.searchItemId)
                findNavController()
                    .navigate(R.id.action_searchItemFragment_to_artistFragment, bundle)
            }
            CategoryItemType.ALBUM -> {
                val bundle = bundleOf("selected_album_id" to searchItem.searchItemId)
                findNavController()
                    .navigate(R.id.action_searchItemFragment_to_albumFragment, bundle)
            }
            CategoryItemType.SONG -> {
                val bundle = bundleOf("selected_song_id" to searchItem.searchItemId)
                findNavController()
                    .navigate(R.id.action_searchItemFragment_to_songFragment, bundle)
            }
            CategoryItemType.PUBLISHED_SONG -> {
                val bundle =
                    bundleOf(
                        "selected_song_id" to searchItem.searchItemId,
                        "item_type" to searchItem.searchItemType
                    )
                findNavController()
                    .navigate(R.id.action_searchItemFragment_to_songFragment, bundle)
            }
            CategoryItemType.PUBLISHED_ALBUM -> {
                val bundle =
                    bundleOf(
                        "selected_album_id" to searchItem.searchItemId,
                        "item_type" to searchItem.searchItemType
                    )
                findNavController()
                    .navigate(R.id.action_searchItemFragment_to_albumFragment, bundle)
            }
        }
    }
}