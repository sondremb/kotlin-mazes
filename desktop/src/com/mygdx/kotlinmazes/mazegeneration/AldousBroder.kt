package com.mygdx.kotlinmazes.mazegeneration

import com.mygdx.kotlinmazes.grids.Grid

fun aldousBroder(grid: Grid) {
    var cell = grid.randomCell()
    var remaining = grid.size - 1

    while (remaining > 0) {
        val neighbor = cell.neighbors().random()
        if (neighbor.links.isEmpty()) {
            cell.link(neighbor)
            remaining -= 1
        }

        cell = neighbor
    }
}