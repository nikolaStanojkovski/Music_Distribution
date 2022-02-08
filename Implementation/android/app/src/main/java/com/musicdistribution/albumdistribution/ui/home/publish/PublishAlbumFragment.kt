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
import com.musicdistribution.albumdistribution.data.domain.Genre
import com.musicdistribution.albumdistribution.data.domain.Tier
import com.musicdistribution.albumdistribution.data.firebase.auth.FirebaseAuthUser
import com.musicdistribution.albumdistribution.model.retrofit.AlbumRetrofitCreate
import com.musicdistribution.albumdistribution.model.retrofit.ArtistRetrofitAuth
import com.musicdistribution.albumdistribution.model.retrofit.EmailDomain
import com.musicdistribution.albumdistribution.ui.home.HomeActivity
import com.musicdistribution.albumdistribution.ui.home.HomeFragmentViewModel
import com.musicdistribution.albumdistribution.util.TransactionUtils


class PublishAlbumFragment : Fragment() {

    private lateinit var fragmentView: View
    private lateinit var homeFragmentViewModel: HomeFragmentViewModel

    private val PICK_IMAGE_REQUEST = 234
    private var picturePath: Uri? = null
    private lateinit var musicDistributorChoices: MutableList<Pair<String, String>>
    private var artistId: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_publish_album, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentView = view


        homeFragmentViewModel =
            ViewModelProvider(requireActivity()).get(HomeFragmentViewModel::class.java)
        fragmentView.findViewById<Button>(R.id.btnCancelCreateAlbum).setOnClickListener {
            navigateOut()
        }
        fragmentView.findViewById<Button>(R.id.btnChangeAlbumPicture).setOnClickListener {
            val intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(
                Intent.createChooser(intent, "Select an album image"),
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
        fragmentView.findViewById<Button>(R.id.btnConfirmCreateAlbum).setOnClickListener {
            val albumNameInput = fragmentView.findViewById<EditText>(R.id.inputAlbumName)
            val distributorSpinner = fragmentView.findViewById<Spinner>(R.id.inputAlbumDistributor)
            val tierSpinner = fragmentView.findViewById<Spinner>(R.id.inputAlbumTier)
            val genreSpinner = fragmentView.findViewById<Spinner>(R.id.inputAlbumGenre)

            val albumName = albumNameInput.text.toString()
            val artistName = FirebaseAuthUser.user!!.name + " " + FirebaseAuthUser.user!!.surname
            if (albumName.isBlank()) {
                Toast.makeText(
                    requireActivity(),
                    "Please input a valid song name",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                val distributorValue = distributorSpinner.selectedItem.toString()
                val tierValue = Tier.valueOf(tierSpinner.selectedItem.toString())
                val genreValue = Genre.valueOf(genreSpinner.selectedItem.toString())

                val distributor =
                    musicDistributorChoices.first { v -> v.second == distributorValue }
                val createAlbum = AlbumRetrofitCreate(
                    albumName,
                    0,
                    false,
                    genreValue,
                    artistName,
                    artistName,
                    artistName,
                    artistId!!
                )

                homeFragmentViewModel.publishAlbum(
                    createAlbum,
                    distributor,
                    tierValue,
                    picturePath
                )
                navigateOut()
            }

        }
    }

    private fun fillImage() {
        val albumImageView = fragmentView.findViewById<ImageView>(R.id.imageCreateAlbum)
        Glide.with(this)
            .load(picturePath)
            .placeholder(R.drawable.default_picture)
            .into(albumImageView!!)
    }

    private fun fillData() {
        homeFragmentViewModel.fetchMusicDistributors()
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
        homeFragmentViewModel.emptyData()

        homeFragmentViewModel.getArtistLiveData()
            .observe(viewLifecycleOwner,
                { artist ->
                    if (artist != null) {
                        artistId = artist.id
                    } else {
                        Toast.makeText(
                            activity,
                            "Error when trying to fetch artist",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                })
        homeFragmentViewModel.getDistributorsLiveData()
            .observe(viewLifecycleOwner,
                { musicDistributors ->
                    if (!musicDistributors.isNullOrEmpty()) {
                        musicDistributorChoices =
                            musicDistributors.map { d -> d.id to d.distributorInfo }.toMutableList()
                        populateSpinners()
                    } else {
                        Toast.makeText(
                            activity,
                            "Error when trying to fetch music distributors",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                })
    }

    private fun populateSpinners() {
        val distributorsSpinner = fragmentView.findViewById<Spinner>(R.id.inputAlbumDistributor)
        val tiersSpinner = fragmentView.findViewById<Spinner>(R.id.inputAlbumTier)
        val genresSpinner = fragmentView.findViewById<Spinner>(R.id.inputAlbumGenre)

        val distributorSpinnerAdapter =
            ArrayAdapter(
                requireActivity(),
                android.R.layout.simple_spinner_item,
                musicDistributorChoices.map { v -> v.second }.toMutableList()
            )
        distributorSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        distributorsSpinner.adapter = distributorSpinnerAdapter

        val tiersSpinnerAdapter =
            ArrayAdapter(
                requireActivity(),
                android.R.layout.simple_spinner_item,
                Tier.values().map { v -> v.toString() })
        tiersSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        tiersSpinner.adapter = tiersSpinnerAdapter

        val genresSpinnerAdapter =
            ArrayAdapter(
                requireActivity(),
                android.R.layout.simple_spinner_item,
                Genre.values().map { v -> v.toString() })
        tiersSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        genresSpinner.adapter = genresSpinnerAdapter

        tiersSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val currentItemValue = tiersSpinner.selectedItem.toString()
                val price = TransactionUtils.calculateAlbumCost(Tier.valueOf(currentItemValue))
                fragmentView.findViewById<TextView>(R.id.txtAlbumCost).text = price
            }
        }
    }

    private fun navigateOut() {
        val intent = Intent(requireActivity(), HomeActivity::class.java)
        startActivity(intent)
        requireActivity().finish()
    }
}