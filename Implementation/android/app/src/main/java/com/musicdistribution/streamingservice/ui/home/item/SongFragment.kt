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
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.musicdistribution.streamingservice.constants.ApiConstants
import com.musicdistribution.streamingservice.constants.SearchConstants
import com.musicdistribution.streamingservice.constants.ExceptionConstants
import com.musicdistribution.streamingservice.constants.FileConstants
import com.musicdistribution.streamingservice.model.search.CategoryItemType
import com.musicdistribution.streamingservice.ui.HomeActivity
import streamingservice.R

class SongFragment : Fragment() {

    private lateinit var fragmentView: View
    private lateinit var homeItemFragmentViewModel: HomeItemFragmentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_song, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentView = view

        val selectedSongId = arguments?.get(SearchConstants.SELECTED_SONG_ID) as String?
        val categoryItemType = arguments?.get(SearchConstants.ITEM_TYPE) as CategoryItemType?

        if (selectedSongId == null || categoryItemType == null || categoryItemType != CategoryItemType.SONG) {
            startActivity(Intent(requireActivity(), HomeActivity::class.java))
            requireActivity().finish()
        } else {
            homeItemFragmentViewModel =
                ViewModelProvider(this)[HomeItemFragmentViewModel::class.java]

            fillData(selectedSongId)
            fragmentView.findViewById<Button>(R.id.btnBackSong).setOnClickListener {
                findNavController().navigate(R.id.action_songFragment_to_homeFragment)
                homeItemFragmentViewModel.clear()
            }
        }
    }

    private fun fillData(selectedSongId: String) {
        homeItemFragmentViewModel.clear()
        homeItemFragmentViewModel.fetchSong(selectedSongId)
        homeItemFragmentViewModel.getSongLiveData()
            .observe(viewLifecycleOwner,
                { item ->
                    if (item != null) {
                        fragmentView.findViewById<TextView>(R.id.txtSongGenre).text =
                            item.songGenre.toString()
                        fragmentView.findViewById<TextView>(R.id.txtSongTitle).text =
                            item.songName
                        fragmentView.findViewById<TextView>(R.id.txtArtistSong).text =
                            if (item.creator.userPersonalInfo.artName.isNotBlank())
                                item.creator.userPersonalInfo.artName else item.creator.email

                        fragmentView.findViewById<TextView>(R.id.txtSongLength).text =
                            item.songLength.formattedString

                        val imageControl =
                            fragmentView.findViewById<ImageView>(R.id.imageSong)
                        val songCoverReference =
                            "${ApiConstants.BASE_URL}${ApiConstants.API_STREAM_SONGS}/${item.id}${FileConstants.PNG_EXTENSION}"

                       if(imageControl != null) {
                           fillImage(songCoverReference, imageControl)
                       }
                    }
                })
    }

    private fun fillImage(songCoverReference: String, imageControl: ImageView) {
        try {
            Glide.with(this)
                .load(songCoverReference)
                .placeholder(R.drawable.default_picture)
                .into(imageControl)
        } catch (exception: Exception) {
            Toast.makeText(
                context,
                ExceptionConstants.SONG_PICTURE_FETCH_FAILED,
                Toast.LENGTH_LONG
            ).show()
        }
    }
//
//    private fun buttonLike(likeButton: ImageView, selectedSongId: String) {
//        likeButton.setImageResource(R.drawable.ic_favourite_unfilled)
//        likeButton.setOnClickListener {
//            homeItemFragmentViewModel.favouriteSong(
//                FirebaseAuthDB.firebaseAuth.currentUser!!.uid,
//                selectedSongId,
//                true
//            )
//            buttonUnlike(likeButton, selectedSongId)
//        }
//    }
//
//    private fun buttonUnlike(likeButton: ImageView, selectedSongId: String) {
//        likeButton.setImageResource(R.drawable.ic_favourite_filled)
//        likeButton.setOnClickListener {
//            homeItemFragmentViewModel.favouriteSong(
//                FirebaseAuthDB.firebaseAuth.currentUser!!.uid,
//                selectedSongId,
//                false,
//            )
//            buttonLike(likeButton, selectedSongId)
//        }
//    }
}