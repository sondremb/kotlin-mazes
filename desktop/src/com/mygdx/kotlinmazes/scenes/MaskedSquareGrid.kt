package com.mygdx.kotlinmazes.scenes

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.utils.ScreenUtils
import com.mygdx.kotlinmazes.Distance
import com.mygdx.kotlinmazes.EngineConfig
import com.mygdx.kotlinmazes.drawers.SquareGridDrawer
import com.mygdx.kotlinmazes.grids.square.MaskedSquareGrid
import com.mygdx.kotlinmazes.grids.square.SquareCell
import com.mygdx.kotlinmazes.mazegeneration.aldousBroder
import com.mygdx.kotlinmazes.playScene
import com.mygdx.kotlinmazes.utils.graphics.Gradient
import kotlin.math.min

fun main() {
    val grid = MaskedSquareGrid(50, 80)
    playScene(MaskedSquareGridScene(grid))
}

class MaskedSquareGridScene(private val grid: MaskedSquareGrid) : Scene() {

    private var distanceSource: SquareCell? = null
    private var distance: Distance? = null
    private lateinit var drawer: SquareGridDrawer
    private val gradient = Gradient(
        Color.valueOf("#f0f921"),
        Color.valueOf("#f89540"),
        Color.valueOf("#cc4778"),
        Color.valueOf("#7e03a8"),
        Color.valueOf("#0d0887")
    )
    private var prevHoveredCell: SquareCell? = null
    private var isLeftHeld = false
    private var isRightHeld = false

    override fun init() {
        val width = EngineConfig.VIEWPORT_WIDTH / grid.width
        val height = EngineConfig.VIEWPORT_HEIGHT / grid.height
        val sideLength = min(width, height)
        drawer = SquareGridDrawer(shapeRenderer, sideLength)
        grid.allCells().forEach(grid::mask)
    }

    private fun handleLeftClick(cell: SquareCell) {
        if (grid.isMasked(cell)) {
            grid.unmask(cell)
        } else {
            grid.mask(cell)
        }
        distanceSource = null
        distance = null
    }

    private fun handleRightClick(cell: SquareCell) {
        distanceSource = cell
        grid.resetLinks()
        aldousBroder(grid)
        distance = Distance(cell)
    }


    override fun update() {
        val cursorPos = cursorPosition()
        val (c, r) = drawer.gridCoordsFromScreenCoords(cursorPos)
        val hoveredCell = grid.get(r, c)

        if (hoveredCell != prevHoveredCell) {
            prevHoveredCell = hoveredCell
            isLeftHeld = false
            isRightHeld = false
        }

        if (hoveredCell == null) {
            return
        }

        if (!isLeftHeld && Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
            isLeftHeld = true
            handleLeftClick(hoveredCell)
        }
        if (!isRightHeld && Gdx.input.isButtonPressed(Input.Buttons.RIGHT) && !grid.isMasked(
                hoveredCell
            )
        ) {
            isRightHeld = true
            handleRightClick(hoveredCell)
        }
    }

    override fun draw() {

        ScreenUtils.clear(1f, 1f, 1f, 1f)
        if (distance == null) {
            grid.allCells().forEach {
                if (grid.isMasked(it)) {
                    drawer.fill(it, Color.GRAY)
                }
            }
            return
        }
        val gradientSampler = gradient.sampler(0f, distance!!.max.toFloat())
        grid.cells().forEach {
            var color = gradientSampler.sample(distance!![it].toFloat())
            if (it.row == prevHoveredCell?.row && it.column == prevHoveredCell?.column) {
                // lighten color if hovered
                color = Color(color.r * 1.5f, color.g * 1.5f, color.b * 1.5f, color.a)
            }
            drawer.fill(it, color)
        }

        if (prevHoveredCell != null) {
            drawer.fill(prevHoveredCell!!, Color.CORAL)
        }
        shapeRenderer.color = Color.BLACK
        grid.cells().forEach(drawer::drawEdges)
    }
}

