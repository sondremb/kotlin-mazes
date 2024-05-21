package com.mygdx.kotlinmazes.scenes

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.utils.ScreenUtils
import com.mygdx.kotlinmazes.Distance
import com.mygdx.kotlinmazes.EngineConfig
import com.mygdx.kotlinmazes.drawers.HexDrawer
import com.mygdx.kotlinmazes.grids.hex.HexCoords
import com.mygdx.kotlinmazes.grids.hex.HexGrid
import com.mygdx.kotlinmazes.mazegeneration.aldousBroder
import com.mygdx.kotlinmazes.playScene
import com.mygdx.kotlinmazes.utils.graphics.Gradient
import kotlin.math.min
import kotlin.math.sqrt

fun main() {
    val grid = HexGrid(40).also { aldousBroder(it) }
    val dist = Distance(grid[HexCoords(0, 0)]!!)
    playScene(DrawHexScene(grid, dist))
}

class DrawHexScene(private val grid: HexGrid, private val dist: Distance) : Scene() {
    private val gradient = Gradient(
        Color.valueOf("#f0f921"),
        Color.valueOf("#f89540"),
        Color.valueOf("#cc4778"),
        Color.valueOf("#7e03a8"),
        Color.valueOf("#0d0887")
    ).sampler(0f, dist.max.toFloat())

    override fun draw() {
        ScreenUtils.clear(1f, 1f, 1f, 1f)

        val sidelengthBasedOnWidth = EngineConfig.VIEWPORT_WIDTH / (3f * grid.radius + 2f)
        val sidelengthBasedOnHeight = EngineConfig.VIEWPORT_HEIGHT / ((2f * grid.radius + 1f) * sqrt(3f))
        val sidelength = min(sidelengthBasedOnWidth, sidelengthBasedOnHeight)
        val drawer = HexDrawer(shapeRenderer, sidelength, EngineConfig.VIEWPORT_CENTER)

        grid.cells().forEach {
            drawer.fill(it, gradient.sample(dist[it].toFloat()))
        }
        shapeRenderer.color = Color.BLACK
        grid.cells().forEach(drawer::drawBorders)
    }
}
