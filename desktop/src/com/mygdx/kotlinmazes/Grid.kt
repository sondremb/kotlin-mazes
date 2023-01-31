package com.mygdx.kotlinmazes

abstract class Grid {
    abstract fun randomCell(): Cell

    abstract val size: Int
}