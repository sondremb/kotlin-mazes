package com.mygdx.kotlinmazes.mazegeneration

import com.mygdx.kotlinmazes.grids.square.SquareGrid

// når denne kalles, kjøres algoritmen på hele gridet umiddelbart og lagrer labyrinten
fun binaryTree(grid: SquareGrid) {
    grid.cells().forEach {
        val neighbors = listOfNotNull(it.north, it.east)
        if (neighbors.isNotEmpty()) {
            it.link(neighbors.random())
        }
    }
}


// klasse-basert implementasjon av BinaryTree-algoritmen, til bruk i animasjon
// instansier klassen, og kalle .next() for å gå til neste steg, helt til hasNext() returnerer false
class BinaryTree(val grid: SquareGrid) : Iterator<Unit> {
    private val cellIterator = grid.cells().iterator()

    override fun hasNext(): Boolean {
        return cellIterator.hasNext()
    }

    override fun next() {
        val cell = cellIterator.next()
        val neighbors = listOfNotNull(cell.north, cell.east)
        if (neighbors.isNotEmpty()) {
            cell.link(neighbors.random())
        }
    }

    companion object {
        // en statisk metode - hvis man ikke hadde hatt binaryTree()-funksjonen,
        // kunne man brukt BinaryTree.on(grid) i stedet
        fun on(grid: SquareGrid) {
            val iterator = BinaryTree(grid)
            while (iterator.hasNext()) {
                iterator.next()
            }
        }
    }
}