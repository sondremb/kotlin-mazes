package com.mygdx.kotlinmazes.grids

abstract class Cell {
    val links = mutableSetOf<Cell>()

    abstract fun neighbors(): List<Cell>

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

    fun resetLinks() = links.clear()

    fun isLinked(other: Cell?): Boolean {
        return links.contains(other)
    }
}