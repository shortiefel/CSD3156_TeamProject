package com.example.catchthefruits

import android.app.Activity
import android.content.Intent
import android.os.Bundle

class GameActivity : Activity() {
    lateinit var gameView : GameView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        gameView = GameView(this, intent.extras?.getBoolean("toggle"))
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