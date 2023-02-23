/***
 * Names:        Lee Yu Ting
 *               Lim Yi Qin
 *               Loh Yun Xuan
 *               Tan Wei Ling Felicia
 *               Woon Ting Ting
 * Student IDs:  2002892
 *               2000804
 *               2001533
 *               2001339
 *               2002323
 * Brief:        Handles the logic for updating the position and collision of fruits and bombs,
 *               and drawing the images for the game objects. Including sound effects
 *
 * Copyright (C) 2023 DigiPen Institute of Technology.
 * Reproduction or disclosure of this file or its contents
 * without the prior written consent of DigiPen Institute of
 * Technology is prohibited.
 */


package com.example.catchthefruits

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.media.MediaPlayer

class GameEngine {
    /**
     * backgroundImage:    the background image of the game
     * basketImage:        the image of the basket that the player controls
     * groundImage:        the image of the ground in the game
     * fruits:             a list of the fruits that appear in the game
     * bombs:              a list of the bombs that appear in the game
     * explosions:         a list of the explosions that occur when a bomb hits the ground
     * explosionPlayers:   a list of MediaPlayer objects for playing explosion sound effects
     * dropPlayers:        a list of MediaPlayer objects for playing fruit drop sound effects
     * canPlaySound:       a flag indicating whether sound effects should be played or not
     * context:            the context in which the game is running
     * health:             the health of the player
     * points:             the number of points the player has scored
     * onGameOverListener: a listener function to be called when the game is over
     * */
    var backgroundImage : BackgroundImage
    var basketImage : BasketImage
    var groundImage : GroundImage
    var fruits = mutableListOf<Fruits>()
    var bombs = mutableListOf<Bomb>()
    var explosions = mutableListOf<Explosion>()
    val explosionPlayers = mutableListOf<MediaPlayer>()
    val dropPlayers = mutableListOf<MediaPlayer>()
    var canPlaySound = true
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

    /**
     * Updates and draws the background image on the given Canvas.
     * */
    fun updateAndDrawBackgroundImage(canvas: Canvas){
        canvas.drawBitmap(AppConstants.bitmapBank.background, backgroundImage.BackgroundImageX.toFloat(), backgroundImage.BackgroundImageY.toFloat(), null)
    }

    /**
     * Updates and draws the basket image on the given Canvas.
     * */
    fun updateAndDrawBasketImage(canvas : Canvas){
        canvas.drawBitmap(AppConstants.bitmapBank.basket, basketImage.BasketImageX.toFloat(), basketImage.BasketImageY.toFloat(), null)
    }

    /**
     * Updates and draws the ground image on the given Canvas.
     * */
    fun updateAndDrawGroundImage(canvas : Canvas){
        canvas.drawBitmap(AppConstants.bitmapBank.ground, null, groundImage.rectGround, null)
    }

    /**
     * Updates and draws the fruit images on the given Canvas.
     * This method also handles the logic for updating the position
     * of the fruits and checking for collisions with the basket.
     * */
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

    /**
     * Checks for collisions between the fruits and the basket,
     * updates the score, and plays the appropriate sound effect.
     * */
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

    /**
     * Checks for collisions between the bombs and the basket,
     * updates the health, and triggers a game over event if the health reaches zero.
     * */
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

    /**
     * Updates and draws the bomb images on the given Canvas. This method also handles
     * the logic for updating the position of the bombs and triggering explosions when
     * the bombs hit the ground.
     * */
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

    /**
     * Updates and draws the explosion images on the given Canvas.
     * */
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

    /**
     * Plays a sound effect using the given MediaPlayer list and sound effect resource ID.
     * */
    private fun playSound(mediaPlayers : MutableList<MediaPlayer>, resourceID: Int)
    {
        if (!canPlaySound)
            return
        var mediaPlayer: MediaPlayer? = null
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(context, resourceID)
            mediaPlayer?.setOnCompletionListener {
                //stopPlaying(mediaPlayers)
                mediaPlayer?.stop()
                mediaPlayer?.reset()
                mediaPlayer?.release()
            }
            mediaPlayer?.start()
            synchronized(mediaPlayers){
                mediaPlayers.add(mediaPlayer)
            }

        }

    }


    private fun stopPlaying(mediaPlayers: MutableList<MediaPlayer>)
    {
        synchronized(mediaPlayers){
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

    }

    /**
     * Sets the game over listener function.
     * */
    fun setOnGameOverListener(listener: () -> Unit) {
        onGameOverListener = listener
    }

    /**
     * Stops playing all sound effects.
     * */
    private fun stopPlayingAll()
    {
        val iteratorExplosion = explosionPlayers.iterator()
        while (iteratorExplosion.hasNext()) {
            var mediaPlayer1: MediaPlayer? = null
            mediaPlayer1 = iteratorExplosion.next()
            /*if (mediaPlayer1 != null){
                if (mediaPlayer1.isPlaying)
                    mediaPlayer1?.stop()
                //mediaPlayer1?.reset()*/
                mediaPlayer1?.release()
            iteratorExplosion.remove()
        }
        val iteratorDrop = dropPlayers.iterator()
        while (iteratorDrop.hasNext()) {
            var mediaPlayer2: MediaPlayer? = null
            mediaPlayer2 = iteratorDrop.next()
            mediaPlayer2?.release()
            iteratorDrop.remove()
        }
        canPlaySound = false
    }
}