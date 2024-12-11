package com.mygdx.kotlinmazes.grids.hex

import com.mygdx.kotlinmazes.grids.Cell
import com.mygdx.kotlinmazes.grids.Grid

class HexGrid() : Grid() {
    private val grid = HashMap<HexCoords, HexCell>()

    constructor(radius: Int) : this() {
        val center = HexCell(0, 0)
        grid[HexCoords(0, 0)] = center
        center.coords.getCellsAround(radius).forEach(::addCell)
        setupNeighbors()
    }

    constructor(rows: Int, columns: Int) : this() {
        for (r in 0 until rows) {
            for (dq in 0 until columns) {
                val q = -r / 2 + dq
                addCell(HexCoords(q, r))
            }

        }
        setupNeighbors()
    }

    fun addCell(coords: HexCoords) {
        grid[coords] = HexCell(coords.q, coords.r)
    }

    private fun setupNeighbors() {
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