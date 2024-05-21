package com.mygdx.kotlinmazes.mazegeneration

import com.mygdx.kotlinmazes.grids.Cell
import com.mygdx.kotlinmazes.grids.Grid

fun huntAndKill(grid: Grid) {
    var current = grid.randomCell()
    val visited = mutableSetOf(current)

    fun isVisited(cell: Cell) = visited.contains(cell)
    fun isNotVisited(cell: Cell) = !isVisited(cell)

    while (visited.size < grid.size) {
        val candidates = current.neighbors().filter(::isNotVisited)
        if (candidates.isEmpty()) {
            current = visited.flatMap { it.neighbors() }.filter(::isNotVisited).random()
            val neighbor = current.neighbors().first(::isVisited)
            current.link(neighbor)
            visited.add(current)
            continue
        }
        val next = candidates.random()
        next.link(current)
        visited.add(next)
        current = next
    }
}