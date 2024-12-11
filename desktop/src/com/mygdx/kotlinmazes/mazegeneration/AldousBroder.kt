package com.mygdx.kotlinmazes.mazegeneration

import com.mygdx.kotlinmazes.grids.Cell
import com.mygdx.kotlinmazes.grids.Grid

class AldousBroder(val grid: Grid) : Iterator<Unit> {
    private var unvisited = grid.size - 1
    private var cell = grid.randomCell()

    override fun hasNext() = unvisited > 0

    override fun next() {
        var neighbor: Cell
        while (true) {
            neighbor = cell.neighbors().random()
            if (neighbor.links.isEmpty()) {
                cell.link(neighbor)

                unvisited--
                cell = neighbor
                break
            }
            cell = neighbor
        }

    }

    companion object {
        fun on(grid: Grid) {
            val iterator = AldousBroder(grid)
            while (iterator.hasNext()) {
                iterator.next()
            }
        }
    }
}
