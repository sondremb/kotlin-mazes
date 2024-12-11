package com.mygdx.kotlinmazes.solving

import com.mygdx.kotlinmazes.grids.Cell
import com.mygdx.kotlinmazes.grids.Grid

class DepthFirstSearch(private val grid: Grid, private val from: Cell, private val to: Cell) {
    val frontier = mutableListOf<Cell>()
    val visited = mutableSetOf<Cell>()
    val cameFrom = mutableMapOf<Cell, Cell>()
    var backTrackingCell: Cell? = null
    val path = mutableListOf<Cell>()
    var state: State = State.Searching

    init {
        frontier.add(from)
        visited.add(from)
    }

    fun step() {
        if (backTrackingCell != null) {
            return backtrack()
        }

        val current = frontier.removeAt(frontier.size - 1)
        if (current == to) {
            backTrackingCell = to
            return backtrack()
        }

        state = State.Searching
        current.links.filter { !visited.contains(it) }.forEach {
            frontier.add(it)
            visited.add(it)
            cameFrom[it] = current
        }
    }

    fun isFinished() = backTrackingCell == from || frontier.isEmpty()

    private fun backtrack() {
        state = State.Backtracking
        path.add(backTrackingCell!!)
        backTrackingCell = cameFrom[backTrackingCell!!]
    }


    enum class State { Searching, Backtracking }
}

// gjør denne animert!
// hvis du lager den som en klasse, og stack og visited ikke er private, kan du tegne de med forskjellige farger i scenen din, for å visualisere algoritmen
fun depthFirstSearch(from: Cell, to: Cell): List<Cell> {
    val stack = mutableListOf<Cell>()
    val visited = mutableSetOf<Cell>()
    val cameFrom = mutableMapOf<Cell, Cell?>()

    stack.add(from)
    visited.add(from)

    while (stack.isNotEmpty()) {
        val current = stack.removeAt(stack.size - 1)

        if (current == to) {
            break
        }

        for (neighbor in current.links) {
            if (!visited.contains(neighbor)) {
                stack.add(neighbor)
                visited.add(neighbor)
                cameFrom[neighbor] = current
            }
        }
    }

    val path = mutableListOf<Cell>()
    var current = to
    while (current != from) {
        path.add(current)
        current = cameFrom[current]!!
    }
    path.add(from)
    path.reverse()

    return path
}