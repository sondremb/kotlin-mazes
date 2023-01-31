package com.mygdx.kotlinmazes

class PolarCell(val row: Int, val column: Int) : Cell() {
    var cw: PolarCell? = null
    var ccw: PolarCell? = null
    var inwards: PolarCell? = null
    var outward: MutableList<PolarCell> = mutableListOf()

    override fun neighbors(): List<PolarCell> {
        return listOfNotNull(cw, ccw, inwards).plus(outward)
    }
}