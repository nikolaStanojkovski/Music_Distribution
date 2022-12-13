package com.musicdistribution.streamingservice.ui.home.item

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.musicdistribution.streamingservice.constants.ApiConstants
import com.musicdistribution.streamingservice.constants.ComponentConstants
import com.musicdistribution.streamingservice.constants.ExceptionConstants
import com.musicdistribution.streamingservice.constants.FileConstants
import com.musicdistribution.streamingservice.data.CategoryData
import com.musicdistribution.streamingservice.listeners.CategoryItemClickListener
import com.musicdistribution.streamingservice.model.search.CategoryItem
import com.musicdistribution.streamingservice.model.search.CategoryItemType
import com.musicdistribution.streamingservice.ui.HomeActivity
import com.musicdistribution.streamingservice.ui.home.HomeVerticalAdapter
import streamingservice.R

class ArtistFragment : Fragment(), CategoryItemClickListener {

    private lateinit var homeItemFragmentViewModel: HomeItemFragmentViewModel
    private lateinit var fragmentView: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_artist, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentView = view

        val selectedArtistId = arguments?.get(ComponentConstants.SELECTED_ARTIST_ID) as String?
        val categoryItemType = arguments?.get(ComponentConstants.ITEM_TYPE) as CategoryItemType?
        if (selectedArtistId == null || categoryItemType == null || categoryItemType != CategoryItemType.ARTIST) {
            startActivity(Intent(requireActivity(), HomeActivity::class.java))
            requireActivity().finish()
        } else {
            homeItemFragmentViewModel =
                ViewModelProvider(this)[HomeItemFragmentViewModel::class.java]
            fillData(selectedArtistId)
            fragmentView.findViewById<Button>(R.id.btnBackArtist).setOnClickListener {
                findNavController().navigate(R.id.action_artistFragment_to_homeFragment)
                homeItemFragmentViewModel.clear()
            }
        }
    }

    private fun fillData(selectedArtistId: String) {
        homeItemFragmentViewModel.fetchArtist(selectedArtistId)
        homeItemFragmentViewModel.fetchArtistAlbums(selectedArtistId)
        homeItemFragmentViewModel.fetchArtistSongs(selectedArtistId)

        homeItemFragmentViewModel.getArtistLiveData()
            .observe(viewLifecycleOwner,
                { item ->
                    if (item != null) {
                        fragmentView.findViewById<TextView>(R.id.txtArtistName).text =
                            if (item.userPersonalInfo.artName.isNotBlank())
                                item.userPersonalInfo.artName else item.email
                        fragmentView.findViewById<TextView>(R.id.txtArtistInfo).text =
                            item.userPersonalInfo.fullName
                        val imageControl =
                            fragmentView.findViewById<ImageView>(R.id.imageArtist)
                        val profilePictureReference =
                            "${ApiConstants.BASE_URL}${ApiConstants.API_STREAM_ARTISTS}/${item.id}${FileConstants.PNG_EXTENSION}"

                        if (imageControl != null) {
                            fillImage(profilePictureReference, imageControl)
                        }
                    }
                })
        fillAdapterData()
    }

    private fun fillAdapterData() {
        val verticalAdapter = HomeVerticalAdapter(CategoryData.artistData, this)
        val verticalRecyclerView =
            fragmentView.findViewById<RecyclerView>(R.id.artistItemRecyclerView)
        verticalRecyclerView.layoutManager =
            LinearLayoutManager(requireActivity(), RecyclerView.VERTICAL, false)
        verticalAdapter.clearData()
        verticalRecyclerView.adapter = verticalAdapter

        CategoryData.clearData()
        verticalAdapter.updateCategory(CategoryData.artistData[0])
        homeItemFragmentViewModel.getArtistSongsLiveData()
            .observe(viewLifecycleOwner,
                { songs ->
                    verticalAdapter.emptyData(CategoryData.artistData[0])
                    if (songs != null && songs.size > 0) {
                        for (song in songs) {
                            val songCoverReference =
                                "${ApiConstants.BASE_URL}${ApiConstants.API_STREAM_SONGS}/${song.id}${FileConstants.PNG_EXTENSION}"
                            verticalAdapter.updateData(
                                CategoryData.artistData[0],
                                CategoryItem(song.id, songCoverReference, CategoryItemType.SONG)
                            )
                        }
                    }
                })

        verticalAdapter.updateCategory(CategoryData.artistData[1])
        homeItemFragmentViewModel.getArtistAlbumsLiveData()
            .observe(viewLifecycleOwner,
                { albums ->
                    verticalAdapter.emptyData(CategoryData.artistData[1])
                    if (albums != null && albums.size > 0) {
                        for (album in albums) {
                            val albumCoverReference =
                                "${ApiConstants.BASE_URL}${ApiConstants.API_STREAM_ALBUMS}/${album.id}${FileConstants.PNG_EXTENSION}"
                            verticalAdapter.updateData(
                                CategoryData.artistData[1],
                                CategoryItem(album.id, albumCoverReference, CategoryItemType.ALBUM)
                            )
                        }
                    }
                })
    }

    private fun fillImage(profilePictureReference: String, imageControl: ImageView) {
        try {
            Glide.with(this)
                .load(profilePictureReference)
                .placeholder(R.drawable.default_picture)
                .into(imageControl)
        } catch (exception: Exception) {
            Toast.makeText(
                context,
                ExceptionConstants.ARTIST_PICTURE_FETCH_FAILED,
                Toast.LENGTH_LONG
            ).show()
        }
    }

//    private fun fillFollowButton(selectedArtistId: String) {
//        FirebaseRealtimeDB.favouriteArtistsReference.child("/follow-${FirebaseAuthDB.firebaseAuth.currentUser!!.uid}-${selectedArtistId}")
//            .get()
//            .addOnSuccessListener { user ->
//                val followButton = fragmentView.findViewById<Button>(R.id.btnFollow)
//                if (user.exists()) {
//                    buttonUnfollow(followButton, selectedArtistId)
//                } else {
//                    buttonFollow(followButton, selectedArtistId)
//                }
//            }
//    }

//    private fun buttonFollow(followButton: Button, selectedArtistId: String) {
//        followButton.text = "Follow"
//        followButton.setOnClickListener {
//            homeItemFragmentViewModel.updateFollowers(
//                FirebaseAuthDB.firebaseAuth.currentUser!!.uid,
//                selectedArtistId,
//                true,
//                currentArtist.email
//            )
//            buttonUnfollow(followButton, selectedArtistId)
//        }
//    }

//    private fun buttonUnfollow(followButton: Button, selectedArtistId: String) {
//        followButton.text = "Unfollow"
//        followButton.setOnClickListener {
//            homeItemFragmentViewModel.updateFollowers(
//                FirebaseAuthDB.firebaseAuth.currentUser!!.uid,
//                selectedArtistId,
//                false,
//                currentArtist.email
//            )
//            buttonFollow(followButton, selectedArtistId)
//        }
//    }

    override fun onClick(item: CategoryItem) {
        when (item.itemType) {
            CategoryItemType.ALBUM -> {
                val bundle = bundleOf(
                    ComponentConstants.SELECTED_ALBUM_ID to item.itemId,
                    ComponentConstants.ITEM_TYPE to CategoryItemType.ALBUM
                )
                findNavController()
                    .navigate(R.id.action_artistFragment_to_albumFragment, bundle)
            }
            CategoryItemType.SONG -> {
                val bundle = bundleOf(
                    ComponentConstants.SELECTED_SONG_ID to item.itemId,
                    ComponentConstants.ITEM_TYPE to CategoryItemType.SONG
                )
                findNavController()
                    .navigate(R.id.action_artistFragment_to_songFragment, bundle)
            }
            else -> findNavController()
                .navigate(R.id.action_artistFragment_to_homeFragment)
        }
    }

}