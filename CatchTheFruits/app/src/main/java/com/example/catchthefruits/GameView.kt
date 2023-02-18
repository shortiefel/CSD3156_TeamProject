package com.example.catchthefruits

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.*
import android.media.MediaPlayer
import android.os.Handler
import android.util.Log
import android.view.MotionEvent
import android.view.View
import kotlin.random.Random


class GameView (context: Context) : View(context) {


    /**
     * Declarations
     * **/
    private var background: Bitmap
    private var ground: Bitmap
    private var basket: Bitmap
    private var rectBackground: Rect
    private var rectGround: Rect
    private val handler = Handler()
    private var context: Context
    private val textPaint = Paint().apply {
        color = Color.rgb(255, 165, 0)
        textSize = TEXT_SIZE
        textAlign = Paint.Align.LEFT
    }
    private val healthPaint = Paint().apply { color = Color.GREEN }
    private val random = Random
    private var basketX = 0f
    private var basketY = 0f
    private var oldX = 0f
    private var oldBasketX = 0f
    private val bombs = mutableListOf<Bomb>()
    private val explosions = mutableListOf<Explosion>()
    private var points = 0
    private var life = 3
    private val runnable = Runnable { invalidate() }
    private val explosionPlayers = mutableListOf<MediaPlayer>()

    companion object {
        private const val UPDATE_MILLIS = 30
        private const val TEXT_SIZE = 120f
        var dWidth = 0
        var dHeight = 0
    }

    /**
     *
     * */
    init {
        this.context = context

        background = BitmapFactory.decodeResource(context.resources, R.drawable.game_bg)
        ground = BitmapFactory.decodeResource(context.resources, R.drawable.game_ground)
        basket = BitmapFactory.decodeResource(context.resources, R.drawable.fruit_basket)

        val display = (context as Activity).windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)
        dWidth = size.x
        dHeight = size.y

        rectBackground = Rect(0, 0, dWidth, dHeight)
        rectGround = Rect(0, dHeight - ground.height, dWidth, dHeight)

        basketX = (dWidth / 2 - basket.width / 2).toFloat()
        basketY = (dHeight - ground.height - basket.height).toFloat()
        for (i in 0 until 3) {
            bombs.add(Bomb(context))
        }
    }

    /**
     * Drawing on the canvas
     * **/
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawBitmap(background, null, rectBackground, null)
        canvas.drawBitmap(ground, null, rectGround, null)
        canvas.drawBitmap(basket, basketX, basketY, null)

        /**
         * Bombing animation and such
         * */
        for (i in bombs.indices) {
            canvas.drawBitmap(
                bombs[i].getBomb(bombs[i].bombFrame),
                bombs[i].bombX.toFloat(),
                bombs[i].bombY.toFloat(),
                null
            )
            bombs[i].bombFrame++
            if (bombs[i].bombFrame > 2) {
                bombs[i].bombFrame = 0
            }
            bombs[i].bombY += bombs[i].bombVelocity
            //if bombs touch ground
            if (bombs[i].bombY + bombs[i].getBombHeight() >= dHeight - ground.height) {
                points += 10
                val explosion = Explosion(context)
                explosion.explosionX = bombs[i].bombX
                explosion.explosionY = bombs[i].bombY
                explosions.add(explosion)
                bombs[i].resetPosition()
                Play()
            }
        }


        /**
         * Bombing
         * */
        for (i in bombs.indices) {
            if (bombs[i].bombX + bombs[i].getBombWidth() >= basketX
                && bombs[i].bombY <= basketX + basket.width //btw this line shud be checking bombX, keeping it as bomby on myside so i can test audio -YT
                && bombs[i].bombY + bombs[i].getBombHeight() >= basketY
                && bombs[i].bombY + bombs[i].getBombHeight() <= basketY + basket.height
            ) {
                life--
                bombs[i].resetPosition()
                if (life == 0) {
                    val intent = Intent(context, GameOver::class.java)
                    intent.putExtra("points", points)
                    context.startActivity(intent)
                    (context as Activity).finish()
                }
            }
        }

        /**
         * When it explodes
         * */
        for (i in explosions.indices) {
            canvas.drawBitmap(
                explosions[i].getExplosion(explosions[i].explosionFrame),
                explosions[i].explosionX.toFloat(), explosions[i].explosionY.toFloat(), null
            )
            if (explosions[i].explosionFrame < 3) {
                explosions[i].explosionFrame++
            }
        }
        var removeAllExploded: Boolean = true
        for (i in explosions.indices) {
            if (explosions[i].explosionFrame != 3) {
                removeAllExploded = false;
            }
        }

        if(removeAllExploded){
            explosions.clear();
        }

        when (life) {
            2 -> healthPaint.color = Color.YELLOW
            1 -> healthPaint.color = Color.RED
        }
        canvas.drawRect(
            (dWidth - 200).toFloat(), 30F,
            (dWidth - 200 + 60 * life).toFloat(), 80F, healthPaint
        )
        canvas.drawText("$points", 20F, TEXT_SIZE, textPaint)
        handler.postDelayed(runnable, UPDATE_MILLIS.toLong())
    }


    /**
     * Movement on screen
     * Touch activity
     *
     * */
    override fun onTouchEvent(event: MotionEvent): Boolean {
        val touchX = event.x
        val touchY = event.y

        if (touchY >= basketY) {
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    oldX = event.x
                    oldBasketX = basketX
                }
                MotionEvent.ACTION_MOVE -> {
                    val shift = oldX - touchX
                    val newBasketX = oldBasketX - shift
                    basketX = if (newBasketX <= 0) {
                        basketX
                    } else (if (newBasketX >= dWidth - basket.width) {
                        dWidth - basket.width
                    } else {
                        newBasketX
                    }).toFloat()
                }
            }
        }
        return true
    }

    private fun Play()
    {
        var mediaPlayer: MediaPlayer? = null
        if (mediaPlayer == null) {

            mediaPlayer = MediaPlayer.create(context, R.raw.explosion)
            mediaPlayer?.setOnCompletionListener {
                StopPlaying()
            }
            mediaPlayer?.start()
            explosionPlayers.add(mediaPlayer)
        }

    }

    private fun StopPlaying()
    {
        val iterator = explosionPlayers.iterator()
        while (iterator.hasNext()) {
            val mediaPlayer = iterator.next()
            if (!mediaPlayer.isPlaying) {
                iterator.remove()
                mediaPlayer.release()
            }
        }

    }



}


