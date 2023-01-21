package com.musicdistribution.streamingservice.ui.home.item

import android.content.Intent
import android.os.Bundle
import android.util.Log
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
import com.musicdistribution.streamingservice.data.CategoryData
import com.musicdistribution.streamingservice.listener.CategoryItemClickListener
import com.musicdistribution.streamingservice.model.enums.EntityType
import com.musicdistribution.streamingservice.model.search.CategoryItem
import com.musicdistribution.streamingservice.model.search.enums.CategoryItemType
import com.musicdistribution.streamingservice.model.search.enums.CategoryListType
import com.musicdistribution.streamingservice.service.SessionService
import com.musicdistribution.streamingservice.ui.home.HomeActivity
import com.musicdistribution.streamingservice.ui.home.HomeVerticalAdapter
import com.musicdistribution.streamingservice.viewmodel.FavouriteItemViewModel
import com.musicdistribution.streamingservice.viewmodel.EntityItemViewModel
import streamingservice.R

class ArtistFragment : Fragment(), CategoryItemClickListener {

    private lateinit var entityItemViewModel: EntityItemViewModel
    private lateinit var favouriteItemViewModel: FavouriteItemViewModel
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

        val selectedArtistId = arguments?.get(SearchConstants.SELECTED_ARTIST_ID) as String?
        val categoryItemType = arguments?.get(SearchConstants.ITEM_TYPE) as CategoryItemType?
        if (selectedArtistId == null
            || categoryItemType == null
            || categoryItemType != CategoryItemType.ARTIST
        ) {
            navigateOut()
        } else {
            entityItemViewModel =
                ViewModelProvider(this)[EntityItemViewModel::class.java]
            favouriteItemViewModel =
                ViewModelProvider(this)[FavouriteItemViewModel::class.java]

            fillData(selectedArtistId)
            fragmentView.findViewById<Button>(R.id.btnBackArtist).setOnClickListener {
                findNavController().navigate(R.id.action_artistFragment_to_homeFragment)
                entityItemViewModel.clear()
            }
        }
    }

    private fun fillData(selectedArtistId: String) {
        entityItemViewModel.fetchArtist(selectedArtistId)
        entityItemViewModel.fetchArtistAlbums(selectedArtistId)
        entityItemViewModel.fetchArtistSongs(selectedArtistId)

        entityItemViewModel.getArtistLiveData()
            .observe(
                viewLifecycleOwner
            ) { item ->
                if (item != null) {
                    fragmentView.findViewById<TextView>(R.id.txtArtistName).text =
                        item.userPersonalInfo.artName.ifBlank { item.email }
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
            }
        fillAdapterData()
        fillFavouriteData(selectedArtistId)
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
        entityItemViewModel.getArtistSongsLiveData()
            .observe(
                viewLifecycleOwner
            ) { songs ->
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
            }

        verticalAdapter.updateCategory(CategoryData.artistData[1])
        entityItemViewModel.getArtistAlbumsLiveData()
            .observe(
                viewLifecycleOwner
            ) { albums ->
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
            }
    }

    private fun fillImage(profilePictureReference: String, imageControl: ImageView) {
        try {
            Glide.with(this)
                .load(profilePictureReference)
                .placeholder(R.drawable.default_picture)
                .into(imageControl)
        } catch (e: Exception) {
            Log.e(MessageConstants.APPLICATION_ID, e.message, e)
            Toast.makeText(
                context,
                ExceptionConstants.ARTIST_PICTURE_FETCH_FAILED,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun fillFavouriteData(selectedArtistId: String) {
        if (favouriteItemViewModel.getArtistsLiveData().value.isNullOrEmpty()) {
            favouriteItemViewModel.fetchFavouritesData(EntityType.ARTISTS)
        }

        val userId = SessionService.read(EntityConstants.USER_ID)
        val likeButton: ImageView? = fragmentView.findViewById(R.id.btnLikeArtist)
        if (!userId.isNullOrEmpty() && likeButton != null) {
            favouriteItemViewModel.getArtistsLiveData()
                .observe(
                    viewLifecycleOwner
                ) { artists ->
                    if (!artists.isNullOrEmpty() && artists.filter { a -> a.id == selectedArtistId }.size == 1) {
                        buttonUnlike(likeButton, userId, selectedArtistId)
                    } else {
                        buttonLike(likeButton, userId, selectedArtistId)
                    }
                }
        }
    }

    private fun buttonLike(likeButton: ImageView, userId: String, selectedArtistId: String) {
        likeButton.setImageResource(R.drawable.ic_favourite_unfilled)
        likeButton.setOnClickListener {
            favouriteItemViewModel.addToFavourites(userId, selectedArtistId, EntityType.ARTISTS)
            buttonUnlike(likeButton, userId, selectedArtistId)
        }
    }

    private fun buttonUnlike(likeButton: ImageView, userId: String, selectedArtistId: String) {
        likeButton.setImageResource(R.drawable.ic_favourite_filled)
        likeButton.setOnClickListener {
            favouriteItemViewModel.removeFromFavourites(userId, selectedArtistId, EntityType.ARTISTS)
            buttonLike(likeButton, userId, selectedArtistId)
        }
    }

    override fun onItemClick(item: CategoryItem) {
        when (item.itemType) {
            CategoryItemType.ALBUM -> {
                val bundle = bundleOf(
                    SearchConstants.SELECTED_ALBUM_ID to item.itemId,
                    SearchConstants.ITEM_TYPE to CategoryItemType.ALBUM
                )
                findNavController()
                    .navigate(R.id.action_artistFragment_to_albumFragment, bundle)
            }
            CategoryItemType.SONG -> {
                val bundle = bundleOf(
                    SearchConstants.SELECTED_SONG_ID to item.itemId,
                    SearchConstants.ITEM_TYPE to CategoryItemType.SONG
                )
                findNavController()
                    .navigate(R.id.action_artistFragment_to_songFragment, bundle)
            }
            else -> findNavController()
                .navigate(R.id.action_artistFragment_to_homeFragment)
        }
    }

    override fun onShowMoreClick(itemType: CategoryItemType) {
        val listItemType: Pair<String, CategoryItemType> = when (itemType) {
            CategoryItemType.ARTIST -> {
                SearchConstants.ITEM_TYPE to CategoryItemType.ARTIST
            }
            CategoryItemType.ALBUM -> {
                SearchConstants.ITEM_TYPE to CategoryItemType.ALBUM
            }
            CategoryItemType.SONG -> {
                SearchConstants.ITEM_TYPE to CategoryItemType.SONG
            }
            CategoryItemType.PUBLISHED_ALBUM -> {
                SearchConstants.ITEM_TYPE to CategoryItemType.PUBLISHED_ALBUM
            }
            CategoryItemType.PUBLISHED_SONG -> {
                SearchConstants.ITEM_TYPE to CategoryItemType.PUBLISHED_SONG
            }
        }
        findNavController()
            .navigate(
                R.id.action_homeFragment_to_listItemFragment, bundleOf(
                    listItemType,
                    SearchConstants.CATEGORY_LISTING_TYPE to CategoryListType.FAVOURITE_ITEMS
                )
            )
    }

    private fun navigateOut() {
        val intent = Intent(requireActivity(), HomeActivity::class.java)
        startActivity(intent)
        requireActivity().finish()
    }

}