package com.example.musicplayer.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.musicplayer.R
import com.example.musicplayer.databinding.FragmentArtistsBinding
import com.example.musicplayer.adapter.ArtistAdapter
import com.example.musicplayer.model.Song
import com.example.musicplayer.viewmodel.ArtistsViewModel

class ArtistsFragment : Fragment(), ArtistAdapter.OnItemClickListener {

    private lateinit var binding: FragmentArtistsBinding
    private lateinit var artistsAdapter: ArtistAdapter

    companion object{
        var viewModel: ArtistsViewModel? = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentArtistsBinding.inflate(inflater, container, false)

        binding.constraintArtistsBackLibrary.setOnClickListener {
            findNavController().navigate(R.id.action_artistsFragment_to_navigation_library)
        }

        setupViewModel()

        artistsAdapter =
            activity?.let { viewModel?.let { it1 -> ArtistAdapter( it1.getDataset(), it, this) } }!!

        return binding.root
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(this).get(ArtistsViewModel::class.java)
        context?.let { viewModel!!.sendDataToFragment(it) }
        viewModel!!.dataset.observe(viewLifecycleOwner, artistsUpdateObserver)
    }

    private val artistsUpdateObserver = Observer<ArrayList<Song>>{
        artistsAdapter.dataset = it
        binding.recyclerviewArtistsLibrary.adapter = artistsAdapter
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerview = binding.recyclerviewArtistsLibrary
        recyclerview.apply {
            adapter = artistsAdapter
            layoutManager = LinearLayoutManager(context)
        }

    }

    override fun onItemClick(position: Int) {
        val artist = artistsAdapter.dataset[position]
        val action = artist.image?.let {
            ArtistsFragmentDirections.actionArtistsFragmentToArtistSongsFragment(artist.artist.toString(),
                it
            )
        }
        if (action != null) {
            findNavController().navigate(action)
        }
    }
}