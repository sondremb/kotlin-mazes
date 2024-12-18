package com.mygdx.kotlinmazes.grids.triangle

import com.mygdx.kotlinmazes.grids.Cell
import com.mygdx.kotlinmazes.grids.Grid

class TriangleGrid(radius: Int) : Grid() {
    private val cells = mutableMapOf<TriCoords, TriangleCell>()

    init {
        val start = TriangleCell(0, 0, 0)
        cells[start.coords] = start

        var stack = mutableListOf(start)
        for (i in 1..radius) {
            val newCells = mutableListOf<TriangleCell>()
            for (cell in stack) {
                for (neighbor in cell.coords.neighbors()) {
                    if (!cells.containsKey(neighbor)) {
                        val newCell = TriangleCell(neighbor.a, neighbor.b, neighbor.c)
                        newCells.add(newCell)
                    }
                }
            }
            newCells.forEach { cells[it.coords] = it }
            stack = newCells
        }
        for (cell in cells.values) {
            if (cell.coords.isPointingUp) {
                cell.south = cells[cell.coords + TriCoords.South]
                cell.northEast = cells[cell.coords + TriCoords.NorthEast]
                cell.northWest = cells[cell.coords + TriCoords.NorthWest]
            } else {
                cell.north = cells[cell.coords + TriCoords.North]
                cell.southEast = cells[cell.coords + TriCoords.SouthEast]
                cell.southWest = cells[cell.coords + TriCoords.SouthWest]
            }
        }
    }

    override fun randomCell(): Cell {
        return cells.values.random()
    }

    override val size: Int
        get() = cells.size

    override fun cells(): List<Cell> {
        return cells.values.toList()
    }
}