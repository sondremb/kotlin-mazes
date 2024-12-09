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
            val checkedCell = cell
            neighbor = cell.neighbors().random()
            var addedCell: Cell? = null
            if (neighbor.links.isEmpty()) {
                cell.link(neighbor)
                addedCell = neighbor

                unvisited--
                cell = neighbor
                break
            }
            cell = neighbor
        }

    }
}
