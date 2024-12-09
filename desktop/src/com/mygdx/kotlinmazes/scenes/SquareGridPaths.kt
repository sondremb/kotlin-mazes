package com.mygdx.kotlinmazes.scenes

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.utils.ScreenUtils
import com.mygdx.kotlinmazes.EngineConfig
import com.mygdx.kotlinmazes.drawers.SquareGridDrawer
import com.mygdx.kotlinmazes.findPath
import com.mygdx.kotlinmazes.grids.square.SquareCell
import com.mygdx.kotlinmazes.grids.square.SquareGrid
import com.mygdx.kotlinmazes.mazegeneration.AldousBroder
import com.mygdx.kotlinmazes.playScene
import com.mygdx.kotlinmazes.utils.graphics.Gradient
import kotlin.math.min

fun main() {
    val grid = SquareGrid(50, 80).also { AldousBroder(it).forEach {} }
    playScene(SquareGridPaths(grid))
}

class SquareGridPaths(private val grid: SquareGrid) : Scene() {

    private var startCell: SquareCell? = null
    private var hoveredCell: SquareCell? = null
    private lateinit var drawer: SquareGridDrawer
    private val gradient = Gradient.Plasma

    override fun init() {
        val width = EngineConfig.VIEWPORT_WIDTH / grid.width
        val height = EngineConfig.VIEWPORT_HEIGHT / grid.height
        val sideLength = min(width, height)
        drawer = SquareGridDrawer(shapeRenderer, sideLength)
    }

    override fun update() {
        val cursorPos = cursorPosition()
        val (c, r) = drawer.gridCoordsFromScreenCoords(cursorPos)
        hoveredCell = grid.get(r, c)

        if (hoveredCell != null && Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
            startCell = hoveredCell!!
        }
    }

    override fun draw() {
        ScreenUtils.clear(1f, 1f, 1f, 1f)
        if (startCell != null && hoveredCell != null) {
            val path = findPath(startCell!!, hoveredCell!!)
            val sampler = gradient.sampler(0f, path.size.toFloat())
            path.forEachIndexed { index, cell ->
                drawer.fill(cell as SquareCell, sampler.sample(index.toFloat()))
            }
        } else if (hoveredCell != null) {
            drawer.fill(hoveredCell!!, Color.CORAL)
        }
        shapeRenderer.color = Color.BLACK
        grid.cells().forEach(drawer::drawUnlinkedBorders)
    }
}

