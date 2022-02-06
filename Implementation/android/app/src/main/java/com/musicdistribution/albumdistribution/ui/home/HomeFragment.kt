package com.musicdistribution.albumdistribution.ui.home

import android.Manifest
import android.content.pm.PackageManager
import android.location.Geocoder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.location.FusedLocationProviderClient
import com.musicdistribution.albumdistribution.R
import com.musicdistribution.albumdistribution.data.CategoryData
import com.musicdistribution.albumdistribution.data.SessionService
import com.musicdistribution.albumdistribution.data.domain.Role
import com.musicdistribution.albumdistribution.data.firebase.auth.FirebaseAuthUser
import com.musicdistribution.albumdistribution.data.firebase.storage.FirebaseStorage
import com.musicdistribution.albumdistribution.model.CategoryItem
import com.musicdistribution.albumdistribution.model.CategoryItemType
import com.musicdistribution.albumdistribution.util.LocalizationUtils
import com.musicdistribution.albumdistribution.util.listeners.CategoryItemClickListener
import java.util.*

class HomeFragment : Fragment(), CategoryItemClickListener {

    private lateinit var fragmentView: View
    private lateinit var homeFragmentViewModel: HomeFragmentViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentView = view

        homeFragmentViewModel =
            ViewModelProvider(requireActivity())[HomeFragmentViewModel::class.java]
        fetchData()

        SessionService.setSessionService(requireActivity().applicationContext)
        getLocation(LocalizationUtils.getLocationProvider(requireActivity()))
        fillAddButton()
        fillDateAndTime()
        fillRecyclerViews()
    }

    private fun fetchData() {
        if (homeFragmentViewModel.getSongsLiveData().value.isNullOrEmpty()) {
            homeFragmentViewModel.fetchSongs()
        }
        if (homeFragmentViewModel.getAlbumsLiveData().value.isNullOrEmpty()) {
            homeFragmentViewModel.fetchAlbums()
        }
        if (homeFragmentViewModel.getArtistsLiveData().value.isNullOrEmpty()) {
            homeFragmentViewModel.fetchArtists()
        }
    }

    private fun fillAddButton() {
        val buttonAdd = fragmentView.findViewById<ImageView>(R.id.btnAddHome)
        if (FirebaseAuthUser.user!!.role == Role.LISTENER) {
            buttonAdd.visibility = View.GONE
        } else {
            buttonAdd.setOnClickListener {
                val createDialog = HomeCreateDialog()
                fragmentManager?.let { it1 -> createDialog.show(it1, "'Create' Dialog") }
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            1 -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(
                            requireActivity(),
                            Manifest.permission.ACCESS_FINE_LOCATION
                        ) == PackageManager.PERMISSION_GRANTED
                    ) {
                        getLocation(LocalizationUtils.getLocationProvider(requireActivity()))
                    } else {
                        Toast.makeText(
                            requireActivity(),
                            "Error when trying to grant permissions for location",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
            else -> {
                Toast.makeText(
                    requireActivity(),
                    "Error when trying to grant permissions for location",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun fillDateAndTime() {
        val greetingTime = LocalizationUtils.getStringForGreeting()

        fragmentView.findViewById<TextView>(R.id.dateTimeText).text = greetingTime
        fragmentView.findViewById<TextView>(R.id.locationText).text = ""
    }

    private fun fillRecyclerViews() {
        val verticalAdapter = HomeVerticalAdapter(CategoryData.mainData, this)
        val verticalRecyclerView =
            fragmentView.findViewById<RecyclerView>(R.id.mainHomeRecyclerView)
        verticalRecyclerView.layoutManager =
            LinearLayoutManager(requireActivity(), RecyclerView.VERTICAL, false)
        verticalRecyclerView.adapter = verticalAdapter

        fetchSongs(verticalAdapter)
        fetchAlbums(verticalAdapter)
        fetchArtists(verticalAdapter)
    }

    private fun fetchSongs(verticalAdapter: HomeVerticalAdapter) {
        verticalAdapter.emptyData(CategoryData.mainData[0])
        homeFragmentViewModel.getSongsLiveData()
            .observe(viewLifecycleOwner,
                { songs ->
                    if (songs != null) {
                        for (item in songs) {
                            if (item.creator!!.email != FirebaseAuthUser.user!!.email) {
                                val gsReference =
                                    FirebaseStorage.storage.getReferenceFromUrl("gs://album-distribution.appspot.com/song-images/${item.id}.jpg")
                                gsReference.downloadUrl.addOnCompleteListener { uri ->
                                    var link = ""
                                    if (uri.isSuccessful) {
                                        link = uri.result.toString()
                                    }
                                    verticalAdapter.updateData(
                                        CategoryData.mainData[0],
                                        CategoryItem(item.id, link, CategoryItemType.SONG)
                                    )
                                }.addOnFailureListener { }
                            }
                        }
                    } else {
                        Toast.makeText(
                            activity,
                            "Error when trying to fetch songs",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                })
    }

    private fun fetchAlbums(verticalAdapter: HomeVerticalAdapter) {
        verticalAdapter.emptyData(CategoryData.mainData[1])
        homeFragmentViewModel.getAlbumsLiveData()
            .observe(viewLifecycleOwner,
                { albums ->
                    if (albums != null) {
                        for (item in albums) {
                            if (item.creator.email != FirebaseAuthUser.user!!.email) {
                                val gsReference =
                                    FirebaseStorage.storage.getReferenceFromUrl("gs://album-distribution.appspot.com/album-images/${item.id}.jpg")
                                gsReference.downloadUrl.addOnCompleteListener { uri ->
                                    var link = ""
                                    if (uri.isSuccessful) {
                                        link = uri.result.toString()
                                    }
                                    verticalAdapter.updateData(
                                        CategoryData.mainData[1],
                                        CategoryItem(item.id, link, CategoryItemType.ALBUM)
                                    )
                                }.addOnFailureListener { }
                            }
                        }
                    } else {
                        Toast.makeText(
                            activity,
                            "Error when trying to fetch albums",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                })
    }

    private fun fetchArtists(verticalAdapter: HomeVerticalAdapter) {
        verticalAdapter.emptyData(CategoryData.mainData[2])
        homeFragmentViewModel.getArtistsLiveData()
            .observe(viewLifecycleOwner,
                { artists ->
                    if (artists != null) {
                        for (item in artists) {
                            if (item.email != FirebaseAuthUser.user!!.email) {
                                val gsReference =
                                    FirebaseStorage.storage.getReferenceFromUrl("gs://album-distribution.appspot.com/profile-images/${item.email}.jpg")
                                gsReference.downloadUrl.addOnCompleteListener { uri ->
                                    var link = ""
                                    if (uri.isSuccessful) {
                                        link = uri.result.toString()
                                    }
                                    verticalAdapter.updateData(
                                        CategoryData.mainData[2],
                                        CategoryItem(item.id!!, link, CategoryItemType.ARTIST)
                                    )
                                }.addOnFailureListener { }
                            }
                        }
                    } else {
                        Toast.makeText(
                            activity,
                            "Error when trying to fetch artists",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                })
    }

    private fun getLocation(locationProvider: FusedLocationProviderClient?) {
        if (ContextCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ),
                1
            )
        } else {
            locationProvider!!.lastLocation.addOnCompleteListener { task ->
                val location = task.result
                if (location != null) {
                    val geocoder = Geocoder(requireActivity(), Locale.getDefault())
                    val address =
                        geocoder.getFromLocation(location.latitude, location.longitude, 1)[0]
                    val locationString = "${address.adminArea}, ${address.countryCode}"

                    SessionService.save("location_latitude", address.latitude.toString())
                    SessionService.save("location_longitude", address.longitude.toString())

                    fragmentView.findViewById<TextView>(R.id.locationText).text = locationString
                }
            }
        }

    }

    override fun onClick(item: CategoryItem) {
        when (item.itemType) {
            CategoryItemType.ARTIST -> {
                val bundle = bundleOf("selected_artist_id" to item.itemId)
                findNavController()
                    .navigate(R.id.action_homeFragment_to_artistFragment, bundle)
            }
            CategoryItemType.ALBUM -> {
                val bundle = bundleOf("selected_album_id" to item.itemId)
                findNavController()
                    .navigate(R.id.action_homeFragment_to_albumFragment, bundle)
            }
            CategoryItemType.SONG -> {
                val bundle = bundleOf("selected_song_id" to item.itemId)
                findNavController()
                    .navigate(R.id.action_homeFragment_to_songFragment, bundle)
            }
        }
    }
}