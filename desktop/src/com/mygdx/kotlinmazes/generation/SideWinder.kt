package com.mygdx.kotlinmazes.generation

import com.mygdx.kotlinmazes.Cell
import com.mygdx.kotlinmazes.Grid
import kotlin.random.Random

fun sideWinder(grid: Grid) {
    grid.rows.forEach { row ->
        var run = mutableListOf<Cell>()
        row.forEach { cell ->
            run.add(cell)
            val shouldCloseOut = cell.east == null || (cell.north != null && Random.nextInt(2) == 0)
            if (shouldCloseOut) {
                val member = run.random()
                if (member.north != null) {
                    member.link(member.north!!)
                }
                run.clear()

            } else {
                cell.link(cell.east!!)
            }
        }
    }
}