package com.example.musicplayer.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.musicplayer.model.Song
import com.example.musicplayer.repository.FavRepository

class FavViewModel: ViewModel() {
    var dataset: MutableLiveData<ArrayList<Any>> = MutableLiveData()
    var favRepository: FavRepository


    init{
        dataset.value = ArrayList()
        favRepository = FavRepository()

    }
    fun setDataToFragment(){
        updateData()
    }

    fun updateData(){
        dataset.value =  favRepository.getFavSongs() as ArrayList<Any>
    }

    fun getDataset(): ArrayList<Song>{
        return dataset.value as ArrayList<Song>
    }
}