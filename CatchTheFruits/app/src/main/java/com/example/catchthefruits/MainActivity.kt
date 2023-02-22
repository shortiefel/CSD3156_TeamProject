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
 * Brief:        Plays background music and a sound effect when the user clicks a button to start the game.
 *
 * Copyright (C) 2023 DigiPen Institute of Technology.
 * Reproduction or disclosure of this file or its contents
 * without the prior written consent of DigiPen Institute of
 * Technology is prohibited.
 */

package com.example.catchthefruits

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.Switch
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    private var bgmPlayer: MediaPlayer? = null
    private var playButtonPlayer: MediaPlayer?  = null
    private var mediaPlayer: MediaPlayer? = null

    /**
     * Initializes AppConstants and starts playing the background music with a MediaPlayer object.
     * */
    override fun onCreate(savedInstanceState: Bundle?) {
        //force the client device to use light mode for this app
        //AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        AppConstants.initialization(this.applicationContext)

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


        val playButton : ImageButton = findViewById(R.id.playbtn)
        val hsButton : ImageButton = findViewById(R.id.highscoreBtn)
        val toggleSwitch : Switch = findViewById<Switch>(R.id.gyroToggle)
        playButton.setOnClickListener {
            if (playButtonPlayer == null) {

                playButtonPlayer = MediaPlayer.create(this, R.raw.buttonclick)
                playButtonPlayer?.setOnCompletionListener {
                    playButtonPlayer?.stop()
                    playButtonPlayer?.reset()
                    playButtonPlayer?.release()
                }
                playButtonPlayer?.start()
            }
            if (bgmPlayer != null)
            {
                bgmPlayer?.stop()
                bgmPlayer?.reset()
                bgmPlayer?.release()
            }
            //temp crash fix...is this supposed to be here? -YQ
            //bgmPlayer?.start()
            startGame(toggleSwitch.isChecked)
        }

        hsButton.setOnClickListener{
            val intent = Intent(this, HighscoreActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    /**
     * Responsible for launching the GameActivity.
     * */
    fun startGame(checked: Boolean) {

        if (mediaPlayer == null) {

            mediaPlayer = MediaPlayer.create(this, R.raw.buttonclick)
            mediaPlayer?.setOnCompletionListener {
                mediaPlayer?.stop()
                mediaPlayer?.reset()
                mediaPlayer?.release()
            }
            mediaPlayer?.start()
        }
        val intent = Intent(this, GameActivity::class.java)
        intent.putExtra("toggle", checked)
        startActivity(intent)
        finish()
    }

    fun exit(view: View){
        bgmPlayer?.stop()
        bgmPlayer?.reset()
        bgmPlayer?.release()
        finish()
    }

}
