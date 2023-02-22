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
 * Brief:        Responsible for running the game loop, which updates and draws
 *               the game elements on the canvas at a fixed rate.
 *
 * Copyright (C) 2023 DigiPen Institute of Technology.
 * Reproduction or disclosure of this file or its contents
 * without the prior written consent of DigiPen Institute of
 * Technology is prohibited.
 */


package com.example.catchthefruits

import android.os.SystemClock
import android.util.Log
import android.view.SurfaceHolder

class GameThread(surfaceHolder: SurfaceHolder, gameView: GameView) : Thread() {
    var surfaceHolder : SurfaceHolder

    var isRunning  = false

    var startTime = SystemClock.uptimeMillis()

    var loopTime = SystemClock.uptimeMillis()

    var DELAY = 33

    init{
        this.surfaceHolder = surfaceHolder
        isRunning = true
    }

    /**
     * Contains the main game loop, which executes while the thread is running.
     * */
    override fun run(){
        while(isRunning){
            startTime = SystemClock.uptimeMillis()
            val canvas = surfaceHolder.lockCanvas(null)
            if (canvas != null){
                synchronized(surfaceHolder){
                    AppConstants.gameEngine.updateAndDrawBackgroundImage(canvas)
                    AppConstants.gameEngine.updateAndDrawBasketImage(canvas)
                    AppConstants.gameEngine.updateAndDrawGroundImage(canvas)
                    AppConstants.gameEngine.updateAndDrawFruitImage(canvas)
                    AppConstants.gameEngine.updateAndDrawBombImage(canvas)
                    AppConstants.gameEngine.fruitCollision()
                    AppConstants.gameEngine.bombCollision()
                    AppConstants.gameEngine.updateExplosions(canvas)
                    AppConstants.gameEngine.updateUIs(canvas)
                    surfaceHolder.unlockCanvasAndPost(canvas)
                }
            }
            loopTime = SystemClock.uptimeMillis() - startTime
            if (loopTime < DELAY){
                try{
                    Thread.sleep(DELAY - loopTime)
                }catch (e : InterruptedException){
                    Log.e("Interrupted", "Interrupted while sleeping")
                }
            }
        }
    }
}