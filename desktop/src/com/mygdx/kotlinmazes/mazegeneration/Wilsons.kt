package com.mygdx.kotlinmazes.mazegeneration

import com.mygdx.kotlinmazes.grids.Cell
import com.mygdx.kotlinmazes.grids.Grid

class Wilsons(grid: Grid) {
    private val unvisited = grid.cells().toMutableSet()
    private var cell: Cell
    private var path: MutableList<Cell>

    init {
        unvisited.remove(unvisited.random())
        cell = unvisited.random()
        path = mutableListOf(cell)
    }

    fun isFinished() = unvisited.isEmpty()

    fun step(): State {

        if (unvisited.contains(cell)) {
            cell = cell.neighbors().random()
            val position = path.indexOf(cell)
            if (position >= 0) {
                path = path.subList(0, position + 1)
            } else {
                path.add(cell)
            }

            return State(unvisited, State.StateType.SEARCHING, path)
        }

        for (i in 0 until path.size - 1) {
            path[i].link(path[i + 1])
            unvisited.remove(path[i])
        }

        if (!unvisited.isEmpty()) {
            cell = unvisited.random()
            path = mutableListOf(cell)
        }

        return State(unvisited, State.StateType.LINKED, path)
    }

    class State(val unvisited: Set<Cell>, val state: StateType, val path: List<Cell>) {
        enum class StateType {
            SEARCHING, LINKED
        }
    }
}


fun wilsons(grid: Grid) {
    val unvisited = grid.cells().toMutableSet()
    val first = unvisited.random()
    unvisited.remove(first)

    while (unvisited.isNotEmpty()) {
        var cell = unvisited.random()
        var path = mutableListOf(cell)

        while (unvisited.contains(cell)) {
            cell = cell.neighbors().random()
            val position = path.indexOf(cell)
            if (position >= 0) {
                path = path.subList(0, position + 1)
            } else {
                path.add(cell)
            }
        }

        for (i in 0 until path.size - 1) {
            path[i].link(path[i + 1])
            unvisited.remove(path[i])
        }
    }
}