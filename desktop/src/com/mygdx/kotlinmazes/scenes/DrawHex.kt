package com.mygdx.kotlinmazes.scenes

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.utils.ScreenUtils
import com.mygdx.kotlinmazes.Distance
import com.mygdx.kotlinmazes.drawers.HexDrawer
import com.mygdx.kotlinmazes.grids.hex.HexCoords
import com.mygdx.kotlinmazes.grids.hex.HexGrid
import com.mygdx.kotlinmazes.mazegeneration.AldousBroder
import com.mygdx.kotlinmazes.utils.graphics.Gradient

fun main() {
    val grid = HexGrid(50).also { AldousBroder(it).forEach {} }
    val dist = Distance(grid[HexCoords(0, 0)]!!)
    DrawHexScene(grid, dist).play()
}

class DrawHexScene(private val grid: HexGrid, private val dist: Distance) : Scene() {
    private val gradient = Gradient.Plasma.sampler(0f, dist.max.toFloat())

    override fun draw() {
        ScreenUtils.clear(1f, 1f, 1f, 1f)

        val drawer = HexDrawer(shapeRenderer, grid)

        grid.cells().forEach {
            drawer.fill(it, gradient.sample(dist[it].toFloat()))
        }
        shapeRenderer.color = Color.BLACK
        grid.cells().forEach(drawer::drawUnlinkedBorders)
    }
}
