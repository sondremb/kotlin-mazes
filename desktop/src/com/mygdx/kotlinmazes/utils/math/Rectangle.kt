package com.mygdx.kotlinmazes.utils.math

import com.badlogic.gdx.math.Rectangle

fun Rectangle.inset(dx: Float, dy: Float): Rectangle {
    return Rectangle(x + dx, y + dy, width - 2 * dx, height - 2 * dy)
}

fun Rectangle.inset(amount: Float): Rectangle {
    return inset(amount, amount)
}