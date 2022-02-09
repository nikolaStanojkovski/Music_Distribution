package com.musicdistribution.albumdistribution.ui.home.publish

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.musicdistribution.albumdistribution.R
import com.musicdistribution.albumdistribution.data.firebase.auth.FirebaseAuthUser
import com.musicdistribution.albumdistribution.model.Tier
import com.musicdistribution.albumdistribution.model.retrofit.ArtistRetrofitAuth
import com.musicdistribution.albumdistribution.model.retrofit.EmailDomain
import com.musicdistribution.albumdistribution.model.retrofit.PublishedAlbumRetrofit
import com.musicdistribution.albumdistribution.model.retrofit.PublishedAlbumRetrofitCreate
import com.musicdistribution.albumdistribution.ui.HomeActivity
import com.musicdistribution.albumdistribution.ui.home.HomeFragmentViewModel
import com.musicdistribution.albumdistribution.util.TransactionUtils


class RaiseTierFragment : Fragment() {

    private lateinit var fragmentView: View
    private lateinit var homeFragmentViewModel: HomeFragmentViewModel

    private lateinit var publishedAlbums: MutableList<PublishedAlbumRetrofit>
    private var artistId: String? = null
    private var tierPriority: MutableList<Pair<Int, Tier>> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_raise_tier, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fragmentView = view

        homeFragmentViewModel =
            ViewModelProvider(requireActivity())[HomeFragmentViewModel::class.java]
        fragmentView.findViewById<Button>(R.id.btnCancelRaiseTier).setOnClickListener {
            navigateOut()
        }

        fillTierPriority()
        fillData()
        handleData()
    }

    private fun handleData() {
        fragmentView.findViewById<Button>(R.id.btnConfirmRaiseTier).setOnClickListener {
            val publishedAlbumsSpinner =
                fragmentView.findViewById<Spinner>(R.id.inputRaiseTierAlbum)
            val tierSpinner = fragmentView.findViewById<Spinner>(R.id.inputRaiseTIerTier)

            val selectedPublishedAlbumValue = publishedAlbumsSpinner.selectedItem.toString()
            val selectedTierValue = Tier.valueOf(tierSpinner.selectedItem.toString())

            if (selectedPublishedAlbumValue.isBlank() || tierSpinner.selectedItem.toString()
                    .isBlank()
            ) {
                Toast.makeText(
                    requireActivity(),
                    "Please choose a published album and a tier",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                val selectedPublishedAlbum =
                    publishedAlbums.first { v -> v.albumName == selectedPublishedAlbumValue }

                val publishedAlbumCreate = PublishedAlbumRetrofitCreate(
                    publishedAlbumId = selectedPublishedAlbum.publishedAlbumId,
                    albumId = selectedPublishedAlbum.albumId,
                    albumName = selectedPublishedAlbum.albumName,
                    artistId = selectedPublishedAlbum.artistId,
                    artistInformation = selectedPublishedAlbum.artistInformation,
                    musicPublisherId = selectedPublishedAlbum.musicPublisherId,
                    musicPublisherInfo = selectedPublishedAlbum.musicPublisherInfo,
                    albumTier = selectedTierValue.toString(),
                    subscriptionFee = TransactionUtils.calculateCost(selectedTierValue),
                    transactionFee = 5.00
                )
                homeFragmentViewModel.raiseAlbumTier(
                    publishedAlbumCreate
                )
                navigateOut()
            }
        }
    }

    private fun fillTierPriority() {
        tierPriority.add(1 to Tier.Bronze)
        tierPriority.add(2 to Tier.Silver)
        tierPriority.add(3 to Tier.Gold)
        tierPriority.add(4 to Tier.Platinum)
        tierPriority.add(5 to Tier.Diamond)
    }

    private fun fillData() {
        homeFragmentViewModel.emptyData()
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
                        homeFragmentViewModel.fetchCurrentPublishedAlbums(artistId!!)
                    }
                })
        homeFragmentViewModel.getCurrentPublishedAlbumsLiveData()
            .observe(viewLifecycleOwner,
                { albums ->
                    if (!albums.isNullOrEmpty()) {
                        publishedAlbums = albums
                        populateSpinners()
                    }
                })
    }

    private fun populateSpinners() {
        val publishedAlbumsSpinner = fragmentView.findViewById<Spinner>(R.id.inputRaiseTierAlbum)
        val tiersSpinner = fragmentView.findViewById<Spinner>(R.id.inputRaiseTIerTier)

        val publishedAlbumsAdapter =
            ArrayAdapter(
                requireActivity(),
                android.R.layout.simple_spinner_item,
                publishedAlbums.map { item -> item.albumName }
            )
        publishedAlbumsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        publishedAlbumsSpinner.adapter = publishedAlbumsAdapter

        val tierItems = Tier.values().map { v -> v.toString() }
        val tiersSpinnerAdapter =
            ArrayAdapter(
                requireActivity(),
                android.R.layout.simple_spinner_item,
                tierItems
            )
        tiersSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        tiersSpinner.adapter = tiersSpinnerAdapter

        tiersSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val currentAlbumValue = publishedAlbumsSpinner.selectedItem.toString()
                val currentTierValue = tiersSpinner.selectedItem.toString()

                val publishedAlbum =
                    publishedAlbums.first { album -> album.albumName == currentAlbumValue }
                val initialTier =
                    tierPriority.first { v -> publishedAlbum.albumTier.toString() == v.second.toString() }
                val currentTier =
                    tierPriority.first { v -> v.second.toString() == currentTierValue }

                if (initialTier.first > currentTier.first) {
                    tiersSpinner.setSelection(tierItems.indexOfFirst { v -> Tier.valueOf(v) == initialTier.second })
                }

                val currentItemValue = tiersSpinner.selectedItem.toString()
                val price = TransactionUtils.calculateAlbumCost(Tier.valueOf(currentItemValue))
                fragmentView.findViewById<TextView>(R.id.txtAlbumRaiseCost).text = price
            }
        }
    }

    private fun navigateOut() {
        val intent = Intent(requireActivity(), HomeActivity::class.java)
        startActivity(intent)
        requireActivity().finish()
    }
}