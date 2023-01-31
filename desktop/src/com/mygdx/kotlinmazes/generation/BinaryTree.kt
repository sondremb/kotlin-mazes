package com.mygdx.kotlinmazes.generation

import com.mygdx.kotlinmazes.SquareGrid

fun binaryTree(grid: SquareGrid) {
    grid.cells.forEach {
        val neighbors = listOfNotNull(it.north, it.east)
        if (neighbors.isNotEmpty()) {
            it.link(neighbors.random())
        }
    }
}
