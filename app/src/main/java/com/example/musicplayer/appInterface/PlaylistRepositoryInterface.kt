package com.example.musicplayer.appInterface

import com.example.musicplayer.model.Playlist

interface PlaylistRepositoryInterface {

    fun createPlaylist(name: String)
    fun createPlaylist(name: String, countOfSongs: Int, songs: String)

    fun getPlaylists(): ArrayList<Playlist>

    //    fun updateDatabase(): Boolean
    fun removePlaylist(id: String): Boolean
}