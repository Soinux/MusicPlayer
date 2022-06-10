package com.example.musicplayer.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.musicplayer.model.Song
import com.example.musicplayer.repository.SongsRepository

class ArtistsViewModel: ViewModel(){
    var dataset: MutableLiveData<ArrayList<Song>> = MutableLiveData()
    lateinit var songsRepository: SongsRepository

    init{
        dataset.value = ArrayList()
    }

    fun sendDataToFragment(context: Context){
        songsRepository = SongsRepository(context)
        updateData()
    }

    fun updateData() {
        dataset.value =  songsRepository.getListOfArtists()
    }

    fun getDataset(): ArrayList<Song>{
        return dataset.value as ArrayList<Song>
    }
}