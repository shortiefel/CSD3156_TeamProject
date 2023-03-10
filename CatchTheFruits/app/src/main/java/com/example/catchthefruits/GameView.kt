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
 * Brief:        Extends SurfaceView and implements SurfaceHolder.Callback and SensorEventListener.
 *
 * Copyright (C) 2023 DigiPen Institute of Technology.
 * Reproduction or disclosure of this file or its contents
 * without the prior written consent of DigiPen Institute of
 * Technology is prohibited.
 */



package com.example.catchthefruits

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Log
import android.view.MotionEvent
import android.view.SurfaceHolder
import android.view.SurfaceView


class GameView(context: Context, cheked: Boolean?) : SurfaceView(context), SurfaceHolder.Callback, SensorEventListener {
    private var gameThread: GameThread? = null

    //Array to store the sensor values
    private val mValuesMagnet = FloatArray(3)
    private val mValuesAccel = FloatArray(3)
    private val mValuesOrientation = FloatArray(3)
    //Matrix array to store and to use for computing the rotation matrix
    private val mRotationMatrix = FloatArray(9)

    init{
        val holder =  getHolder()
        holder.addCallback(this)
        isFocusable = true
        gameThread = GameThread(holder, this)
        if(cheked == true){
            //Sets the both accelerometer and magnetic field sensor to be able to listen in the game
            //only if user toggles on the gyroscope
            AppConstants.sensorEngine.sensorManager.registerListener(this, AppConstants.sensorEngine.acceleSensor, SensorManager.SENSOR_DELAY_NORMAL)
            AppConstants.sensorEngine.sensorManager.registerListener(this, AppConstants.sensorEngine.magfieldSensor, SensorManager.SENSOR_DELAY_NORMAL)

        }
    }

    override fun surfaceCreated(holder : SurfaceHolder){
        if (!gameThread!!.isRunning)
        {
            gameThread = GameThread(holder, this)
            gameThread?.start()
        }
        else gameThread?.start()

    }

    override fun surfaceChanged(p0: SurfaceHolder, p1: Int, p2: Int, p3: Int) {
        Log.d("Surface Changed", "Surfaced Changed Called Game Thread")
    }

    override fun surfaceDestroyed(p0: SurfaceHolder) {
        if (gameThread!!.isRunning){
            gameThread?.isRunning = false
            var retry = true
            while (retry){
                try{
                    gameThread?.join()
                    retry = false;
                }catch (e : InterruptedException){}
            }
        }
        AppConstants.sensorEngine.sensorManager.unregisterListener(this)
    }

    /**
     * Responsible for handling touch events on the screen and updating
     * the position of the BasketImage accordingly.
     * */
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val touchX = event?.x
        val touchY = event?.y

        if (touchY!! >= AppConstants.gameEngine.basketImage.BasketImageY) {
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    AppConstants.gameEngine.basketImage.oldX = event.x
                    AppConstants.gameEngine.basketImage.oldBasketImageX = AppConstants.gameEngine.basketImage.BasketImageX
                }
                MotionEvent.ACTION_MOVE -> {
                    val shift = AppConstants.gameEngine.basketImage.oldX - touchX!!
                    val newBasketX = AppConstants.gameEngine.basketImage.oldBasketImageX - shift
                    AppConstants.gameEngine.basketImage.BasketImageX = if (newBasketX <= 0) {
                        AppConstants.gameEngine.basketImage.BasketImageX
                    } else (if (newBasketX >= AppConstants.SCREEN_WIDTH - AppConstants.bitmapBank.getBasketWidth()!!) {
                        AppConstants.SCREEN_WIDTH - AppConstants.bitmapBank.getBasketWidth()!!
                    } else {
                        newBasketX
                    }).toFloat().toInt()
                }
            }
        }
        return true
    }

    /**
     * Updates the position of the BasketImage based on the changes in the device's orientation as
     * detected by the accelerometer and magnetometer sensors.
     * */
    override fun onSensorChanged(event: SensorEvent?) {
        if (event?.sensor?.getType() == Sensor.TYPE_ACCELEROMETER) {
            System.arraycopy(event.values, 0, mValuesAccel, 0, 3);
        }
        else if (event?.sensor?.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            System.arraycopy(event.values, 0, mValuesMagnet, 0, 3);
        }

        SensorManager.getRotationMatrix(mRotationMatrix, null, mValuesAccel, mValuesMagnet);
        SensorManager.getOrientation(mRotationMatrix, mValuesOrientation);

        val ss = (mValuesOrientation[2] * 100).toInt()
        AppConstants.gameEngine.basketImage.BasketImageX += ss
        if(AppConstants.gameEngine.basketImage.BasketImageX < 0){
            AppConstants.gameEngine.basketImage.BasketImageX = 0
        }
        if(AppConstants.gameEngine.basketImage.BasketImageX > (AppConstants.SCREEN_WIDTH - AppConstants.bitmapBank.getBasketWidth()!!)){
            AppConstants.gameEngine.basketImage.BasketImageX = (AppConstants.SCREEN_WIDTH - AppConstants.bitmapBank.getBasketWidth()!!)
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        return //nothing need to do here
    }
}


