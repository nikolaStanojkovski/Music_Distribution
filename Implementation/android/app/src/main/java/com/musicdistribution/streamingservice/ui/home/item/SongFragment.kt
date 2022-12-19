package com.musicdistribution.streamingservice.ui.home.item

import android.content.Intent
import android.media.MediaPlayer
import android.os.AsyncTask
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
import com.musicdistribution.streamingservice.constant.*
import com.musicdistribution.streamingservice.data.SessionService
import com.musicdistribution.streamingservice.model.enums.EntityType
import com.musicdistribution.streamingservice.model.search.CategoryItemType
import com.musicdistribution.streamingservice.service.SongFetchService
import com.musicdistribution.streamingservice.ui.home.HomeActivity
import com.musicdistribution.streamingservice.viewmodel.FavouriteViewModel
import com.musicdistribution.streamingservice.viewmodel.ItemTypeViewModel
import streamingservice.R

@Suppress(MessageConstants.DEPRECATION)
class SongFragment : Fragment() {

    private lateinit var fragmentView: View
    private lateinit var itemTypeViewModel: ItemTypeViewModel
    private lateinit var favouriteViewModel: FavouriteViewModel

    private var songPlaying: Boolean = false
    private var mediaPlayer: MediaPlayer = MediaPlayer()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_song, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentView = view

        val selectedSongId = arguments?.get(SearchConstants.SELECTED_SONG_ID) as String?
        val categoryItemType = arguments?.get(SearchConstants.ITEM_TYPE) as CategoryItemType?

        if (selectedSongId == null || categoryItemType == null || categoryItemType != CategoryItemType.SONG) {
            navigateOut()
        } else {
            itemTypeViewModel = ViewModelProvider(this)[ItemTypeViewModel::class.java]
            favouriteViewModel = ViewModelProvider(this)[FavouriteViewModel::class.java]

            fillData(selectedSongId)
            fragmentView.findViewById<Button>(R.id.btnBackSong).setOnClickListener {
                findNavController().navigate(R.id.action_songFragment_to_homeFragment)
                itemTypeViewModel.clear()
            }
            fillPlayData(selectedSongId)
        }
    }

    private fun fillData(selectedSongId: String) {
        itemTypeViewModel.clear()
        itemTypeViewModel.fetchSong(selectedSongId)
        itemTypeViewModel.getSongLiveData().observe(
            viewLifecycleOwner
        ) { item ->
            if (item != null) {
                fragmentView.findViewById<TextView>(R.id.txtSongGenre).text =
                    item.songGenre.toString()
                fragmentView.findViewById<TextView>(R.id.txtSongTitle).text = item.songName
                fragmentView.findViewById<TextView>(R.id.txtArtistSong).text =
                    item.creator.userPersonalInfo.artName.ifBlank { item.creator.email }

                fragmentView.findViewById<TextView>(R.id.txtSongLength).text =
                    item.songLength.formattedString

                val imageControl = fragmentView.findViewById<ImageView>(R.id.imageSong)
                val songCoverReference =
                    "${ApiConstants.BASE_URL}${ApiConstants.API_STREAM_SONGS}/${item.id}${FileConstants.PNG_EXTENSION}"

                if (imageControl != null) {
                    fillImage(songCoverReference, imageControl)
                }
            }
        }
        fillFavouriteData(selectedSongId)
    }

    private fun fillPlayData(selectedSongId: String) {
        val playSongControl = fragmentView.findViewById<ImageView>(R.id.btnPlaySong)
        val filePath =
            "${ApiConstants.BASE_URL}${ApiConstants.API_STREAM}" +
                    "/${selectedSongId}${FileConstants.MP3_EXTENSION}"
        var mp3Play: AsyncTask<String, String, String>? = null

        playSongControl.setOnClickListener {
            songPlaying = if (songPlaying) {
                mp3Play!!.cancel(true)
                mediaPlayer.pause()
                mediaPlayer = MediaPlayer()
                playSongControl.setImageResource(R.drawable.ic_play)
                false
            } else {
                mp3Play = SongFetchService(
                    requireActivity().application, selectedSongId, mediaPlayer
                )
                mp3Play!!.execute(filePath)
                playSongControl.setImageResource(R.drawable.ic_stop)
                true
            }
        }
    }

    private fun fillImage(songCoverReference: String, imageControl: ImageView) {
        try {
            Glide.with(this).load(songCoverReference)
                .placeholder(R.drawable.default_picture)
                .into(imageControl)
        } catch (exception: Exception) {
            Toast.makeText(
                context, ExceptionConstants.SONG_PICTURE_FETCH_FAILED, Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun fillFavouriteData(selectedSongId: String) {
        if (favouriteViewModel.getSongsLiveData().value.isNullOrEmpty()) {
            favouriteViewModel.fetchFavouritesData(EntityType.SONGS)
        }

        val userId = SessionService.read(EntityConstants.USER_ID)
        val likeButton: ImageView? = fragmentView.findViewById(R.id.btnLikeSong)
        if (!userId.isNullOrEmpty() && likeButton != null) {
            favouriteViewModel.getSongsLiveData().observe(
                viewLifecycleOwner
            ) { songs ->
                if (!songs.isNullOrEmpty() && songs.filter { s -> s.id == selectedSongId }.size == 1) {
                    buttonUnlike(likeButton, userId, selectedSongId)
                } else {
                    buttonLike(likeButton, userId, selectedSongId)
                }
            }
        }
    }

    private fun buttonLike(likeButton: ImageView, userId: String, selectedSongId: String) {
        likeButton.setImageResource(R.drawable.ic_favourite_unfilled)
        likeButton.setOnClickListener {
            favouriteViewModel.addToFavourites(userId, selectedSongId, EntityType.SONGS)
            buttonUnlike(likeButton, userId, selectedSongId)
        }
    }

    private fun buttonUnlike(likeButton: ImageView, userId: String, selectedSongId: String) {
        likeButton.setImageResource(R.drawable.ic_favourite_filled)
        likeButton.setOnClickListener {
            favouriteViewModel.removeFromFavourites(userId, selectedSongId, EntityType.SONGS)
            buttonLike(likeButton, userId, selectedSongId)
        }
    }

    private fun navigateOut() {
        val intent = Intent(requireActivity(), HomeActivity::class.java)
        startActivity(intent)
        requireActivity().finish()
    }
}