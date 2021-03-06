package com.example.musicplayer.fragments

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.musicplayer.R
import com.example.musicplayer.adapter.SongAdapter
import com.example.musicplayer.databinding.FragmentSongBinding
import com.example.musicplayer.appInterface.PassDataForSelectPlaylist
import com.example.musicplayer.dialogs.AddSongFromSongToPlaylistDialog
import com.example.musicplayer.model.Playlist
import com.example.musicplayer.model.Song
import com.example.musicplayer.repository.RoomRepository
import com.example.musicplayer.viewmodel.SongViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SongFragment : Fragment(), PassDataForSelectPlaylist {
    companion object{
        lateinit var binding: FragmentSongBinding
        lateinit var songAdapter: SongAdapter
        lateinit var viewModel: SongViewModel
        lateinit var mactivity: FragmentActivity
        lateinit var selectedSong: Song
        lateinit var selectedPlaylists: ArrayList<Playlist>

        fun notifyDataSetChange(){
            viewModel.updateData()
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSongBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(SongViewModel::class.java)

        context?.let { viewModel.setDataToFragment(it) }
        val updateListSong = Observer<ArrayList<Song>>{
            songAdapter.listSong = it
            binding.songsRv.adapter = songAdapter
        }
        viewModel.liveListSong.observe(viewLifecycleOwner, updateListSong)
        songAdapter = activity?.let { viewModel.liveListSong.value?.let { it1 -> SongAdapter(it1, it) } }!!
        val rcv = binding.songsRv
        rcv.apply {
            adapter = songAdapter
            layoutManager = LinearLayoutManager(context)
        }

        songAdapter.OnDataSend(
            object : SongAdapter.OnDataSend{
                override fun onSend(context: Activity, song: Song) {
                    selectedSong = song

                    if (RoomRepository.cachedPlaylistArray != null) {
                        if (RoomRepository.cachedPlaylistArray.size > 0) {
                            createDialogToSelectPlaylist()
                        } else {
                            val i = RoomRepository.cachedPlaylistArray
                            Toast.makeText(
                                requireActivity().baseContext,
                                getString(R.string.createPlaylist_error),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } else {
                        Toast.makeText(
                            requireActivity().baseContext,
                            getString(R.string.createPlaylist_error),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

            }
        )

        notifyDataSetChange()
    }

    fun createDialogToSelectPlaylist() {

        RoomRepository.updateCachedPlaylist()

        val addSongToPlaylistDialog = RoomRepository.cachedPlaylistArray?.let {
            AddSongFromSongToPlaylistDialog(
                it
            )
        }


        addSongToPlaylistDialog?.setTargetFragment(this, 0)
        this.fragmentManager?.let { it1 -> addSongToPlaylistDialog?.show(it1, "pl") }

    }

    override fun onResume() {
        super.onResume()

        viewModel.updateData()

        mactivity = requireActivity()

    }

    override fun passDataToInvokingFragment(playlist: ArrayList<Playlist>) {
        selectedPlaylists = playlist

        addSongToSelectedPlaylist()

        selectedPlaylists.clear()
    }

    private fun addSongToSelectedPlaylist() {

        for (playlist in selectedPlaylists) {
            addSongToPlaylist(playlist)
        }
    }

    fun addSongToPlaylist(playlist: Playlist) {
        GlobalScope.launch {

            RoomRepository.addSongsToPlaylist(
                playlist.name,
                selectedSong.id.toString()
            )
        }
    }
}