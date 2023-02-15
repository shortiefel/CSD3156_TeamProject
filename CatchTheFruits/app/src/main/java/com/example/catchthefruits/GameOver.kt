package com.example.catchthefruits

import android.content.Intent
import android.content.SharedPreferences
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
    lateinit var points: TextView;
    lateinit var highpoints: TextView;
    lateinit var newhighpointsIV: ImageView;
    lateinit var sharedPref: SharedPreferences;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gameover);

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

    fun restart(view: View){
        val i: Intent = Intent(this, MainActivity::class.java);
        startActivity(i);
        finish();
    }

    fun exit(view: View){
        finish();
    }
}