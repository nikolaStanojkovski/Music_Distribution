package com.musicdistribution.streamingservice.ui.home

import android.Manifest
import android.content.pm.PackageManager
import android.location.Geocoder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.musicdistribution.streamingservice.constants.*
import com.musicdistribution.streamingservice.data.CategoryData
import com.musicdistribution.streamingservice.data.SessionService
import com.musicdistribution.streamingservice.listeners.CategoryItemClickListener
import com.musicdistribution.streamingservice.model.search.CategoryItem
import com.musicdistribution.streamingservice.model.search.CategoryItemType
import com.musicdistribution.streamingservice.util.LocalizationUtils
import com.musicdistribution.streamingservice.util.StringUtils
import com.musicdistribution.streamingservice.viewmodel.HomeViewModel
import streamingservice.R
import java.util.*

@Suppress(MessageConstants.DEPRECATION)
class HomeFragment : Fragment(), CategoryItemClickListener {

    private lateinit var fragmentView: View
    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentView = view

        homeViewModel =
            ViewModelProvider(this)[HomeViewModel::class.java]
        fetchData()

        CategoryData.clearData()
        getLocation(LocalizationUtils.getLocationProvider(requireActivity()))
        fillDateAndTime()
        fillRecyclerViews()
    }

    private fun fetchData() {
        homeViewModel.emptyData()

        homeViewModel.fetchSongs()
        homeViewModel.fetchAlbums()
        homeViewModel.fetchArtists()
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
                            ExceptionConstants.LOCATION_PERMISSIONS,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
            else -> {
                Toast.makeText(
                    requireActivity(),
                    ExceptionConstants.LOCATION_PERMISSIONS,
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun fillDateAndTime() {
        val greetingTime = LocalizationUtils.getStringForGreeting()

        fragmentView.findViewById<TextView>(R.id.dateTimeText).text = greetingTime
        fragmentView.findViewById<TextView>(R.id.locationText).text = AlphabetConstants.EMPTY_STRING
    }

    private fun fillRecyclerViews() {
        val verticalAdapter = HomeVerticalAdapter(CategoryData.mainData, this)
        val verticalRecyclerView =
            fragmentView.findViewById<RecyclerView>(R.id.mainHomeRecyclerView)
        verticalRecyclerView.layoutManager =
            LinearLayoutManager(requireActivity(), RecyclerView.VERTICAL, false)
        verticalAdapter.clearData()
        verticalRecyclerView.adapter = verticalAdapter

        fetchSongs(verticalAdapter)
        fetchArtists(verticalAdapter)
        fetchAlbums(verticalAdapter)
    }

    private fun fetchSongs(verticalAdapter: HomeVerticalAdapter) {
        verticalAdapter.emptyData(CategoryData.mainData[0])
        verticalAdapter.updateCategory(CategoryData.mainData[0])
        homeViewModel.getSongsLiveData()
            .observe(viewLifecycleOwner,
                { songs ->
                    if (songs != null) {
                        for (item in songs) {
                            val songCoverReference =
                                "${ApiConstants.BASE_URL}${ApiConstants.API_STREAM_SONGS}/${item.id}${FileConstants.PNG_EXTENSION}"
                            verticalAdapter.updateData(
                                CategoryData.mainData[0],
                                CategoryItem(
                                    item.id,
                                    songCoverReference,
                                    CategoryItemType.SONG
                                )
                            )
                        }
                    } else {
                        Toast.makeText(
                            activity,
                            ExceptionConstants.SONGS_FETCH_FAILED,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                })
    }

    private fun fetchAlbums(verticalAdapter: HomeVerticalAdapter) {
        verticalAdapter.emptyData(CategoryData.mainData[1])
        verticalAdapter.updateCategory(CategoryData.mainData[1])
        homeViewModel.getAlbumsLiveData()
            .observe(viewLifecycleOwner,
                { albums ->
                    if (albums != null) {
                        for (item in albums) {
                            val albumCoverReference =
                                "${ApiConstants.BASE_URL}${ApiConstants.API_STREAM_ALBUMS}/${item.id}${FileConstants.PNG_EXTENSION}"
                            verticalAdapter.updateData(
                                CategoryData.mainData[1],
                                CategoryItem(
                                    item.id,
                                    albumCoverReference,
                                    CategoryItemType.ALBUM
                                )
                            )
                        }
                    } else {
                        Toast.makeText(
                            activity,
                            ExceptionConstants.ALBUMS_FETCH_FAILED,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                })
    }

    private fun fetchArtists(verticalAdapter: HomeVerticalAdapter) {
        verticalAdapter.emptyData(CategoryData.mainData[2])
        verticalAdapter.updateCategory(CategoryData.mainData[2])
        homeViewModel.getArtistsLiveData()
            .observe(viewLifecycleOwner,
                { artists ->
                    if (artists != null) {
                        for (item in artists) {
                            val profilePictureReference =
                                "${ApiConstants.BASE_URL}${ApiConstants.API_STREAM_ARTISTS}/${item.id}${FileConstants.PNG_EXTENSION}"
                            verticalAdapter.updateData(
                                CategoryData.mainData[2],
                                CategoryItem(
                                    item.id,
                                    profilePictureReference,
                                    CategoryItemType.ARTIST
                                )
                            )
                        }
                    } else {
                        Toast.makeText(
                            activity,
                            ExceptionConstants.ARTISTS_FETCH_FAILED,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                })
    }


    private fun fillLocation(longitude: Double, latitude: Double) {
        val geocoder = Geocoder(requireActivity(), Locale.getDefault())

        val address =
            geocoder.getFromLocation(latitude, longitude, 1)[0]
        val locationString = "${address.adminArea}, ${address.countryCode}"
        fragmentView.findViewById<TextView>(R.id.locationText).text = locationString
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
                var latitude = LocationConstants.DEFAULT_LOCATION_LATITUDE
                var longitude = LocationConstants.DEFAULT_LOCATION_LONGITUDE

                if (location != null) {
                    if (StringUtils.isDouble(location.latitude.toString())
                        && StringUtils.isDouble(location.longitude.toString())
                    ) {
                        SessionService.save(
                            LocationConstants.LOCATION_LATITUDE,
                            location.latitude.toString()
                        )
                        SessionService.save(
                            LocationConstants.LOCATION_LONGITUDE,
                            location.longitude.toString()
                        )
                        latitude = location.latitude
                        longitude = location.longitude
                    }
                } else {
                    val locationLatitude = SessionService.read(LocationConstants.LOCATION_LATITUDE)
                    val locationLongitude =
                        SessionService.read(LocationConstants.LOCATION_LONGITUDE)
                    if (locationLatitude != null && locationLongitude != null
                        && StringUtils.isDouble(locationLatitude.toString())
                        && StringUtils.isDouble(locationLongitude.toString())
                    ) {
                        latitude = locationLatitude.toDouble()
                        longitude = locationLongitude.toDouble()
                    }
                }

                fillLocation(longitude, latitude)
            }
        }
    }

    override fun onClick(item: CategoryItem) {
        when (item.itemType) {
            CategoryItemType.ARTIST -> {
                val bundle = bundleOf(
                    SearchConstants.SELECTED_ARTIST_ID to item.itemId,
                    SearchConstants.ITEM_TYPE to CategoryItemType.ARTIST
                )
                findNavController()
                    .navigate(R.id.action_homeFragment_to_artistFragment, bundle)
            }
            CategoryItemType.ALBUM -> {
                val bundle = bundleOf(
                    SearchConstants.SELECTED_ALBUM_ID to item.itemId,
                    SearchConstants.ITEM_TYPE to CategoryItemType.ALBUM
                )
                findNavController()
                    .navigate(R.id.action_homeFragment_to_albumFragment, bundle)
            }
            CategoryItemType.SONG -> {
                val bundle = bundleOf(
                    SearchConstants.SELECTED_SONG_ID to item.itemId,
                    SearchConstants.ITEM_TYPE to CategoryItemType.SONG
                )
                findNavController()
                    .navigate(R.id.action_homeFragment_to_songFragment, bundle)
            }
        }
    }
}