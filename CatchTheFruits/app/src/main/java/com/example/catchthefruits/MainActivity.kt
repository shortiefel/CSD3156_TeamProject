package com.example.catchthefruits

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorManager
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        //force the client device to use light mode for this app
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        val sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val magSensor: Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)
        val accSensor: Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

        val playButton : Button = findViewById(R.id.playbtn)
        playButton.setOnClickListener {
            startGame(GameView(this, magSensor, accSensor, sensorManager))
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
