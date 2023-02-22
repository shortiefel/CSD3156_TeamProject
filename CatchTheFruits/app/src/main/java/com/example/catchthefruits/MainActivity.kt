package com.example.catchthefruits

import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate


class MainActivity : AppCompatActivity() {

    private var bgmPlayer: MediaPlayer? = null
    private var playButtonPlayer: MediaPlayer?  = null

    override fun onCreate(savedInstanceState: Bundle?) {
        //force the client device to use light mode for this app
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        //play bgm
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
            startGame(GameView(this))
        }
    }

    /**
     * Loading of gameView
     * */
    fun startGame(view: View) {
//        val gameView = GameView(this)
        setContentView(view)
    }

}
