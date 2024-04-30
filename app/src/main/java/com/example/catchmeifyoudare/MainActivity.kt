package com.example.catchmeifyoudare

import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView

class MainActivity : AppCompatActivity() {
    private var mediaPlayer: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Add background music
        mediaPlayer = MediaPlayer.create(this, R.raw.backgroundmusic)
        mediaPlayer?.start()

        // Simple logo animation
        val mainLogo: ImageView = findViewById(R.id.main_logo)
        mainLogo.animate().apply {
            duration = 1500
            rotationXBy(360f)
        }

        // Set onClickListener to direct GameActivity
        val startButton: ImageView = findViewById(R.id.start_btn)
        startButton.setOnClickListener {
            val intent = Intent(this, GameActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release()
        mediaPlayer = null
    }
}