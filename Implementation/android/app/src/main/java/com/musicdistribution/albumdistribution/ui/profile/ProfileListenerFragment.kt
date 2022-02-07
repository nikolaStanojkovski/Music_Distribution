package com.musicdistribution.albumdistribution.ui.profile

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.musicdistribution.albumdistribution.R
import com.musicdistribution.albumdistribution.data.domain.Role
import com.musicdistribution.albumdistribution.data.firebase.auth.FirebaseAuthDB
import com.musicdistribution.albumdistribution.data.firebase.auth.FirebaseAuthUser
import com.musicdistribution.albumdistribution.data.firebase.realtime.FirebaseRealtimeDB
import com.musicdistribution.albumdistribution.data.firebase.storage.FirebaseStorage
import com.musicdistribution.albumdistribution.model.CategoryItemType
import com.musicdistribution.albumdistribution.ui.auth.AuthActivity
import com.musicdistribution.albumdistribution.ui.home.HomeActivity
import java.io.IOException


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
        if (FirebaseAuthUser.user!!.role == Role.CREATOR) {
            findNavController().navigate(R.id.action_profileFragment_to_profileFragmentArtist)
        }
        profileFragmentViewModel =
            ViewModelProvider(requireActivity()).get(ProfileFragmentViewModel::class.java)


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
            FirebaseAuthUser.user = null
            FirebaseAuthDB.firebaseAuth.signOut()
            val intent = Intent(requireActivity(), AuthActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data!!.data != null) {
            val filePath = data.data
            val profileImagesRef =
                FirebaseStorage.storage.reference.child("profile-images/${FirebaseAuthUser.user!!.email}.jpg")
            val progressDialog = ProgressDialog(requireActivity())
            progressDialog.setTitle("Uploading...")
            progressDialog.show()
            profileImagesRef.putFile(filePath!!).addOnSuccessListener {
                Toast.makeText(requireActivity(), "File successfully uploaded", Toast.LENGTH_SHORT)
                    .show()
            }.addOnProgressListener { task ->
                val progress = (100.0 * task.bytesTransferred) / task.totalByteCount
                progressDialog.setMessage("${progress.toInt()}% Uploaded...")
                if (progress.toInt() == 100) {
                    progressDialog.dismiss()
                    refresh()
                }
            }
        }
    }

    private fun fillProfileInfo() {
        FirebaseRealtimeDB.usersReference.child("/${FirebaseAuthDB.firebaseAuth.currentUser!!.uid}")
            .get().addOnSuccessListener { user ->
                FirebaseAuthUser.updateUser(user)
            }

        val currentUser = FirebaseAuthUser.user!!
        fragmentView!!.findViewById<TextView>(R.id.txtNoFollowingListener).text =
            currentUser.noFollowing.toString()
        fragmentView!!.findViewById<TextView>(R.id.txtNoFollowersListener).text =
            currentUser.noFollowers.toString()
        fragmentView!!.findViewById<TextView>(R.id.txtNameSurnameListener).text =
            "${currentUser.name} ${currentUser.surname}"

        fragmentView!!.findViewById<EditText>(R.id.profileNameInputListener)
            .setText(currentUser.name)
        fragmentView!!.findViewById<EditText>(R.id.profileSurnameInputListener)
            .setText(currentUser.surname)

        imageControl = fragmentView!!.findViewById(R.id.profileImageListener)
        val gsReference =
            FirebaseStorage.storage.getReferenceFromUrl("gs://album-distribution.appspot.com/profile-images/${FirebaseAuthUser.user!!.email}.jpg")
        try {
            gsReference.downloadUrl.addOnCompleteListener { uri ->
                var link = ""
                if (uri.isSuccessful) {
                    link = uri.result.toString()
                }
                Glide.with(this)
                    .load(link)
                    .placeholder(R.drawable.default_profile)
                    .into(imageControl!!)
            }
        } catch (ignored: IOException) {
        }
    }

    private fun refresh() {
        val intent = Intent(requireActivity(), HomeActivity::class.java)
        startActivity(intent)
        requireActivity().finish()
    }
}