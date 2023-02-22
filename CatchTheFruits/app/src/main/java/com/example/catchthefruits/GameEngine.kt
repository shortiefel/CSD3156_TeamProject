package com.example.catchthefruits

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.media.MediaPlayer

class GameEngine {
    var backgroundImage : BackgroundImage
    var basketImage : BasketImage
    var groundImage : GroundImage
    var fruits = mutableListOf<Fruits>()
    var bombs = mutableListOf<Bomb>()
    var explosions = mutableListOf<Explosion>()
    val explosionPlayers = mutableListOf<MediaPlayer>()
    private val dropPlayers = mutableListOf<MediaPlayer>()
    private var canPlaySound = true
    val context : Context
    var health : HealthUI
    var points = 0

    private var onGameOverListener: (() -> Unit)? = null


    constructor(context: Context){
        this.context = context
        backgroundImage = BackgroundImage()
        basketImage = BasketImage()
        groundImage = GroundImage()

        health = HealthUI()

        for (i in 0 until 3) {
            bombs.add(Bomb())
        }

        for (i in 0 until 8) {
            fruits.add(Fruits())
        }
    }

    fun updateAndDrawBackgroundImage(canvas: Canvas){
        canvas.drawBitmap(AppConstants.bitmapBank.background, backgroundImage.BackgroundImageX.toFloat(), backgroundImage.BackgroundImageY.toFloat(), null)
    }

    fun updateAndDrawBasketImage(canvas : Canvas){
        canvas.drawBitmap(AppConstants.bitmapBank.basket, basketImage.BasketImageX.toFloat(), basketImage.BasketImageY.toFloat(), null)
    }

    fun updateAndDrawGroundImage(canvas : Canvas){
        canvas.drawBitmap(AppConstants.bitmapBank.ground, null, groundImage.rectGround, null)
    }

    fun updateAndDrawFruitImage(canvas : Canvas){
        for (i in fruits.indices) {
            AppConstants.bitmapBank.fruits[i]?.let {
                canvas.drawBitmap(
                    it,
                    fruits[i].fruitX.toFloat(),
                    fruits[i].fruitY.toFloat(),
                    null
                )
            }

            fruits[i].fruitY += fruits[i].fruitVelocity
            //if fruit touches ground, reset pos
            if (fruits[i].fruitY + AppConstants.bitmapBank.getFruitHeight(i)!! >=
                AppConstants.SCREEN_HEIGHT - AppConstants.bitmapBank.getGroundHeight()!!) {
                fruits[i].resetPosition(i)
            }
        }
    }

    fun fruitCollision(){
        for (i in fruits.indices) {
            if (fruits[i].fruitX + AppConstants.bitmapBank.getFruitHeight(i)!! >= basketImage.BasketImageX
                && fruits[i].fruitX <= basketImage.BasketImageX + AppConstants.bitmapBank.getBasketWidth()!!
                && fruits[i].fruitY + AppConstants.bitmapBank.getFruitHeight(i)!! >= basketImage.BasketImageY
                && fruits[i].fruitY + AppConstants.bitmapBank.getFruitHeight(i)!! <= basketImage.BasketImageY + AppConstants.bitmapBank.getBasketHeight()!!
            ) {
                points += 10;
                fruits[i].resetPosition(i)
            }

            if (!fruits[i].playedDroppingSound && fruits[i].fruitY > -200 && canPlaySound)
            {
                fruits[i].playedDroppingSound = true
                playSound(dropPlayers, R.raw.drop)
            }
        }
    }

    fun bombCollision(){
        for (i in bombs.indices) {
            if (bombs[i].bombX + AppConstants.bitmapBank.getBombWidth()!! >= basketImage.BasketImageX
                && bombs[i].bombX <= basketImage.BasketImageX +AppConstants.bitmapBank.getBasketWidth()!! //btw this line shud be checking bombX, keeping it as bomby on myside so i can test audio -YT
                && bombs[i].bombY + AppConstants.bitmapBank.getBombHeight()!! >= basketImage.BasketImageY
                && bombs[i].bombY + AppConstants.bitmapBank.getBombHeight()!! <= basketImage.BasketImageY + AppConstants.bitmapBank.getBasketHeight()!!
            ) {
                health.life--
                bombs[i].resetPosition()
                if (health.life == 0) {
                    stopPlayingAll()
                    onGameOverListener?.invoke()
                }
            }
        }
    }

    fun updateAndDrawBombImage(canvas: Canvas){
        for (i in bombs.indices) {
            AppConstants.bitmapBank.getBomb(bombs[i].currentFrame)?.let {
                canvas.drawBitmap(
                    it,
                    bombs[i].bombX.toFloat(),
                    bombs[i].bombY.toFloat(),
                    null
                )
            }
            bombs[i].currentFrame++
            if (bombs[i].currentFrame > 2) {
                bombs[i].currentFrame = 0
            }
            bombs[i].bombY += bombs[i].bombVelocity
            //if bombs touch ground
            if (bombs[i].bombY + AppConstants.bitmapBank.getBombHeight()!! >=
                AppConstants.SCREEN_HEIGHT - AppConstants.bitmapBank.getGroundHeight()!!) {
                val explosion = Explosion()
                explosion.explosionX = bombs[i].bombX
                explosion.explosionY = bombs[i].bombY
                explosions.add(explosion)
                bombs[i].resetPosition()
                playSound(explosionPlayers, R.raw.explosion)
            }
        }
    }

    fun updateExplosions(canvas: Canvas){
        for (i in explosions.indices) {
            AppConstants.bitmapBank.getExplosions(explosions[i].explosionFrame)?.let {
                canvas.drawBitmap(
                    it,
                    explosions[i].explosionX.toFloat(), explosions[i].explosionY.toFloat(), null
                )
            }
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
    }

    fun updateUIs(canvas: Canvas){
        when (health.life) {
            2 -> health.healthPaint.color = Color.YELLOW
            1 -> health.healthPaint.color = Color.RED
        }
        canvas.drawRect(
            (AppConstants.SCREEN_WIDTH - 200).toFloat(), 30F,
            (AppConstants.SCREEN_WIDTH - 200 + 60 * health.life).toFloat(), 80F, health.healthPaint
        )
        canvas.drawText("$points", 20F, health.TEXT_SIZE, health.textPaint)
    }

    private fun playSound(mediaPlayers : MutableList<MediaPlayer>, resourceID: Int)
    {
        if (!canPlaySound)
            return
        var mediaPlayer: MediaPlayer? = null
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(context, resourceID)
            mediaPlayer?.setOnCompletionListener {
                stopPlaying(mediaPlayers)
            }
            mediaPlayer?.start()
            mediaPlayers.add(mediaPlayer)
        }

    }

    private fun stopPlaying(mediaPlayers: MutableList<MediaPlayer>)
    {
        val iterator = mediaPlayers.iterator()
        while (iterator.hasNext()) {
            var mediaPlayer = iterator.next()
            if (!mediaPlayer.isPlaying) {
                iterator.remove()
                mediaPlayer.reset()
                mediaPlayer.release()
            }
        }
    }

    fun setOnGameOverListener(listener: () -> Unit) {
        onGameOverListener = listener
    }

    private fun stopPlayingAll()
    {
        val iteratorExplosion = explosionPlayers.iterator()
        while (iteratorExplosion.hasNext()) {
            var mediaPlayer = iteratorExplosion.next()
            iteratorExplosion.remove()
            mediaPlayer.stop()
            mediaPlayer.reset()
            mediaPlayer.release()
        }
        val iteratorDrop = dropPlayers.iterator()
        while (iteratorDrop.hasNext()) {
            var mediaPlayer = iteratorDrop.next()
            iteratorDrop.remove()
            mediaPlayer.stop()
            mediaPlayer.reset()
            mediaPlayer.release()
        }
        canPlaySound = false
    }
}