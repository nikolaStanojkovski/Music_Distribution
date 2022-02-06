package com.musicdistribution.albumdistribution.ui.home.item

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.musicdistribution.albumdistribution.R
import com.musicdistribution.albumdistribution.data.CategoryData
import com.musicdistribution.albumdistribution.data.firebase.auth.FirebaseAuthDB
import com.musicdistribution.albumdistribution.data.firebase.realtime.FirebaseRealtimeDB
import com.musicdistribution.albumdistribution.data.firebase.storage.FirebaseStorage
import com.musicdistribution.albumdistribution.model.CategoryItem
import com.musicdistribution.albumdistribution.model.CategoryItemType
import com.musicdistribution.albumdistribution.model.retrofit.ArtistRetrofit
import com.musicdistribution.albumdistribution.ui.home.HomeActivity
import com.musicdistribution.albumdistribution.ui.home.HomeVerticalAdapter
import com.musicdistribution.albumdistribution.util.listeners.CategoryItemClickListener
import java.io.IOException

class ArtistFragment : Fragment(), CategoryItemClickListener {

    private lateinit var homeItemFragmentViewModel: HomeItemFragmentViewModel
    private lateinit var fragmentView: View
    private lateinit var currentArtist: ArtistRetrofit

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_artist, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentView = view

        val selectedArtistId = arguments?.get("selected_artist_id") as String?
        if (selectedArtistId == null) {
            startActivity(Intent(requireActivity(), HomeActivity::class.java))
            requireActivity().finish()
        }

        homeItemFragmentViewModel =
            ViewModelProvider(requireActivity())[HomeItemFragmentViewModel::class.java]
        fillData(selectedArtistId!!)
        fragmentView.findViewById<Button>(R.id.btnBackArtist).setOnClickListener {
            findNavController().navigate(R.id.action_artistFragment_to_homeFragment)
            homeItemFragmentViewModel.clear()
        }
    }

    private fun fillData(selectedArtistId: String) {
        homeItemFragmentViewModel.fetchArtistApi(selectedArtistId)
        homeItemFragmentViewModel.fetchArtistAlbumsApi(selectedArtistId)
        homeItemFragmentViewModel.fetchArtistSongsApi(selectedArtistId)

        homeItemFragmentViewModel.getArtistsLiveData()
            .observe(viewLifecycleOwner,
                { artist ->
                    if (artist != null) {
                        homeItemFragmentViewModel.fetchArtistFirebase(artist.email)

                        currentArtist = artist
                        fragmentView.findViewById<TextView>(R.id.txtArtistName).text =
                            artist.artistPersonalInfo.fullName
                        val imageControl =
                            fragmentView.findViewById<ImageView>(R.id.imageArtist)
                        val gsReference =
                            FirebaseStorage.storage.getReferenceFromUrl("gs://album-distribution.appspot.com/profile-images/${artist.email}.jpg")
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
        homeItemFragmentViewModel.getUsersLiveData()
            .observe(viewLifecycleOwner,
                { user ->
                    val followButton = fragmentView.findViewById<Button>(R.id.btnFollow)
                    if (user != null) {
                        fragmentView.findViewById<TextView>(R.id.txtAlbumInfo).text =
                            "Has ${user.noFollowers} followers"
                        followButton.isClickable = true
                        followButton.isEnabled = true
                    } else {
                        fragmentView.findViewById<TextView>(R.id.txtAlbumInfo).text =
                            "Unknown number of artist's followers"
                        followButton.isClickable = false
                        followButton.isEnabled = false
                    }
                })
        fillFollowButton(selectedArtistId)

        val verticalAdapter = HomeVerticalAdapter(CategoryData.artistData, this)
        val verticalRecyclerView =
            fragmentView.findViewById<RecyclerView>(R.id.artistItemRecyclerView)
        verticalRecyclerView.layoutManager =
            LinearLayoutManager(requireActivity(), RecyclerView.VERTICAL, false)
        verticalRecyclerView.adapter = verticalAdapter
        verticalAdapter.emptyData(CategoryData.artistData[0])
        verticalAdapter.emptyData(CategoryData.artistData[1])

        homeItemFragmentViewModel.getArtistSongsLiveData()
            .observe(viewLifecycleOwner,
                { songs ->
                    verticalAdapter.emptyData(CategoryData.artistData[0])
                    if (songs != null) {
                        for (song in songs) {
                            val gsReference =
                                FirebaseStorage.storage.getReferenceFromUrl("gs://album-distribution.appspot.com/song-images/${song.id}.jpg")
                            try {
                                gsReference.downloadUrl.addOnCompleteListener { uri ->
                                    var link = ""
                                    if (uri.isSuccessful) {
                                        link = uri.result.toString()
                                    }
                                    verticalAdapter.updateData(
                                        CategoryData.artistData[0],
                                        CategoryItem(song.id, link, CategoryItemType.SONG)
                                    )
                                }
                            } catch (ignored: IOException) {
                            }
                        }
                    }
                })
        homeItemFragmentViewModel.getArtistAlbumsLiveData()
            .observe(viewLifecycleOwner,
                { albums ->
                    if (albums != null) {
                        for (album in albums) {
                            val gsReference =
                                FirebaseStorage.storage.getReferenceFromUrl("gs://album-distribution.appspot.com/album-images/${album.id}.jpg")
                            try {
                                gsReference.downloadUrl.addOnCompleteListener { uri ->
                                    var link = ""
                                    if (uri.isSuccessful) {
                                        link = uri.result.toString()
                                    }
                                    verticalAdapter.updateData(
                                        CategoryData.artistData[1],
                                        CategoryItem(album.id, link, CategoryItemType.ALBUM)
                                    )
                                }
                            } catch (ignored: IOException) {
                            }
                        }
                    }
                })
    }

    private fun fillFollowButton(selectedArtistId: String) {
        FirebaseRealtimeDB.favouriteArtistsReference.child("/follow-${FirebaseAuthDB.firebaseAuth.currentUser!!.uid}-${selectedArtistId}")
            .get()
            .addOnSuccessListener { user ->
                val followButton = fragmentView.findViewById<Button>(R.id.btnFollow)
                if (user.exists()) {
                    buttonUnfollow(followButton, selectedArtistId)
                } else {
                    buttonFollow(followButton, selectedArtistId)
                }
            }
    }

    private fun buttonFollow(followButton: Button, selectedArtistId: String) {
        followButton.text = "Follow"
        followButton.setOnClickListener {
            homeItemFragmentViewModel.updateFollowers(
                FirebaseAuthDB.firebaseAuth.currentUser!!.uid,
                selectedArtistId,
                true,
                currentArtist.email
            )
            buttonUnfollow(followButton, selectedArtistId)
        }
    }

    private fun buttonUnfollow(followButton: Button, selectedArtistId: String) {
        followButton.text = "Unfollow"
        followButton.setOnClickListener {
            homeItemFragmentViewModel.updateFollowers(
                FirebaseAuthDB.firebaseAuth.currentUser!!.uid,
                selectedArtistId,
                false,
                currentArtist.email
            )
            buttonFollow(followButton, selectedArtistId)
        }
    }

    override fun onClick(item: CategoryItem) {
        when (item.itemType) {
            CategoryItemType.ALBUM -> {
                val bundle = bundleOf("selected_album_id" to item.itemId)
                findNavController()
                    .navigate(R.id.action_artistFragment_to_albumFragment, bundle)
            }
            CategoryItemType.SONG -> {
                val bundle = bundleOf("selected_song_id" to item.itemId)
                findNavController()
                    .navigate(R.id.action_artistFragment_to_songFragment, bundle)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        homeItemFragmentViewModel.clear()
    }
}