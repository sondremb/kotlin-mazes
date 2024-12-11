package com.mygdx.kotlinmazes.scenes

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.utils.ScreenUtils
import com.mygdx.kotlinmazes.drawers.SquareGridDrawer
import com.mygdx.kotlinmazes.grids.square.SquareCell
import com.mygdx.kotlinmazes.grids.square.SquareGrid
import com.mygdx.kotlinmazes.mazegeneration.AldousBroder
import com.mygdx.kotlinmazes.solving.depthFirstSearch

fun main() {
    val grid = SquareGrid(50, 80).also { AldousBroder(it).forEach {} }
    DfsClick(grid).play()
}

class DfsClick(private val grid: SquareGrid) : Scene() {

    private var sourceCell: SquareCell? = null
    private var targetCell: SquareCell? = null
    private var hoveredCell: SquareCell? = null
    private lateinit var drawer: SquareGridDrawer
    private var path = listOf<SquareCell>()

    override fun init() {
        drawer = SquareGridDrawer(shapeRenderer, grid)
    }

    override fun update() {
        val cursorPos = cursorPosition()
        hoveredCell = drawer.cellAtScreenCoords(cursorPos)

        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT) && hoveredCell != null) {
            if (sourceCell == null) {
                sourceCell = hoveredCell
            } else if (targetCell == null) {
                targetCell = hoveredCell
                path = depthFirstSearch(sourceCell!!, targetCell!!) as List<SquareCell>
            }
        }
    }

    override fun draw() {
        ScreenUtils.clear(Color.WHITE)

        if (sourceCell != null) {
            drawer.fill(sourceCell!!, Color.BLUE)
        }
        if (targetCell != null) {
            drawer.fill(targetCell!!, Color.RED)
        }
        if (hoveredCell != null) {
            drawer.fill(hoveredCell!!, Color.GOLD)
        }
        if (path.isNotEmpty()) {
            drawer.fill(Color.LIGHT_GRAY)
            path.forEach { drawer.fill(it, Color.WHITE) }
        }
        drawer.drawUnlinkedBorders(Color.GRAY)
    }
}

