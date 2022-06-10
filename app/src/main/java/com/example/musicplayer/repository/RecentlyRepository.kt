package com.example.musicplayer.repository

import android.util.Log
import com.example.musicplayer.model.Song
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class RecentlyRepository {
    val applicationScope = CoroutineScope(SupervisorJob())
    companion object{
        var cashedRecArray = RoomRepository.cachedRecArray
    }

    init{
        applicationScope.launch {
            cashedRecArray = RoomRepository.cachedRecArray
        }
    }

    fun getRecSongs(): ArrayList<Song>{
        RoomRepository.updateCachedRecently()
        RoomRepository.convertRecentlySongsToRealSongs()
        return RoomRepository.cachedRecArray
    }

    fun addSong(songId: Long){
        Log.e("RecentlyRepository", songId.toString())
        RoomRepository.addSongToRecently(songId)
    }

    fun removeSong(song: Song){
        RoomRepository.removeSongFromRecently(song)
    }
}