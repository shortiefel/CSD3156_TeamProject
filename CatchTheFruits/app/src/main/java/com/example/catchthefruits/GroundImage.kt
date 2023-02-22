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
 * Brief:        Define the rectangle area for the ground image in the game.
 *
 * Copyright (C) 2023 DigiPen Institute of Technology.
 * Reproduction or disclosure of this file or its contents
 * without the prior written consent of DigiPen Institute of
 * Technology is prohibited.
 */


package com.example.catchthefruits

import android.graphics.Rect

class GroundImage {
    var rectGround: Rect

    /**
     * Ground image will be positioned at the bottom of the screen
     * */
    constructor(){
        rectGround = Rect(0, AppConstants.SCREEN_HEIGHT - AppConstants.bitmapBank.getGroundHeight()!!,
            AppConstants.SCREEN_WIDTH, AppConstants.SCREEN_HEIGHT)
    }
}