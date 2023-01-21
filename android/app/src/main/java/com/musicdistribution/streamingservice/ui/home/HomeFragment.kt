@file:Suppress(MessageConstants.DEPRECATION)

package com.musicdistribution.streamingservice.ui.home

import android.Manifest
import android.app.LauncherActivity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.location.Geocoder
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.location.FusedLocationProviderClient
import com.musicdistribution.streamingservice.constant.*
import com.musicdistribution.streamingservice.data.CategoryData
import com.musicdistribution.streamingservice.listener.CategoryItemClickListener
import com.musicdistribution.streamingservice.model.enums.EntityType
import com.musicdistribution.streamingservice.model.retrofit.core.Notification
import com.musicdistribution.streamingservice.model.search.CategoryItem
import com.musicdistribution.streamingservice.model.search.enums.CategoryItemType
import com.musicdistribution.streamingservice.model.search.enums.CategoryListType
import com.musicdistribution.streamingservice.service.SequenceService
import com.musicdistribution.streamingservice.service.SessionService
import com.musicdistribution.streamingservice.util.ApiUtils
import com.musicdistribution.streamingservice.util.LocalizationUtils
import com.musicdistribution.streamingservice.util.StringUtils
import com.musicdistribution.streamingservice.viewmodel.EntityViewModel
import com.musicdistribution.streamingservice.viewmodel.NotificationViewModel
import streamingservice.R
import java.util.*

class HomeFragment : Fragment(), CategoryItemClickListener {

    private lateinit var fragmentView: View
    private lateinit var entityViewModel: EntityViewModel
    private lateinit var notificationViewModel: NotificationViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentView = view

        entityViewModel =
            ViewModelProvider(this)[EntityViewModel::class.java]
        notificationViewModel =
            ViewModelProvider(this)[NotificationViewModel::class.java]
        fetchData()

        CategoryData.clearData()
        getLocation(LocalizationUtils.getLocationProvider(requireActivity()))
        fillDateAndTime()
        fillRecyclerViews()
        checkNotifications()
    }

    private fun fetchData() {
        entityViewModel.emptyData()
        notificationViewModel.emptyData()

        entityViewModel.fetchSongs(PaginationConstants.DEFAULT_PAGE_NUMBER)
        entityViewModel.fetchAlbums(PaginationConstants.DEFAULT_PAGE_NUMBER)
        entityViewModel.fetchArtists(PaginationConstants.DEFAULT_PAGE_NUMBER)
    }

    @Deprecated(MessageConstants.DEPRECATION_JAVA)
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
        entityViewModel.getSongsLiveData()
            .observe(
                viewLifecycleOwner
            ) { songs ->
                if (songs != null) {
                    for (item in songs) {
                        verticalAdapter.updateData(
                            CategoryData.mainData[0],
                            CategoryItem(
                                item.id,
                                ApiUtils.getSongCoverUrl(item),
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
            }
    }

    private fun fetchAlbums(verticalAdapter: HomeVerticalAdapter) {
        verticalAdapter.emptyData(CategoryData.mainData[1])
        verticalAdapter.updateCategory(CategoryData.mainData[1])
        entityViewModel.getAlbumsLiveData()
            .observe(
                viewLifecycleOwner
            ) { albums ->
                if (albums != null) {
                    for (item in albums) {
                        verticalAdapter.updateData(
                            CategoryData.mainData[1],
                            CategoryItem(
                                item.id,
                                ApiUtils.getAlbumCoverUrl(item),
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
            }
    }

    private fun fetchArtists(verticalAdapter: HomeVerticalAdapter) {
        verticalAdapter.emptyData(CategoryData.mainData[2])
        verticalAdapter.updateCategory(CategoryData.mainData[2])
        entityViewModel.getArtistsLiveData()
            .observe(
                viewLifecycleOwner
            ) { artists ->
                if (artists != null) {
                    for (item in artists) {
                        verticalAdapter.updateData(
                            CategoryData.mainData[2],
                            CategoryItem(
                                item.id,
                                ApiUtils.getArtistCoverUrl(item),
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
            }
    }

    private fun fillLocation(longitude: Double, latitude: Double) {
        val geocoder = Geocoder(requireActivity(), Locale.getDefault())

        val address =
            geocoder.getFromLocation(latitude, longitude, 1)?.get(0)
        val locationString = "${address?.adminArea}, ${address?.countryCode}"
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

    override fun onItemClick(item: CategoryItem) {
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
            else -> {}
        }
    }

    override fun onShowMoreClick(itemType: CategoryItemType) {
        findNavController()
            .navigate(
                R.id.action_homeFragment_to_listItemFragment, bundleOf(
                    getListItemType(itemType),
                    SearchConstants.CATEGORY_LISTING_TYPE to CategoryListType.ALL_ITEMS
                )
            )
    }

    private fun getListItemType(itemType: CategoryItemType): Pair<String, CategoryItemType> {
        when (itemType) {
            CategoryItemType.ARTIST -> {
                return SearchConstants.LISTING_TYPE to CategoryItemType.ARTIST
            }
            CategoryItemType.ALBUM -> {
                return SearchConstants.LISTING_TYPE to CategoryItemType.ALBUM
            }
            CategoryItemType.SONG -> {
                return SearchConstants.LISTING_TYPE to CategoryItemType.SONG
            }
            CategoryItemType.PUBLISHED_ALBUM -> {
                return SearchConstants.LISTING_TYPE to CategoryItemType.PUBLISHED_ALBUM
            }
            CategoryItemType.PUBLISHED_SONG -> {
                return SearchConstants.LISTING_TYPE to CategoryItemType.PUBLISHED_SONG
            }
        }
    }

    private fun checkNotifications() {
        val listenerId = SessionService.read(EntityConstants.USER_ID)
        if (!listenerId.isNullOrBlank()) {
            notificationViewModel.fetchNotifications(listenerId)
            notificationViewModel.getNotificationLiveData()
                .observe(
                    viewLifecycleOwner
                ) { notifications ->
                    notifications.forEach { showNotification(it) }
                }
        }
    }

    @Synchronized
    private fun showNotification(notification: Notification) {
        val notificationManager =
            requireActivity().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val creatorName =
                notification.creatorResponse.userPersonalInfo.artName.ifBlank {
                    notification.creatorResponse.userPersonalInfo.fullName
                }
            val publishingName =
                if (notification.type == EntityType.ALBUMS)
                    EntityConstants.ALBUM
                else EntityConstants.SONG
            val message =
                "$publishingName was published by $creatorName on ${notification.publishedTime}"

            val notificationChannel =
                NotificationChannel(
                    NotificationConstants.CHANNEL_ID,
                    message,
                    NotificationManager.IMPORTANCE_HIGH
                )
            notificationChannel.enableLights(true)
            notificationChannel.enableVibration(true)
            notificationManager.createNotificationChannel(notificationChannel)

            notificationManager.notify(SequenceService.nextValue(), buildNotification(message))
        }
    }

    @Synchronized
    private fun buildNotification(message: String): android.app.Notification {
        val intent = Intent(requireActivity(), LauncherActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            requireActivity(),
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        return NotificationCompat.Builder(requireActivity(), NotificationConstants.CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_launcher_foreground)
            .setContentTitle(NotificationConstants.CONTENT_TITLE)
            .setContentText(message)
            .setLargeIcon(
                BitmapFactory.decodeResource(
                    this.resources,
                    R.mipmap.ic_launcher_foreground
                )
            )
            .setStyle(
                NotificationCompat.BigTextStyle().bigText(message)
            )
            .setContentIntent(pendingIntent).build()
    }
}