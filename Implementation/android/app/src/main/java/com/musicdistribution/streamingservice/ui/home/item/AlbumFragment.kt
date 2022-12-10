package com.musicdistribution.streamingservice.ui.home.item

import android.app.AlertDialog
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
import com.musicdistribution.streamingservice.model.CategoryItemType
import com.musicdistribution.streamingservice.model.SearchItem
import com.musicdistribution.streamingservice.ui.HomeActivity
import com.musicdistribution.streamingservice.ui.search.SearchItemAdapter
import com.musicdistribution.streamingservice.util.listeners.SearchItemClickListener
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

        val selectedAlbumId = arguments?.get("selected_album_id") as String?
        val categoryType = arguments?.get("item_type") as CategoryItemType?
        if (selectedAlbumId == null) {
            startActivity(Intent(requireActivity(), HomeActivity::class.java))
            requireActivity().finish()
        }

        homeItemFragmentViewModel =
            ViewModelProvider(this)[HomeItemFragmentViewModel::class.java]
        fillData(selectedAlbumId!!, categoryType)
        fragmentView.findViewById<Button>(R.id.btnBackAlbum).setOnClickListener {
            findNavController().navigate(R.id.action_albumFragment_to_homeFragment)
            homeItemFragmentViewModel.clear()
        }
    }

    private fun fillData(selectedAlbumId: String, categoryItemType: CategoryItemType?) {
        val songItemAdapter = SearchItemAdapter(mutableListOf(), this)
        val songItemRecyclerView =
            fragmentView.findViewById<RecyclerView>(R.id.songListAlbumRecyclerView)
        songItemRecyclerView!!.layoutManager =
            LinearLayoutManager(requireActivity(), RecyclerView.VERTICAL, false)
        songItemAdapter.emptyData()
        songItemRecyclerView.adapter = songItemAdapter

        if (categoryItemType != null && categoryItemType == CategoryItemType.PUBLISHED_ALBUM) {
            fragmentView.findViewById<TextView>(R.id.txtAlbumHeading).visibility = View.GONE
        } else {
            fragmentView.findViewById<Button>(R.id.btnUnPublishAlbum).visibility = View.GONE
            fragmentView.findViewById<TextView>(R.id.txtAlbumHeading).visibility = View.VISIBLE
        }

        homeItemFragmentViewModel.clear()
        homeItemFragmentViewModel.fetchAlbumApi(selectedAlbumId)
        homeItemFragmentViewModel.fetchAlbumSongsApi(selectedAlbumId)
        homeItemFragmentViewModel.getAlbumsLiveData()
            .observe(viewLifecycleOwner,
                { album ->
                    if (album != null) {
                        fragmentView.findViewById<TextView>(R.id.txtAlbumHeading).text =
                            album.albumName
                        fragmentView.findViewById<TextView>(R.id.txtAlbumTitle).text =
                            album.albumName
                        fragmentView.findViewById<TextView>(R.id.txtAlbumInfo).text =
                            "Album by ${album.artistName}"

                        val imageControl =
                            fragmentView.findViewById<ImageView>(R.id.imageAlbum)
                    }
                })
        homeItemFragmentViewModel.getAlbumSongsLiveData()
            .observe(viewLifecycleOwner,
                { songs ->
//                    if (songs != null) {
//                        val gsReference =
//                            FirebaseStorage.storage.getReferenceFromUrl("gs://album-distribution.appspot.com/album-images/${selectedAlbumId}.jpg")
//                        var link = ""
//                        gsReference.downloadUrl.addOnCompleteListener { uri ->
//                            if (uri.isSuccessful) {
//                                link = uri.result.toString()
//                            }
//                            val searchItems = mutableListOf<SearchItem>()
//                            for (item in songs) {
//                                val searchItem = SearchItem(
//                                    item.id,
//                                    item.songName,
//                                    "Length: ${ValidationUtils.generateTimeString(item.songLength!!.lengthInSeconds)}",
//                                    CategoryItemType.SONG,
//                                    link
//                                )
//                                searchItems.add(searchItem)
//                            }
//                            songItemAdapter.updateData(searchItems)
//                        }
//                    }
                })
    }

    private fun navigateOut() {
        val intent = Intent(requireActivity(), HomeActivity::class.java)
        startActivity(intent)
        requireActivity().finish()
    }

    override fun onClick(searchItem: SearchItem) {
        val bundle = bundleOf("selected_song_id" to searchItem.searchItemId)
        findNavController().navigate(R.id.action_albumFragment_to_songFragment, bundle)
    }

}