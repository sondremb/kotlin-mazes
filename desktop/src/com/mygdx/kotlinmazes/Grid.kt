package com.mygdx.kotlinmazes

import kotlin.random.Random

class Grid(val rows: Int, val columns: Int) {
    private val grid: Array<Array<Cell>> = Array(rows) { row -> Array(columns) { column -> Cell(row, column) } }

    init {
        for (row in grid) {
            for (cell in row) {
                val r = cell.row
                val c = cell.column

                cell.north = get(r + 1, c)
                cell.east = get(r, c + 1)
                cell.south = get(r - 1, c)
                cell.west = get(r, c - 1)
            }
        }
    }

    val cells: List<Cell>
        get() {
            return grid.flatten()
        }

    fun get(row: Int, column: Int): Cell? {
        return grid.getOrNull(row)?.getOrNull(column)
    }

    fun randomCell(): Cell {
        return get(Random.nextInt(rows), Random.nextInt(columns))!!
    }
}