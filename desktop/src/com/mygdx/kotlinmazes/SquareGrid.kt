package com.mygdx.kotlinmazes

class SquareGrid(val height: Int, val width: Int) : Grid() {
    val grid: Array<Array<SquareCell>> = Array(height) { row -> Array(width) { column -> SquareCell(row, column) } }

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

    val cells: List<SquareCell>
        get() {
            return grid.flatten()
        }

    fun get(row: Int, column: Int): SquareCell? {
        return grid.getOrNull(row)?.getOrNull(column)
    }

    override fun randomCell(): SquareCell {
        return grid.random().random()
    }

    val rows get() = grid
    override val size get() = height * width
}