/***
 * Names:        Lee Yu Ting
 *               Lim Yi Qin
 *               Loh Yun Xuan
 *               Tan Wei Ling Felicia
 *               Woon Ting Ting
 * Student IDs:  2002892
 *               2000804
 *               2001533
 *               2001339
 *               2002323
 * Brief:        Extends the AppCompatActivity class and is responsible for
 *               displaying the high score achieved in the game.
 *
 * Copyright (C) 2023 DigiPen Institute of Technology.
 * Reproduction or disclosure of this file or its contents
 * without the prior written consent of DigiPen Institute of
 * Technology is prohibited.
 */


package com.example.catchthefruits

import android.content.Intent
import android.content.SharedPreferences
import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class HighscoreActivity : AppCompatActivity() {
    lateinit var points: TextView
    lateinit var highpoints: TextView
    lateinit var newhighpointsIV: ImageView
    lateinit var sharedPref: SharedPreferences
    private var backButtonPlayer: MediaPlayer?  = null
    private var bgmPlayer: MediaPlayer?  = null
    /**
     * TextView for displaying the player's current score,
     * another TextView for displaying the highest score achieved in previous games,
     * and an ImageView that displays a "new high score" icon
     * */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_highscore)

        highpoints = findViewById<TextView>(R.id.highpoints)
        val backButton: ImageButton = findViewById<ImageButton>(R.id.backBtn)
        sharedPref = getSharedPreferences("mypref", 0)
        var highest: Int = sharedPref.getInt("highest", 0)
        highpoints.setText(""+ highest.toString())

        backButton.setOnClickListener{
            if (backButtonPlayer == null) {

                backButtonPlayer = MediaPlayer.create(this, R.raw.buttonclick)
                backButtonPlayer?.setOnCompletionListener {
                    backButtonPlayer?.stop()
                    backButtonPlayer?.reset()
                    backButtonPlayer?.release()
                }
                backButtonPlayer?.start()
            }
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun playBgm(){
        if (bgmPlayer == null)
        {
            bgmPlayer = MediaPlayer.create(this, R.raw.bgm)
            bgmPlayer?.setOnCompletionListener {
                if (!bgmPlayer!!.isPlaying)
                {
                    bgmPlayer?.start()
                }
            }
            bgmPlayer?.start()
        }
    }

    private fun stopBgm() {
        if (bgmPlayer != null)
        {
            bgmPlayer?.stop()
            bgmPlayer?.reset()
            bgmPlayer?.release()
            bgmPlayer = null
        }
    }

    override fun onPause() {
        super.onPause()
        stopBgm()
    }

    override fun onResume(){
        super.onResume()
        playBgm()
    }

}