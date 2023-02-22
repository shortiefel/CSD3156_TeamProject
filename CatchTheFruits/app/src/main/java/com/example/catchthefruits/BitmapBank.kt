package com.example.catchthefruits

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import kotlin.math.exp

class BitmapBank(res : Resources) {
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

    fun getGroundWidth() : Int? {
        return ground.width
    }

    fun getGroundHeight() : Int? {
        return ground.height
    }

    fun getBasketWidth() : Int? {
        return basket.width
    }

    fun getBasketHeight() : Int? {
        return basket.height
    }

    fun getExplosions(frame : Int) : Bitmap? {
        return explosion[frame]
    }

    fun getFruit(frame : Int) : Bitmap? {
        return fruits[frame]
    }

    fun getFruitWidth(frame : Int) : Int? {
        return fruits[frame]?.width
    }

    fun getFruitHeight(frame : Int) : Int? {
        return fruits[frame]?.height
    }

    fun getBomb(frame : Int) : Bitmap? {
        return bomb[frame]
    }

    fun getBombWidth() : Int? {
        return bomb[0]?.width
    }

    fun getBombHeight() : Int? {
        return bomb[0]?.height
    }

    fun getBackgroundWidth() : Int {
        return background.width
    }

    fun getBackgroundHeight() : Int {
        return background.height
    }

    fun scaleImage(bitmap: Bitmap) : Bitmap{
        return Bitmap.createScaledBitmap(bitmap, AppConstants.SCREEN_WIDTH, AppConstants.SCREEN_HEIGHT, false)
    }
}