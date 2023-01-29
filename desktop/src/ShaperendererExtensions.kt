package com.mygdx.kotlinmazes

import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.MathUtils
import kotlin.math.max

fun ShapeRenderer.strokeArc(x: Float, y: Float, radius: Float, start: Float, degrees: Float) {
    strokeArc(x, y, radius, start, degrees, max(1, (6.0 * Math.cbrt(radius.toDouble()) * (degrees / 360.0)).toInt()))
}

fun ShapeRenderer.strokeArc(x: Float, y: Float, radius: Float, start: Float, degrees: Float, segments: Int) {
    require(segments > 0) { "segments must be > 0." }
    val colorBits = color.toFloatBits()
    val theta = 2 * MathUtils.PI * (degrees / 360.0f) / segments
    val cos = MathUtils.cos(theta)
    val sin = MathUtils.sin(theta)
    var cx = radius * MathUtils.cos(start * MathUtils.degreesToRadians)
    var cy = radius * MathUtils.sin(start * MathUtils.degreesToRadians)

    if (currentType == ShapeRenderer.ShapeType.Line) {
        // TODO finn ut hva denne gjør
        // check(ShapeRenderer.ShapeType.Line, ShapeRenderer.ShapeType.Filled, segments * 2 + 2)
        // TODO
        // renderer.color(colorBits)
        // renderer.vertex(x, y, 0f)
        // TODO tror denne er redundant
        // renderer.color(colorBits)
        // renderer.vertex(x + cx, y + cy, 0f)
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
        // TODO denne også
        // check(ShapeType.Line, ShapeType.Filled, segments * 3 + 3)
        for (i in 0 until segments) {
            // TODO
            // renderer.color(colorBits)
            // renderer.vertex(x, y, 0f)
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

    // TODO
    // cx = 0f
    // cy = 0f
    // renderer.color(colorBits)
    // renderer.vertex(x + cx, y + cy, 0f)
}