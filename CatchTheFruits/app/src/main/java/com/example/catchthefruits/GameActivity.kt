package com.example.catchthefruits

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorManager
import android.os.Bundle

class GameActivity : Activity() {
    lateinit var gameView : GameView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppConstants.sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        AppConstants.magfieldSensor = AppConstants.sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)
        AppConstants.acceleSensor = AppConstants.sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        gameView = GameView(this)
        setContentView(gameView)

        AppConstants.gameEngine.setOnGameOverListener {
            showGameOverScreen()
        }
    }

    private fun showGameOverScreen() {
        val intent = Intent(this@GameActivity, GameOver::class.java)
        intent.putExtra("points", AppConstants.gameEngine.points)
        startActivity(intent)
        finish()
    }
}