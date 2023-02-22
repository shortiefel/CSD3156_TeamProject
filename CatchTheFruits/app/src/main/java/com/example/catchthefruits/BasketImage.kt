package com.example.catchthefruits

class BasketImage {
    var oldBasketImageX : Int
    var oldBasketImageY : Int

    var oldX = 0f

    var BasketImageX : Int
    var BasketImageY : Int

    constructor(){
        BasketImageX = (AppConstants.SCREEN_WIDTH / 2 - AppConstants.bitmapBank.getBombWidth()!! / 2)
        oldBasketImageX = (AppConstants.SCREEN_WIDTH / 2 - AppConstants.bitmapBank.getBombWidth()!! / 2)
        BasketImageY = (AppConstants.SCREEN_HEIGHT - AppConstants.bitmapBank.getGroundHeight()!! - AppConstants.bitmapBank.getBasketHeight()!!)
        oldBasketImageY = (AppConstants.SCREEN_HEIGHT - AppConstants.bitmapBank.getGroundHeight()!! - AppConstants.bitmapBank.getBasketHeight()!!)
    }
}