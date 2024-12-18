package com.mygdx.kotlinmazes.mazegeneration

import com.mygdx.kotlinmazes.grids.Grid

class Prims(grid: Grid) : Iterator<Unit> {
    private val active = mutableSetOf(grid.randomCell())
    override fun hasNext(): Boolean = active.isNotEmpty()

    override fun next() {
        val current = active.random()
        val candidates = current.neighbors().filter { it.links.isEmpty() }
        if (candidates.isNotEmpty()) {
            val neighbor = candidates.random()
            current.link(neighbor)
            active.add(neighbor)
        } else {
            active.remove(current)
        }
    }
}