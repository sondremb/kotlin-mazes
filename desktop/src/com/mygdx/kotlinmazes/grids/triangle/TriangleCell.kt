package com.mygdx.kotlinmazes.grids.triangle

import com.mygdx.kotlinmazes.grids.Cell

class TriangleCell(val coords: TriCoords) : Cell() {
    var north: TriangleCell? = null
    var northEast: TriangleCell? = null
    var southEast: TriangleCell? = null
    var south: TriangleCell? = null
    var southWest: TriangleCell? = null
    var northWest: TriangleCell? = null

    constructor(a: Int, b: Int, c: Int) : this(TriCoords(a, b, c))

    var neighbors = mutableMapOf<TriCoords, TriangleCell>()
    override fun neighbors(): List<Cell> = listOfNotNull(north, northEast, southEast, south, southWest, northWest)
}