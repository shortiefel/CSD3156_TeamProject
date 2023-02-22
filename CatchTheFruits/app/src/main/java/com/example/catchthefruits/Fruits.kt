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

import kotlin.random.Random

class Fruits {
    var fruitFrame = 0
    var fruitX: Int = 0
    var fruitY: Int = 0
    var fruitVelocity: Int = 0
    var playedDroppingSound: Boolean = false

    init {
        playedDroppingSound = false
        resetPosition(0)
    }

    fun resetPosition(frame: Int) {
        fruitX = Random.nextInt(AppConstants.SCREEN_WIDTH - AppConstants.bitmapBank.getFruitWidth(frame)!!)
        fruitY = -200 + Random.nextInt(600) * -1
        fruitVelocity = 35 + Random.nextInt(16)
    }
}
