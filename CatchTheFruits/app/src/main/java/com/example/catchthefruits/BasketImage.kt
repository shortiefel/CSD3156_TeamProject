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
 * Brief:        Represents the visual element of the basket in the game
 *               and is responsible for updating its position on the screen.
 *
 * Copyright (C) 2023 DigiPen Institute of Technology.
 * Reproduction or disclosure of this file or its contents
 * without the prior written consent of DigiPen Institute of
 * Technology is prohibited.
 */

package com.example.catchthefruits

class BasketImage {

    /**
     * oldBasketImageX and oldBasketImageY: store the previous position of the basket image
     *                                      before it is updated.
     * oldX:                                stores the previous x-position of the basket image
     *                                      before it is updated.
     * BasketImageX and BasketImageY:       store the current position of the basket image.
     * */
    var oldBasketImageX : Int
    var oldBasketImageY : Int

    var oldX = 0f

    var BasketImageX : Int
    var BasketImageY : Int

    /**
     * Initializes the position of the basket image
     * based on the screen dimensions and the dimensions of the basket
     * */
    constructor(){
        BasketImageX = (AppConstants.SCREEN_WIDTH / 2 - AppConstants.bitmapBank.getBombWidth()!! / 2)
        oldBasketImageX = (AppConstants.SCREEN_WIDTH / 2 - AppConstants.bitmapBank.getBombWidth()!! / 2)
        BasketImageY = (AppConstants.SCREEN_HEIGHT - AppConstants.bitmapBank.getGroundHeight()!! - AppConstants.bitmapBank.getBasketHeight()!!)
        oldBasketImageY = (AppConstants.SCREEN_HEIGHT - AppConstants.bitmapBank.getGroundHeight()!! - AppConstants.bitmapBank.getBasketHeight()!!)
    }
}