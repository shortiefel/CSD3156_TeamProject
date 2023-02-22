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
 * Brief:        Displayed when the game is over and it shows the points achieved by the player
 *               in the current game and the highest points achieved in previous games.
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
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class GameOver : AppCompatActivity() {
    lateinit var points: TextView
    lateinit var highpoints: TextView
    lateinit var newhighpointsIV: ImageView
    lateinit var sharedPref: SharedPreferences

    /**
     * When the activity is created. It sets the layout for the activity and initializes some
     * variables and views. It also plays a sound using the PlaySound() function when the game
     * is lost.
     * */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gameover)
        PlaySound(R.raw.lose)

        points = findViewById<TextView>(R.id.points)
        highpoints = findViewById<TextView>(R.id.highpoints)
        newhighpointsIV = findViewById<ImageView>(R.id.imgViewNewHigh)

        val thisscore: Int? = intent.extras?.getInt("points")
        if(thisscore != null){
            points.setText(""+thisscore.toString())
            sharedPref = getSharedPreferences("mypref", 0)
            var highest: Int = sharedPref.getInt("highest", 0)
            if(thisscore > highest){
                newhighpointsIV.visibility = View.VISIBLE
                highest = thisscore
                var updatePref = sharedPref.edit()
                updatePref.putInt("highest", highest)
                updatePref.commit()
            }
            highpoints.setText(""+ highest.toString())
        }
    }

    /**
     * When the "Restart" button is clicked. It plays a sound using the PlaySound() function,
     * creates an intent to start the MainActivity and starts the activity. The current activity
     * is finished using the finish() function.
     * */
    fun restart(view: View){
        PlaySound(R.raw.buttonclick)
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    /**
     *  When the "Exit" button is clicked. It plays a sound using the PlaySound() function and
     *  finishes the current activity using the finish() function.
     * */
    fun exit(view: View){
        PlaySound(R.raw.buttonclick)
        finish()
    }

    /**
     * Used to play sounds when buttons are clicked or the game is lost. It creates a MediaPlayer
     * object, sets the audio resource using the create() function, sets a completion listener to
     * release the MediaPlayer object when the audio is finished playing, and starts the audio
     * using the start() function.
     * */
    fun PlaySound(resourceID : Int){
        var mediaPlayer: MediaPlayer? = null
        if (mediaPlayer == null) {

            mediaPlayer = MediaPlayer.create(this, resourceID)
            mediaPlayer?.setOnCompletionListener {
                mediaPlayer?.stop()
                mediaPlayer?.reset()
                mediaPlayer?.release()
            }
            mediaPlayer?.start()
        }
    }
}