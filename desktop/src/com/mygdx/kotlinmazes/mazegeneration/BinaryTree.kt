package com.mygdx.kotlinmazes.mazegeneration

import com.mygdx.kotlinmazes.grids.square.SquareGrid

fun binaryTree(grid: SquareGrid) {
    grid.cells().forEach {
        val neighbors = listOfNotNull(it.north, it.east)
        if (neighbors.isNotEmpty()) {
            it.link(neighbors.random())
        }
    }
}

class BinaryTree(val grid: SquareGrid) : Iterator<Unit> {
    private val cellIterator = grid.cells().iterator()

    override fun hasNext(): Boolean {
        return cellIterator.hasNext()
    }

    override fun next() {
        val cell = cellIterator.next()
        val neighbors = listOfNotNull(cell.north, cell.east)
        if (neighbors.isNotEmpty()) {
            cell.link(neighbors.random())
        }
    }
}