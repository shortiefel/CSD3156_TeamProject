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
 * Brief:       This file contains the code needed for creation of explosion
 *
 * Copyright (C) 2023 DigiPen Institute of Technology.
 * Reproduction or disclosure of this file or its contents
 * without the prior written consent of DigiPen Institute of
 * Technology is prohibited.
 */


package com.example.catchthefruits

import android.content.Context
import android.graphics.BitmapFactory

class Explosion(context: Context) {

    /**
     * Creation of a bitmap for explosion
     * */
    private val explosion = arrayOf(
        BitmapFactory.decodeResource(context.resources, R.drawable.explode_0),
        BitmapFactory.decodeResource(context.resources, R.drawable.explode_1),
        BitmapFactory.decodeResource(context.resources, R.drawable.explode_2),
        BitmapFactory.decodeResource(context.resources, R.drawable.explode_3)   //yes it is an invisible image =)
    )

    /**
     * Declarations
     * **/
    var explosionFrame = 0
    var explosionX: Int = 0
    var explosionY: Int = 0

    fun getExplosion(frame: Int) = explosion[frame]
}



