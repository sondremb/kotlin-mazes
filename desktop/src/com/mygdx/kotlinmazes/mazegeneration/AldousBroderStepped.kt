package com.mygdx.kotlinmazes.mazegeneration

import com.mygdx.kotlinmazes.grids.Cell
import com.mygdx.kotlinmazes.grids.Grid

class AldousBroderStepped(val grid: Grid) {
    private var unvisited = grid.size - 1
    private var cell = grid.randomCell()

    fun isFinished() = unvisited <= 0
    fun step(): AldousBroderState {
        val checkedCell = cell
        val neighbor = cell.neighbors().random()
        var addedCell: Cell? = null
        if (neighbor.links.isEmpty()) {
            cell.link(neighbor)
            addedCell = neighbor
            unvisited -= 1
        }

        cell = neighbor
        return AldousBroderState(checkedCell, addedCell)
    }
}

class AldousBroderState(val checkedCell: Cell, val addedCell: Cell?)