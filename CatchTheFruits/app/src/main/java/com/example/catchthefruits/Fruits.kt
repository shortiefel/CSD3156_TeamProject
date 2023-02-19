package com.example.catchthefruits

import android.content.Context
import android.graphics.BitmapFactory
import kotlin.random.Random

class Fruits(context: Context) {
    private val fruit = arrayOf(
        BitmapFactory.decodeResource(context.resources, R.drawable.fruit_1),
        BitmapFactory.decodeResource(context.resources, R.drawable.fruit_2),
        BitmapFactory.decodeResource(context.resources, R.drawable.fruit_3),
        BitmapFactory.decodeResource(context.resources, R.drawable.fruit_4),
        BitmapFactory.decodeResource(context.resources, R.drawable.fruit_5),
        BitmapFactory.decodeResource(context.resources, R.drawable.fruit_6),
        BitmapFactory.decodeResource(context.resources, R.drawable.fruit_7),
        BitmapFactory.decodeResource(context.resources, R.drawable.fruit_8)
    )
    var fruitFrame = 0
    var fruitX: Int = 0
    var fruitY: Int = 0
    var fruitVelocity: Int = 0
    private val random = Random

    init {
        resetPosition(0)
    }

    fun getfruit(frame: Int) = fruit[frame]

    fun getfruitWidth(frame: Int) = fruit[frame].width

    fun getfruitHeight(frame: Int) = fruit[frame].height

    fun resetPosition(frame: Int) {
        fruitX = random.nextInt(GameView.dWidth - getfruitWidth(frame))
        fruitY = -200 + random.nextInt(600) * -1
        fruitVelocity = 35 + random.nextInt(16)
    }
}
