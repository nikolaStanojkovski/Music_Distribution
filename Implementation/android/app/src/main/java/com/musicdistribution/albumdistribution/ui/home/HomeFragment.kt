package com.musicdistribution.albumdistribution.ui.home

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
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.location.FusedLocationProviderClient
import com.musicdistribution.albumdistribution.R
import com.musicdistribution.albumdistribution.data.CategoryData
import com.musicdistribution.albumdistribution.data.SessionService
import com.musicdistribution.albumdistribution.util.LocalizationUtils
import java.util.*

class HomeFragment : Fragment() {

    private lateinit var fragmentView: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fragmentView = view

        SessionService.setSessionService(requireActivity().applicationContext)
        getLocation(LocalizationUtils.getLocationProvider(requireActivity()))
        fillDateAndTime()
        fillRecyclerViews()
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
        // TODO: Check user role, and decide category upon that
        val verticalAdapter = HomeVerticalAdapter(CategoryData.creatorCategoryData)
        val verticalRecyclerView =
            fragmentView.findViewById<RecyclerView>(R.id.mainHomeRecyclerView)
        verticalRecyclerView.layoutManager = LinearLayoutManager(requireActivity())
        verticalRecyclerView.adapter = verticalAdapter
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
}