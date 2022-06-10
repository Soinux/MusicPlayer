package com.example.musicplayer.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.musicplayer.databinding.ItemRecentlyBinding
import com.example.musicplayer.helper.Coordinator
import com.example.musicplayer.utils.ImageUtils
import com.example.musicplayer.model.Song
import com.example.musicplayer.repository.RoomRepository
import com.example.musicplayer.activity.MainActivity

class RecentlyAdapter (var listSong : ArrayList<Song>, val context: Activity) : RecyclerView.Adapter<RecentlyAdapter.RecentlyViewHolder>() {
    var position = 0
    inner class RecentlyViewHolder(private var binding: ItemRecentlyBinding): RecyclerView.ViewHolder(binding.root){
        var title = binding.txtRecently
        var image = binding.imgRecently
        var itemRcn = binding.recentllyContainer
        fun bind(song: Song){
            title.text = song.title
            song.image?.let {
                ImageUtils.loadImgToImgViewNotRound(
                    context = context,
                    imageView = image,
                    image = it
                )
            }
        }
        fun onClickItem(){
            itemRcn.setOnClickListener {

                upDatePosition(adapterPosition)
                Coordinator.sourceOfSelectedSong = "songs"
                Coordinator.currentDataSource = listSong
                Coordinator.playSelectedSong(listSong[adapterPosition])
                RoomRepository.addSongToRecently(listSong[adapterPosition].id!!.toLong())
                MainActivity.activity.updateVisibility(listSong[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentlyAdapter.RecentlyViewHolder {
        var binding = ItemRecentlyBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return RecentlyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecentlyViewHolder, position: Int) {
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