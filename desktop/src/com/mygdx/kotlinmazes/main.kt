package com.mygdx.kotlinmazes

import com.badlogic.gdx.math.Rectangle
import com.mygdx.kotlinmazes.grids.square.SquareGrid
import com.mygdx.kotlinmazes.mazegeneration.AldousBroder
import com.mygdx.kotlinmazes.scenes.SquareGridDistance
import ktx.math.vec2

object EngineConfig {
    const val VIEWPORT_WIDTH = 1920f
    const val VIEWPORT_HEIGHT = 1080f
    val VIEWPORT_CENTER = vec2(VIEWPORT_WIDTH / 2, VIEWPORT_HEIGHT / 2)
    val VIEWPORT_RECT = Rectangle(0f, 0f, VIEWPORT_WIDTH, VIEWPORT_HEIGHT)
}


fun main(arg: Array<String>) {
    val grid = SquareGrid(50, 80).also { AldousBroder(it).forEach {} }
    val dists = Distance(grid.get(grid.height / 2, grid.width / 2)!!)
    SquareGridDistance(grid, dists).play()
}
