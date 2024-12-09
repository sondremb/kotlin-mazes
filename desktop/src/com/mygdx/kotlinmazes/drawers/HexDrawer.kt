package com.mygdx.kotlinmazes.drawers

import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.math.Vector2
import com.mygdx.kotlinmazes.grids.hex.HexCell
import com.mygdx.kotlinmazes.grids.hex.HexCoords
import ktx.graphics.triangle
import ktx.graphics.use
import ktx.math.plus
import ktx.math.times
import ktx.math.vec2
import kotlin.math.sqrt

class HexDrawer(shapeRenderer: ShapeRenderer, val sidelength: Float, val offset: Vector2) :
    GridDrawer<HexCell>(shapeRenderer) {

    override fun fill(cell: HexCell) {
        val coord = cell.coords
        shapeRenderer.use(ShapeRenderer.ShapeType.Filled) {
            it.triangle(north(coord), northEast(coord), center(coord))
            it.triangle(northEast(coord), southEast(coord), center(coord))
            it.triangle(southEast(coord), south(coord), center(coord))
            it.triangle(south(coord), southWest(coord), center(coord))
            it.triangle(southWest(coord), northWest(coord), center(coord))
            it.triangle(northWest(coord), north(coord), center(coord))
        }
    }

    override fun drawUnlinkedBorders(cell: HexCell) {
        val coords = cell.coords
        shapeRenderer.use(ShapeRenderer.ShapeType.Line) {
            if (!cell.isLinked(cell.east)) {
                it.line(northEast(coords), southEast(coords))
            }
            if (!cell.isLinked(cell.southeast)) {
                it.line(southEast(coords), south(coords))
            }
            if (!cell.isLinked(cell.northeast)) {
                it.line(northEast(coords), north(coords))
            }
            if (cell.northwest == null) {
                it.line(northWest(coords), north(coords))
            }
            if (cell.southwest == null) {
                it.line(southWest(coords), south(coords))
            }
            if (cell.west == null) {
                it.line(northWest(coords), southWest(coords))
            }
        }
    }

    override fun drawAllBorders(cell: HexCell) {
        val coords = cell.coords
        shapeRenderer.use(ShapeRenderer.ShapeType.Line) {
            it.line(northEast(coords), southEast(coords))
            it.line(southEast(coords), south(coords))
            it.line(south(coords), southWest(coords))
            it.line(southWest(coords), northWest(coords))
            it.line(northWest(coords), north(coords))
            it.line(north(coords), northEast(coords))
        }
    }

    private fun center(coord: HexCoords): Vector2 {
        val (q, r) = coord
        val x = sidelength * (sqrt(3f) * q + r * sqrt(3f) / 2f)
        val y = sidelength * (3f / 2f * r)
        return vec2(x, y) + offset
    }

    private fun north(coord: HexCoords) = center(coord) + vec2(0f, -1f) * sidelength
    private fun northEast(coord: HexCoords) = center(coord) + vec2(sqrt(3f) / 2f, -0.5f) * sidelength
    private fun southEast(coord: HexCoords) = center(coord) + vec2(sqrt(3f) / 2f, 0.5f) * sidelength
    private fun south(coord: HexCoords) = center(coord) + vec2(0f, 1f) * sidelength
    private fun southWest(coord: HexCoords) = center(coord) + vec2(-sqrt(3f) / 2f, 0.5f) * sidelength
    private fun northWest(coord: HexCoords) = center(coord) + vec2(-sqrt(3f) / 2f, -0.5f) * sidelength
}