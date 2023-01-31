package com.mygdx.kotlinmazes.grids

abstract class Grid {
    abstract fun randomCell(): Cell

    abstract val size: Int
}