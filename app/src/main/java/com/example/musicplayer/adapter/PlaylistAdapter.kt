package com.example.musicplayer.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.example.musicplayer.R
import com.example.musicplayer.databinding.ItemPlaylistsLibraryBinding
import com.example.musicplayer.fragments.PlaylistsFragment
import com.example.musicplayer.model.Playlist
import com.example.musicplayer.repository.PlaylistRepository

class PlaylistAdapter (var arrayList: ArrayList<Playlist>, val context: Activity, private val listener: OnItemClickListener) : RecyclerView.Adapter<PlaylistAdapter.PlaylistViewHolder>(){

    var dataset: ArrayList<Playlist>
    lateinit var dataSend: PlaylistAdapter.OnDataSend

    init {
        dataset = arrayList
    }

    inner class PlaylistViewHolder(var binding: ItemPlaylistsLibraryBinding): RecyclerView.ViewHolder(binding.root){
        var name_playlist = binding.txtNamePlaylist
        fun bind(playlist: Playlist){
            name_playlist.text = playlist.name
        }
        init {
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }

        }
    }

    private fun handleMenuButtonClickListener(itemId: Int, playlistId: String): Boolean {
        when (itemId) {
            R.id.deletePlaylist_menu_item -> {
                PlaylistsFragment.viewModel?.playlistRepository?.removePlaylist(playlistId)
                dataSend.onSend(context, playlistId)
            }
            else -> return false
        }
        return true
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaylistViewHolder {
        var binding = ItemPlaylistsLibraryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PlaylistViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PlaylistViewHolder, position: Int) {
        val playlist = dataset[position]
        holder.apply {
            bind(playlist)
        }

        val playlistRepository = PlaylistRepository(context)
        holder.binding.imgNextSongPlaylist.setOnClickListener {
            val popUpMenu = PopupMenu(context, it)
            popUpMenu.inflate(R.menu.playlists_popup_menu)

            popUpMenu.setOnMenuItemClickListener {
                val id = playlistRepository.getPlaylists()[position].id

                return@setOnMenuItemClickListener handleMenuButtonClickListener(
                    it.itemId,
                    id
                )
            }
            popUpMenu.show()
        }
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    interface OnItemClickListener{
        fun onItemClick(position: Int)
    }

    interface OnDataSend {
        fun onSend(context: Activity, id: String)
    }

    fun OnDataSend(dataSend: OnDataSend) {
        this.dataSend = dataSend
    }
}