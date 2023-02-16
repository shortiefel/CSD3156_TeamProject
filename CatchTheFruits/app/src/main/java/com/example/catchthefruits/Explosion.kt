package com.example.catchthefruits

import android.content.Context
import android.graphics.BitmapFactory

class Explosion(context: Context) {
    private val explosion = arrayOf(
        BitmapFactory.decodeResource(context.resources, R.drawable.explode_0),
        BitmapFactory.decodeResource(context.resources, R.drawable.explode_1),
        BitmapFactory.decodeResource(context.resources, R.drawable.explode_2),
        BitmapFactory.decodeResource(context.resources, R.drawable.explode_3)   //yes it is an invisible image =)
    )
    var explosionFrame = 0
    var explosionX: Int = 0
    var explosionY: Int = 0

    fun getExplosion(frame: Int) = explosion[frame]
}



