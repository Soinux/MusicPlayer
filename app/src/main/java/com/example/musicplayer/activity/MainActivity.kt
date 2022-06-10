package com.example.musicplayer.activity

import android.Manifest
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.musicplayer.R
import com.example.musicplayer.databinding.ActivityMainBinding
import com.example.musicplayer.fragments.*
import com.example.musicplayer.helper.Coordinator
import com.example.musicplayer.model.Song
import com.example.musicplayer.repository.RoomRepository
import com.example.musicplayer.utils.ImageUtils
import com.example.musicplayer.utils.PermissionProvider

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    companion object {
        var permissionsGranted: Boolean = false
        lateinit var activity: MainActivity

    }



    var prefs: SharedPreferences? = null

    fun updateVisibility(song : Song) {
        if(Coordinator.isPlaying()){
            binding.btnPlay.visibility = View.GONE
            binding.btnPause.visibility = View.VISIBLE
        }
        else{
            binding.btnPlay.visibility = View.VISIBLE
            binding.btnPause.visibility = View.GONE
        }
        binding.layoutOnCollapsed.visibility = View.VISIBLE
        binding.txtArtistOnHeader.text = song.artist
        binding.txtTitleOnHeader.text = song.title
        song.image?.let {
            ImageUtils.loadImageToImageView(
                context = applicationContext,
                imageView = binding.imgOnHeader,
                image = it
            )
        }
    }

    private val permissions = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE
    )

    private fun layoutCollapsedListener(){
        binding.layoutOnCollapsed.setOnClickListener {
            val intent = Intent(this, PlayerPanelActivity::class.java)
            startActivity(intent)

        }
        binding.playPauseLayout.setOnClickListener {
            if (Coordinator.isPlaying()) {
                Coordinator.pause()
            } else {
                Coordinator.resume()
            }
            switchPlayPauseButton()
        }
    }

    private fun switchPlayPauseButton() {
        if (Coordinator.isPlaying()) {
            binding.btnPlay.visibility = View.GONE
            binding.btnPause.visibility = View.VISIBLE
        } else {
            binding.btnPlay.visibility = View.VISIBLE
            binding.btnPause.visibility = View.GONE
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity = this

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        Coordinator.setup(baseContext) // set up media player
        RoomRepository.createDatabase()

//        Coordinator.setup(baseContext) // set up


        supportActionBar?.hide()

        // set up navigation
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment
        binding.navView.setupWithNavController(navHostFragment.navController)
        checkForPermissions()
        layoutCollapsedListener()


    }

    private fun checkForPermissions() {
        val permissionProvider = PermissionProvider()
        permissionProvider.askForPermission(this, permissions)

        if (permissionProvider.permissionIsGranted) {

        }
    }

    override fun onResume() {
        super.onResume()
        Coordinator.currentPlayingSong?.let { updateVisibility(it) }
    }

    override fun onDestroy() {
        super.onDestroy()
        Coordinator.mediaPlayerAgent.stop()
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}