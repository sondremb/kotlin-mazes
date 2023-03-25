package com.mygdx.kotlinmazes.mazegeneration

import com.mygdx.kotlinmazes.grids.square.SquareGrid

fun binaryTree(grid: SquareGrid) {
    grid.cells.forEach {
        val neighbors = listOfNotNull(it.north, it.east)
        if (neighbors.isNotEmpty()) {
            it.link(neighbors.random())
        }
    }
}
