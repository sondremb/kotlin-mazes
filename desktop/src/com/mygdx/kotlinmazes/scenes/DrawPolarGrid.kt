package com.mygdx.kotlinmazes.scenes

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.utils.ScreenUtils
import com.mygdx.kotlinmazes.EngineConfig
import com.mygdx.kotlinmazes.Scene
import com.mygdx.kotlinmazes.grids.polar.PolarGrid
import com.mygdx.kotlinmazes.mazegeneration.aldousBroder
import com.mygdx.kotlinmazes.utils.graphics.strokeArc
import com.mygdx.kotlinmazes.utils.math.toDegrees
import ktx.graphics.use
import ktx.math.plus
import ktx.math.times
import ktx.math.vec2
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

fun main() {
    val grid = PolarGrid(20).also { aldousBroder(it) }
    com.mygdx.kotlinmazes.run(DrawPolarGrid(grid))
}

class DrawPolarGrid(private val grid: PolarGrid) : Scene() {

    init {
        showViewportEdge = true
    }

    override fun draw() {
        ScreenUtils.clear(1f, 1f, 1f, 1f)
        shapeRenderer.color = Color.BLACK
        for (cell in grid.cells) {
            val r = cell.row
            val c = cell.column
            if (r == 0) {
                continue
            }
            val theta = 2f * PI / grid[r].size
            // vi bruker høyden for å beregne radius, fordi den er minst
            val cellHeight = EngineConfig.VIEWPORT_HEIGHT / (2f * grid.rows)
            val innerRadius = r * cellHeight
            val outerRadius = (r + 1) * cellHeight
            val thetaCw = (c + 1) * theta
            val thetaCcw = c * theta
            if (!cell.isLinked(cell.inwards)) {
                shapeRenderer.use(ShapeRenderer.ShapeType.Line) {
                    it.strokeArc(
                        EngineConfig.VIEWPORT_CENTER.x,
                        EngineConfig.VIEWPORT_CENTER.y,
                        radius = innerRadius,
                        start = toDegrees(thetaCcw).toFloat(),
                        degrees = toDegrees(theta).toFloat(),
                        10
                    )
                }
            }
            if (!cell.isLinked(cell.cw)) {
                val base = vec2(cos(thetaCw).toFloat(), sin(thetaCw).toFloat())
                val from = (base * innerRadius) + EngineConfig.VIEWPORT_CENTER
                val to = (base * outerRadius) + EngineConfig.VIEWPORT_CENTER
                shapeRenderer.use(ShapeRenderer.ShapeType.Line) {
                    shapeRenderer.line(from, to)
                }

            }
        }
        shapeRenderer.use(ShapeRenderer.ShapeType.Line) {
            it.circle(
                EngineConfig.VIEWPORT_CENTER.x,
                EngineConfig.VIEWPORT_CENTER.y,
                EngineConfig.VIEWPORT_HEIGHT / 2f,
                100
            )
        }
    }
}
