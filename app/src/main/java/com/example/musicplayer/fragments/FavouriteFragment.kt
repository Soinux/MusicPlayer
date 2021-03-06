package com.example.musicplayer.fragments

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.musicplayer.databinding.FragmentFavouriteBinding
import com.example.musicplayer.adapter.FavAdapter
import com.example.musicplayer.viewmodel.FavViewModel
import com.example.musicplayer.model.Song

class FavouriteFragment : Fragment() {
    lateinit var binding: FragmentFavouriteBinding

    companion object {
        lateinit var viewModel: FavViewModel
        lateinit var favSongsAdapter: FavAdapter

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFavouriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this).get(FavViewModel::class.java)

        context?.let { viewModel.setDataToFragment() }
        viewModel.updateData()
        viewModel!!.dataset.observe(viewLifecycleOwner, favSongsObserver)
        favSongsAdapter = activity?.let {
            viewModel.dataset.value?.let { it1 ->
                FavAdapter(
                    it1 as ArrayList<Song>,
                    it
                )
            }
        }!!

        val mHandler = Handler()
        activity?.runOnUiThread(object : Runnable{
            override fun run() {
                viewModel?.updateData()
                mHandler.postDelayed(this,1000)
            }
        })

        val recyclerView = binding.recyclerviewFavourite
        recyclerView.apply {
            adapter = favSongsAdapter
            layoutManager = LinearLayoutManager(context)
        }


    }
    private val favSongsObserver = Observer<ArrayList<Any>> {
        favSongsAdapter?.listSong = it as ArrayList<Song>
        binding.recyclerviewFavourite.adapter = favSongsAdapter
    }

}