package com.example.musicplayer.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.musicplayer.R
import com.example.musicplayer.databinding.FragmentPlaylistSongsBinding
import com.example.musicplayer.adapter.PlaylistSongAdapter
import com.example.musicplayer.viewmodel.PlaylistSongViewModel
import com.example.musicplayer.model.Song
import com.example.musicplayer.repository.SongsRepository

class PlaylistSongsFragment : Fragment() {

    private lateinit var binding: FragmentPlaylistSongsBinding
    private val args by navArgs<PlaylistSongsFragmentArgs>()
    lateinit var playlistSongsAdapter: PlaylistSongAdapter
    lateinit var songsRepository: SongsRepository

    companion object {
        var viewModel: PlaylistSongViewModel? = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPlaylistSongsBinding.inflate(inflater, container, false)
        songsRepository = context?.let { SongsRepository(it) }!!
        binding.constraintBackPlaylist.setOnClickListener {
            findNavController().navigate(R.id.action_playlistSongsFragment_to_playlistsFragment)
        }
        binding.txtNamePlaylistSong.text = args.myArg.name

        setupViewModel()
        playlistSongsAdapter = activity?.let { viewModel?.let { it1 -> PlaylistSongAdapter( it1.getDataset(), it) } }!!

        return binding.root
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this).get(PlaylistSongViewModel::class.java)
        viewModel!!.setPlaylistId(args.myArg.id, songsRepository.getListOfSongs())
        viewModel!!.updateDataset()
        viewModel!!.dataset.observe(viewLifecycleOwner, songUpdateObserver)
    }
    private val songUpdateObserver = Observer<ArrayList<Any>> {
        playlistSongsAdapter.listSong = it as ArrayList<Song>
        binding.recyclerviewPlaylistsSongs.adapter = playlistSongsAdapter
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = binding.recyclerviewPlaylistsSongs
        recyclerView.apply {
            adapter = playlistSongsAdapter
            layoutManager = LinearLayoutManager(context)
        }

    }

}