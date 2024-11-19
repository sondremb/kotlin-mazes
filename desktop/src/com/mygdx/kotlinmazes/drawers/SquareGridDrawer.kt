package com.mygdx.kotlinmazes.drawers

import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import com.mygdx.kotlinmazes.grids.square.SquareCell
import com.mygdx.kotlinmazes.utils.math.vec2
import ktx.graphics.rect
import ktx.graphics.use
import ktx.math.times

class SquareGridDrawer(shapeRenderer: ShapeRenderer, private val sideLength: Float) :
    GridDrawer<SquareCell>(shapeRenderer) {

    override fun fill(cell: SquareCell) {
        shapeRenderer.use(ShapeRenderer.ShapeType.Filled) {
            shapeRenderer.rect(bottomLeft(cell), sideLength, sideLength)
        }
    }

    override fun drawBorders(cell: SquareCell) {
        shapeRenderer.use(ShapeRenderer.ShapeType.Line) {
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
    }

    override fun drawAllBorders(cell: SquareCell) {
        shapeRenderer.use(ShapeRenderer.ShapeType.Line) {
            shapeRenderer.line(topLeft(cell), topRight(cell))
            shapeRenderer.line(topRight(cell), bottomRight(cell))
            shapeRenderer.line(bottomRight(cell), bottomLeft(cell))
            shapeRenderer.line(bottomLeft(cell), topLeft(cell))
        }
    }

    private fun bottomLeft(cell: SquareCell) = vec2(cell.column, cell.row) * sideLength
    private fun bottomRight(cell: SquareCell) = vec2(cell.column + 1, cell.row) * sideLength
    private fun topLeft(cell: SquareCell) = vec2(cell.column, cell.row + 1) * sideLength
    private fun topRight(cell: SquareCell) = vec2(cell.column + 1, cell.row + 1) * sideLength

    fun gridCoordsFromScreenCoords(screenCoords: Vector2): Pair<Int, Int> {
        return Pair((screenCoords.x / sideLength).toInt(), (screenCoords.y / sideLength).toInt())
    }
}