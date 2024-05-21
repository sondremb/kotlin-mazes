package com.mygdx.kotlinmazes.grids.hex

import com.mygdx.kotlinmazes.grids.Cell

class HexCell(val q: Int, val r: Int) : Cell() {
    val coords = HexCoords(q, r)
    var northeast: HexCell? = null
    var east: HexCell? = null
    var southeast: HexCell? = null
    var southwest: HexCell? = null
    var west: HexCell? = null
    var northwest: HexCell? = null

    override fun neighbors(): List<Cell> {
        return listOfNotNull(northeast, east, southeast, southwest, west, northwest)
    }
}
