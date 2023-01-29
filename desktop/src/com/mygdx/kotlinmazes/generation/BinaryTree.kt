package com.mygdx.kotlinmazes.generation

import com.mygdx.kotlinmazes.Grid

fun binaryTree(grid: Grid) {
    grid.cells.forEach {
        val neighbors = listOfNotNull(it.north, it.east)
        if (neighbors.isNotEmpty()) {
            it.link(neighbors.random())
        }
    }
}
