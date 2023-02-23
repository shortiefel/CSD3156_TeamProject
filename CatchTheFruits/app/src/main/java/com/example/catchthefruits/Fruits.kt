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
 * Brief:        Responsible for generating and updating the falling fruits' position and velocity.
 *
 * Copyright (C) 2023 DigiPen Institute of Technology.
 * Reproduction or disclosure of this file or its contents
 * without the prior written consent of DigiPen Institute of
 * Technology is prohibited.
 */

package com.example.catchthefruits

import kotlin.random.Random

class Fruits {

    /**
     * fruitFrame:          the current frame of the fruit animation.
     * fruitX:              the x-coordinate of the fruit's position on the screen.
     * fruitY:              the y-coordinate of the fruit's position on the screen.
     * fruitVelocity:       the speed at which the fruit falls.
     * playedDroppingSound: a Boolean flag indicating whether the sound of
     *                      the fruit dropping has been played.
     * */
    var fruitFrame = 0
    var fruitX: Int = 0
    var fruitY: Int = 0
    var fruitVelocity: Int = 0
    var playedDroppingSound: Boolean = false

    init {
        playedDroppingSound = false
        resetPosition(0)
    }

    /**
     *  Resets the position and velocity
     * */
    fun resetPosition(frame: Int) {
        fruitX = Random.nextInt(AppConstants.SCREEN_WIDTH - AppConstants.bitmapBank.getFruitWidth(frame)!!)
        fruitY = -200 + Random.nextInt(600) * -1
        fruitVelocity = 35 + Random.nextInt(16)
        playedDroppingSound = false
    }
}
