package com.example.catchthefruits

import android.content.Context
import android.graphics.BitmapFactory
import kotlin.random.Random

class Bomb(context: Context) {
    private val bomb = arrayOf(
        BitmapFactory.decodeResource(context.resources, R.drawable.bomb_0),
        BitmapFactory.decodeResource(context.resources, R.drawable.bomb_1),
        BitmapFactory.decodeResource(context.resources, R.drawable.bomb_2)
    )
    var bombFrame = 0
    var bombX: Int = 0
    var bombY: Int = 0
    var bombVelocity: Int = 0
    private val random = Random

    init {
        resetPosition()
    }

    fun getBomb(frame: Int) = bomb[frame]

    fun getBombWidth() = bomb[0].width

    fun getBombHeight() = bomb[0].height

    fun resetPosition() {
        bombX = random.nextInt(GameView.dWidth - getBombWidth())
        bombY = -200 + random.nextInt(600) * -1
        bombVelocity = 35 + random.nextInt(16)
    }
}
