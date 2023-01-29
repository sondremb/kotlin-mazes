package com.mygdx.kotlinmazes.math

import com.badlogic.gdx.graphics.Color

fun lerp(from: Float, to: Float, value: Float): Float {
    return value * (to - from) + from
}

fun lerp(from: Color, to: Color, value: Float): Color {
    return Color(
            lerp(from.r, to.r, value),
            lerp(from.g, to.g, value),
            lerp(from.b, to.b, value),
            lerp(from.a, to.a, value)
    )
}

fun norm(start: Float, end: Float, value: Float): Float {
    return (value - start) / (end - start)
}