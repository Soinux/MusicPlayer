package com.example.musicplayer.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.musicplayer.R
import com.example.musicplayer.databinding.FragmentAddPlaylistsBinding
import com.example.musicplayer.adapter.AddPlaylistAdapter
import com.example.musicplayer.appInterface.PassDataForSelectSong
import com.example.musicplayer.dialogs.AddSongToPlaylistDialog
import com.example.musicplayer.model.Song
import com.example.musicplayer.repository.RoomRepository
import com.example.musicplayer.repository.SongsRepository
import com.example.musicplayer.utils.KeyboardUtils.hideKeyboard
import com.example.musicplayer.utils.SwipeToDelete

class AddPlaylistsFragment : Fragment(), PassDataForSelectSong {

    private lateinit var binding: FragmentAddPlaylistsBinding
    lateinit var songsRepository: SongsRepository
    lateinit var selectedSongs: ArrayList<Song>
    var addPlaylistAdapter: AddPlaylistAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddPlaylistsBinding.inflate(inflater, container, false)
        selectedSongs = ArrayList()
        binding.txtCancelAddPlaylistsFragment.setOnClickListener {
            try {
                selectedSongs.clear()
            } catch (exception: Exception){
                //TODO(handle the exception)
            }

            findNavController().navigate(R.id.action_addPlaylistsFragment_to_playlistsFragment)
        }

        binding.txtDoneAddPlaylistsFragment.setOnClickListener {
            if(binding.editTxtNamePlaylistsAddFragment.text.toString().trim().isEmpty()){
                binding.editTxtNamePlaylistsAddFragment.error = "Please enter a name"
            } else if (isUnique(binding.editTxtNamePlaylistsAddFragment.text.toString().lowercase())){
                binding.editTxtNamePlaylistsAddFragment.error = "Duplicate name"
            } else {
                var res = ""
                for(song in selectedSongs){
                    res += "${song.id},"
                }
                var s = binding.editTxtNamePlaylistsAddFragment.text.toString().trim().lowercase().split(" ")
                var name = ""
                for(i in s){
                    name += i.substring(0, 1).uppercase() + i.substring(1, i.length) + " "
                }

                PlaylistsFragment.viewModel?.playlistRepository?.createPlaylist(name, selectedSongs.size, res)
                PlaylistsFragment.viewModel?.updateDataset()

                try {
                    selectedSongs.clear()
                } catch (exception: Exception){
                    //TODO(handle the exception)
                }

                Toast.makeText(context, "Done", Toast.LENGTH_LONG).show()
                findNavController().navigate(R.id.action_addPlaylistsFragment_to_playlistsFragment)
            }
        }

        binding.constraintAddMusicAddFragment.setOnClickListener {
            createDialogToSelectPlaylist()
        }

        binding.layoutAddPlaylist.setOnClickListener {
            hideKeyboard(requireActivity())
        }

        return binding.root
    }

    fun createDialogToSelectPlaylist() {
        songsRepository = context?.let { SongsRepository(it) }!!
        var listSongs: ArrayList<Song> = ArrayList()
        for(i in songsRepository.getListOfSongs()){
            var ok = true
            for(j in selectedSongs){
                if(i.id == j.id) {
                    ok = false
                    break
                }
            }
            if(ok) listSongs.add(i)
        }
        Log.e("songRepo", songsRepository.getListOfSongs().toString())
        Log.e("selectedSong", selectedSongs.toString())
        Log.e("listSong", listSongs.toString())
        val addSongToPlaylistDialog = AddSongToPlaylistDialog(listSongs)

        addSongToPlaylistDialog?.setTargetFragment(this, 0)
        this.fragmentManager?.let { it1 -> addSongToPlaylistDialog?.show(it1, "pl") }
    }

    private fun isUnique(name: String): Boolean {
        for (playlist in RoomRepository.cachedPlaylistArray!!) {
            if (playlist.name.trim().lowercase() == name)
                return true
        }
        return false
    }

    override fun passDataToInvokingFragment(songs: ArrayList<Song>) {
        selectedSongs = songs

        addPlaylistAdapter = activity?.let {
            AddPlaylistAdapter(
                it,
                selectedSongs
            )
        }
        swipeToDelete(binding.recyclerviewAddPlaylistsFragment)
        binding.recyclerviewAddPlaylistsFragment.layoutManager = LinearLayoutManager(context)
        binding.recyclerviewAddPlaylistsFragment.adapter = addPlaylistAdapter

    }

    private fun swipeToDelete(recyclerView: RecyclerView){
        val swipeToDeleteCallback = object : SwipeToDelete(){
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val deletedItem = addPlaylistAdapter!!.dataset[viewHolder.adapterPosition]
                selectedSongs.remove(deletedItem)
                addPlaylistAdapter!!.notifyItemRemoved(viewHolder.adapterPosition)
                Toast.makeText(context, "delete", Toast.LENGTH_LONG).show()
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }
}