package com.example.catchmeifyoudare

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView

class ScoreActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_score)

        // Get score value from GameActivity
        val score = intent.getIntExtra("SCORE", 0)

        // Display score
        var scoreValue: TextView = findViewById(R.id.scoreValue)
        scoreValue.text = "$score"

        // Set onClickListener to direct GameActivity
        val playAgainBtn: ImageView = findViewById(R.id.playAgainButton)
        playAgainBtn.setOnClickListener {
            val intent = Intent(this, GameActivity::class.java)
            startActivity(intent)
        }
    }
}