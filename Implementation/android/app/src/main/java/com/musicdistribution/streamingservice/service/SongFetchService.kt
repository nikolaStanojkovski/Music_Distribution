package com.musicdistribution.streamingservice.service

import android.app.Application
import android.media.MediaPlayer
import android.os.AsyncTask
import android.os.Handler
import android.widget.Toast
import com.musicdistribution.streamingservice.constant.AlphabetConstants
import com.musicdistribution.streamingservice.constant.ExceptionConstants
import com.musicdistribution.streamingservice.constant.MessageConstants

@Suppress(MessageConstants.DEPRECATION)
class SongFetchService(
    app: Application?,
    private val selectedSongId: String,
    private val mediaPlayer: MediaPlayer
) : AsyncTask<String, String, String>() {

    val application: Application? = app

    private val handler: Handler = Handler()
    //    val seekBar: SeekBar = playSongControl

    private var mediaFileLength: Int = 0
    private var realtimeLength: Int = 0

    @Deprecated(MessageConstants.DEPRECATION_JAVA)
    override fun doInBackground(vararg params: String?): String {
        if (!isCancelled) {
            try {
                mediaPlayer.setDataSource(params[0])
                mediaPlayer.prepare()
            } catch (ex: Exception) {
                if (application != null) {
                    Toast.makeText(
                        application,
                        "${ExceptionConstants.SONG_FETCH_FAILED} $selectedSongId",
                        Toast.LENGTH_LONG
                    ).show()
                }
                ex.printStackTrace()
            }
        } else {
            mediaPlayer.pause()
        }
        return AlphabetConstants.EMPTY_STRING
    }

    @Deprecated(MessageConstants.DEPRECATION_JAVA)
    override fun onPostExecute(result: String?) {
        mediaFileLength = mediaPlayer.duration
        realtimeLength = mediaFileLength
        if (!mediaPlayer.isPlaying) {
            mediaPlayer.start()
        } else {
            mediaPlayer.pause()
        }

        updateSeekBar()
    }

    // TODO: Implement this for notification
    private fun updateSeekBar() {
//        seekBar.progress = (mediaPlayer.currentPosition / mediaFileLength) * 100
        if (mediaPlayer.isPlaying) {
            val updater = Runnable {
                kotlin.run {
                    updateSeekBar()
                    realtimeLength -= 1000
//                    timeProgressView.text = StringUtils.generateTimeString(realtimeLength)
                }
            }
            handler.postDelayed(updater, 1000)
        }
    }
}