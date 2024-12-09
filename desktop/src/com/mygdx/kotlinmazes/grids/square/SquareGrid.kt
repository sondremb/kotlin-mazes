package com.mygdx.kotlinmazes.grids.square

import com.mygdx.kotlinmazes.grids.Grid

open class SquareGrid(val height: Int, val width: Int) : Grid() {
    val grid: List<List<SquareCell>> = List(height) { row ->
        List(width) { column -> SquareCell(row, column) }
    }

    init {
        grid.forEach { row ->
            row.forEach { cell ->
                val r = cell.row
                val c = cell.column

                cell.north = get(r + 1, c)
                cell.east = get(r, c + 1)
                cell.south = get(r - 1, c)
                cell.west = get(r, c - 1)
            }
        }
    }

    fun get(row: Int, column: Int): SquareCell? {
        return grid.getOrNull(row)?.getOrNull(column)
    }

    override fun cells() = grid.flatten()

    override fun randomCell() = grid.random().random()

    override val size get() = height * width
}