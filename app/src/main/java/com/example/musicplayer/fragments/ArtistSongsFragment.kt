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
import com.example.musicplayer.databinding.FragmentArtistSongsBinding
import com.example.musicplayer.adapter.ArtistSongAdapter
import com.example.musicplayer.model.Song
import com.example.musicplayer.utils.ImageUtils
import com.example.musicplayer.viewmodel.ArtistsSongViewModel

class ArtistSongsFragment : Fragment() {

    private lateinit var binding: FragmentArtistSongsBinding
    private val args by navArgs<ArtistSongsFragmentArgs>()
    private lateinit var artistSongAdapter: ArtistSongAdapter

    companion object{
        var viewModel: ArtistsSongViewModel? = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentArtistSongsBinding.inflate(inflater, container, false)

        binding.textView2.text = args.myArtist
        args.imgArtist.let {
            context?.let { it1 ->
                ImageUtils.loadImageToImageView(
                    it1,
                    binding.imageView,
                    it
                )
            }
        }

        binding.constraintBackArtists.setOnClickListener {
            findNavController().navigate(R.id.action_artistSongsFragment_to_artistsFragment)
        }

        setupViewModel()

        artistSongAdapter =
            activity?.let { viewModel?.let { it1 -> ArtistSongAdapter( it1.getDataset(), it) } }!!

        return binding.root
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this).get(ArtistsSongViewModel::class.java)
        context?.let { viewModel!!.sendDataToFragment(it, args.myArtist) }
        viewModel!!.dataset.observe(viewLifecycleOwner, artistsSongUpdateObserver)
    }

    private val artistsSongUpdateObserver = Observer<ArrayList<Song>>{
        artistSongAdapter.dataset = it
        binding.recyclerviewArtistsSongs.adapter = artistSongAdapter
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerview = binding.recyclerviewArtistsSongs
        recyclerview.apply {
            adapter = artistSongAdapter
            layoutManager = LinearLayoutManager(context)
        }

    }

}