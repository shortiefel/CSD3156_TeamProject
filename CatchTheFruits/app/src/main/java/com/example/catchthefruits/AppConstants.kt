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
 * Brief:       Contains static properties and methods.
 *              It serves as a utility class for storing constants and initializing game resources.
 *
 * Copyright (C) 2023 DigiPen Institute of Technology.
 * Reproduction or disclosure of this file or its contents
 * without the prior written consent of DigiPen Institute of
 * Technology is prohibited.
 */




package com.example.catchthefruits

import android.content.Context
import android.graphics.Point
import android.view.WindowManager

class AppConstants {

    /**
     * Create a singleton object
     * Object can be accessed globally
     * */
    companion object{
        lateinit var bitmapBank: BitmapBank
        lateinit var gameEngine: GameEngine
        lateinit var sensorEngine: SensorEngine
        var SCREEN_WIDTH = 10
        var SCREEN_HEIGHT = 1


        /**
         * For initializing
         * */
        fun initialization(context : Context){
            setScreenSize(context)
            bitmapBank = BitmapBank(context.resources)
            gameEngine = GameEngine(context)
            sensorEngine = SensorEngine(context)
        }

        /**
         * For screen size
         * */
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