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
 * Brief:        Represents the health status of the player in the game.
 *
 * Copyright (C) 2023 DigiPen Institute of Technology.
 * Reproduction or disclosure of this file or its contents
 * without the prior written consent of DigiPen Institute of
 * Technology is prohibited.
 */


package com.example.catchthefruits

import android.graphics.Color
import android.graphics.Paint

class HealthUI {

    /**
     * life:        an integer representing the current number of lives the player has.
     * healthPaint: a Paint object that is used to draw the health bar on the screen.
     * textPaint:   a Paint object that is used to draw the text that displays the
     *              current number of lives.
     * TEXT_SIZE:   a constant float value that determines the font size of the text.
     * */
    var life : Int
    var healthPaint : Paint
    val textPaint : Paint
    val TEXT_SIZE = 120f

    init{
        life = 3
        healthPaint = Paint().apply { color = android.graphics.Color.GREEN }
        textPaint = Paint().apply {
            color = Color.rgb(255, 165, 0)
            textSize = TEXT_SIZE
            textAlign = Paint.Align.LEFT
        }
    }
}