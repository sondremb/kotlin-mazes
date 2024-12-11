package com.mygdx.kotlinmazes.drawers

import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.math.Vector2
import com.mygdx.kotlinmazes.EngineConfig
import com.mygdx.kotlinmazes.grids.hex.HexCell
import com.mygdx.kotlinmazes.grids.hex.HexCoords
import com.mygdx.kotlinmazes.grids.hex.HexGrid
import ktx.graphics.triangle
import ktx.graphics.use
import ktx.math.plus
import ktx.math.times
import ktx.math.vec2
import kotlin.math.sqrt

class HexDrawer(shapeRenderer: ShapeRenderer, private val grid: HexGrid, boundingRect: Rectangle? = null) :
    GridDrawer<HexCell>(shapeRenderer) {

    private val sidelength: Float
    private val offset: Vector2

    init {
        val rect = boundingRect ?: Rectangle(0F, 0F, EngineConfig.VIEWPORT_WIDTH, EngineConfig.VIEWPORT_HEIGHT)
        val minX = grid.cells().minOf { Companion.northWest(it.coords).x }
        val maxX = grid.cells().maxOf { Companion.southEast(it.coords).x }
        val minY = grid.cells().minOf { Companion.north(it.coords).y }
        val maxY = grid.cells().maxOf { Companion.south(it.coords).y }
        val width = maxX - minX
        val height = maxY - minY
        sidelength = minOf(rect.width / width, rect.height / height)
        offset = vec2(
            rect.x - minX * sidelength + (rect.width - sidelength * width) / 2,
            rect.y - minY * sidelength + (rect.height - sidelength * height) / 2
        )
    }

    override fun fill(cell: HexCell) {
        shapeRenderer.use(ShapeRenderer.ShapeType.Filled) {
            fillInternal(cell)
        }
    }

    private fun fillInternal(cell: HexCell) {
        val coord = cell.coords
        shapeRenderer.triangle(north(coord), northEast(coord), center(coord))
        shapeRenderer.triangle(northEast(coord), southEast(coord), center(coord))
        shapeRenderer.triangle(southEast(coord), south(coord), center(coord))
        shapeRenderer.triangle(south(coord), southWest(coord), center(coord))
        shapeRenderer.triangle(southWest(coord), northWest(coord), center(coord))
        shapeRenderer.triangle(northWest(coord), north(coord), center(coord))
    }

    override fun drawUnlinkedBorders(cell: HexCell) {
        shapeRenderer.use(ShapeRenderer.ShapeType.Line) {
            drawUnlinkedBordersInternal(cell)
        }
    }

    private fun drawUnlinkedBordersInternal(cell: HexCell) {
        val coords = cell.coords
        if (!cell.isLinked(cell.east)) {
            shapeRenderer.line(northEast(coords), southEast(coords))
        }
        if (!cell.isLinked(cell.southeast)) {
            shapeRenderer.line(southEast(coords), south(coords))
        }
        if (!cell.isLinked(cell.northeast)) {
            shapeRenderer.line(northEast(coords), north(coords))
        }
        if (cell.northwest == null) {
            shapeRenderer.line(northWest(coords), north(coords))
        }
        if (cell.southwest == null) {
            shapeRenderer.line(southWest(coords), south(coords))
        }
        if (cell.west == null) {
            shapeRenderer.line(northWest(coords), southWest(coords))
        }
    }

    override fun drawAllBorders(cell: HexCell) {
        shapeRenderer.use(ShapeRenderer.ShapeType.Line) {
            drawAllBordersInternal(cell)
        }
    }

    private fun drawAllBordersInternal(cell: HexCell) {
        val coords = cell.coords
        shapeRenderer.line(northEast(coords), southEast(coords))
        shapeRenderer.line(southEast(coords), south(coords))
        shapeRenderer.line(south(coords), southWest(coords))
        shapeRenderer.line(southWest(coords), northWest(coords))
        shapeRenderer.line(northWest(coords), north(coords))
        shapeRenderer.line(north(coords), northEast(coords))
    }


    override fun fill() {
        shapeRenderer.use(ShapeRenderer.ShapeType.Filled) {
            grid.cells().forEach { fillInternal(it) }
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

    private fun center(coord: HexCoords) = Companion.center(coord, sidelength) + offset
    private fun north(coord: HexCoords) = Companion.north(coord, sidelength) + offset
    private fun northEast(coord: HexCoords) = Companion.northEast(coord, sidelength) + offset
    private fun southEast(coord: HexCoords) = Companion.southEast(coord, sidelength) + offset
    private fun south(coord: HexCoords) = Companion.south(coord, sidelength) + offset
    private fun southWest(coord: HexCoords) = Companion.southWest(coord, sidelength) + offset
    private fun northWest(coord: HexCoords) = Companion.northWest(coord, sidelength) + offset

    companion object {
        fun center(coord: HexCoords, sidelength: Float = 1f): Vector2 {
            val (q, r) = coord
            val x = sidelength * (sqrt(3f) * q + r * sqrt(3f) / 2f)
            val y = sidelength * (3f / 2f * r)
            return vec2(x, y)
        }

        fun north(coord: HexCoords, sidelength: Float = 1f) = center(coord, sidelength) + vec2(0f, -1f) * sidelength
        fun northEast(coord: HexCoords, sidelength: Float = 1f) =
            center(coord, sidelength) + vec2(sqrt(3f) / 2f, -0.5f) * sidelength

        fun southEast(coord: HexCoords, sidelength: Float = 1f) =
            center(coord, sidelength) + vec2(sqrt(3f) / 2f, 0.5f) * sidelength

        fun south(coord: HexCoords, sidelength: Float = 1f) = center(coord, sidelength) + vec2(0f, 1f) * sidelength
        fun southWest(coord: HexCoords, sidelength: Float = 1f) =
            center(coord, sidelength) + vec2(-sqrt(3f) / 2f, 0.5f) * sidelength

        fun northWest(coord: HexCoords, sidelength: Float = 1f) =
            center(coord, sidelength) + vec2(-sqrt(3f) / 2f, -0.5f) * sidelength
    }
}