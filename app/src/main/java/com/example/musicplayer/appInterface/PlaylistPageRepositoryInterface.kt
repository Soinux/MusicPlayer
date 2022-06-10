package com.example.musicplayer.appInterface

import com.example.musicplayer.model.Song

interface PlaylistPageRepositoryInterface {
    fun getSongsIdFromDatabase(): String
    fun songsIdToSongModelConverter(songId: String): Song?
    fun getSongs(): ArrayList<Song>
}