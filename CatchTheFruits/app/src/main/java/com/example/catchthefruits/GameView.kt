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
 * Brief:       This file contains the code needed for creation of gameview and how
 *              the game runs
 *
 * Copyright (C) 2023 DigiPen Institute of Technology.
 * Reproduction or disclosure of this file or its contents
 * without the prior written consent of DigiPen Institute of
 * Technology is prohibited.
 */



package com.example.catchthefruits

import android.content.Context
import android.util.Log
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView


class GameView(context: Context) : SurfaceView(context), SurfaceHolder.Callback {

    private var gameThread: GameThread? = null

    init{
        val holder =  getHolder()
        holder.addCallback(this)
        isFocusable = true
        gameThread = GameThread(holder, this)
    }

    override fun surfaceCreated(holder : SurfaceHolder){
        if (!gameThread!!.isRunning)
        {
            gameThread = GameThread(holder, this)
            gameThread?.start()
        }
        else gameThread?.start()
    }

    override fun surfaceChanged(p0: SurfaceHolder, p1: Int, p2: Int, p3: Int) {
        Log.d("Surface Changed", "Surfaced Changed Called Game Thread")
    }

    override fun surfaceDestroyed(p0: SurfaceHolder) {
        if (gameThread!!.isRunning){
            gameThread?.isRunning = false
            var retry = true
            while (retry){
                try{
                    gameThread?.join()
                    retry = false;
                }catch (e : InterruptedException){}
            }
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val touchX = event?.x
        val touchY = event?.y

        if (touchY!! >= AppConstants.gameEngine.basketImage.BasketImageY) {
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    AppConstants.gameEngine.basketImage.oldX = event.x
                    AppConstants.gameEngine.basketImage.oldBasketImageX = AppConstants.gameEngine.basketImage.BasketImageX
                }
                MotionEvent.ACTION_MOVE -> {
                    val shift = AppConstants.gameEngine.basketImage.oldX - touchX!!
                    val newBasketX = AppConstants.gameEngine.basketImage.oldBasketImageX - shift
                    AppConstants.gameEngine.basketImage.BasketImageX = if (newBasketX <= 0) {
                        AppConstants.gameEngine.basketImage.BasketImageX
                    } else (if (newBasketX >= AppConstants.SCREEN_WIDTH - AppConstants.bitmapBank.getBasketWidth()!!) {
                        AppConstants.SCREEN_WIDTH - AppConstants.bitmapBank.getBasketWidth()!!
                    } else {
                        newBasketX
                    }).toFloat().toInt()
                }
            }
        }
        return true
    }
}


