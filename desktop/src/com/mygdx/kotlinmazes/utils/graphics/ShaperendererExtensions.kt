package com.mygdx.kotlinmazes.utils.graphics

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.MathUtils
import ktx.graphics.copy
import kotlin.math.max

fun ShapeRenderer.strokeArc(x: Float, y: Float, radius: Float, start: Float, degrees: Float) {
    strokeArc(x, y, radius, start, degrees, max(1, (6.0 * Math.cbrt(radius.toDouble()) * (degrees / 360.0)).toInt()))
}

// a version of ShapeRenderer.strokeArc that does not draw lines from the center of the arc to the start and end points
// adapted from original code at:
// https://github.com/libgdx/libgdx/blob/master/gdx/src/com/badlogic/gdx/graphics/glutils/ShapeRenderer.java#L836
fun ShapeRenderer.strokeArc(x: Float, y: Float, radius: Float, start: Float, degrees: Float, segments: Int) {
    require(segments > 0) { "segments must be > 0." }
    val colorBits = color.toFloatBits()
    val theta = 2 * MathUtils.PI * (degrees / 360.0f) / segments
    val cos = MathUtils.cos(theta)
    val sin = MathUtils.sin(theta)
    var cx = radius * MathUtils.cos(start * MathUtils.degreesToRadians)
    var cy = radius * MathUtils.sin(start * MathUtils.degreesToRadians)

    if (currentType == ShapeRenderer.ShapeType.Line) {
        for (i in 0 until segments) {
            renderer.color(colorBits)
            renderer.vertex(x + cx, y + cy, 0f)
            val temp = cx
            cx = cos * cx - sin * cy
            cy = sin * temp + cos * cy
            renderer.color(colorBits)
            renderer.vertex(x + cx, y + cy, 0f)
        }
        renderer.color(colorBits)
        renderer.vertex(x + cx, y + cy, 0f)
    } else {
        for (i in 0 until segments) {
            renderer.color(colorBits)
            renderer.vertex(x + cx, y + cy, 0f)
            val temp = cx
            cx = cos * cx - sin * cy
            cy = sin * temp + cos * cy
            renderer.color(colorBits)
            renderer.vertex(x + cx, y + cy, 0f)
        }
        renderer.color(colorBits)
        renderer.vertex(x, y, 0f)
        renderer.color(colorBits)
        renderer.vertex(x + cx, y + cy, 0f)
    }
}

fun ShapeRenderer.withColor(color: Color, block: () -> Unit) {
    val oldColor = this.color.copy()
    this.color = color
    block()
    this.color = oldColor
}