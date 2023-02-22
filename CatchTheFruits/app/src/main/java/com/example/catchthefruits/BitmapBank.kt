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
 * Brief:        Centralizes the loading and management of all the images used in the game
 *               , making it easier for other parts of the game to access and use them.
 *
 * Copyright (C) 2023 DigiPen Institute of Technology.
 * Reproduction or disclosure of this file or its contents
 * without the prior written consent of DigiPen Institute of
 * Technology is prohibited.
 */


package com.example.catchthefruits

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import kotlin.math.exp

class BitmapBank(res : Resources) {

    /**
     * background:  stores the background image of the game
     * ground:      stores the ground image of the game
     * basket:      stores the image of the basket used to catch the fruits
     * fruits:      stores an array of images for the fruits that fall from the sky
     * bomb:        stores an array of images for the bombs that fall from the sky.
     * explosion:   stores an array of images for the explosion animation that
     *              occurs when a bomb hits the ground.
     * */
    var background : Bitmap
    var ground : Bitmap
    var basket : Bitmap
    var fruits : Array<Bitmap?> = Array(8) { null }
    val bomb: Array<Bitmap?> = Array(3) { null }
    val explosion: Array<Bitmap?> = Array(4) { null }


    init{
        background = BitmapFactory.decodeResource(res, R.drawable.game_bg)
        ground = BitmapFactory.decodeResource(res, R.drawable.game_ground)
        basket = BitmapFactory.decodeResource(res, R.drawable.fruit_basket)

        fruits[0] = BitmapFactory.decodeResource(res, R.drawable.fruit_1)
        fruits[1] = BitmapFactory.decodeResource(res, R.drawable.fruit_2)
        fruits[2] = BitmapFactory.decodeResource(res, R.drawable.fruit_3)
        fruits[3] = BitmapFactory.decodeResource(res, R.drawable.fruit_4)
        fruits[4] = BitmapFactory.decodeResource(res, R.drawable.fruit_5)
        fruits[5] = BitmapFactory.decodeResource(res, R.drawable.fruit_6)
        fruits[6] = BitmapFactory.decodeResource(res, R.drawable.fruit_7)
        fruits[7] = BitmapFactory.decodeResource(res, R.drawable.fruit_8)

        bomb[0] = BitmapFactory.decodeResource(res, R.drawable.bomb_0)
        bomb[1] = BitmapFactory.decodeResource(res, R.drawable.bomb_1)
        bomb[2] = BitmapFactory.decodeResource(res, R.drawable.bomb_2)

        explosion[0] = BitmapFactory.decodeResource(res, R.drawable.explode_0)
        explosion[1] = BitmapFactory.decodeResource(res, R.drawable.explode_1)
        explosion[2] = BitmapFactory.decodeResource(res, R.drawable.explode_2)
        explosion[3] = BitmapFactory.decodeResource(res, R.drawable.explode_3)

        background = scaleImage(background)
    }

    /**
     * Returns the width of the ground image.
     * */
    fun getGroundWidth() : Int? {
        return ground.width
    }

    /**
     * Returns the height of the ground image.
     * */
    fun getGroundHeight() : Int? {
        return ground.height
    }

    /**
     * Returns the width of the basket image.
     * */
    fun getBasketWidth() : Int? {
        return basket.width
    }

    /**
     * Returns the height of the basket image.
     * */
    fun getBasketHeight() : Int? {
        return basket.height
    }

    /**
     * Returns the explosion image at the specified frame index.
     * */
    fun getExplosions(frame : Int) : Bitmap? {
        return explosion[frame]
    }

    /**
     * Returns the fruit image at the specified frame index.
     * */
    fun getFruit(frame : Int) : Bitmap? {
        return fruits[frame]
    }

    /**
     * Returns the width of the fruit image at the specified frame index.
     * */
    fun getFruitWidth(frame : Int) : Int? {
        return fruits[frame]?.width
    }

    /**
     * Returns the height of the fruit image at the specified frame index.
     * */
    fun getFruitHeight(frame : Int) : Int? {
        return fruits[frame]?.height
    }

    /**
     * Returns the bomb image at the specified frame index.
     * */
    fun getBomb(frame : Int) : Bitmap? {
        return bomb[frame]
    }

    /**
     * Returns the width of the bomb image.
     * */
    fun getBombWidth() : Int? {
        return bomb[0]?.width
    }

    /**
     * Returns the height of the bomb image.
     * */
    fun getBombHeight() : Int? {
        return bomb[0]?.height
    }

    /**
     * Returns the width of the background image.
     * */
    fun getBackgroundWidth() : Int {
        return background.width
    }

    /**
     * Returns the height of the background image.
     * */
    fun getBackgroundHeight() : Int {
        return background.height
    }

    /**
     * Scales the input bitmap to the size of the screen and returns the scaled bitmap.
     * */
    fun scaleImage(bitmap: Bitmap) : Bitmap{
        return Bitmap.createScaledBitmap(bitmap, AppConstants.SCREEN_WIDTH, AppConstants.SCREEN_HEIGHT, false)
    }
}