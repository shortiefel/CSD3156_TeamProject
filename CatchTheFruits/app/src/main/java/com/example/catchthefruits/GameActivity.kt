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
 * Brief:        Class that extends the Activity class.
 *               It creates a GameView instance and sets it as the content view of the activity.
 *
 * Copyright (C) 2023 DigiPen Institute of Technology.
 * Reproduction or disclosure of this file or its contents
 * without the prior written consent of DigiPen Institute of
 * Technology is prohibited.
 */

package com.example.catchthefruits

import android.app.Activity
import android.content.Intent
import android.os.Bundle

class GameActivity : Activity() {
    lateinit var gameView : GameView
    /*
    Creates the GameView class and sets the content view to GameView
    Also sets a listener to show game over screen
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        gameView = GameView(this, intent.extras?.getBoolean("toggle"))
        setContentView(gameView)

        AppConstants.gameEngine.setOnGameOverListener {
            showGameOverScreen()
        }
    }

    /*
    Switches and shows the Game Over Screen
     */
    private fun showGameOverScreen() {
        val intent = Intent(this@GameActivity, GameOver::class.java)
        intent.putExtra("points", AppConstants.gameEngine.points)
        startActivity(intent)
        finish()
    }
}