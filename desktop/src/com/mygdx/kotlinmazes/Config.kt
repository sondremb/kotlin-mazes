package com.mygdx.kotlinmazes

import com.badlogic.gdx.math.Rectangle
import ktx.math.vec2

object Config {
    const val VIEWPORT_WIDTH = 512f
    const val VIEWPORT_HEIGHT = 512f
    val VIEWPORT_CENTER = vec2(VIEWPORT_WIDTH / 2, VIEWPORT_HEIGHT / 2)
    val VIEWPORT_RECT = Rectangle(0f, 0f, VIEWPORT_WIDTH, VIEWPORT_HEIGHT)
}
