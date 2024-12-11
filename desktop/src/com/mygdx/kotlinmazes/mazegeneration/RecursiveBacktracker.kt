package com.mygdx.kotlinmazes.mazegeneration

import com.mygdx.kotlinmazes.grids.Cell
import com.mygdx.kotlinmazes.grids.Grid

class RecursiveBacktracker(grid: Grid) : Iterator<Unit> {
    private val stack = mutableListOf<Cell>()

    init {
        stack.add(grid.randomCell())
    }

    override fun hasNext(): Boolean {
        return stack.isNotEmpty()
    }

    override fun next() {
        val current = stack.last()
        val candidates = current.neighbors().filter { it.links.isEmpty() }
        if (candidates.isNotEmpty()) {
            val next = candidates.random()
            current.link(next)
            stack.add(next)
        } else {
            stack.removeLast()
        }
    }

    companion object {
        fun on(grid: Grid) {
            val algo = RecursiveBacktracker(grid)
            while (algo.hasNext()) {
                algo.next()
            }
        }
    }
}