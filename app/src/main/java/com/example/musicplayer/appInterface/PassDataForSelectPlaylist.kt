package com.example.musicplayer.appInterface

import com.example.musicplayer.model.Playlist

interface PassDataForSelectPlaylist {

    fun passDataToInvokingFragment(playlist : ArrayList<Playlist>)
}