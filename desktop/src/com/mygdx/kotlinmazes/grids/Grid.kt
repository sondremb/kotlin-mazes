package com.mygdx.kotlinmazes.grids

abstract class Grid {
    abstract fun randomCell(): Cell

    abstract val size: Int

    abstract fun cells(): List<Cell>

    fun resetLinks() {
        cells().forEach { it.links.clear() }
    }
}