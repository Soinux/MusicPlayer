package com.example.musicplayer.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.musicplayer.databinding.ItemAddSongsToPlaylistsDialogBinding
import com.example.musicplayer.utils.ImageUtils
import com.example.musicplayer.model.Song

class AddSongToPlaylistAdapter(var context: Activity, var listSong: ArrayList<Song>) : RecyclerView.Adapter<AddSongToPlaylistAdapter.AddSongToPlaylistViewHolder>(){

    companion object {
        var choices: ArrayList<Song> = arrayListOf()
    }

    var position = 0

    inner class AddSongToPlaylistViewHolder(var binding: ItemAddSongsToPlaylistsDialogBinding): RecyclerView.ViewHolder(binding.root){
        var name = binding.txtNameSongAddToPlaylistsDialog
        var artist = binding.txtArtistAddToPlaylistsDialog
        var imageSong = binding.imgSongAddToPlaylistsDialog
        fun bind(song: Song){
            name.text = song.title
            artist.text = song.artist
            song.image?.let {
                ImageUtils.loadImageToImageView(
                    context = context,
                    imageView = imageSong,
                    image = it
                )
            }
        }

        fun onClickItem(){
            binding.materialCheckBox.setOnClickListener {
                upDatePosition(adapterPosition)
                if (binding.materialCheckBox.isChecked) {
                    choices?.add(listSong[adapterPosition])
                } else {
                    choices?.remove(listSong[adapterPosition])
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddSongToPlaylistViewHolder {
        var binding = ItemAddSongsToPlaylistsDialogBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AddSongToPlaylistViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AddSongToPlaylistViewHolder, position: Int) {
        val song = listSong[position]
        this.position = position
        holder.apply {
            bind(song)
            onClickItem()
        }
    }

    override fun getItemCount(): Int {
        return listSong.size
    }

    fun upDatePosition(newPosition: Int){
        position = newPosition
    }
}