package com.example.musicplayer.appInterface

import com.example.musicplayer.model.Song


interface PassDataForSelectSong {
    fun passDataToInvokingFragment(songs : ArrayList<Song>)
}