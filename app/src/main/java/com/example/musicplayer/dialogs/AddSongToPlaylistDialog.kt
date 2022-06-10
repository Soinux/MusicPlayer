package com.example.musicplayer.dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.musicplayer.R
import com.example.musicplayer.databinding.AddSongsToPlaylistsBinding
import com.example.musicplayer.adapter.AddSongToPlaylistAdapter
import com.example.musicplayer.appInterface.PassDataForSelectSong
import com.example.musicplayer.utils.ScreenSizeUtils
import com.example.musicplayer.model.Song
import kotlinx.android.synthetic.main.add_songs_to_playlists.view.*

class AddSongToPlaylistDialog(val array: ArrayList<Song>) : DialogFragment(){

    lateinit var binding: AddSongsToPlaylistsBinding
    var playlistAdapter: AddSongToPlaylistAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = AddSongsToPlaylistsBinding.inflate(inflater, container, false)

        dialog!!.window?.setBackgroundDrawableResource(R.drawable.round_corner_bg);
        playlistAdapter = activity?.let {
            AddSongToPlaylistAdapter(
                it,
                array
            )
        }

        binding.recyclerviewAddSongsDialog.layoutManager = LinearLayoutManager(context)
        binding.recyclerviewAddSongsDialog.adapter = playlistAdapter

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.addSongToPlaylistLayout.layoutParams.width =
            ScreenSizeUtils.getScreenWidth() * 6 / 10
        binding.addSongToPlaylistLayout.layoutParams.height =
            ScreenSizeUtils.getScreenHeight() * 6 / 10
        binding.addSongToPlaylistLayout.requestLayout()

        binding.addSongToPlaylistLayout.recyclerview_add_songs_dialog.layoutParams.width =
            binding.addSongToPlaylistLayout.layoutParams.width * 10 / 10
        binding.addSongToPlaylistLayout.recyclerview_add_songs_dialog.layoutParams.height =
            (binding.addSongToPlaylistLayout.layoutParams.height* 6.5 / 10).toInt()
        binding.addSongToPlaylistLayout.recyclerview_add_songs_dialog.requestLayout()

        binding.addSongToPlaylistLayout.btn_ok_add_songs_dialog.layoutParams.width =
            binding.addSongToPlaylistLayout.layoutParams.width * 4 / 10
        binding.addSongToPlaylistLayout.btn_ok_add_songs_dialog.layoutParams.height =
            (binding.addSongToPlaylistLayout.layoutParams.height* 1.2 / 10).toInt()
        binding.addSongToPlaylistLayout.btn_ok_add_songs_dialog.requestLayout()

    }

    override fun onResume() {

        super.onResume()

        binding.btnOkAddSongsDialog.setOnClickListener {

            val targetFragment = targetFragment
            val passData: PassDataForSelectSong = targetFragment as PassDataForSelectSong
            passData.passDataToInvokingFragment(AddSongToPlaylistAdapter.choices)

            this.dismiss()
        }
    }

}