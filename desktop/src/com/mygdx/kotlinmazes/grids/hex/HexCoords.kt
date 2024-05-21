package com.mygdx.kotlinmazes.grids.hex

import kotlin.math.max
import kotlin.math.min

class HexCoords(val q: Int, val r: Int) {
    fun add(other: HexCoords): HexCoords {
        return HexCoords(q + other.q, r + other.r)
    }

    companion object {
        val NorthEast = HexCoords(1, -1)
        val East = HexCoords(1, 0)
        val SouthEast = HexCoords(0, 1)
        val SouthWest = HexCoords(-1, 1)
        val West = HexCoords(-1, 0)
        val NorthWest = HexCoords(0, -1)
        val NEIGHBOR_INDICES = arrayOf(NorthEast, East, SouthEast, SouthWest, West, NorthWest)
    }

    operator fun component1(): Int = q
    operator fun component2(): Int = r

    operator fun plus(other: HexCoords): HexCoords = HexCoords(q + other.q, r + other.r)

    fun getCellsAround(n: Int): List<HexCoords> {
        val result = mutableListOf<HexCoords>()
        for (deltaQ in -n..n) {
            val low = max(-n, -deltaQ - n)
            val high = min(n, -deltaQ + n)
            for (deltaR in low..high) {
                result.add(this + HexCoords(deltaQ, deltaR))
            }
        }
        return result
    }

    override fun hashCode(): Int = Pair(q, r).hashCode()

    override fun equals(other: Any?): Boolean {
        if (other == null || other !is HexCoords) return false
        return q == other.q && r == other.r
    }

    fun getNeighbors(): List<HexCoords> {
        return NEIGHBOR_INDICES.map { it + this }
    }
}