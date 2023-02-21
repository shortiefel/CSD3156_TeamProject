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
 * Brief:       This file contains the code needed for creation of gameover
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
import android.opengl.Visibility
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


/**
 * When player dies
 * */
class GameOver : AppCompatActivity() {

    /**
     * Declarations
     * **/
    lateinit var points: TextView;
    lateinit var highpoints: TextView;
    lateinit var newhighpointsIV: ImageView;
    lateinit var sharedPref: SharedPreferences;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gameover);
        PlaySound(R.raw.lose)
        points = findViewById<TextView>(R.id.points);
        highpoints = findViewById<TextView>(R.id.highpoints);
        newhighpointsIV = findViewById<ImageView>(R.id.imgViewNewHigh);

        val thisscore: Int? = intent.extras?.getInt("points");
        if(thisscore != null){
            points.setText(""+thisscore.toString());
            sharedPref = getSharedPreferences("mypref", 0);
            var highest: Int = sharedPref.getInt("highest", 0);
            if(thisscore > highest){
                newhighpointsIV.visibility = View.VISIBLE;
                highest = thisscore;
                var updatePref = sharedPref.edit();
                updatePref.putInt("highest", highest);
                updatePref.commit();
            }
            highpoints.setText(""+ highest.toString());
        }
    }

    /**
     * Restarting game
     * */
    fun restart(view: View){
        PlaySound(R.raw.buttonclick)
        val i: Intent = Intent(this, MainActivity::class.java);
        startActivity(i);
        finish();
    }

    /**
     * Exiting game
     * */
    fun exit(view: View){
        PlaySound(R.raw.buttonclick)
        finish();
    }

    /**
     * Playing of audio
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