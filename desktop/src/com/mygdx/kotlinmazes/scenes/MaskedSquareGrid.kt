package com.mygdx.kotlinmazes.scenes

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.utils.ScreenUtils
import com.mygdx.kotlinmazes.Distance
import com.mygdx.kotlinmazes.drawers.SquareGridDrawer
import com.mygdx.kotlinmazes.grids.square.MaskedSquareGrid
import com.mygdx.kotlinmazes.grids.square.SquareCell
import com.mygdx.kotlinmazes.mazegeneration.AldousBroder
import com.mygdx.kotlinmazes.utils.graphics.Gradient

fun main() {
    val grid = MaskedSquareGrid(108, 192)
    MaskedSquareGridScene(grid).play()
}

class MaskedSquareGridScene(private val grid: MaskedSquareGrid) : Scene() {

    private var distanceSource: SquareCell? = null
    private var distance: Distance? = null
    private lateinit var drawer: SquareGridDrawer
    private val gradient = Gradient.Plasma
    private var prevHoveredCell: SquareCell? = null
    private var isLeftHeld = false
    private var isRightHeld = false
    private var isForwardHeld = false
    private var isBackHeld = false
    private var isSpaceHeld = false
    private var range = 2
    private var currentDistance = 0

    override fun init() {
        drawer = SquareGridDrawer(shapeRenderer, grid)
        //grid.allCells().forEach(grid::mask)
    }

    private fun setMask(cell: SquareCell, masked: Boolean) {
        if (masked) grid.mask(cell) else grid.unmask(cell)
        distanceSource = null
        distance = null
        currentDistance = 0
    }

    private fun handleSomething(cell: SquareCell) {
        distanceSource = cell
        grid.resetLinks()
        AldousBroder(grid).forEach {}
        distance = Distance(cell)
    }

    private fun getCellNeighborhood(cell: SquareCell): List<SquareCell> {
        val neighbors = mutableListOf<SquareCell>()
        val rangeSquared = range * range
        for (r in -range..range) {
            val rSquare = r * r
            for (c in -range..range) {
                if (rSquare + c * c > rangeSquared) {
                    continue
                }
                val neighbor = grid.get(cell.row + r, cell.column + c)
                if (neighbor != null) {
                    neighbors.add(neighbor)
                }
            }
        }
        return neighbors
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

        var neighborhood = getCellNeighborhood(hoveredCell)
        if (!isLeftHeld && Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
            isLeftHeld = true
            neighborhood.forEach { setMask(it, false) }
        }
        if (!isRightHeld && Gdx.input.isButtonPressed(Input.Buttons.RIGHT)) {
            isRightHeld = true
            neighborhood.forEach { setMask(it, true) }
        }

        if (!isForwardHeld && Gdx.input.isButtonPressed(Input.Buttons.FORWARD)) {
            isForwardHeld = true
            range++
        }
        isForwardHeld = Gdx.input.isButtonPressed(Input.Buttons.FORWARD)

        if (!isBackHeld && Gdx.input.isButtonPressed(Input.Buttons.BACK)) {
            isBackHeld = true
            range = maxOf(0, range - 1)
        }
        isBackHeld = Gdx.input.isButtonPressed(Input.Buttons.BACK)

        if (!isSpaceHeld && Gdx.input.isKeyPressed(Input.Keys.SPACE) && !grid.isMasked(
                hoveredCell
            )
        ) {
            isSpaceHeld = true
            handleSomething(hoveredCell)
        }
        isSpaceHeld = Gdx.input.isKeyPressed(Input.Keys.SPACE)
    }

    override fun draw() {
        ScreenUtils.clear(1f, 1f, 1f, 1f)
        var neighborHood = prevHoveredCell?.let(::getCellNeighborhood) ?: emptyList()
        if (distance == null) {
            grid.allCells().forEach {
                if (grid.isMasked(it)) {
                    drawer.fill(it, Color.GRAY)
                }
            }
            neighborHood.forEach { drawer.drawAllBorders(it, Color.RED) }
            return
        }

        val gradientSampler = gradient.sampler(0f, distance!!.max.toFloat())
        grid.cells().forEach {
            val cellDist = distance!![it]
            var color = if (cellDist <= currentDistance) gradientSampler.sample(cellDist.toFloat()) else Color.WHITE
            if (it.row == prevHoveredCell?.row && it.column == prevHoveredCell?.column) {
                // lighten color if hovered
                color = Color(color.r * 1.5f, color.g * 1.5f, color.b * 1.5f, color.a)
            }
            drawer.fill(it, color)
        }
        currentDistance += 5

        if (prevHoveredCell != null) {
            drawer.fill(prevHoveredCell!!, Color.CORAL)
        }
        shapeRenderer.color = Color.BLACK
        grid.cells().forEach(drawer::drawAllBorders)
        prevHoveredCell?.let { drawer.drawAllBorders(it, Color.RED) }
    }
}

