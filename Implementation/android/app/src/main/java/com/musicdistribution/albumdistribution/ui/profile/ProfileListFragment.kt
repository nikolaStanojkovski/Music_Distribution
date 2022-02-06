package com.musicdistribution.albumdistribution.ui.profile

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
import com.musicdistribution.albumdistribution.R
import com.musicdistribution.albumdistribution.data.firebase.storage.FirebaseStorage
import com.musicdistribution.albumdistribution.model.CategoryItemType
import com.musicdistribution.albumdistribution.model.SearchItem
import com.musicdistribution.albumdistribution.ui.home.HomeActivity
import com.musicdistribution.albumdistribution.ui.search.SearchItemAdapter
import com.musicdistribution.albumdistribution.util.listeners.SearchItemClickListener

class ProfileListFragment : Fragment(), SearchItemClickListener {

    private lateinit var fragmentView: View
    private lateinit var profileFragmentViewModel: ProfileFragmentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentView = view

        val listing_type = arguments?.get("listing_type") as CategoryItemType?
        if (listing_type == null) {
            startActivity(Intent(requireActivity(), HomeActivity::class.java))
            requireActivity().finish()
        }

        profileFragmentViewModel =
            ViewModelProvider(requireActivity())[ProfileFragmentViewModel::class.java]

        val adapter = fillRecyclerView()
        adapter.emptyData()
        fetchData(listing_type!!, adapter)
        fragmentView.findViewById<Button>(R.id.btnBackProfileItem).setOnClickListener {
            findNavController()
                .navigate(R.id.action_profileListFragment_to_homeFragment)
        }
    }

    private fun fillRecyclerView(): SearchItemAdapter {
        val searchItemAdapter = SearchItemAdapter(mutableListOf(), this)
        val searchItemRecyclerView =
            fragmentView.findViewById<RecyclerView>(R.id.profileListRecyclerView)
        searchItemRecyclerView.layoutManager =
            LinearLayoutManager(requireActivity(), RecyclerView.VERTICAL, false)
        searchItemRecyclerView.adapter = searchItemAdapter
        return searchItemAdapter
    }

    private fun fetchData(listing_type: CategoryItemType, adapter: SearchItemAdapter) {
        when (listing_type) {
            CategoryItemType.ALBUM -> {
            }
            CategoryItemType.SONG -> {
                profileFragmentViewModel.fetchFavoriteSongs()
                fragmentView.findViewById<TextView>(R.id.txtProfileItemHeading).text =
                    "Your favourite songs"
            }
            CategoryItemType.ARTIST -> {
                profileFragmentViewModel.fetchFavouriteArtists()
                fragmentView.findViewById<TextView>(R.id.txtProfileItemHeading).text =
                    "Your favourite artists"
            }
        }

        profileFragmentViewModel.getSongsLiveData()
            .observe(viewLifecycleOwner,
                { song ->
                    if (song != null) {
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
                            adapter.updateDataItem(item)
                        }
                    }
                })
        profileFragmentViewModel.getArtistsLiveData()
            .observe(viewLifecycleOwner,
                { artist ->
                    if (artist != null) {
                        val gsReference =
                            FirebaseStorage.storage.getReferenceFromUrl("gs://album-distribution.appspot.com/profile-images/${artist.id}.jpg")
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
                            adapter.updateDataItem(item)
                        }
                    }
                })
    }

    override fun onClick(searchItem: SearchItem) {
        when (searchItem.searchItemType) {
            CategoryItemType.ARTIST -> {
                val bundle = bundleOf("selected_artist_id" to searchItem.searchItemId)
                findNavController()
                    .navigate(R.id.action_profileListFragment_to_artistFragment, bundle)
            }
            CategoryItemType.ALBUM -> {
                val bundle = bundleOf("selected_album_id" to searchItem.searchItemId)
                findNavController()
                    .navigate(R.id.action_profileListFragment_to_albumFragment, bundle)
            }
            CategoryItemType.SONG -> {
                val bundle = bundleOf("selected_song_id" to searchItem.searchItemId)
                findNavController()
                    .navigate(R.id.action_profileListFragment_to_songFragment, bundle)
            }
        }
    }
}