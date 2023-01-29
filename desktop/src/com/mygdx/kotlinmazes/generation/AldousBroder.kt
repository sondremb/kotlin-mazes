package com.mygdx.kotlinmazes.generation

import com.mygdx.kotlinmazes.Grid
import com.mygdx.kotlinmazes.PolarGrid

fun aldousBroder(grid: PolarGrid) {
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