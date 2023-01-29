package com.mygdx.kotlinmazes

class Cell(val row: Int, val column: Int) {
    val links = mutableSetOf<Cell>()
    var north: Cell? = null
    var east: Cell? = null
    var south: Cell? = null
    var west: Cell? = null

    fun link(other: Cell, bidirectional: Boolean = true) {
        links.add(other)
        if (bidirectional) {
            other.link(this, false)
        }
    }

    fun unlink(other: Cell, bidirectional: Boolean = true) {
        links.remove(other)
        if (bidirectional) {
            other.unlink(this, false)
        }
    }

    fun isLinked(other: Cell?): Boolean {
        return links.contains(other)
    }

    fun neighbors(): List<Cell> {
        return listOfNotNull(north, east, south, west)
    }

    override fun toString(): String {
        return "Cell($row, $column)"
    }
}