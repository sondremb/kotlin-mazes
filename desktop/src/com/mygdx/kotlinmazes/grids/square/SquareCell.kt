package com.mygdx.kotlinmazes.grids.square

import com.mygdx.kotlinmazes.grids.Cell

class SquareCell(val row: Int, val column: Int) : Cell() {
    var north: SquareCell? = null
    var east: SquareCell? = null
    var south: SquareCell? = null
    var west: SquareCell? = null

    override fun neighbors(): List<SquareCell> {
        return listOfNotNull(north, east, south, west)
    }

    override fun toString(): String {
        return "Cell($row, $column)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is SquareCell) return false
        return row == other.row && column == other.column
    }
}