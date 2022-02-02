package com.musicdistribution.albumdistribution.ui.profile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.musicdistribution.albumdistribution.R
import com.musicdistribution.albumdistribution.data.firebase.auth.FirebaseAuthDB
import com.musicdistribution.albumdistribution.data.firebase.auth.FirebaseAuthUser
import com.musicdistribution.albumdistribution.ui.auth.AuthActivity


class ProfileListenerFragment : Fragment() {

    private var fragmentView: View? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile_listener, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentView = view

        fillProfileInfo()
        fragmentView!!.findViewById<Button>(R.id.btnFavouriteSongsListener).setOnClickListener {
            findNavController().navigate(R.id.action_profileFragmentListener_to_profileListFragment)
        }
        fragmentView!!.findViewById<Button>(R.id.btnFavouriteArtistsListener).setOnClickListener {
            findNavController().navigate(R.id.action_profileFragmentListener_to_profileListFragment)
        }
//        fragmentView!!.findViewById<Button>(R.id.btnEditProfileListener).setOnClickListener {
//            val name = fragmentView!!.findViewById<EditText>(R.id.profileNameInputListener).text
//            val surname = fragmentView!!.findViewById<EditText>(R.id.profileSurnameInputListener).text
//        }
        fragmentView!!.findViewById<Button>(R.id.btnLogout).setOnClickListener {
            FirebaseAuthUser.user = null
            FirebaseAuthDB.firebaseAuth.signOut()
            val intent = Intent(requireActivity(), AuthActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }
    }

    private fun fillProfileInfo() {
        val currentUser = FirebaseAuthUser.user!!
        fragmentView!!.findViewById<TextView>(R.id.txtNoFollowingListener).text = currentUser.noFollowing.toString()
        fragmentView!!.findViewById<TextView>(R.id.txtNoFollowersListener).text = currentUser.noFollowers.toString()
        fragmentView!!.findViewById<TextView>(R.id.txtNameSurnameListener).text = "${currentUser.name} ${currentUser.surname}"

        fragmentView!!.findViewById<EditText>(R.id.profileNameInputListener).setText(currentUser.name)
        fragmentView!!.findViewById<EditText>(R.id.profileSurnameInputListener).setText(currentUser.surname)

        val profileImageControl = fragmentView!!.findViewById<ImageView>(R.id.profileImageListener)
        Glide.with(this)
            .load(FirebaseAuthUser.user!!.picture)
            .placeholder(R.drawable.default_profile)
            .into(profileImageControl);
    }
}