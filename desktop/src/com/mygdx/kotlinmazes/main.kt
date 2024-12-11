package com.mygdx.kotlinmazes

import com.mygdx.kotlinmazes.grids.square.SquareGrid
import com.mygdx.kotlinmazes.mazegeneration.AldousBroder
import com.mygdx.kotlinmazes.scenes.SquareGridDistance
import ktx.math.vec2

object EngineConfig {
    const val VIEWPORT_WIDTH = 1080f
    const val VIEWPORT_HEIGHT = 1080f
    val VIEWPORT_CENTER = vec2(VIEWPORT_WIDTH / 2, VIEWPORT_HEIGHT / 2)
}


fun main(arg: Array<String>) {
    val grid = SquareGrid(50, 80).also { AldousBroder(it).forEach {} }
    val dists = Distance(grid.get(grid.height / 2, grid.width / 2)!!)
    SquareGridDistance(grid, dists).play()
}
