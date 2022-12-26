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
import com.musicdistribution.streamingservice.constant.*
import com.musicdistribution.streamingservice.data.SessionService
import com.musicdistribution.streamingservice.listener.SearchItemClickListener
import com.musicdistribution.streamingservice.model.enums.EntityType
import com.musicdistribution.streamingservice.model.search.CategoryItemType
import com.musicdistribution.streamingservice.model.search.SearchItem
import com.musicdistribution.streamingservice.ui.home.HomeActivity
import com.musicdistribution.streamingservice.ui.search.SearchItemAdapter
import com.musicdistribution.streamingservice.viewmodel.FavouriteViewModel
import com.musicdistribution.streamingservice.viewmodel.ItemTypeViewModel
import streamingservice.R


class AlbumFragment : Fragment(), SearchItemClickListener {

    private lateinit var fragmentView: View
    private lateinit var itemTypeViewModel: ItemTypeViewModel
    private lateinit var favouriteViewModel: FavouriteViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_album, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentView = view

        val selectedAlbumId = arguments?.get(SearchConstants.SELECTED_ALBUM_ID) as String?
        val categoryItemType = arguments?.get(SearchConstants.ITEM_TYPE) as CategoryItemType?
        if (selectedAlbumId == null || categoryItemType == null || categoryItemType != CategoryItemType.ALBUM) {
            navigateOut()
        } else {
            itemTypeViewModel =
                ViewModelProvider(this)[ItemTypeViewModel::class.java]
            favouriteViewModel =
                ViewModelProvider(this)[FavouriteViewModel::class.java]

            fillData(selectedAlbumId)
            fragmentView.findViewById<Button>(R.id.btnBackAlbum).setOnClickListener {
                findNavController().navigate(R.id.action_albumFragment_to_homeFragment)
                itemTypeViewModel.clear()
            }
        }
    }

    private fun fillData(selectedAlbumId: String) {
        itemTypeViewModel.fetchAlbum(selectedAlbumId)
        itemTypeViewModel.fetchAlbumSongs(selectedAlbumId)

        itemTypeViewModel.getAlbumLiveData()
            .observe(viewLifecycleOwner
            ) { item ->
                if (item != null) {
                    fragmentView.findViewById<TextView>(R.id.txtAlbumHeading).text =
                        if (item.creator.userPersonalInfo.artName.isNotBlank())
                            item.creator.userPersonalInfo.artName else item.creator.email
                    fragmentView.findViewById<TextView>(R.id.txtAlbumTitle).text =
                        item.albumName
                    fragmentView.findViewById<TextView>(R.id.txtAlbumLength).text =
                        item.totalLength.formattedString

                    val imageControl =
                        fragmentView.findViewById<ImageView>(R.id.imageAlbum)
                    val coverPictureReference =
                        "${ApiConstants.BASE_URL}${ApiConstants.API_STREAM_ALBUMS}/${item.id}${FileConstants.PNG_EXTENSION}"

                    if (imageControl != null) {
                        fillImage(coverPictureReference, imageControl)
                    }
                }
            }

        fillFavouriteData(selectedAlbumId)
        fillAdapterData()
    }

    private fun fillAdapterData() {
        val songItemAdapter = SearchItemAdapter(mutableListOf(), this)
        val songItemRecyclerView =
            fragmentView.findViewById<RecyclerView>(R.id.songListAlbumRecyclerView)
        songItemRecyclerView!!.layoutManager =
            LinearLayoutManager(requireActivity(), RecyclerView.VERTICAL, false)
        songItemAdapter.emptyData()
        songItemRecyclerView.adapter = songItemAdapter

        itemTypeViewModel.getAlbumSongsLiveData()
            .observe(viewLifecycleOwner
            ) { songs ->
                if (songs != null && songs.size > 0) {
                    songItemAdapter.updateData(songs.map { song ->
                        SearchItem(
                            song.id,
                            song.songName,
                            getString(
                                R.string.length_parameter_placeholder,
                                song.songLength.formattedString
                            ),
                            CategoryItemType.SONG,
                            "${ApiConstants.BASE_URL}${ApiConstants.API_STREAM_ARTISTS}/${song.id}${FileConstants.PNG_EXTENSION}"
                        )
                    }.toMutableList())
                }
            }
    }

    private fun fillImage(coverPictureReference: String, imageControl: ImageView) {
        try {
            Glide.with(this)
                .load(coverPictureReference)
                .placeholder(R.drawable.default_picture)
                .into(imageControl)
        } catch (exception: Exception) {
            Toast.makeText(
                context,
                ExceptionConstants.ALBUM_PICTURE_FETCH_FAILED,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun fillFavouriteData(selectedAlbumId: String) {
        if (favouriteViewModel.getAlbumsLiveData().value.isNullOrEmpty()) {
            favouriteViewModel.fetchFavouritesData(EntityType.ALBUMS)
        }

        val userId = SessionService.read(EntityConstants.USER_ID)
        val likeButton: ImageView? = fragmentView.findViewById(R.id.btnLikeAlbum)
        if (!userId.isNullOrEmpty() && likeButton != null) {
            favouriteViewModel.getAlbumsLiveData()
                .observe(viewLifecycleOwner
                ) { albums ->
                    if (!albums.isNullOrEmpty() && albums.filter { a -> a.id == selectedAlbumId }.size == 1) {
                        buttonUnlike(likeButton, userId, selectedAlbumId)
                    } else {
                        buttonLike(likeButton, userId, selectedAlbumId)
                    }
                }
        }
    }

    private fun buttonLike(likeButton: ImageView, userId: String, selectedAlbumId: String) {
        likeButton.setImageResource(R.drawable.ic_favourite_unfilled)
        likeButton.setOnClickListener {
            favouriteViewModel.addToFavourites(userId, selectedAlbumId, EntityType.ALBUMS)
            buttonUnlike(likeButton, userId, selectedAlbumId)
        }
    }

    private fun buttonUnlike(likeButton: ImageView, userId: String, selectedAlbumId: String) {
        likeButton.setImageResource(R.drawable.ic_favourite_filled)
        likeButton.setOnClickListener {
            favouriteViewModel.removeFromFavourites(userId, selectedAlbumId, EntityType.ALBUMS)
            buttonLike(likeButton, userId, selectedAlbumId)
        }
    }

    override fun onClick(searchItem: SearchItem) {
        val bundle = bundleOf(
            SearchConstants.SELECTED_SONG_ID to searchItem.searchItemId,
            SearchConstants.ITEM_TYPE to CategoryItemType.SONG
        )
        findNavController().navigate(R.id.action_albumFragment_to_songFragment, bundle)
    }

    private fun navigateOut() {
        val intent = Intent(activity, HomeActivity::class.java)
        startActivity(intent)
        requireActivity().finish()
    }
}