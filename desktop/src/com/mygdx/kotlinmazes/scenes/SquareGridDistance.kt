package com.mygdx.kotlinmazes.scenes

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.utils.ScreenUtils
import com.mygdx.kotlinmazes.Distance
import com.mygdx.kotlinmazes.EngineConfig
import com.mygdx.kotlinmazes.Scene
import com.mygdx.kotlinmazes.drawers.SquareGridDrawer
import com.mygdx.kotlinmazes.utils.graphics.Gradient
import com.mygdx.kotlinmazes.grids.square.SquareGrid
import com.mygdx.kotlinmazes.mazegeneration.aldousBroder
import kotlin.math.min

fun main(arg: Array<String>) {
    val grid = SquareGrid(50, 80).also { aldousBroder(it) }
    val dists = Distance(grid.get(grid.height / 2, grid.width / 2)!!)
    com.mygdx.kotlinmazes.run(SquareGridDistance(grid, dists))
}

class SquareGridDistance(private val grid: SquareGrid, private val distance: Distance) : Scene() {

    // farger basert på colormapet "plasma" fra matplotlib
    // hex-koder hentet fra https://waldyrious.net/viridis-palette-generator/
    private var gradient = Gradient(
        Color.valueOf("#f0f921"),
        Color.valueOf("#f89540"),
        Color.valueOf("#cc4778"),
        Color.valueOf("#7e03a8"),
        Color.valueOf("#0d0887")
    ).sampler(0f, distance.max.toFloat())

    override fun draw() {
        val width = EngineConfig.VIEWPORT_WIDTH / grid.width
        val height = EngineConfig.VIEWPORT_HEIGHT / grid.height
        val sideLength = min(width, height)
        val drawer = SquareGridDrawer(shapeRenderer, sideLength)

        ScreenUtils.clear(1f, 1f, 1f, 1f)
        grid.cells.forEach {
            drawer.fill(it, gradient.sample(distance[it].toFloat()))
        }
        shapeRenderer.color = Color.BLACK
        grid.cells.forEach(drawer::drawEdges)
    }
}