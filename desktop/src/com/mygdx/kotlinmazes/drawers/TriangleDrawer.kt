package com.mygdx.kotlinmazes.drawers

import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.mygdx.kotlinmazes.Config
import com.mygdx.kotlinmazes.grids.triangle.TriangleCell
import com.mygdx.kotlinmazes.grids.triangle.TriangleGrid
import ktx.graphics.triangle
import ktx.graphics.use
import ktx.math.plus
import ktx.math.times
import ktx.math.vec2
import kotlin.math.sqrt

class TriangleDrawer(shapeRenderer: ShapeRenderer, private val grid: TriangleGrid, boundingRect: Rectangle? = null) :
    GridDrawer<TriangleCell>(shapeRenderer) {

    private val sideLength: Float
    private val offset: Vector2

    init {
        val rect = boundingRect ?: Rectangle(0F, 0F, Config.VIEWPORT_WIDTH, Config.VIEWPORT_HEIGHT)
        val minX = grid.cells().minOf { TriangleDrawer.northWest(it as TriangleCell).x }
        val maxX = grid.cells().maxOf { TriangleDrawer.southEast(it as TriangleCell).x }
        val minY = grid.cells().minOf { TriangleDrawer.north(it as TriangleCell).y }
        val maxY = grid.cells().maxOf { TriangleDrawer.south(it as TriangleCell).y }
        val width = maxX - minX
        val height = maxY - minY
        sideLength = minOf(rect.width / width, rect.height / height)
        offset = vec2(
            rect.x - minX * sideLength + (rect.width - sideLength * width) / 2,
            rect.y - minY * sideLength + (rect.height - sideLength * height) / 2
        )
    }

    override fun fill(cell: TriangleCell) {
        shapeRenderer.use(ShapeRenderer.ShapeType.Filled) {
            fillInternal(cell)
        }
    }

    override fun fill() {
        shapeRenderer.use(ShapeRenderer.ShapeType.Filled) {
            grid.cells().forEach { fillInternal(it as TriangleCell) }
        }
    }

    override fun drawUnlinkedBorders() {
        shapeRenderer.use(ShapeRenderer.ShapeType.Line) {
            grid.cells().forEach { drawUnlinkedBordersInternal(it as TriangleCell) }
        }
    }

    override fun drawAllBorders() {
        shapeRenderer.use(ShapeRenderer.ShapeType.Line) {
            grid.cells().forEach { drawAllBordersInternal(it as TriangleCell) }
        }
    }

    override fun drawAllBorders(cell: TriangleCell) {
        shapeRenderer.use(ShapeRenderer.ShapeType.Line) {
            drawAllBordersInternal(cell)
        }
    }

    override fun drawUnlinkedBorders(cell: TriangleCell) {
        shapeRenderer.use(ShapeRenderer.ShapeType.Line) {
            drawUnlinkedBordersInternal(cell)
        }
    }

    private fun drawAllBordersInternal(cell: TriangleCell) {
        if (cell.coords.isPointingUp) {
            shapeRenderer.triangle(north(cell), southEast(cell), southWest(cell))
        } else {
            shapeRenderer.triangle(northEast(cell), south(cell), northWest(cell))
        }
    }

    private fun drawUnlinkedBordersInternal(cell: TriangleCell) {
        if (cell.coords.isPointingUp) {
            if (!cell.isLinked(cell.northEast)) {
                shapeRenderer.line(north(cell), southEast(cell))
            }
            if (!cell.isLinked(cell.northWest)) {
                shapeRenderer.line(north(cell), southWest(cell))
            }
            if (!cell.isLinked(cell.south)) {
                shapeRenderer.line(southEast(cell), southWest(cell))
            }
        } else {
            if (!cell.isLinked(cell.north)) {
                shapeRenderer.line(northEast(cell), northWest(cell))
            }
            if (!cell.isLinked(cell.southEast)) {
                shapeRenderer.line(northEast(cell), south(cell))
            }
            if (!cell.isLinked(cell.southWest)) {
                shapeRenderer.line(northWest(cell), south(cell))
            }
        }
    }

    private fun fillInternal(cell: TriangleCell) {
        if (cell.coords.isPointingUp) {
            shapeRenderer.triangle(north(cell), southEast(cell), southWest(cell))
        } else {
            shapeRenderer.triangle(northEast(cell), south(cell), northWest(cell))
        }
    }

    private fun center(cell: TriangleCell): Vector2 = Companion.center(cell, sideLength) + offset
    private fun north(cell: TriangleCell) = Companion.north(cell, sideLength) + offset
    private fun south(cell: TriangleCell) = Companion.south(cell, sideLength) + offset
    private fun northEast(cell: TriangleCell) = Companion.northEast(cell, sideLength) + offset
    private fun southEast(cell: TriangleCell) = Companion.southEast(cell, sideLength) + offset
    private fun northWest(cell: TriangleCell) = Companion.northWest(cell, sideLength) + offset
    private fun southWest(cell: TriangleCell) = Companion.southWest(cell, sideLength) + offset

    companion object {
        private fun center(cell: TriangleCell, sideLength: Float = 1f): Vector2 {
            val (a, b, c) = cell.coords
            val sqrt3 = sqrt(3f)
            return Vector2(0.5f * a - 0.5f * c, -sqrt3 / 6 * a + sqrt3 / 3 * b - sqrt3 / 6 * c) * sideLength
        }

        private fun north(cell: TriangleCell, sideLength: Float = 1f) =
            center(cell, sideLength) + Vector2(0f, 1f / sqrt(3f)) * sideLength

        private fun south(cell: TriangleCell, sideLength: Float = 1f) =
            center(cell, sideLength) + Vector2(0f, -1f / sqrt(3f)) * sideLength

        private fun northEast(cell: TriangleCell, sideLength: Float = 1f) =
            center(cell, sideLength) + Vector2(1f / 2f, 1f / (2f * sqrt(3f))) * sideLength

        private fun southEast(cell: TriangleCell, sideLength: Float = 1f) =
            center(cell, sideLength) + Vector2(1f / 2f, -1f / (2f * sqrt(3f))) * sideLength

        private fun southWest(cell: TriangleCell, sideLength: Float = 1f) =
            center(cell, sideLength) + Vector2(-1f / 2f, -1f / (2f * sqrt(3f))) * sideLength

        private fun northWest(cell: TriangleCell, sideLength: Float = 1f) =
            center(cell, sideLength) + Vector2(-1f / 2f, 1f / (2f * sqrt(3f))) * sideLength
    }
}