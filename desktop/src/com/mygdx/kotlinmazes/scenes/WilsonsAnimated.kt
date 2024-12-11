package com.mygdx.kotlinmazes.scenes

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.utils.ScreenUtils
import com.mygdx.kotlinmazes.drawers.HexDrawer
import com.mygdx.kotlinmazes.grids.hex.HexGrid
import com.mygdx.kotlinmazes.mazegeneration.Wilsons

fun main() {
    val grid = HexGrid(15)
    WilsonsAnimated(grid).play()
}

class WilsonsAnimated(private val grid: HexGrid) : Scene() {
    private lateinit var drawer: HexDrawer
    private val algorithm = Wilsons(grid)
    override fun init() {
        super.init()
        drawer = HexDrawer(shapeRenderer, grid)
    }

    override fun draw() {
        ScreenUtils.clear(Color.WHITE)
        var state: Wilsons.State? = null
        if (!algorithm.isFinished()) {
            state = algorithm.step()
        }

        if (state == null) {
            grid.cells().forEach {
                drawer.fill(it, if (it.links.isEmpty()) Color.GRAY else Color.WHITE)
            }
            shapeRenderer.color = Color.GRAY
            grid.cells().forEach(drawer::drawUnlinkedBorders)
            return
        }
        grid.cells().forEach {
            val isHead = state.path.first() == it
            val inPath = state.path.contains(it)
            if (isHead) {
                drawer.fill(it, Color.BLUE)
            } else if (state.state == Wilsons.State.StateType.LINKED && inPath) {
                drawer.fill(it, Color.GREEN)
            } else if (state.state == Wilsons.State.StateType.SEARCHING && inPath) {
                drawer.fill(it, Color.RED)
            } else if (state.unvisited.contains(it)) {
                drawer.fill(it, Color.GRAY)
            } else {
                drawer.fill(it, Color.WHITE)
                drawer.drawUnlinkedBorders(it, Color.GRAY)
            }
        }
        shapeRenderer.color = Color.GRAY
        grid.cells().forEach(drawer::drawUnlinkedBorders)
    }
}