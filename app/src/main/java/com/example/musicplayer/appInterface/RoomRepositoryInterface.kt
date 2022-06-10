package com.example.musicplayer.appInterface

import com.example.musicplayer.model.Favorites
import com.example.musicplayer.model.Playlist
import com.example.musicplayer.model.Recently
import com.example.musicplayer.model.Song

interface RoomRepositoryInterface {

    // ----------------------- init Database ------------------------
    fun createDatabase()

    // ----------------------- playlist ------------------------
    fun createPlaylist(playlist: Playlist)
    fun removePlaylist(id: String): Boolean
    fun getPlaylists(): ArrayList<Playlist>
    fun addSongsToPlaylist(playlist_name: String, songsId: String): Boolean
    fun removeSongFromPlaylist(playlistId: String, songsId: String)
    fun listOfPlaylistsContainSpecificSong(songId: Long): ArrayList<String>
    fun removeSongFromPlaylistObject(playlist: Playlist, songsId: String) //check
    fun decreaseCountInDatabase(playlistId: String, countOfSongs: Int)
    fun increaseCountInPlaylistObject(playlist: Playlist)
    fun increaseCountInDatabase(playlist: Playlist)
    fun addSongsToPlaylistInObject(playlist: Playlist, songsId: String) //check
    fun addSongsToPlaylistInDatabase(playlist: Playlist, songsId: String)
    fun getPlaylistFromDatabase(): ArrayList<Playlist>

    fun updateCachedPlaylist()
    fun findPlaylistPositionInCachedArray(playlist: Playlist): Int
    fun getIdByName(name: String): String
    fun getPlaylistById(id: String): Playlist?


    // ----------------------- favorite ------------------------

    fun updateCachedFav()
    fun addSongToFavorites(songsId: Long)
    fun removeSongFromFavorites(song: Song)
    fun getFavoritesFromDatabase(): ArrayList<Favorites>
    fun convertFavSongsToRealSongs(): ArrayList<Song>
    fun songsIdToSongModelConverter(favSong: Favorites): Song?

    // ----------------------- recently ------------------------

    fun updateCachedRecently()
    fun addSongToRecently(songsId: Long)
    fun removeSongFromRecently(song: Song)
    fun getRecentlyFromDatabase(): ArrayList<Recently>
    fun convertRecentlySongsToRealSongs(): ArrayList<Song>
    fun songsIdToSongModelConverterRecently(recently: Recently): Song?
}