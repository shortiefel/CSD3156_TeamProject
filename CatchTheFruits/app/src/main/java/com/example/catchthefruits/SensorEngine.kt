package com.example.catchthefruits

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorManager

class SensorEngine {
    private var context: Context
    var sensorManager: SensorManager
    var magfieldSensor: Sensor
    var acceleSensor: Sensor

    constructor(context: Context){
        this.context = context
        sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        magfieldSensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD)
        acceleSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
    }
}