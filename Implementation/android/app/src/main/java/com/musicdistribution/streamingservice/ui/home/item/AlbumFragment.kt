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
import com.musicdistribution.streamingservice.listeners.SearchItemClickListener
import com.musicdistribution.streamingservice.model.search.CategoryItemType
import com.musicdistribution.streamingservice.model.search.SearchItem
import com.musicdistribution.streamingservice.ui.HomeActivity
import com.musicdistribution.streamingservice.ui.search.SearchItemAdapter
import streamingservice.R


class AlbumFragment : Fragment(), SearchItemClickListener {

    private lateinit var fragmentView: View
    private lateinit var homeItemFragmentViewModel: HomeItemFragmentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_album, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentView = view

        val selectedAlbumId = arguments?.get(ComponentConstants.SELECTED_ALBUM_ID) as String?
        val categoryItemType = arguments?.get(ComponentConstants.ITEM_TYPE) as CategoryItemType?
        if (selectedAlbumId == null || categoryItemType == null || categoryItemType != CategoryItemType.ALBUM) {
            startActivity(Intent(requireActivity(), HomeActivity::class.java))
            requireActivity().finish()
        } else {
            homeItemFragmentViewModel =
                ViewModelProvider(this)[HomeItemFragmentViewModel::class.java]
            fillData(selectedAlbumId)
            fragmentView.findViewById<Button>(R.id.btnBackAlbum).setOnClickListener {
                findNavController().navigate(R.id.action_albumFragment_to_homeFragment)
                homeItemFragmentViewModel.clear()
            }
        }
    }

    private fun fillData(selectedAlbumId: String) {
        homeItemFragmentViewModel.fetchAlbum(selectedAlbumId)
        homeItemFragmentViewModel.fetchAlbumSongs(selectedAlbumId)

        homeItemFragmentViewModel.getAlbumLiveData()
            .observe(viewLifecycleOwner,
                { item ->
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
                })

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

        homeItemFragmentViewModel.getAlbumSongsLiveData()
            .observe(viewLifecycleOwner,
                { songs ->
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
                })
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

    override fun onClick(searchItem: SearchItem) {
        val bundle = bundleOf(
            ComponentConstants.SELECTED_SONG_ID to searchItem.searchItemId,
            ComponentConstants.ITEM_TYPE to CategoryItemType.SONG
        )
        findNavController().navigate(R.id.action_albumFragment_to_songFragment, bundle)
    }
}