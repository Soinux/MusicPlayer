package com.example.musicplayer.utils

import android.app.Activity
import android.content.Context
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import com.example.musicplayer.model.Song
import com.example.musicplayer.activity.MainActivity
import com.example.musicplayer.fragments.LibraryFragment
import com.example.kookplayer.utlis.FileUtils
import pl.droidsonroids.gif.BuildConfig


object SongUtils {

    fun shareMusic(context: Context, song: Song) {


        val fileToBeShared = song.data?.let { FileUtils.convertSongToFile(it) }

        if (fileToBeShared != null) {

            val fileUri = FileProvider.getUriForFile(
                context,
                "${BuildConfig.APPLICATION_ID}.FileProvider",
                fileToBeShared
            )

            fileUri?.let { FileUtils.shareFile(context, it) }
        }

    }


    fun deleteMusic(context: Context, activity: Activity, uri: Uri) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val urisToModify = mutableListOf(uri)
            val deletePendingIntent =
                MediaStore.createDeleteRequest(context.contentResolver, urisToModify)

            ActivityCompat.startIntentSenderForResult(
                activity,
                deletePendingIntent.intentSender,
                LibraryFragment.DELETE_REQUEST_CODE,
                null,
                0,
                0,
                0,
                null
            )
        } else {
            context.contentResolver.delete(uri, null, null)
        }
    }

    fun del(songId: String, uris: Uri) {
        try {
            val where = "${MediaStore.Audio.AudioColumns._ID} = ?"
            val args = arrayOf(songId)
            val uri = FilePathUtlis.getMusicsUri()
            MainActivity.activity.baseContext.contentResolver.delete(uri, where, args)
            LibraryFragment.viewModel.updateData()
        } catch (ignored: Exception) {
        }
    }

    fun shareMultipleMusics(vararg positions: ArrayList<Int>) {
    }


    fun getSongById(id: Long): Song? {
        for (song in LibraryFragment.viewModel.getData()) {
            if (song.id == id)
                return song
        }

        return null
    }


}