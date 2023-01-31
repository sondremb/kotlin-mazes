package com.mygdx.kotlinmazes

import kotlin.math.PI
import kotlin.math.roundToInt

class PolarGrid(val rows: Int) : Grid() {
    private val grid: MutableList<MutableList<PolarCell>> = MutableList(rows) { mutableListOf() }

    init {
        grid[0].add(PolarCell(0, 0))
        val rowHeight = 1f / rows
        for (row in 1 until rows) {
            val radius = row.toFloat() / rows
            val circumference = 2 * PI * radius
            val previousCount = grid[row - 1].size
            val estimatedWidth = circumference / previousCount
            val ratio = (estimatedWidth / rowHeight).roundToInt()
            val cells = previousCount * ratio
            grid[row] = MutableList(cells) { col -> PolarCell(row, col) }
        }

        cells.forEach { cell ->
            val row = cell.row
            val column = cell.column
            if (row > 0) {
                cell.cw = get(row, column + 1)
                cell.ccw = get(row, column - 1)
                val ratio = grid[row].size / grid[row - 1].size
                val parent = get(row - 1, (column / ratio))
                if (parent != null) {
                    cell.inwards = parent
                    parent.outward.add(cell)
                }
            }
        }
    }

    override fun randomCell(): PolarCell {
        // gjør ikke grid.random().random(), fordi det hadde ikke blitt uniform fordeling:
        // alle radene er like sannsynlig å bli valgt, men de innerste radene har mange færre elementer,
        // så indre celler hadde hatt mye større sannsynlighet for å bli valgt
        return cells.random()
    }

    val cells: List<PolarCell> get() = grid.flatten()

    operator fun get(row: Int): List<PolarCell> {
        return grid[row]
    }

    operator fun get(row: Int, column: Int): PolarCell? {
        val cellRow = grid.getOrNull(row) ?: return null
        return cellRow.getOrNull(column.mod(cellRow.size))
    }

    override val size: Int get() = grid.sumOf { it.size }
}