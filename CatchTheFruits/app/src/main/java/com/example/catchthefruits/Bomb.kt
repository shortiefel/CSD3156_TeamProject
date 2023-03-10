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
 * Brief:        Represents a Bomb in the game
 *
 * Copyright (C) 2023 DigiPen Institute of Technology.
 * Reproduction or disclosure of this file or its contents
 * without the prior written consent of DigiPen Institute of
 * Technology is prohibited.
 */



package com.example.catchthefruits

import kotlin.random.Random

class Bomb() {

    /**
     * currentFrame: An integer representing the current frame of the Bomb animation.
     * bombX:        An integer representing the x-coordinate of the Bomb's position on the screen.
     * bombY:        An integer representing the y-coordinate of the Bomb's position on the screen.
     * bombVelocity: An integer representing the velocity at which the Bomb is falling.
     * */
    var currentFrame = 0
    var bombX: Int = 0
    var bombY: Int = 0
    var bombVelocity: Int = 0

    init {
        resetPosition()
    }

    /**
     * Resets the position and velocity of the Bomb to a random location at the top of the screen.
     * */
    fun resetPosition() {
        bombX = Random.nextInt(AppConstants.SCREEN_WIDTH - AppConstants.bitmapBank.getBombWidth()!!)
        bombY = -200 + Random.nextInt(600) * -1
        bombVelocity = 35 + Random.nextInt(16)
        currentFrame = 0
    }
}
