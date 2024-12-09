package com.mygdx.kotlinmazes.scenes

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.utils.ScreenUtils
import com.mygdx.kotlinmazes.EngineConfig
import com.mygdx.kotlinmazes.drawers.SquareGridDrawer
import com.mygdx.kotlinmazes.grids.square.SquareCell
import com.mygdx.kotlinmazes.grids.square.SquareGrid
import com.mygdx.kotlinmazes.mazegeneration.aldousBroder
import com.mygdx.kotlinmazes.playScene
import com.mygdx.kotlinmazes.solving.DepthFirstSearch
import kotlin.math.min

fun main() {
    val grid = SquareGrid(50, 80).also { aldousBroder(it) }
    playScene(DfsClick(grid))
}

class DfsClick(private val grid: SquareGrid) : Scene() {

    private var sourceCell: SquareCell? = null
    private var targetCell: SquareCell? = null
    private var hoveredCell: SquareCell? = null
    private var algorithm: DepthFirstSearch? = null
    private lateinit var drawer: SquareGridDrawer
    /*    private val gradient = Gradient(
            Color.valueOf("#f0f921"),
            Color.valueOf("#f89540"),
            Color.valueOf("#cc4778"),
            Color.valueOf("#7e03a8"),
            Color.valueOf("#0d0887")
        )*/

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

        if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT) && hoveredCell != null) {
            if (sourceCell == null) {
                sourceCell = hoveredCell
            } else if (targetCell == null) {
                targetCell = hoveredCell
                algorithm = DepthFirstSearch(grid, sourceCell!!, targetCell!!)
            }
        }
    }

    override fun draw() {
        ScreenUtils.clear(1f, 1f, 1f, 1f)
        algorithm?.let { algo ->
            if (!algo.isFinished()) {
                algo.step()
                if (algo.state == DepthFirstSearch.State.Searching) {
                    grid.cells().forEach {
                        if (algo.frontier.contains(it)) {
                            drawer.fill(it, Color.GREEN)
                        } else if (!algo.visited.contains(it)) {
                            drawer.fill(it, Color.LIGHT_GRAY)
                        }
                    }
                } else if (algo.state == DepthFirstSearch.State.Backtracking) {
                    grid.cells().forEach {
                        if (algo.path.contains(it)) drawer.fill(it, Color.GREEN)
                    }
                }
            }
        }

        if (sourceCell != null) {
            drawer.fill(sourceCell!!, Color.BLUE)
        }
        if (targetCell != null) {
            drawer.fill(targetCell!!, Color.RED)
        }
        if (hoveredCell != null) {
            drawer.fill(hoveredCell!!, Color.GOLD)
        }
        shapeRenderer.color = Color.GRAY
        grid.cells().forEach(drawer::drawBorders)
    }
}

