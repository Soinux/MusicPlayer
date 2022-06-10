package com.example.musicplayer.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.musicplayer.model.Song
import com.example.musicplayer.repository.RecentlyRepository

class RecentlyViewModel : ViewModel(){
    var dataset: MutableLiveData<ArrayList<Any>> = MutableLiveData()
    var recRepository: RecentlyRepository
    init{
        dataset.value = ArrayList()
        recRepository = RecentlyRepository()
    }

    fun sendDataToFragment(){
        updateData()
    }

    fun updateData() {
        dataset.value = recRepository.getRecSongs() as ArrayList<Any>
    }

    fun getDataset(): ArrayList<Song>{
        return dataset.value as ArrayList<Song>
    }
}