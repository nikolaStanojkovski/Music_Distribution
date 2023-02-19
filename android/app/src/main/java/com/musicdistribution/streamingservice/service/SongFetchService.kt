@file:Suppress(MessageConstants.DEPRECATION)

package com.musicdistribution.streamingservice.service

import android.media.MediaPlayer
import android.os.AsyncTask
import android.os.Handler
import android.util.Log
import com.musicdistribution.streamingservice.constant.AlphabetConstants
import com.musicdistribution.streamingservice.constant.MessageConstants
import com.musicdistribution.streamingservice.listener.SeekBarListener

class SongFetchService(
    private val seekBarListener: SeekBarListener,
    private val mediaPlayer: MediaPlayer?,
) : AsyncTask<String, String, String>(),
    MediaPlayer.OnCompletionListener,
    MediaPlayer.OnPreparedListener {

    private val handler: Handler = Handler()
    private var mediaFileLength: Int = 0

    @Deprecated(MessageConstants.DEPRECATION_JAVA)
    override fun doInBackground(vararg params: String?): String {
        try {
            if (mediaPlayer != null) {
                if (!mediaPlayer.isPlaying) {
                    mediaPlayer.setDataSource(params[0])
                    mediaPlayer.prepare()
                    updateSeekBar()
                }
            }
        } catch (e: Exception) {
            Log.e(MessageConstants.APPLICATION_ID, e.message, e)
        }
        return AlphabetConstants.EMPTY_STRING
    }

    @Deprecated(MessageConstants.DEPRECATION_JAVA)
    override fun onPostExecute(result: String?) {
        if (mediaPlayer != null) {
            mediaFileLength = mediaPlayer.duration

            if (!mediaPlayer.isPlaying) {
                mediaPlayer.start()
            } else {
                mediaPlayer.pause()
            }

            mediaPlayer.setOnCompletionListener(this)
        }
    }

    override fun onCompletion(mp: MediaPlayer?) {
        if (mp != null) {
            seekBarListener.onUpdate(0)
        }
    }

    override fun onPrepared(mp: MediaPlayer?) {
        updateSeekBar()
    }

    private fun updateSeekBar() {
        if (mediaPlayer != null) {
            val updater = Runnable {
                kotlin.run {
                    try {
                        seekBarListener.onUpdate(
                            ((mediaPlayer.currentPosition.toDouble()
                                    / mediaFileLength.toDouble()) * 100).toInt()
                        )
                        updateSeekBar()
                    } catch (e: Exception) {
                        Log.e(MessageConstants.APPLICATION_ID, e.message, e)
                    }
                }
            }
            handler.postDelayed(updater, 10)
        }
    }
}