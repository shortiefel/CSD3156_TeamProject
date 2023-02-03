package com.example.catchthefruits

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import kotlin.random.Random

class Bomb {

 /**
  * Declare bomb variables
  * */
 private val bomb = arrayOfNulls<Bitmap>(3)
 var bombFrame = 0
 var bombX: Int = 0
 var bombY: Int = 0
 var bombVelocity: Int = 0
 private val random = Random

 /**
  * Construct a bitmap for bomb for it to load
  * drawable
  * */
 constructor(context: Context) {
  bomb[0] = BitmapFactory.decodeResource(context.resources, R.drawable.bomb_0)
  bomb[1] = BitmapFactory.decodeResource(context.resources, R.drawable.bomb_1)
  bomb[2] = BitmapFactory.decodeResource(context.resources, R.drawable.bomb_2)

  resetPosition()
 }

 fun getBomb(bombFrame: Int): Bitmap?
 {
  return bomb[bombFrame]
 }


 fun getBombWidth(): Int {
  return bomb[0]?.width ?: 0
 }

 private fun getBombHeight(): Int {
  return bomb[0]?.height ?: 0
 }


 /**
  * Resetting bomb positiion
  * */
 fun resetPosition()
 {
  bombX = random.nextInt(GameView.dWidth - getBombWidth())
  bombY = -200 + random.nextInt(600) * - 1
  bombVelocity = 35 + random.nextInt(16)
 }


}