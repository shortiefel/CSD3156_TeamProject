package com.example.catchthefruits

import android.graphics.Color
import android.graphics.Paint

class HealthUI {
    var life : Int
    var healthPaint : Paint
    val textPaint : Paint
    val TEXT_SIZE = 120f

    init{
        life = 3
        healthPaint = Paint().apply { color = android.graphics.Color.GREEN }
        textPaint = Paint().apply {
            color = Color.rgb(255, 165, 0)
            textSize = TEXT_SIZE
            textAlign = Paint.Align.LEFT
        }
    }
}