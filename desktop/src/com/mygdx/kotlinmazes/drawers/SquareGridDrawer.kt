package com.mygdx.kotlinmazes.drawers

import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.mygdx.kotlinmazes.Config
import com.mygdx.kotlinmazes.grids.square.SquareCell
import com.mygdx.kotlinmazes.grids.square.SquareGrid
import com.mygdx.kotlinmazes.utils.math.vec2
import ktx.graphics.rect
import ktx.graphics.use
import ktx.math.plus
import ktx.math.times
import kotlin.math.min

class SquareGridDrawer(
    shapeRenderer: ShapeRenderer,
    private val grid: SquareGrid,
    boundingRect: Rectangle? = null
) :
    GridDrawer<SquareCell>(shapeRenderer) {

    private val sideLength: Float
    private val offset: Vector2

    init {
        val rect = boundingRect ?: Rectangle(0F, 0F, Config.VIEWPORT_WIDTH, Config.VIEWPORT_HEIGHT)
        sideLength = min(rect.width / grid.width, rect.height / grid.height)
        offset = Vector2(
            rect.x + (rect.width - sideLength * grid.width) / 2,
            rect.y + (rect.height - sideLength * grid.height) / 2
        )
    }

    override fun fill(cell: SquareCell) {
        shapeRenderer.use(ShapeRenderer.ShapeType.Filled) {
            fillInternal(cell)
        }
    }

    private fun fillInternal(cell: SquareCell) {
        shapeRenderer.rect(bottomLeft(cell), sideLength, sideLength)
    }

    override fun fill() {
        shapeRenderer.use(ShapeRenderer.ShapeType.Filled) {
            grid.cells().forEach { fill(it) }
        }
    }

    override fun drawUnlinkedBorders() {
        shapeRenderer.use(ShapeRenderer.ShapeType.Line) {
            grid.cells().forEach { drawUnlinkedBordersInternal(it) }
        }
    }

    override fun drawAllBorders() {
        shapeRenderer.use(ShapeRenderer.ShapeType.Line) {
            grid.cells().forEach { drawAllBordersInternal(it) }
        }
    }


    override fun drawUnlinkedBorders(cell: SquareCell) {
        shapeRenderer.use(ShapeRenderer.ShapeType.Line) {
            drawUnlinkedBordersInternal(cell)
        }
    }

    private fun drawUnlinkedBordersInternal(cell: SquareCell) {
        if (!cell.isLinked(cell.north)) {
            shapeRenderer.line(topLeft(cell), topRight(cell))
        }
        if (!cell.isLinked(cell.east)) {
            shapeRenderer.line(bottomRight(cell), topRight(cell))
        }
        if (cell.south == null) {
            shapeRenderer.line(bottomLeft(cell), bottomRight(cell))
        }
        if (cell.west == null) {
            shapeRenderer.line(bottomLeft(cell), topLeft(cell))
        }
    }

    override fun drawAllBorders(cell: SquareCell) {
        shapeRenderer.use(ShapeRenderer.ShapeType.Line) {
            drawAllBordersInternal(cell)
        }
    }

    private fun drawAllBordersInternal(cell: SquareCell) {
        shapeRenderer.line(topLeft(cell), topRight(cell))
        shapeRenderer.line(topRight(cell), bottomRight(cell))
        shapeRenderer.line(bottomRight(cell), bottomLeft(cell))
        shapeRenderer.line(bottomLeft(cell), topLeft(cell))
    }

    private fun bottomLeft(cell: SquareCell) = offset + vec2(cell.column, cell.row) * sideLength
    private fun bottomRight(cell: SquareCell) = offset + vec2(cell.column + 1, cell.row) * sideLength
    private fun topLeft(cell: SquareCell) = offset + vec2(cell.column, cell.row + 1) * sideLength
    private fun topRight(cell: SquareCell) = offset + vec2(cell.column + 1, cell.row + 1) * sideLength

    fun gridCoordsFromScreenCoords(screenCoords: Vector2): Pair<Int, Int> {
        return Pair((screenCoords.x / sideLength).toInt(), (screenCoords.y / sideLength).toInt())
    }

    fun cellAtScreenCoords(screenCoords: Vector2): SquareCell? {
        val (column, row) = gridCoordsFromScreenCoords(screenCoords)
        return grid.get(row, column)
    }
}