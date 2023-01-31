package com.mygdx.kotlinmazes

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.utils.ScreenUtils
import com.badlogic.gdx.utils.viewport.FitViewport
import com.mygdx.kotlinmazes.generation.aldousBroder
import com.mygdx.kotlinmazes.grids.polar.PolarGrid
import com.mygdx.kotlinmazes.math.toDegrees
import ktx.math.plus
import ktx.math.times
import ktx.math.vec2
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

fun main(arg: Array<String>) {
    val config = Lwjgl3ApplicationConfiguration()
    config.setForegroundFPS(60)
    config.setTitle("Kotlin Mazes")
    val grid = PolarGrid(20).also { aldousBroder(it) }
    Lwjgl3Application(DrawPolarGrid(grid), config)
}

class DrawPolarGrid(private val grid: PolarGrid) : Scene() {
    override fun init() {
        viewport = FitViewport(1f, 1f, camera)
        shape.projectionMatrix.setToOrtho2D(0f, 0f, 1f, 1f)
    }

    override fun render() {
        ScreenUtils.clear(1f, 1f, 1f, 1f)
        for (cell in grid.cells) {
            val r = cell.row
            val c = cell.column
            if (r == 0) {
                continue
            }
            val theta = 2f * PI / grid[r].size
            val cellHeight = 1f / (2f * grid.rows)
            val innerRadius = r * cellHeight
            val outerRadius = (r + 1) * cellHeight
            val thetaCw = (c + 1) * theta
            val thetaCcw = c * theta
            shape.color = Color.BLACK
            if (!cell.isLinked(cell.inwards)) {
                shape.begin(ShapeRenderer.ShapeType.Line)
                shape.strokeArc(0.5f, 0.5f, radius = innerRadius, start = toDegrees(thetaCcw).toFloat(), degrees = toDegrees(theta).toFloat(), 10)
                shape.end()
            }
            if (!cell.isLinked(cell.cw)) {
                val base = vec2(cos(thetaCw).toFloat(), sin(thetaCw).toFloat())
                val from = (base * innerRadius)
                val to = (base * outerRadius)
                shape.begin(ShapeRenderer.ShapeType.Line)
                shape.line(from + 0.5f, to + 0.5f)
                shape.end()
            }
            shape.begin(ShapeRenderer.ShapeType.Line)
            shape.circle(0.5f, 0.5f, 0.5f, 100)
            shape.end()
        }
    }
}