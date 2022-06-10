package com.example.musicplayer.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.musicplayer.model.Playlist
import com.example.musicplayer.repository.PlaylistRepository

class PlaylistViewModel : ViewModel(){

    var dataset: MutableLiveData<ArrayList<Playlist>> = MutableLiveData()
    lateinit var playlistRepository: PlaylistRepository

    init {
        dataset.value = ArrayList()
    }

    fun setFragmentContext(context: Context) {
        playlistRepository = PlaylistRepository(context)
        updateDataset()
    }

    fun updateDataset() {
        dataset.value = playlistRepository.getPlaylists()
    }

    fun getDataSet(): ArrayList<Playlist> {
        updateDataset()
        return dataset.value as ArrayList<Playlist>
    }
}