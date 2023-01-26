package com.musicdistribution.albumdistribution.ui.home.publish

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.musicdistribution.albumdistribution.R
import com.musicdistribution.albumdistribution.data.firebase.auth.FirebaseAuthUser
import com.musicdistribution.albumdistribution.model.retrofit.ArtistRetrofitAuth
import com.musicdistribution.albumdistribution.model.retrofit.EmailDomain
import com.musicdistribution.albumdistribution.model.retrofit.SongRetrofitCreate
import com.musicdistribution.albumdistribution.ui.HomeActivity
import com.musicdistribution.albumdistribution.ui.home.HomeFragmentViewModel
import com.musicdistribution.albumdistribution.util.ValidationUtils

class PublishSongFragment : Fragment() {

    private lateinit var fragmentView: View
    private lateinit var homeFragmentViewModel: HomeFragmentViewModel

    private val PICK_IMAGE_REQUEST = 234
    private var picturePath: Uri? = null
    private lateinit var publishedAlbumChoices: MutableList<Pair<String, String>>
    private var artistId: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_publish_song, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentView = view

        homeFragmentViewModel =
            ViewModelProvider(this)[HomeFragmentViewModel::class.java]
        fragmentView.findViewById<Button>(R.id.btnCancelCreateSong).setOnClickListener {
            navigateOut()
        }
        fragmentView.findViewById<Button>(R.id.btnChangeSongPicture).setOnClickListener {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(
                Intent.createChooser(intent, "Select a song image"),
                PICK_IMAGE_REQUEST
            )
        }

        fillData()
        fillImage()
        handleData()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data!!.data != null) {
            picturePath = data.data!!
            fillImage()
        }
    }

    private fun handleData() {
        fragmentView.findViewById<Button>(R.id.btnConfirmCreateSong).setOnClickListener {
            val songNameInput = fragmentView.findViewById<EditText>(R.id.inputSongName)
            val songLengthInput = fragmentView.findViewById<EditText>(R.id.inputSongLength)
            val songAlbumSpinner = fragmentView.findViewById<Spinner>(R.id.inputAlbums)

            val songName = songNameInput.text.toString()
            val songLength = songLengthInput.text.toString()
            if (songName.isBlank()) {
                Toast.makeText(
                    requireActivity(),
                    "Please input a valid song name",
                    Toast.LENGTH_LONG
                ).show()
            } else if (!ValidationUtils.isNumeric(songLength)) {
                Toast.makeText(
                    requireActivity(),
                    "Please input a valid song length (an integer)",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                val albumValue = songAlbumSpinner.selectedItem.toString()
                val albumId =
                    if (albumValue == "None") "" else publishedAlbumChoices.filter { v -> v.second == albumValue }
                        .map { item -> item.first }.first()

                val songPublishModel = SongRetrofitCreate(
                    songName,
                    albumId == "",
                    songLength.toInt(),
                    artistId!!,
                    albumId
                )
                homeFragmentViewModel.publishSong(songPublishModel, picturePath)
                navigateOut()
            }

        }
    }

    private fun fillImage() {
        val songImageView = fragmentView.findViewById<ImageView>(R.id.imageCreateSong)
        Glide.with(this)
            .load(picturePath)
            .placeholder(R.drawable.default_picture)
            .into(songImageView!!)
    }

    private fun fillData() {
        homeFragmentViewModel.emptyData()
        homeFragmentViewModel.fetchPublishedAlbums()
        val artistRetrofit = ArtistRetrofitAuth(
            username = FirebaseAuthUser.user!!.email.split("@")[0],
            emailDomain = EmailDomain.valueOf(FirebaseAuthUser.user!!.email.split("@")[1].split(".")[0]),
            telephoneNumber = "[not-defined]",
            firstName = FirebaseAuthUser.user!!.name,
            lastName = FirebaseAuthUser.user!!.surname,
            artName = "[not-defined]",
            password = "[not-defined]"
        )
        homeFragmentViewModel.fetchArtist(artistRetrofit)
        homeFragmentViewModel.getArtistLiveData()
            .observe(viewLifecycleOwner,
                { artist ->
                    if (artist != null) {
                        artistId = artist.id
                    } else {
                        Toast.makeText(
                            activity,
                            "Error when trying to fetch artist information",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                })

        val albumChoices = mutableListOf<Pair<String, String>>()
        albumChoices.add("" to "None")
        publishedAlbumChoices = albumChoices
        populateSpinner()

        homeFragmentViewModel.getPublishedAlbumsLiveData()
            .observe(viewLifecycleOwner,
                { publishedAlbums ->
                    if (!publishedAlbums.isNullOrEmpty()) {
                        for (item in publishedAlbums) {
                            if (item.artistId.isNotBlank() && artistId.isNullOrBlank()) {
                                artistId = item.artistId
                            }
                            albumChoices.add(item.id to item.albumName)
                        }
                        publishedAlbumChoices = albumChoices
                        populateSpinner()
                    } else {
                        Toast.makeText(
                            activity,
                            "Error when trying to fetch albums",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                })
    }

    private fun populateSpinner() {
        val albumSpinner = fragmentView.findViewById<Spinner>(R.id.inputAlbums)

        val items = publishedAlbumChoices.map { v -> v.second }.toMutableList()
        val spinnerAdapter =
            ArrayAdapter(requireActivity(), android.R.layout.simple_spinner_item, items)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        albumSpinner.adapter = spinnerAdapter

        albumSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val currentItemValue = albumSpinner.selectedItem.toString()
                val updatePictureBUtton =
                    fragmentView.findViewById<Button>(R.id.btnChangeSongPicture)
                if (currentItemValue == "None") {
                    updatePictureBUtton.isEnabled = true
                    updatePictureBUtton.isClickable = true
                } else {
                    updatePictureBUtton.isEnabled = false
                    updatePictureBUtton.isClickable = false
                }
            }
        }
    }

    private fun navigateOut() {
        val intent = Intent(requireActivity(), HomeActivity::class.java)
        startActivity(intent)
        requireActivity().finish()
    }
}