package com.example.catchthefruits

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory


class Explode {

    private val explode = arrayOfNulls<Bitmap>(3)
    private var explodeFrame = 0
    var explodeX: Int = 0
    var explodeY: Int = 0

    /**
     * Construct a bitmap for bomb for it to load
     * drawable
     * */
    constructor(context: Context) {
        explode[0] = BitmapFactory.decodeResource(context.resources, R.drawable.explode_0)
        explode[1] = BitmapFactory.decodeResource(context.resources, R.drawable.explode_1)
        explode[2] = BitmapFactory.decodeResource(context.resources, R.drawable.explode_2)

    }

    private fun getExplode(bombFrame: Int): Bitmap?
    {
        return explode[explodeFrame]
    }






}