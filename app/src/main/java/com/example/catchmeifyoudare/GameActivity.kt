package com.example.catchmeifyoudare

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.CountDownTimer
import android.widget.ImageView
import android.content.Intent
import android.media.MediaPlayer
import android.view.MotionEvent
import android.widget.TextView
import kotlin.random.Random

class GameActivity : AppCompatActivity() {

    private lateinit var countdownTimer: CountDownTimer
    private lateinit var countdownTextView: TextView
    private val COUNTDOWN_TIME: Long = 20000
    private lateinit var npc: ImageView
    private lateinit var handler: Handler
    private lateinit var runnable: Runnable
    private lateinit var scoreTextView: TextView
    private var score = 0
    private var mediaPlayer: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        // Initialize music player with music file
        mediaPlayer = MediaPlayer.create(this, R.raw.scoresound)

        // Get count down timer ID
        countdownTextView = findViewById(R.id.countDownTimer)

        // Get score text ID
        scoreTextView = findViewById(R.id.score)

        // Start the countdown timer when the game starts
        startCountdown()

        // Get npc ImageView ID
        npc = findViewById(R.id.npc)

        // Initialize Handler
        handler = Handler()

        // Initialize Runnable
        runnable = Runnable {

            // Set random X and Y positions for ImageView
            val randomX = Random.nextInt(0, resources.displayMetrics.widthPixels - npc.width)
            val randomY = Random.nextInt(0, resources.displayMetrics.heightPixels - npc.height)

            npc.x = randomX.toFloat()
            npc.y = randomY.toFloat()

            // Set ImageView visibility to VISIBLE
            npc.visibility = ImageView.VISIBLE

            // Schedule the next appearance after a random delay
            val nextAppearanceDelay = Random.nextLong(800, 1300)
            handler.postDelayed(runnable, nextAppearanceDelay)
        }

        // Start the game by posting the initial appearance of the NPC
        handler.post(runnable)

        // Set touch listener to making score
        npc.setOnTouchListener { _, event ->
            if(event.action == MotionEvent.ACTION_DOWN) {
                incrementScore()
                mediaPlayer?.start()
                true
            }else {
                false
            }
        }

    }

    private fun startCountdown() {
        countdownTimer = object : CountDownTimer(COUNTDOWN_TIME, 1000) { // 1000 ms = 1 second
            override fun onTick(millisUntilFinished: Long) {
                val secondsRemaining = millisUntilFinished / 1000
                updateCountdownText(secondsRemaining)
            }

            override fun onFinish() {
                stopGame()
            }
        }.start()
    }

    private fun updateCountdownText(secondsRemaining: Long) {
        countdownTextView.text = "$secondsRemaining"
    }

    private fun stopGame() {
        val intent = Intent(this, ScoreActivity::class.java)
        intent.putExtra("SCORE", score) // send score value to ScoreActivity
        startActivity(intent)
        finish()
    }

    private fun incrementScore() {
        score++
        scoreTextView.text = "$score"
    }

    override fun onDestroy() {
        super.onDestroy()
        countdownTimer.cancel()
        handler.removeCallbacks(runnable)
        mediaPlayer?.release()
    }
}