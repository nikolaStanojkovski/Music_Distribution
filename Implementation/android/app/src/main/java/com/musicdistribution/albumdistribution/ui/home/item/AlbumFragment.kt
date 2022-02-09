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
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.musicdistribution.albumdistribution.R
import com.musicdistribution.albumdistribution.data.firebase.storage.FirebaseStorage
import com.musicdistribution.albumdistribution.model.CategoryItemType
import com.musicdistribution.albumdistribution.model.SearchItem
import com.musicdistribution.albumdistribution.ui.HomeActivity
import com.musicdistribution.albumdistribution.ui.search.SearchItemAdapter
import com.musicdistribution.albumdistribution.util.ValidationUtils
import com.musicdistribution.albumdistribution.util.listeners.SearchItemClickListener


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
            ViewModelProvider(requireActivity())[HomeItemFragmentViewModel::class.java]
        fillData(selectedAlbumId!!, categoryType)
        fragmentView.findViewById<Button>(R.id.btnBackAlbum).setOnClickListener {
            findNavController().navigate(com.musicdistribution.albumdistribution.R.id.action_albumFragment_to_homeFragment)
            homeItemFragmentViewModel.clear()
        }
    }

    private fun fillData(selectedAlbumId: String, categoryItemType: CategoryItemType?) {
        val songItemAdapter = SearchItemAdapter(mutableListOf(), this)
        val songItemRecyclerView =
            fragmentView.findViewById<RecyclerView>(R.id.songListAlbumRecyclerView)
        songItemRecyclerView!!.layoutManager =
            LinearLayoutManager(requireActivity(), RecyclerView.VERTICAL, false)
        songItemRecyclerView.adapter = songItemAdapter
        songItemAdapter.emptyData()

        if (categoryItemType != null && categoryItemType == CategoryItemType.PUBLISHED_ALBUM) {
            fragmentView.findViewById<TextView>(R.id.txtAlbumHeading).visibility = View.GONE
            val unpublishButton = fragmentView.findViewById<Button>(R.id.btnUnPublishAlbum)
            unpublishButton.visibility = View.VISIBLE
            unpublishButton.setOnClickListener {
                fillConfirmDialog(selectedAlbumId)
            }
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
                        val gsReference =
                            FirebaseStorage.storage.getReferenceFromUrl("gs://album-distribution.appspot.com/album-images/${album.id}.jpg")
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
        homeItemFragmentViewModel.getAlbumSongsLiveData()
            .observe(viewLifecycleOwner,
                { songs ->
                    if (songs != null) {
                        val gsReference =
                            FirebaseStorage.storage.getReferenceFromUrl("gs://album-distribution.appspot.com/album-images/${selectedAlbumId}.jpg")
                        var link = ""
                        gsReference.downloadUrl.addOnCompleteListener { uri ->
                            if (uri.isSuccessful) {
                                link = uri.result.toString()
                            }
                            val searchItems = mutableListOf<SearchItem>()
                            for (item in songs) {
                                val searchItem = SearchItem(
                                    item.id,
                                    item.songName,
                                    "Length: ${ValidationUtils.generateTimeString(item.songLength!!.lengthInSeconds)}",
                                    CategoryItemType.SONG,
                                    link
                                )
                                searchItems.add(searchItem)
                            }
                            songItemAdapter.updateData(searchItems)
                        }
                    }
                })
    }

    private fun fillConfirmDialog(selectedAlbumId: String) {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("Confirm Unpublish Album")
        builder.setMessage("Are you sure you want to unpublish this album?")
        builder.setPositiveButton("Confirm") { dialog, _ ->
            dialog.dismiss()
            homeItemFragmentViewModel.unPublishAlbum(selectedAlbumId)
            navigateOut()
        }
        builder.setNegativeButton(
            "Cancel"
        ) { dialog, _ -> dialog.dismiss() }
        builder.show()
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