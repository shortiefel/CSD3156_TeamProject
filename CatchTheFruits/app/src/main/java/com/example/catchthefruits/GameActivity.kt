package com.example.catchthefruits

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.*
import android.os.Looper
import android.view.View
import java.util.logging.Handler
import kotlin.random.Random

class GameActivity(context: Context): View(context) {

    /**
     * Default BitMap values
     * for loading of resources
     * */
    private val background: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.game_bg)
    private val ground: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.game_ground)
    private val basket: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.fruit_basket)

    private val rectBackground = Rect(0, 0, dWidth, dHeight)
    private val rectGround = Rect(0, dHeight - ground.height, dWidth, dHeight)

    private var points = 0
    private var life = 3

    /**
     * Handling the context
     * and invalidate Running
     * */
    private val handler = Handler()
    private val runnable = Runnable { invalidate() }

    /**
     * Painting colors for text
     * */
    private val textPaint = Paint().apply {
        color = Color.rgb(255, 165, 0)
        var TEXT_SIZE = 10.0f
        textSize = TEXT_SIZE
        textAlign = Paint.Align.LEFT
    }

    companion object {
        var dWidth = 0
        var dHeight = 0
    }

    /**
     * Default for randoming
     * Creation of basket & basket position
     * */
    private val random = Random
    private var basketX = dWidth / 2 - basket.width / 2
    private var basketY = dHeight - ground.height - basket.height
    private var oldX = 0f
    private var oldBasketX = 0f

    /**
     * Making a list of bomb drawable and exploding
     * */
    private val bomb = mutableListOf<Bomb>()
    private val explode = mutableListOf<Explode>()

    /**
     * Init the Activity itself
     * */
    init {
        val display = (context as Activity).windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        dWidth = size.x
        dHeight = size.y

        for (i in 0 until 3) {
            bomb.add(Bomb(context))
        }
    }

    /**
     * Drawing of canvas
     * */
    override fun onDraw(canvas: Canvas) {

        super.onDraw(canvas)
        canvas.drawBitmap(background, null, rectBackground, null)
        canvas.drawBitmap(ground, null, rectGround, null)
        canvas.drawBitmap(basket, basketX, basketY, null)

        for (i in bomb.indices) {
            canvas.drawBitmap(
                bomb[i].getBomb(bomb[i].bombFrame),
                bomb[i].bombX,
                bomb[i].bombY,
                null
            )

            /**
             * Reset frame to allow smooth
             * animation
             * */
            bomb[i].bombFrame++
            if (bomb[i].bombFrame > 2) {
                bomb[i].bombFrame = 0
            }

            /**
             * Bomb explosion
             * */
            bomb[i].bombY += bomb[i].bombVelocity
            if (bomb[i].bombY + bomb[i].getBombHeight() >= dHeight - ground.height) {
                points += 10
                val explodes = Explode(context)
                explodes.explodeX = bomb[i].bombX
                explodes.explodeY = bomb[i].bombY
                explode.add(explodes)
                bomb[i].resetPosition()
            }
        }

        /**
         * Loop for bomb if it touches the basket
         * */
        for (i in bomb.indices)
            if (bomb[i].bombX + bomb[i].getBombWidth() >= basketX
                && bomb[i].bombX <= basketX + basket.width
                && bomb[i].bombY + bomb[i].getBombWidth() >= basketY
                && bomb[i].bombY + bomb[i].getBombWidth() <= basketY + basket.width
            ) {
                life--
                bomb[i].resetPosition()
                if (life == 0) {
                    val toGameOver = Intent(context, GameOver::class.java)
                    toGameOver.putExtra("Points", points)
                    context.startActivity(toGameOver)
                    (context as Activity).finish()
                }
            }
    }
}