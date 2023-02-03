package com.example.catchthefruits

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /**
         * OnClick Listener
         * */
        val playBtn = findViewById<Button>(R.id.playbtn)
        playBtn.setOnClickListener()
        {
            val toGame = Intent(this, GameActivity::class.java)
            startActivity(toGame)
        }

    }
}