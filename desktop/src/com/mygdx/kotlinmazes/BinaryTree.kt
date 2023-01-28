package com.mygdx.kotlinmazes

import kotlin.random.Random

fun binaryTree(grid: Grid) {
    grid.cells.forEach {
        val neighbors = listOfNotNull(it.north, it.east)
        if (neighbors.isNotEmpty()) {
            val index = Random.nextInt(neighbors.size)
            val neighbor = neighbors[index]
            it.link(neighbor)
        }
    }
}
