package com.mygdx.kotlinmazes.scenes

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.utils.ScreenUtils
import com.mygdx.kotlinmazes.drawers.SquareGridDrawer
import com.mygdx.kotlinmazes.grids.square.MaskedSquareGrid
import com.mygdx.kotlinmazes.grids.square.SquareCell
import com.mygdx.kotlinmazes.mazegeneration.AldousBroder

fun main() {
    val grid = MaskedSquareGrid(108, 192)
    MaskedSquareGridScene(grid).play()
}

// [VENSTREKLIKK] u-maskerer cellen så den kan bli en del av labyrinten
// [HØYREKLIKK] maskerer cellen så den ikke kan bli en del av labyrinten
// [SPACE] genererer en labyrint
// [PIL OPP] øk størrelsen på "penselen"
// [PIL NED] reduser størrelsen på "penselen"
class MaskedSquareGridScene(private val grid: MaskedSquareGrid) : Scene() {

    private lateinit var drawer: SquareGridDrawer
    private var prevHoveredCell: SquareCell? = null
    private var isLeftHeld = false
    private var isRightHeld = false
    private var range = 2
    private var hasLabyrinth = false

    override fun init() {
        drawer = SquareGridDrawer(shapeRenderer, grid)
    }

    private fun setMask(cell: SquareCell, masked: Boolean) {
        hasLabyrinth = false
        if (masked) grid.mask(cell) else grid.unmask(cell)
    }

    private fun handleSomething() {
        hasLabyrinth = true
        grid.resetLinks()
        AldousBroder.on(grid)
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

        if (!isLeftHeld && Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
            val neighborhood = getCellNeighborhood(hoveredCell)
            isLeftHeld = true
            neighborhood.forEach { setMask(it, false) }
        } else if (!isRightHeld && Gdx.input.isButtonPressed(Input.Buttons.RIGHT)) {
            val neighborhood = getCellNeighborhood(hoveredCell)
            isRightHeld = true
            neighborhood.forEach { setMask(it, true) }
        }

        if (Gdx.input.isButtonJustPressed(Input.Buttons.FORWARD) || Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            range++
        }

        if (Gdx.input.isButtonJustPressed(Input.Buttons.BACK) || Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
            range = maxOf(0, range - 1)
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            handleSomething()
        }
    }

    override fun draw() {
        ScreenUtils.clear(Color.WHITE)
        val neighborHood = prevHoveredCell?.let(::getCellNeighborhood) ?: emptyList()
        if (!hasLabyrinth) {
            grid.allCells().forEach {
                if (grid.isMasked(it)) {
                    drawer.fill(it, Color.GRAY)
                }
            }
            neighborHood.forEach { drawer.drawAllBorders(it, Color.RED) }
            return
        }

        if (prevHoveredCell != null) {
            drawer.fill(prevHoveredCell!!, Color.CORAL)
        }
        drawer.drawUnlinkedBorders(Color.GRAY)
        prevHoveredCell?.let { drawer.drawAllBorders(it, Color.RED) }
    }
}

