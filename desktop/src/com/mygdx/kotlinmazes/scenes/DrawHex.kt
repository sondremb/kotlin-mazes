package com.mygdx.kotlinmazes.scenes

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.utils.ScreenUtils
import com.mygdx.kotlinmazes.drawers.HexDrawer
import com.mygdx.kotlinmazes.grids.hex.HexGrid

fun main() {
    // 1. Kjør en algoritme som genererer en labyrint
    // 2. Bruk Distance-klassen til å finne ut hvor langt hver celle er fra den midterste cellen (HexCoords(0, 0))
    // 3. Bruk en Gradient til å fargelegge cellene basert på avstanden - se scenen SquareGridDistance for inspirasjon
    // 4. Legg til at hvis R-tasten trykkes på, kjøres algoritmen på nytt
    DrawHexScene().play()
}

class DrawHexScene : Scene() {
    lateinit var grid: HexGrid
    lateinit var drawer: HexDrawer
    override fun init() {
        val grid = HexGrid(10)
        drawer = HexDrawer(shapeRenderer, grid)
    }

    override fun draw() {
        ScreenUtils.clear(Color.WHITE)
        drawer.drawUnlinkedBorders(Color.GRAY)
    }
}
