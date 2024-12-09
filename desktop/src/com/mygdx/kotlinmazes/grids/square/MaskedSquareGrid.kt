package com.mygdx.kotlinmazes.grids.square

class MaskedSquareGrid(width: Int, height: Int) : SquareGrid(width, height) {
    private val mask: MutableSet<Pair<Int, Int>> = HashSet()

    fun isMasked(cell: SquareCell) = mask.contains(cell.row to cell.column)
    fun mask(cell: SquareCell) {
        mask.add(cell.row to cell.column)
        applyMask(cell)
    }

    fun unmask(cell: SquareCell) {
        mask.remove(cell.row to cell.column)
        applyMask(cell)
    }

    fun resetMask() {
        mask.clear()
    }

    override val size: Int
        get() = cells().size

    override fun cells(): List<SquareCell> {
        return super.cells().filter { !isMasked(it) }
    }

    fun allCells(): List<SquareCell> {
        return super.cells()
    }

    fun applyMask(cell: SquareCell) {
        val r = cell.row
        val c = cell.column
        val value = if (isMasked(cell)) null else cell
        get(r + 1, c)?.south = value
        get(r, c + 1)?.west = value
        get(r - 1, c)?.north = value
        get(r, c - 1)?.east = value
    }


    override fun randomCell(): SquareCell {
        return cells().random()
    }
}