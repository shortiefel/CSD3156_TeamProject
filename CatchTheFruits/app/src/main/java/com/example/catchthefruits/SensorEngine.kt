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
 * Brief:        Creates the device's magnetometer and accelerometer sensors for game use
 *
 * Copyright (C) 2023 DigiPen Institute of Technology.
 * Reproduction or disclosure of this file or its contents
 * without the prior written consent of DigiPen Institute of
 * Technology is prohibited.
 */


package com.example.catchthefruits

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorManager

class SensorEngine {
    private var context: Context
    var sensorManager: SensorManager
    var magfieldSensor: Sensor
    var acceleSensor: Sensor

    /**
    Constructs a SensorEngine class that creates the magnetic field and accelerometer sensor that will
     be used in the game
     **/
    constructor(context: Context){
        this.context = context
        sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        magfieldSensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)
        acceleSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
    }
}