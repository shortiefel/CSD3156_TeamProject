package com.example.catchthefruits

import android.graphics.Rect

class GroundImage {
    var rectGround: Rect

    constructor(){
        rectGround = Rect(0, AppConstants.SCREEN_HEIGHT - AppConstants.bitmapBank.getGroundHeight()!!,
            AppConstants.SCREEN_WIDTH, AppConstants.SCREEN_HEIGHT)
    }
}