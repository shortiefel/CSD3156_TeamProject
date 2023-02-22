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

    override fun onCreate(savedInstanceState: Bundle?) {
        //force the client device to use light mode for this app
        //AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        AppConstants.initialization(this.applicationContext)
    }

    /**
     * Loading of gameView
     * */
    fun startGame(view: View) {
        /**
         * Playing of audio
         * */
        var mediaPlayer: MediaPlayer? = null
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
