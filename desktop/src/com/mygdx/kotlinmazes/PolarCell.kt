package com.mygdx.kotlinmazes

class PolarCell(val row: Int, val column: Int) {
    val links = mutableSetOf<PolarCell>()
    var cw: PolarCell? = null
    var ccw: PolarCell? = null
    var inwards: PolarCell? = null
    var outward: MutableList<PolarCell> = mutableListOf()

    fun link(other: PolarCell, bidirectional: Boolean = true) {
        links.add(other)
        if (bidirectional) {
            other.link(this, false)
        }
    }

    fun unlink(other: PolarCell, bidirectional: Boolean = true) {
        links.remove(other)
        if (bidirectional) {
            other.unlink(this, false)
        }
    }

    fun isLinked(other: PolarCell?): Boolean {
        return links.contains(other)
    }

    fun neighbors(): List<PolarCell> {
        return listOfNotNull(cw, ccw, inwards).plus(outward)
    }
}