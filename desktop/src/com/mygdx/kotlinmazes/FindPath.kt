package com.mygdx.kotlinmazes

import com.mygdx.kotlinmazes.grids.Cell

fun findPath(from: Cell, to: Cell): List<Cell> {
    val parent = HashMap<Cell, Cell>()
    var frontier = mutableListOf(from)
    while (frontier.isNotEmpty()) {
        val newFrontier = mutableListOf<Cell>()
        frontier.forEach { cell ->
            cell.links.forEach { neighbor ->
                if (parent[neighbor] == null) {
                    parent[neighbor] = cell
                    newFrontier.add(neighbor)
                }
                if (neighbor == to) {
                    val path = mutableListOf<Cell>()
                    var current = to
                    while (current != from) {
                        path.add(current)
                        current = parent[current]!!
                    }
                    path.add(from)
                    return path.reversed()
                }
            }
        }
        frontier = newFrontier
    }
    return emptyList()
}