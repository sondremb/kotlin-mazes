package com.mygdx.kotlinmazes.utils.graphics

import com.badlogic.gdx.graphics.Color
import com.mygdx.kotlinmazes.utils.math.lerp
import com.mygdx.kotlinmazes.utils.math.norm

class Gradient(stops: List<GradientStop>) {
    private lateinit var _stops: List<GradientStop>

    init {
        _stops = stops.sortedBy { it.value }
    }

    constructor(vararg colors: Color) : this(colors.mapIndexed { i, c -> GradientStop(c, i / (colors.size - 1f)) })
    constructor(vararg stops: GradientStop) : this(stops.asList())

    fun sample(value: Float): Color {
        validateValue(value)
        val iter = _stops.iterator()
        var start = iter.next()
        var end = iter.next()
        while (end.value < value) {
            start = end
            end = iter.next()
        }
        val relativeValue = norm(start.value, end.value, value)
        return lerp(start.color, end.color, relativeValue)
    }

    fun sampler(min: Float, max: Float): GradientSampler {
        return GradientSampler(this, min, max)
    }
}

class GradientSampler(private val gradient: Gradient, private val min: Float, private val max: Float) {
    fun sample(value: Float): Color {
        return gradient.sample(norm(min, max, value))
    }
}

data class GradientStop(val color: Color, val value: Float) {
    init {
        validateValue(value)
    }
}


fun validateValue(value: Float) {
    require(value in 0f..1f) { "Value must be between 0 and 1, was $value" }
}