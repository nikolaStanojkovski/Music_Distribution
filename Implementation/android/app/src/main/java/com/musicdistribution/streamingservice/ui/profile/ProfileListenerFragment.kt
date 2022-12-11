package com.musicdistribution.streamingservice.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.musicdistribution.streamingservice.constants.ApiConstants
import com.musicdistribution.streamingservice.constants.EntityConstants
import com.musicdistribution.streamingservice.data.SessionService
import com.musicdistribution.streamingservice.model.search.CategoryItemType
import com.musicdistribution.streamingservice.ui.HomeActivity
import com.musicdistribution.streamingservice.ui.auth.AuthActivity
import streamingservice.R

class ProfileListenerFragment : Fragment() {

    private var fragmentView: View? = null
    private var imageControl: ImageView? = null

    private lateinit var profileFragmentViewModel: ProfileFragmentViewModel
    private val PICK_IMAGE_REQUEST = 234

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile_listener, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentView = view

        profileFragmentViewModel =
            ViewModelProvider(this)[ProfileFragmentViewModel::class.java]


        fillProfileInfo()
        fragmentView!!.findViewById<Button>(R.id.btnFavouriteSongsListener).setOnClickListener {
            findNavController().navigate(R.id.action_profileFragmentListener_to_profileListFragment)
        }
        fragmentView!!.findViewById<Button>(R.id.btnFavouriteArtistsListener).setOnClickListener {
            findNavController().navigate(R.id.action_profileFragmentListener_to_profileListFragment)
        }
        fragmentView!!.findViewById<Button>(R.id.btnEditImageListener).setOnClickListener {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(
                Intent.createChooser(intent, "Select a profile image"),
                PICK_IMAGE_REQUEST
            )
        }
        fragmentView!!.findViewById<Button>(R.id.btnEditProfileListener).setOnClickListener {
            val name = fragmentView!!.findViewById<EditText>(R.id.profileNameInputListener).text
            val surname =
                fragmentView!!.findViewById<EditText>(R.id.profileSurnameInputListener).text
            if (name.isNotBlank() && surname.isNotBlank()) {
                profileFragmentViewModel.updateUserInfo(name.toString(), surname.toString())
                refresh()
            }
        }
        fragmentView!!.findViewById<Button>(R.id.btnLogoutListener).setOnClickListener {
            logout()
        }

        fragmentView!!.findViewById<Button>(R.id.btnFavouriteArtistsListener).setOnClickListener {
            val bundle = bundleOf("listing_type" to CategoryItemType.ARTIST)
            findNavController().navigate(
                R.id.action_profileFragmentListener_to_profileListFragment,
                bundle
            )
        }
        fragmentView!!.findViewById<Button>(R.id.btnFavouriteSongsListener).setOnClickListener {
            val bundle = bundleOf("listing_type" to CategoryItemType.SONG)
            findNavController().navigate(
                R.id.action_profileFragmentListener_to_profileListFragment,
                bundle
            )
        }
    }

    private fun fillProfileInfo() {
//        FirebaseRealtimeDB.usersReference.child("/${FirebaseAuthDB.firebaseAuth.currentUser!!.uid}")
//            .get().addOnSuccessListener { user ->
//                FirebaseAuthUser.updateUser(user)
//            }
//
//        val currentUser = FirebaseAuthUser.user!!
//        fragmentView!!.findViewById<TextView>(R.id.txtNoFollowingListener).text =
//            currentUser.noFollowing.toString()
//        fragmentView!!.findViewById<TextView>(R.id.txtNoFollowersListener).text =
//            currentUser.noFollowers.toString()
//        fragmentView!!.findViewById<TextView>(R.id.txtNameSurnameListener).text =
//            "${currentUser.name} ${currentUser.surname}"
//
//        fragmentView!!.findViewById<EditText>(R.id.profileNameInputListener)
//            .setText(currentUser.name)
//        fragmentView!!.findViewById<EditText>(R.id.profileSurnameInputListener)
//            .setText(currentUser.surname)
//
//        imageControl = fragmentView!!.findViewById(R.id.profileImageListener)
//        val gsReference =
//            FirebaseStorage.storage.getReferenceFromUrl("gs://album-distribution.appspot.com/profile-images/${FirebaseAuthUser.user!!.email}.jpg")
//        try {
//            gsReference.downloadUrl.addOnCompleteListener { uri ->
//                var link = ""
//                if (uri.isSuccessful) {
//                    link = uri.result.toString()
//                }
//                Glide.with(this)
//                    .load(link)
//                    .placeholder(R.drawable.default_profile)
//                    .into(imageControl!!)
//            }
//        } catch (ignored: IOException) {
//        }
    }

    private fun refresh() {
        val intent = Intent(requireActivity(), HomeActivity::class.java)
        startActivity(intent)
        requireActivity().finish()
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