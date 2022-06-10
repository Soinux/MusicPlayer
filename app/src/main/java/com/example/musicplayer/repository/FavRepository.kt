package com.example.musicplayer.repository

import com.example.musicplayer.model.Song
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class FavRepository {
    val applicationScope = CoroutineScope(SupervisorJob())
    companion object {
        var cashedFavArray = RoomRepository.cachedFavArray
    }

    init {
        applicationScope.launch {
            cashedFavArray =
                RoomRepository.cachedFavArray
        }
    }

    fun getFavSongs(): ArrayList<Song> {

        RoomRepository.updateCachedFav()
        RoomRepository.convertFavSongsToRealSongs()
        return RoomRepository.cachedFavArray

    }
}