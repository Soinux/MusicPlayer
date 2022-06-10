package com.example.musicplayer.repository

import com.example.musicplayer.appInterface.PlaylistPageRepositoryInterface
import com.example.musicplayer.fragments.PlaylistsFragment
import com.example.musicplayer.utils.DatabaseConverterUtils
import com.example.musicplayer.model.Song
import kotlinx.coroutines.runBlocking

class PlaylistSongRepository(private val playlistId: String, val array: ArrayList<Song>):
    PlaylistPageRepositoryInterface{
    override fun getSongsIdFromDatabase(): String {
        var songsOfPlaylist = ""
        runBlocking {
            songsOfPlaylist = RoomRepository.localDatabase.playlistDAO().getSongsOfPlaylist(playlistId)
        }
        return songsOfPlaylist
    }

    override fun songsIdToSongModelConverter(songId: String): Song? {

        for (song in array) {
            if (song.id == songId.toLong()) {
                return song
            }
        }
        return null
    }

    override fun getSongs(): ArrayList<Song> {
        var songs: ArrayList<Song> = arrayListOf()
        val songsIdInString = getSongsIdFromDatabase()
        if (songsIdInString != "") {
            val songsIdInArraylist = convertStringToArraylist(songsIdInString)

            for (songId in songsIdInArraylist) {
                val realSong = songsIdToSongModelConverter(songId)
                if (realSong != null)
                    songs.add(realSong)
            }
        }

        return songs
    }

    fun convertStringToArraylist(songs: String): ArrayList<String> {
        return DatabaseConverterUtils.stringToArraylist(songs)
    }

    fun removeSongFromPlaylist(songId: String) {

        PlaylistsFragment.viewModel?.playlistRepository?.removeSongFromPlaylist(playlistId, songId)

    }
}