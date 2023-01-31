package com.mygdx.kotlinmazes

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
}