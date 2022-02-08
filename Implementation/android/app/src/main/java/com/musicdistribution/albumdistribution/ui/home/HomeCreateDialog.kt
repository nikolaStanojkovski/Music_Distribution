package com.musicdistribution.albumdistribution.ui.home

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.navigation.fragment.findNavController
import com.musicdistribution.albumdistribution.R

class HomeCreateDialog : AppCompatDialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        val layoutInflater = activity?.layoutInflater
        val view = layoutInflater?.inflate(R.layout.dialog_create_item, null)

        view?.findViewById<Button>(R.id.btnPublishAlbum)!!.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_publishAlbumFragment)
            dialog!!.dismiss()
        }

        view.findViewById<Button>(R.id.btnPublishSong)!!.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_publishSongFragment)
            dialog!!.dismiss()
        }

        view.findViewById<Button>(R.id.btnRaiseTier)!!.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_raiseTierFragment)
            dialog!!.dismiss()
        }

        builder.setView(view)

        return builder.create()
    }
}