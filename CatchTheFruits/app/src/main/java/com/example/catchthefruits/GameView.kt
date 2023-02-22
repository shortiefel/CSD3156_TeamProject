package com.example.catchthefruits

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.*
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.media.MediaPlayer
import android.os.Handler
import android.util.Log
import android.view.MotionEvent
import android.view.View
import kotlin.random.Random


class GameView(context: Context, msensor: Sensor?, asensor: Sensor?, sensorMgr: SensorManager) : View(context), SensorEventListener {


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
    private val fruits = mutableListOf<Fruits>()
    private val explosions = mutableListOf<Explosion>()
    private var points = 0
    private var life = 3
    private val runnable = Runnable { invalidate() }
    private val explosionPlayers = mutableListOf<MediaPlayer>()

    private val sensorManager = sensorMgr
    private val mValuesMagnet = FloatArray(3)
    private val mValuesAccel = FloatArray(3)
    private val mValuesOrientation = FloatArray(3)

    private val mRotationMatrix = FloatArray(9)

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

        for (i in 0 until 8) {
            fruits.add(Fruits(context))
        }

        sensorManager.registerListener(this, asensor, SensorManager.SENSOR_DELAY_NORMAL)
        sensorManager.registerListener(this, msensor, SensorManager.SENSOR_DELAY_NORMAL)

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
         * Dropping Fruits
         * */
        for (i in fruits.indices) {
            canvas.drawBitmap(
                fruits[i].getfruit(i),
                fruits[i].fruitX.toFloat(),
                fruits[i].fruitY.toFloat(),
                null
            )

            fruits[i].fruitY += fruits[i].fruitVelocity
            //if fruit touches ground, reset pos
            if (fruits[i].fruitY + fruits[i].getfruitHeight(i) >= dHeight - ground.height) {
                fruits[i].resetPosition(i)
            }
        }


        /**
         * Bombing animation and such
         * - Rendering this last so that player can see - YQ
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
                //points += 10
                val explosion = Explosion(context)
                explosion.explosionX = bombs[i].bombX
                explosion.explosionY = bombs[i].bombY
                explosions.add(explosion)
                bombs[i].resetPosition()
                Play()
            }
        }


        //if fruit hits basket, add points
        //also, im putting this on top of the bomb collision, so it will still add points during the
        //last life, before switching intent
        for (i in fruits.indices) {
            if (fruits[i].fruitX + fruits[i].getfruitWidth(i) >= basketX
                && fruits[i].fruitX <= basketX + basket.width
                && fruits[i].fruitY + fruits[i].getfruitHeight(i) >= basketY
                && fruits[i].fruitY + fruits[i].getfruitHeight(i) <= basketY + basket.height
            ) {
                points += 10;
                fruits[i].resetPosition(i)
            }
        }


        /**
         * Bombing
         * */
        for (i in bombs.indices) {
            if (bombs[i].bombX + bombs[i].getBombWidth() >= basketX
                && bombs[i].bombX <= basketX + basket.width //btw this line shud be checking bombX, keeping it as bomby on myside so i can test audio -YT
                && bombs[i].bombY + bombs[i].getBombHeight() >= basketY
                && bombs[i].bombY + bombs[i].getBombHeight() <= basketY + basket.height
            ) {
                life--
                bombs[i].resetPosition()
                if (life == 0) {
                    sensorManager.unregisterListener(this)
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


    override fun onSensorChanged(event: SensorEvent?) {
        if (event?.sensor?.getType() == Sensor.TYPE_ACCELEROMETER) {
            System.arraycopy(event.values, 0, mValuesAccel, 0, 3);
        }
        else if (event?.sensor?.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            System.arraycopy(event.values, 0, mValuesMagnet, 0, 3);
        }

        SensorManager.getRotationMatrix(mRotationMatrix, null, mValuesAccel, mValuesMagnet);
        SensorManager.getOrientation(mRotationMatrix, mValuesOrientation);

        //Log.e("lalalalala", "orientation is ${mValuesOrientation[0]}, ${mValuesOrientation[1]}, ${mValuesOrientation[2]}")
        //Log.e("lalalalala", "basket is ${basketX}")


        val ss = mValuesOrientation[2] * 100F
        basketX += ss
        if(basketX < 0F){
            basketX = 0F
        }
        if(basketX > (dWidth - basket.width)){
            basketX = (dWidth - basket.width).toFloat()
        }

    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        Log.e("lalala", "Accuracy Changed")
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


