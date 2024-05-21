package com.mygdx.kotlinmazes.grids.hex

import com.mygdx.kotlinmazes.grids.Cell
import com.mygdx.kotlinmazes.grids.Grid

class HexGrid(val radius: Int) : Grid() {
    private val grid = HashMap<HexCoords, HexCell>()

    init {
        val center = HexCell(0, 0)
        grid[HexCoords(0, 0)] = center
        for (coordinate in center.coords.getCellsAround(radius)) {
            grid[coordinate] = HexCell(coordinate.q, coordinate.r)
        }

        grid.values.forEach { cell ->
            cell.northeast = grid[cell.coords + HexCoords.NorthEast]
            cell.east = grid[cell.coords + HexCoords.East]
            cell.southeast = grid[cell.coords + HexCoords.SouthEast]
            cell.southwest = grid[cell.coords + HexCoords.SouthWest]
            cell.west = grid[cell.coords + HexCoords.West]
            cell.northwest = grid[cell.coords + HexCoords.NorthWest]
        }
    }

    override fun cells() = grid.values.toList()
    override fun randomCell(): Cell = grid.values.random()

    override val size: Int get() = grid.size

    operator fun get(coords: HexCoords): HexCell? = grid[coords]

}