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
 * Brief:       This file contains the code needed for creation of fruits
 *
 * Copyright (C) 2023 DigiPen Institute of Technology.
 * Reproduction or disclosure of this file or its contents
 * without the prior written consent of DigiPen Institute of
 * Technology is prohibited.
 */


package com.example.catchthefruits

import android.content.Context
import android.graphics.BitmapFactory
import kotlin.random.Random

class Fruits(context: Context) {

    /**
     * Creation of a bitmap for fruits
     * */
    private val fruit = arrayOf(
        BitmapFactory.decodeResource(context.resources, R.drawable.fruit_1),
        BitmapFactory.decodeResource(context.resources, R.drawable.fruit_2),
        BitmapFactory.decodeResource(context.resources, R.drawable.fruit_3),
        BitmapFactory.decodeResource(context.resources, R.drawable.fruit_4),
        BitmapFactory.decodeResource(context.resources, R.drawable.fruit_5),
        BitmapFactory.decodeResource(context.resources, R.drawable.fruit_6),
        BitmapFactory.decodeResource(context.resources, R.drawable.fruit_7),
        BitmapFactory.decodeResource(context.resources, R.drawable.fruit_8)
    )

    /**
     * Declarations
     * **/
    var fruitFrame = 0
    var fruitX: Int = 0
    var fruitY: Int = 0
    var fruitVelocity: Int = 0
    var playedDroppingSound: Boolean = false
    private val random = Random

    init {
        resetPosition(0)
    }

    fun getfruit(frame: Int) = fruit[frame]

    /**
     * Getting width of image fruits
     * */
    fun getfruitWidth(frame: Int) = fruit[frame].width

    /**
     * Getting height of image fruits
     * */
    fun getfruitHeight(frame: Int) = fruit[frame].height

    /**
     * Resetting the explosion position
     * */
    fun resetPosition(frame: Int) {
        fruitX = random.nextInt(GameView.dWidth - getfruitWidth(frame))
        fruitY = -200 + random.nextInt(600) * -1
        fruitVelocity = 35 + random.nextInt(16)
        playedDroppingSound = false
    }
}
