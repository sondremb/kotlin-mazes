package com.mygdx.kotlinmazes.generation

import com.mygdx.kotlinmazes.SquareCell
import com.mygdx.kotlinmazes.SquareGrid
import kotlin.random.Random

fun sideWinder(grid: SquareGrid) {
    grid.rows.forEach { row ->
        var run = mutableListOf<SquareCell>()
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