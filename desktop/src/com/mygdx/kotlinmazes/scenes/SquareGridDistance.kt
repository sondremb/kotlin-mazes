package com.mygdx.kotlinmazes.scenes

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.utils.ScreenUtils
import com.mygdx.kotlinmazes.Distance
import com.mygdx.kotlinmazes.drawers.SquareGridDrawer
import com.mygdx.kotlinmazes.grids.square.SquareGrid
import com.mygdx.kotlinmazes.mazegeneration.huntAndKill
import com.mygdx.kotlinmazes.utils.graphics.Gradient

fun main() {
    val grid = SquareGrid(50, 80).also { huntAndKill(it) }
    val dists = Distance(grid.get(grid.height / 2, grid.width / 2)!!)
    SquareGridDistance(grid, dists).play()
}

class SquareGridDistance(private val grid: SquareGrid, private val distance: Distance) : Scene() {

    private val gradient = Gradient.Plasma.sampler(0f, distance.max.toFloat())

    private lateinit var drawer: SquareGridDrawer;

    override fun init() {
        drawer = SquareGridDrawer(shapeRenderer, grid)
    }

    override fun draw() {
        ScreenUtils.clear(1f, 1f, 1f, 1f)
        grid.cells().forEach {
            drawer.fill(it, gradient.sample(distance[it].toFloat()))
            it.resetLinks()
        }
        shapeRenderer.color = Color.BLACK
        grid.cells().forEach(drawer::drawUnlinkedBorders)
    }
}