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
 * Brief:       This file contains the code needed main activity 
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
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    private var bgmPlayer: MediaPlayer? = null
    private var playButtonPlayer: MediaPlayer?  = null

    override fun onCreate(savedInstanceState: Bundle?) {
        //force the client device to use light mode for this app
        //AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        AppConstants.initialization(this.applicationContext)

        /**
         * Playing of audio
         * */
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


        val playButton : Button = findViewById(R.id.playbtn)
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
            bgmPlayer?.start()
        }
    }

    /**
     * Loading of gameView
     * */
    fun startGame(view: View) {
        /**
         * Playing of audio
         * */
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
        startActivity(intent)
        finish()
    }

}
