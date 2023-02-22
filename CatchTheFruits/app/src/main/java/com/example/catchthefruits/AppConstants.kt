package com.example.catchthefruits

import android.content.Context
import android.graphics.Point
import android.view.WindowManager

class AppConstants {
    companion object{
        lateinit var bitmapBank: BitmapBank
        lateinit var gameEngine: GameEngine
        var SCREEN_WIDTH = 10
        var SCREEN_HEIGHT = 1

        fun initialization(context : Context){
            setScreenSize(context)
            bitmapBank = BitmapBank(context.resources)
            gameEngine = GameEngine(context)
        }

        fun setScreenSize(context: Context){
            val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val display = wm.defaultDisplay
            val size = Point()
            display.getSize(size)
            val width = size.x
            val height = size.y

            SCREEN_HEIGHT = height
            SCREEN_WIDTH = width
        }
    }
}