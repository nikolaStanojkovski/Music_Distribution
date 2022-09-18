package com.musicdistribution.albumdistribution.ui.home.item

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.musicdistribution.albumdistribution.R
import com.musicdistribution.albumdistribution.data.firebase.auth.FirebaseAuthDB
import com.musicdistribution.albumdistribution.data.firebase.realtime.FirebaseRealtimeDB
import com.musicdistribution.albumdistribution.data.firebase.storage.FirebaseStorage
import com.musicdistribution.albumdistribution.model.CategoryItemType
import com.musicdistribution.albumdistribution.ui.HomeActivity
import com.musicdistribution.albumdistribution.util.ValidationUtils

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

        val selectedSongId = arguments?.get("selected_song_id") as String?
        val categoryItemType = arguments?.get("item_type") as CategoryItemType?
        if (selectedSongId == null) {
            startActivity(Intent(requireActivity(), HomeActivity::class.java))
            requireActivity().finish()
        }

        homeItemFragmentViewModel =
            ViewModelProvider(this)[HomeItemFragmentViewModel::class.java]

        fillData(selectedSongId!!, categoryItemType)
        fragmentView.findViewById<Button>(R.id.btnBackSong).setOnClickListener {
            findNavController().navigate(R.id.action_songFragment_to_homeFragment)
            homeItemFragmentViewModel.clear()
        }
    }

    private fun fillData(selectedSongId: String, categoryItemType: CategoryItemType?) {
        if (categoryItemType != null && categoryItemType == CategoryItemType.PUBLISHED_SONG) {
            fragmentView.findViewById<ImageView>(R.id.btnLikeSong).visibility = View.GONE
            val unpublishButton = fragmentView.findViewById<Button>(R.id.btnUnPublishSong)
            unpublishButton.visibility = View.VISIBLE
            unpublishButton.setOnClickListener {
                fillConfirmDialog(selectedSongId)
            }
        } else {
            fragmentView.findViewById<Button>(R.id.btnUnPublishSong).visibility = View.GONE
            fragmentView.findViewById<ImageView>(R.id.btnLikeSong).visibility = View.VISIBLE
            fillLikeButton(fragmentView.findViewById(R.id.btnLikeSong), selectedSongId)
        }

        homeItemFragmentViewModel.clear()
        homeItemFragmentViewModel.fetchSongApi(selectedSongId)
        homeItemFragmentViewModel.getSongsLiveData()
            .observe(viewLifecycleOwner,
                { song ->
                    if (song != null) {
                        fragmentView.findViewById<TextView>(R.id.txtSongHeading).text =
                            if (song.isASingle) "Taken from" else "Taken from album"
                        fragmentView.findViewById<TextView>(R.id.txtSongProperty).text =
                            if (!song.isASingle) song.album!!.albumName else song.songName
                        fragmentView.findViewById<TextView>(R.id.txtSongTitle).text =
                            song.songName
                        fragmentView.findViewById<TextView>(R.id.txtArtistSong).text =
                            song.creator!!.artistPersonalInfo.fullName

                        fragmentView.findViewById<TextView>(R.id.txtSongLength).text =
                            "Length: ${ValidationUtils.generateTimeString(song.songLength!!.lengthInSeconds)}"

                        val imageControl =
                            fragmentView.findViewById<ImageView>(R.id.imageSong)
                        val gsReference =
                            if (song.isASingle) FirebaseStorage.storage.getReferenceFromUrl("gs://album-distribution.appspot.com/song-images/${song.id}.jpg")
                            else FirebaseStorage.storage.getReferenceFromUrl("gs://album-distribution.appspot.com/album-images/${song.album!!.id}.jpg")
                        gsReference.downloadUrl.addOnCompleteListener { uri ->
                            var link = ""
                            if (uri.isSuccessful) {
                                link = uri.result.toString()
                            }
                            Glide.with(this)
                                .load(link)
                                .placeholder(R.drawable.default_picture)
                                .into(imageControl!!)
                        }
                    }
                })
    }

    private fun fillConfirmDialog(selectedSongId: String) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Confirm Unpublish Song")
        builder.setMessage("Are you sure you want to unpublish this song?")
        builder.setPositiveButton("Confirm") { dialog, _ ->
            dialog.dismiss()
            homeItemFragmentViewModel.unPublishSong(selectedSongId)
            navigateOut()
        }
        builder.setNegativeButton(
            "Cancel"
        ) { dialog, _ -> dialog.dismiss() }
        builder.show()
    }

    private fun fillLikeButton(btnLikeSong: ImageView?, selectedSongId: String) {
        FirebaseRealtimeDB.favouriteSongsReference.child("/like-${FirebaseAuthDB.firebaseAuth.currentUser!!.uid}-${selectedSongId}")
            .get()
            .addOnSuccessListener { entry ->
                if (entry.exists()) {
                    buttonUnlike(btnLikeSong!!, selectedSongId)
                } else {
                    buttonLike(btnLikeSong!!, selectedSongId)
                }
            }
    }

    private fun buttonLike(likeButton: ImageView, selectedSongId: String) {
        likeButton.setImageResource(R.drawable.ic_favourite_unfilled)
        likeButton.setOnClickListener {
            homeItemFragmentViewModel.favouriteSong(
                FirebaseAuthDB.firebaseAuth.currentUser!!.uid,
                selectedSongId,
                true
            )
            buttonUnlike(likeButton, selectedSongId)
        }
    }

    private fun buttonUnlike(likeButton: ImageView, selectedSongId: String) {
        likeButton.setImageResource(R.drawable.ic_favourite_filled)
        likeButton.setOnClickListener {
            homeItemFragmentViewModel.favouriteSong(
                FirebaseAuthDB.firebaseAuth.currentUser!!.uid,
                selectedSongId,
                false,
            )
            buttonLike(likeButton, selectedSongId)
        }
    }

    private fun navigateOut() {
        val intent = Intent(requireActivity(), HomeActivity::class.java)
        startActivity(intent)
        requireActivity().finish()
    }
}