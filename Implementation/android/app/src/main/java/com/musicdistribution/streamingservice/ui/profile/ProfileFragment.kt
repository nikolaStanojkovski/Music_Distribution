package com.musicdistribution.streamingservice.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.musicdistribution.streamingservice.constant.ApiConstants
import com.musicdistribution.streamingservice.constant.EntityConstants
import com.musicdistribution.streamingservice.constant.SearchConstants
import com.musicdistribution.streamingservice.data.SessionService
import com.musicdistribution.streamingservice.model.search.CategoryItemType
import com.musicdistribution.streamingservice.model.search.CategoryListType
import com.musicdistribution.streamingservice.ui.auth.AuthActivity
import streamingservice.R

class ProfileFragment : Fragment() {

    private var fragmentView: View? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentView = view

        fillData()
    }

    private fun fillData() {
        if (fragmentView != null) {
            fragmentView!!.findViewById<TextView>(R.id.txtEmailAddress).text =
                SessionService.read(EntityConstants.USER_EMAIL)
            fragmentView!!.findViewById<Button>(R.id.btnFavouriteSongs).setOnClickListener {
                val bundle = bundleOf(
                    SearchConstants.LISTING_TYPE to CategoryItemType.SONG,
                    SearchConstants.CATEGORY_LISTING_TYPE to CategoryListType.FAVOURITE_ITEMS
                )
                findNavController().navigate(
                    R.id.action_profileFragment_to_listItemFragment,
                    bundle
                )
            }
            fragmentView!!.findViewById<Button>(R.id.btnFavouriteAlbums).setOnClickListener {
                val bundle = bundleOf(
                    SearchConstants.LISTING_TYPE to CategoryItemType.ALBUM,
                    SearchConstants.CATEGORY_LISTING_TYPE to CategoryListType.FAVOURITE_ITEMS
                )
                findNavController().navigate(
                    R.id.action_profileFragment_to_listItemFragment,
                    bundle
                )
            }
            fragmentView!!.findViewById<Button>(R.id.btnFavouriteArtists).setOnClickListener {
                val bundle = bundleOf(
                    SearchConstants.LISTING_TYPE to CategoryItemType.ARTIST,
                    SearchConstants.CATEGORY_LISTING_TYPE to CategoryListType.FAVOURITE_ITEMS
                )
                findNavController().navigate(
                    R.id.action_profileFragment_to_listItemFragment,
                    bundle
                )
            }
            fragmentView!!.findViewById<Button>(R.id.btnLogoutListener).setOnClickListener {
                logout()
            }
        }
    }

    private fun logout() {
        SessionService.remove(ApiConstants.ACCESS_TOKEN)
        SessionService.remove(EntityConstants.USER_ID)
        SessionService.remove(EntityConstants.USER_EMAIL)

        val intent = Intent(requireActivity(), AuthActivity::class.java)
        startActivity(intent)
        requireActivity().finish()
    }
}