package com.mygdx.kotlinmazes

import com.mygdx.kotlinmazes.grids.Cell
import kotlin.math.max

class Distance(start: Cell) {
    private val dists = HashMap<Cell, Int>()
    var max = -1

    init {
        set(start, 0)
        var frontier = mutableListOf(start)
        while (frontier.isNotEmpty()) {
            val newFrontier = mutableListOf<Cell>()
            frontier.forEach { cell ->
                cell.links.forEach { neighbor ->
                    if (get(neighbor) == -1) {
                        val distance = get(cell) + 1
                        set(neighbor, distance)
                        max = max(max, distance)
                        newFrontier.add(neighbor)
                    }
                }
            }
            frontier = newFrontier
        }
    }

    operator fun get(cell: Cell): Int {
        return dists[cell] ?: -1
    }

    operator fun set(cell: Cell, distance: Int) {
        dists[cell] = distance
    }
}