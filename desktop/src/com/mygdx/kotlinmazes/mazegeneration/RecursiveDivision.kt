package com.mygdx.kotlinmazes.mazegeneration

import com.mygdx.kotlinmazes.grids.square.SquareGrid

class RecursiveDivision(private val grid: SquareGrid) : Iterator<Unit> {
    private val stack = mutableListOf(Box(0, 0, grid.width, grid.height))

    init {
        grid.cells()
            .forEach { cell ->
                cell.neighbors()
                    .forEach { neighbor -> cell.link(neighbor) }
            }
    }

    override fun hasNext(): Boolean = stack.isNotEmpty()

    override fun next() {
        var box: Box
        do {
            box = stack.removeLast()
        } while (stack.isNotEmpty() && (box.height <= 1 || box.width <= 1))
        if (box.width <= 1 || box.height <= 1) return
        if (box.width > box.height) {
            divideVertically(box)
        } else {
            divideHorizontally(box)
        }
    }

    private fun divideVertically(box: Box) {
        val divideEastOf = (0 until box.width - 1).random()
        val openingAtY = (0 until box.height).random()
        for (y in 0 until box.height) {
            if (y != openingAtY) {
                val cell = grid.get(box.y + y, box.x + divideEastOf)!!
                cell.unlink(cell.east!!)
            }
        }
        stack.add(Box(box.x, box.y, divideEastOf + 1, box.height))
        stack.add(Box(box.x + divideEastOf + 1, box.y, box.width - divideEastOf - 1, box.height))
    }

    private fun divideHorizontally(box: Box) {
        val divideNorthOf = (0 until box.height - 1).random()
        val openingAtX = (0 until box.width).random()
        for (x in 0 until box.width) {
            if (x != openingAtX) {
                val cell = grid.get(box.y + divideNorthOf, box.x + x)!!
                cell.unlink(cell.north!!)
            }
        }
        stack.add(Box(box.x, box.y, box.width, divideNorthOf + 1))
        stack.add(Box(box.x, box.y + divideNorthOf + 1, box.width, box.height - divideNorthOf - 1))
    }

    private class Box(val x: Int, val y: Int, val width: Int, val height: Int)

    companion object {
        fun on(grid: SquareGrid) {
            val iterator = RecursiveDivision(grid)
            while (iterator.hasNext()) {
                iterator.next()
            }
        }
    }
}