package com.mygdx.kotlinmazes.grids.triangle

data class TriCoords(val a: Int, val b: Int, val c: Int) {
    fun neighbors(): List<TriCoords> {
        return if (isPointingUp) {
            listOf(NorthWest, NorthEast, South).map { this + it }
        } else {
            listOf(North, SouthEast, SouthWest).map { this + it }
        }
    }

    val isPointingUp = (a + b + c) % 2 == 0

    operator fun plus(other: TriCoords): TriCoords = TriCoords(a + other.a, b + other.b, c + other.c)
    operator fun minus(other: TriCoords): TriCoords = TriCoords(a - other.a, b - other.b, c - other.c)

    companion object {
        val North = TriCoords(0, 1, 0)
        val NorthEast = TriCoords(0, 0, -1)
        val SouthEast = TriCoords(1, 0, 0)
        val South = TriCoords(0, -1, 0)
        val SouthWest = TriCoords(0, 0, 1)
        val NorthWest = TriCoords(-1, 0, 0)
    }
}