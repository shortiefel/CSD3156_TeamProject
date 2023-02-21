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
 * Brief:       This file contains the code needed for creation of bombs
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

class Bomb(context: Context) {

    /**
     * Creation of a bitmap for bombs
     * */
    private val bomb = arrayOf(
        BitmapFactory.decodeResource(context.resources, R.drawable.bomb_0),
        BitmapFactory.decodeResource(context.resources, R.drawable.bomb_1),
        BitmapFactory.decodeResource(context.resources, R.drawable.bomb_2)
    )

    /**
     * Declarations
     * **/
    var bombFrame = 0
    var bombX: Int = 0
    var bombY: Int = 0
    var bombVelocity: Int = 0
    private val random = Random

    init {
        resetPosition()
    }

    fun getBomb(frame: Int) = bomb[frame]

    /**
     * Getting width of image bomb
     * */
    fun getBombWidth() = bomb[0].width

    /**
     * Getting height of image bomb
     * */
    fun getBombHeight() = bomb[0].height

    /**
     * Resetting the bomb position
     * */
    fun resetPosition() {
        bombX = random.nextInt(GameView.dWidth - getBombWidth())
        bombY = -200 + random.nextInt(600) * -1
        bombVelocity = 35 + random.nextInt(16)
    }
}
